package com.examen.websocketejercicio3.listener

import com.examen.websocketejercicio3.websocket.response.ResJoinedWebSocket
import com.examen.websocketejercicio3.websocket.response.ResMessageWebSocket
import com.examen.websocketejercicio3.websocket.response.ResWebSocket

interface IWebSocketListener {
    fun responseJoinWebSocket(response: ResJoinedWebSocket?)
    fun responseJoinLogin(response: ResWebSocket?)
    fun responseMessageWebSocket(responseMessage: ResMessageWebSocket?)
    fun onCloseConnection()
}
