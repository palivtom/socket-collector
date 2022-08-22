package cz.palivtom.socketcollector.service

import cz.palivtom.socketcollector.PricingData
import cz.palivtom.socketcollector.events.PricingDataAcceptationEvent
import cz.palivtom.socketcollector.model.entity.Ticker
import cz.palivtom.socketcollector.repository.TickerRepository
import mu.KotlinLogging
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Service

@Service
class TickerService(
    private val tickerRepository: TickerRepository
) : ApplicationListener<PricingDataAcceptationEvent> {

    private val logger = KotlinLogging.logger {}

    override fun onApplicationEvent(event: PricingDataAcceptationEvent) {
        create(event.data)
    }

    fun create(data: PricingData): Ticker {
        var ticker = Ticker().apply {
            ticker = data.id
            price = data.price
            timestamp = data.time
        }
        ticker = tickerRepository.save(ticker)
        logger.debug { "Ticker: '${ticker.ticker}' with id: '${ticker.id}' has been saved to database." }
        return ticker
    }
}