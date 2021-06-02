package com.examen.websocketejercicio3.websocket.request


data class ReqLoginChat(
    var topic: String,
    var event: String,
    var payload: PayLoad,
    var ref: String
) {
    data class PayLoad(var name: String)
}
