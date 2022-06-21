package cz.palivtom.socketcollector.websocket

import org.springframework.context.Lifecycle
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.ConnectionManagerSupport
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.handler.LoggingWebSocketHandlerDecorator

class CustomWebSocketConnectorManager(
    private val client: WebSocketClient,
    webSocketHandler: WebSocketHandler,
    uriTemplate: String,
    vararg uriVariables: Any
) : ConnectionManagerSupport(uriTemplate, *uriVariables) {

    companion object {
        private const val ATTEMPT_SLEEP = 5000L
    }
    private val webSocketHandler: WebSocketHandler
    private lateinit var webSocketSession: WebSocketSession
    private val headers = WebSocketHttpHeaders()

    private var connectAttempt: Int = 1

    init {
        this.webSocketHandler = decorateWebSocketHandler(webSocketHandler)
    }

    override fun startInternal() {
        if (client is Lifecycle && !(client as Lifecycle).isRunning) {
            (client as Lifecycle).start()
        }
        super.startInternal()
    }

    @Throws(Exception::class)
    override fun stopInternal() {
        if (client is Lifecycle && (client as Lifecycle).isRunning) {
            (client as Lifecycle).stop()
        }
        super.stopInternal()
    }

    override fun openConnection() {
        logger.info("Connecting [attempt: ${connectAttempt}] to WebSocket at $uri")

        val future: ListenableFuture<WebSocketSession> = client.doHandshake(webSocketHandler, headers, uri)
        future.addCallback(object : ListenableFutureCallback<WebSocketSession?> {
            override fun onSuccess(result: WebSocketSession?) {
                webSocketSession = result!!
                connectAttempt = 1
                logger.info("Successfully connected")
            }

            override fun onFailure(ex: Throwable) {
                logger.error("Failed to connect: ${ex.message}")
                connectAttempt++

                Thread.sleep(ATTEMPT_SLEEP)
                openConnection()
            }
        })
    }

    override fun closeConnection() {
        webSocketSession.close()
    }

    public override fun isConnected(): Boolean {
        return webSocketSession.isOpen
    }

    private fun decorateWebSocketHandler(handler: WebSocketHandler): WebSocketHandler {
        return LoggingWebSocketHandlerDecorator(handler)
    }
}