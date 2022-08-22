package cz.palivtom.socketcollector.service.writer

import cz.palivtom.socketcollector.PricingData
import cz.palivtom.socketcollector.events.PricingDataAcceptationEvent
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
@ConditionalOnProperty(name = ["ticker.data.save"], havingValue = "csv")
class CsvFileWriter : AbstractFileWriter(), ApplicationListener<PricingDataAcceptationEvent> {

    override fun onApplicationEvent(event: PricingDataAcceptationEvent) {
        saveToFile(event.data)
    }

    override fun saveToFile(pricingData: PricingData) {
        getFile(pricingData).apply {
            if (!exists()) {
                createNewFile()
                val headers = pricingData.allFields.keys.stream().map { it.name }.collect(Collectors.joining(","))
                appendText(dataFormatter(headers))
            }
            val values = pricingData.allFields.values.stream().map { it.toString() }.collect(Collectors.joining(","))
            appendText(dataFormatter(values))
        }
        logger.debug { "Ticker: '${pricingData.id}, timestamp: '${pricingData.time}', price: '${pricingData.price}' has been saved to csv file." }
    }

    private fun dataFormatter(data: String): String {
        return "$data${System.lineSeparator()}"
    }
}