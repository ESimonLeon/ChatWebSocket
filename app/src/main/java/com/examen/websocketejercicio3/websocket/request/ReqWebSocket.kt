package com.examen.websocketejercicio3.websocket.request

data class ReqWebSocket(
    var event: String,
    var topic: String,
    var ref: String,
    var payload: PayLoad
) {
    data class PayLoad(var value: String)

}
