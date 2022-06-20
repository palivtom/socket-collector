package cz.palivtom.socketcollector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SocketCollectorApplication

fun main(args: Array<String>) {
	runApplication<SocketCollectorApplication>(*args)
}
