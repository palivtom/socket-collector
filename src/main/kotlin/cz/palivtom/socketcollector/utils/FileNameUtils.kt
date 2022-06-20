package cz.palivtom.socketcollector.utils

import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class FileNameUtils {
    companion object {
        private val dateTimeFormatter = DateTimeFormatter.ofPattern("DDMMyyyy-HHMMSS")
    }
    private val logger = KotlinLogging.logger {}

    var name = generateName()

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Prague")
    private fun regenerateName() {
        name = generateName()
    }

    private fun generateName(): String {
        val localDateTime = LocalDateTime.now()
        return localDateTime.format(dateTimeFormatter).apply {
            logger.info { "New fine name has been generated: $this" }
        }
    }
}