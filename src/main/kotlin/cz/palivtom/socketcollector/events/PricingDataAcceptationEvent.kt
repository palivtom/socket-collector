package cz.palivtom.socketcollector.events

import cz.palivtom.socketcollector.PricingData
import org.springframework.context.ApplicationEvent

class PricingDataAcceptationEvent(
    source: Any,
    val data: PricingData
) : ApplicationEvent(source)