package cz.palivtom.socketcollector.model.entity

import cz.palivtom.socketcollector.model.entity.base.AbstractEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "tickers")
open class Ticker(

    @Column(name = "created_at", nullable = false)
    open val createdAt: LocalDateTime = LocalDateTime.now()

) : AbstractEntity<Long>() {

    @Column(name = "ticker", nullable = false)
    open lateinit var ticker: String

    @Column(name = "price", nullable = false)
    open var price: Float? = null

    @Column(name = "timestamp", nullable = false)
    open var timestamp: Long? = null
}