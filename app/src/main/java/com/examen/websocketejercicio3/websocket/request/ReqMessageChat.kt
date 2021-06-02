package com.examen.websocketejercicio3.websocket.request

data class ReqMessageChat(
    var topic: String,
    var event: String,
    var payload: PayLoad,
    var ref: String
) {
    data class PayLoad(val message: String)
}
