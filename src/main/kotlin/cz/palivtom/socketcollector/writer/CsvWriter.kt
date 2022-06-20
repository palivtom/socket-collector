package cz.palivtom.socketcollector.writer

import cz.palivtom.socketcollector.PricingData
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class CsvWriter : AbstractWriter() {

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
        logger.info { "Ticker: '${pricingData.id}, timestamp: '${pricingData.time}', price: '${pricingData.price}' has been saved." }
    }

    private fun dataFormatter(data: String): String {
        return "$data${System.lineSeparator()}"
    }
}