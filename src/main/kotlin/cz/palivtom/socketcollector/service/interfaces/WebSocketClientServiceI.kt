package cz.palivtom.socketcollector.service.interfaces

interface WebSocketClientServiceI {

    /**
     * Closes the WebSocket.
     */
    fun close()

    /**
     * True if WebSocket is opened.
     */
    fun isRunning(): Boolean
}