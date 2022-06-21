package cz.palivtom.socketcollector.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import cz.palivtom.socketcollector.PricingData
import cz.palivtom.socketcollector.writer.CsvWriter
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.Base64
import org.springframework.context.annotation.Lazy

@Component
class CustomTextWebSocketHandler(
    private val objectMapper: ObjectMapper,
    private val csvWriter: CsvWriter,
    @Lazy private val webSocketConnection: WebSocketConnectionI
) : TextWebSocketHandler() {

    private val logger = KotlinLogging.logger {}

    @Value("\${subcribe-tickers}")
    private lateinit var tickers: List<String>

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val subscribe = mapOf("subscribe" to tickers)
        val jsonSubscribe = objectMapper.writeValueAsString(subscribe)
        session.sendMessage(TextMessage(jsonSubscribe))
        logger.info { "Tickers have been subscribed: $tickers" }
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        val payload = message.payload as String
        val decodedPayload = Base64.getDecoder().decode(payload)
        val pricingData = PricingData.parseFrom(decodedPayload)
        csvWriter.saveToFile(pricingData)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.error { "Connection closed with status code: ${status.code}. Trying to reconnect..." }
        webSocketConnection.reconnect()
        Thread.sleep(5000)
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        logger.error { "Transport error occurs: ${exception.message}" }
    }
}