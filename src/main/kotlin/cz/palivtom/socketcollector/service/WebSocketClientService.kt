package cz.palivtom.socketcollector.service

import cz.palivtom.socketcollector.service.interfaces.WebSocketClientServiceI
import cz.palivtom.socketcollector.handler.CustomTextWebSocketHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFuture
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

    @Value("\${websocket-client-uri}")
    private lateinit var uriString: String

    private lateinit var webSocketClient: WebSocketClient
    private lateinit var session: ListenableFuture<WebSocketSession>

    @PostConstruct
    private fun connect() {
        webSocketClient = StandardWebSocketClient()
        session = webSocketClient.doHandshake(customTextWebSocketHandler, WebSocketHttpHeaders(), URI.create(uriString))
    }

    override fun close() {
        session.get().close()
    }

    override fun isRunning(): Boolean {
        return !(session.isCancelled || session.isDone)
    }
}