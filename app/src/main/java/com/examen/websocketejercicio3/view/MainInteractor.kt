package com.examen.websocketejercicio3.view

import android.content.Context
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_STATUS_OK
import com.examen.websocketejercicio3.util.ConstantsApp.SP_USERNAME
import com.examen.websocketejercicio3.listener.IMainPresenter
import com.examen.websocketejercicio3.listener.IWebSocketListener
import com.examen.websocketejercicio3.util.UtilSharedPreferences
import com.examen.websocketejercicio3.websocket.WebSocketRideCallBack
import com.examen.websocketejercicio3.websocket.response.ResJoinedWebSocket
import com.examen.websocketejercicio3.websocket.response.ResMessageWebSocket
import com.examen.websocketejercicio3.websocket.response.ResWebSocket

class MainInteractor(var context: Context) : IMainPresenter.INTERACTOR, IWebSocketListener {

    var viewActivity: IMainPresenter.VIEW = context as MainActivity

    var sharedPreferences = UtilSharedPreferences(context)
    private lateinit var webSocketRideCallBack: WebSocketRideCallBack

    init {
        openConnection()
    }

    fun openConnection() {
        webSocketRideCallBack = WebSocketRideCallBack.openConection(this)
    }

    var username: String? = null

    override fun loginChatWebSocket(username: String) {
        this.username = username
        webSocketRideCallBack.loginChatWebSocket(username)
    }

    override fun sendMessage(message: String) {
        webSocketRideCallBack.sendMessageWebSocket(message)
    }

    override fun responseJoinWebSocket(response: ResJoinedWebSocket?) {
        viewActivity.responseJoinedWebSocket(response)
    }

    override fun responseJoinLogin(response: ResWebSocket?) {
        if (response?.payload?.status == SOCKET_STATUS_OK) {
            sharedPreferences.saveSharedPreferences(SP_USERNAME, username!!)
            viewActivity.successLoginChat(response)
        } else viewActivity.errorLoginChat()
    }

    override fun responseMessageWebSocket(responseMessage: ResMessageWebSocket?) {
        viewActivity.responseMessageWebSocket(responseMessage)
    }

    override fun onCloseConnection() {
        openConnection()
        viewActivity.failConection()
    }

}
