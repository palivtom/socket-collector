package cz.palivtom.socketcollector.events

import cz.palivtom.socketcollector.PricingData
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class PricingDataAcceptationPublisher(
    private val publisher: ApplicationEventPublisher,
) {

    private val logger = KotlinLogging.logger {}

    fun publishCustomEvent(data: PricingData) {
        logger.info { "Ticker: '${data.id}, timestamp: '${data.time}', price: '${data.price}' has been published." }
        val customSpringEvent = PricingDataAcceptationEvent(this, data)
        publisher.publishEvent(customSpringEvent)
    }
}