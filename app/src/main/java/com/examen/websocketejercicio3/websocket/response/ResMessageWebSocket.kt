package com.examen.websocketejercicio3.websocket.response

data class ResMessageWebSocket(
    val event: String,
    val topic: String,
    val ref: String,
    var payload: PayLoad
) {
    data class PayLoad(val color_name: String, val message: String, val user_name: String)
}
