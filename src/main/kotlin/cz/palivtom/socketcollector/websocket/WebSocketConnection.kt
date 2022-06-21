package cz.palivtom.socketcollector.websocket

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.WebSocketConnectionManager
import javax.annotation.PostConstruct

@Component
class WebSocketConnection(
    private val webSocketClient: WebSocketClient,
    private val customTextWebSocketHandler: WebSocketHandler
) : WebSocketConnectionI {

    @Value("\${websocket-client-uri}")
    private lateinit var uriString: String

    private lateinit var manager: WebSocketConnectionManager

    @PostConstruct
    private fun webSocketConnectionManager() {
        manager = WebSocketConnectionManager(webSocketClient, customTextWebSocketHandler, uriString)
        start()
    }

    override fun reconnect() {
        manager.apply {
            stop()
            start()
        }
    }

    override fun start() {
        manager.start()
    }

    override fun stop() {
        manager.stop()
    }

    override fun isRunning(): Boolean {
        return manager.isRunning
    }
}