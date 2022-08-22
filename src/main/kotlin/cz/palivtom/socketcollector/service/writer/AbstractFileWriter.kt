package cz.palivtom.socketcollector.service.writer

import cz.palivtom.socketcollector.PricingData
import cz.palivtom.socketcollector.utils.FileNameUtils
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import java.io.File

abstract class AbstractFileWriter : FileWriter {
    protected val logger = KotlinLogging.logger {}

    @Autowired
    protected lateinit var fileNameUtils: FileNameUtils

    @Value("\${output-path}")
    protected lateinit var path: String

    protected fun getFile(pricingData: PricingData): File {
        val fullPath = "$path/${pricingData.id}"

        val directory = File(fullPath)
        if (!directory.exists()) directory.mkdirs()

        return File("$fullPath/${fileNameUtils.name}.csv")
    }
}