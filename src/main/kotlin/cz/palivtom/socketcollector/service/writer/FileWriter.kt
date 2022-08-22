package cz.palivtom.socketcollector.service.writer

import cz.palivtom.socketcollector.PricingData

/**
 * Writes collected tickers to files.
 */
interface FileWriter {

    /**
     * Saves data to appropriate file.
     */
    fun saveToFile(pricingData: PricingData)

}