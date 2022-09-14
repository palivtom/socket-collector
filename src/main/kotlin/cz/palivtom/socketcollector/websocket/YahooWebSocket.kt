package cz.palivtom.socketcollector.websocket;

import com.fasterxml.jackson.databind.ObjectMapper
import cz.palivtom.socketcollector.events.PricingDataAcceptationEvent
import cz.palivtom.socketcollector.events.PricingDataAcceptationPublisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.socket.client.WebSocketConnectionManager
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import java.nio.channels.UnresolvedAddressException

@Component
class YahooWebSocket(
    private val objectMapper: ObjectMapper,
    private val pricingDataAcceptationPublisher: PricingDataAcceptationPublisher,
) : ApplicationListener<PricingDataAcceptationEvent> {

    companion object {
        private const val INACTIVITY_RESET = 5 * 60 * 1000
    }

    @Value("\${websocket-client-uri}")
    private lateinit var uriString: String

    @Value("\${subscribe-tickers}")
    private lateinit var tickers: List<String>

    private lateinit var webSocketManager: WebSocketConnectionManager
    private var conntecting = false
    private var lastSocketResponseEvent: PricingDataAcceptationEvent? = null

    @Bean
    private fun init() {
        val client = StandardWebSocketClient()
        val handler = YahooWebSocketHandler(tickers, objectMapper, pricingDataAcceptationPublisher)
        webSocketManager = WebSocketConnectionManager(client, handler, uriString)
    }

    override fun onApplicationEvent(event: PricingDataAcceptationEvent) {
        lastSocketResponseEvent = event
    }

    @Scheduled(fixedRate = 5000)
    private fun connectIfNotConnected() {
        if (!conntecting && lastSocketResponseEvent != null && lastSocketResponseEvent!!.timestamp + INACTIVITY_RESET < System.currentTimeMillis()) {
            webSocketManager.stop()
        }

        if (!webSocketManager.isRunning) {
            conntecting = true
            try {
                webSocketManager.start()
            } catch (e: UnresolvedAddressException) {
                // scheduler tries to connect later
            }
            if (webSocketManager.isRunning) {
                conntecting = false
            }
        }
    }
}