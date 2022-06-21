package cz.palivtom.socketcollector.websocket

/**
 * Initialize WebSocketConnectionManager and starts the WebSocket.
 */
interface WebSocketConnectionI {

    /**
     * Stops and starts the WebSocket.
     */
    fun reconnect()

    /**
     * Stats the WebSocket.
     */
    fun start()

    /**
     * Stops the WebSocket.
     */
    fun stop()

    /**
     * Returns true if WebSocket is running.
     */
    fun isRunning(): Boolean
}