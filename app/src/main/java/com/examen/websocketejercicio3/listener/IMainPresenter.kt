package com.examen.websocketejercicio3.listener

import com.examen.websocketejercicio3.websocket.response.ResJoinedWebSocket
import com.examen.websocketejercicio3.websocket.response.ResMessageWebSocket
import com.examen.websocketejercicio3.websocket.response.ResWebSocket

interface IMainPresenter {

    interface VIEW {
        fun responseJoinedWebSocket(value: ResJoinedWebSocket?)
        fun successLoginChat(response: ResWebSocket)
        fun responseMessageWebSocket(responseMessage: ResMessageWebSocket?)
        fun errorLoginChat()
        fun failConection()
    }

    interface INTERACTOR {
        fun loginChatWebSocket(username: String)
        fun sendMessage(message: String)
    }
}
