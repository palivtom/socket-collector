package cz.palivtom.socketcollector.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import cz.palivtom.socketcollector.PricingData
import cz.palivtom.socketcollector.events.PricingDataAcceptationPublisher
import mu.KotlinLogging
import org.springframework.web.socket.*
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.Base64

class YahooWebSocketHandler(
    private val tickers: List<String>,
    private val objectMapper: ObjectMapper,
    private val pricingDataAcceptationPublisher: PricingDataAcceptationPublisher
) : TextWebSocketHandler() {

    private val logger = KotlinLogging.logger {}

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val subscribe = mapOf("subscribe" to tickers)
        val jsonSubscribe = objectMapper.writeValueAsString(subscribe)
        session.sendMessage(TextMessage(jsonSubscribe))
        logger.info { "Tickers have been subscribed: $tickers" }
    }
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        val decodedPayload = Base64.getDecoder().decode(payload)
        val pricingData = PricingData.parseFrom(decodedPayload)
        pricingDataAcceptationPublisher.publishCustomEvent(pricingData)
    }

    override fun handlePongMessage(session: WebSocketSession, message: PongMessage) {
        logger.warn { "A pong message has been received: $message" }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.error { "Connection closed with status code: ${status.code}" }
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        logger.error { "Transport error occurs: ${exception.message}" }
    }
}