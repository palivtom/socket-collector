package cz.palivtom.socketcollector.facade

import cz.palivtom.socketcollector.model.response.IsRunningResponseEntity
import cz.palivtom.socketcollector.utils.FileNameUtils
import cz.palivtom.socketcollector.websocket.WebSocketConnectionI
import org.springframework.stereotype.Component

@Component
class WebSocketFacade(
    private val webSocketClientConnection: WebSocketConnectionI,
    private val fileNameUtils: FileNameUtils
) {
    fun isRunning(): IsRunningResponseEntity {
        return IsRunningResponseEntity(
            isRunning = webSocketClientConnection.isRunning(),
            generatedName = fileNameUtils.name
        )
    }
}