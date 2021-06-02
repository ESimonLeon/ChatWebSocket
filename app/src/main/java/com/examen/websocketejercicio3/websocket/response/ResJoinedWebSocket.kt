package com.examen.websocketejercicio3.websocket.response

data class ResJoinedWebSocket(
    val event: String,
    val topic: String,
    val ref: String,
    var payload: PayLoad
) {
    data class PayLoad(
        var message: String
    )
}
