package com.examen.websocketejercicio3.websocket.response

data class ResWebSocket(
    val event: String,
    val topic: String,
    val ref: String,
    var payload: PayLoad
) {
    data class PayLoad(val response: MessageSocket, val status: String)

    data class MessageSocket(val color: String, val message: String)
}
