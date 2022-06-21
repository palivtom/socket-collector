package cz.palivtom.socketcollector.writer

import cz.palivtom.socketcollector.PricingData

/**
 * Writes collected tickers to files.
 */
interface Writer {

    /**
     * Saves data to appropriate file.
     */
    fun saveToFile(pricingData: PricingData)

}