package cz.palivtom.socketcollector.repository

import cz.palivtom.socketcollector.model.entity.Ticker
import org.springframework.data.repository.CrudRepository

interface TickerRepository : CrudRepository<Ticker, Long>