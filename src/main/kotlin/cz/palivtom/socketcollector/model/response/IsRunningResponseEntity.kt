package cz.palivtom.socketcollector.model.response

data class IsRunningResponseEntity(
    val isRunning: Boolean,
    val isConnected: Boolean,
    val generatedName: String
)