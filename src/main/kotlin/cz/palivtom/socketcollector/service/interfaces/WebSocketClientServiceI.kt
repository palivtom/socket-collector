package cz.palivtom.socketcollector.service.interfaces

interface WebSocketClientServiceI {

    /**
     * Closes the WebSocket.
     */
    fun close()

    /**
     * True if WebSocket is not already done or canceled.
     */
    fun isRunning(): Boolean
}