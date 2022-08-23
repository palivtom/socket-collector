package cz.palivtom.socketcollector.websocket

import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.ConnectionManagerSupport
import org.springframework.web.socket.client.WebSocketClient

class CustomWebSocketConnectorManager(
    private val client: WebSocketClient,
    private val webSocketHandler: WebSocketHandler,
    uriTemplate: String,
    vararg uriVariables: Any
) : ConnectionManagerSupport(uriTemplate, *uriVariables) {

    companion object {
        private const val ATTEMPT_SLEEP = 5000L
    }
    private var webSocketSession: WebSocketSession? = null;
    private val headers = WebSocketHttpHeaders()

    private var connectAttempt: Int = 1

    override fun openConnection() {
        if (!isConnected) {
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
    }

    override fun closeConnection() {
        webSocketSession?.close()
    }

    override fun isConnected(): Boolean {
        return webSocketSession?.isOpen == true
    }
}