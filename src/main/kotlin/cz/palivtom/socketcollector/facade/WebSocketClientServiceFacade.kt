package cz.palivtom.socketcollector.facade

import cz.palivtom.socketcollector.model.response.IsRunningResponseEntity
import cz.palivtom.socketcollector.service.interfaces.WebSocketClientServiceI
import cz.palivtom.socketcollector.utils.FileNameUtils
import org.springframework.stereotype.Component

@Component
class WebSocketClientServiceFacade(
    private val webSocketClientService: WebSocketClientServiceI,
    private val fileNameUtils: FileNameUtils
) {
    fun isRunning(): IsRunningResponseEntity {
        return IsRunningResponseEntity(
            isRunning = webSocketClientService.isRunning(),
            generatedName = fileNameUtils.name
        )
    }
}