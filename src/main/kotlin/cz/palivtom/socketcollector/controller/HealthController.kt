package cz.palivtom.socketcollector.controller

import cz.palivtom.socketcollector.facade.WebSocketFacade
import cz.palivtom.socketcollector.model.response.IsRunningResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/health")
class HealthController(
    private val webSocketFacade: WebSocketFacade
) {

    @GetMapping("/is-running")
    fun isRunning(): ResponseEntity<IsRunningResponseEntity> {
        val result = webSocketFacade.status()
        return ResponseEntity.status(HttpStatus.OK).body(result)
    }

}