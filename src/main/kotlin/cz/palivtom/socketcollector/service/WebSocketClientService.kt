package cz.palivtom.socketcollector.service

import cz.palivtom.socketcollector.service.interfaces.WebSocketClientServiceI
import cz.palivtom.socketcollector.handler.CustomTextWebSocketHandler
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import java.net.URI
import javax.annotation.PostConstruct

@Service
class WebSocketClientService(
        private val customTextWebSocketHandler: CustomTextWebSocketHandler
) : WebSocketClientServiceI {

    private val logger = KotlinLogging.logger {}

    @Value("\${websocket-client-uri}")
    private lateinit var uriString: String

    private lateinit var webSocketClient: WebSocketClient
    private lateinit var webSocketSession: WebSocketSession

    @PostConstruct
    private fun connect() {
        webSocketClient = StandardWebSocketClient()
        webSocketClient
            .doHandshake(customTextWebSocketHandler, WebSocketHttpHeaders(), URI.create(uriString))
            .addCallback(
                object : ListenableFutureCallback<WebSocketSession> {
                    override fun onSuccess(result: WebSocketSession?) {
                        webSocketSession = result!!
                    }

                    override fun onFailure(ex: Throwable) {
                        logger.error { "Socket connection failed with message: ${ex.message}" }
                    }
                }
            )
    }

    override fun close() {
        webSocketSession.close()
    }

    override fun isRunning(): Boolean {
        return webSocketSession.isOpen
    }
}