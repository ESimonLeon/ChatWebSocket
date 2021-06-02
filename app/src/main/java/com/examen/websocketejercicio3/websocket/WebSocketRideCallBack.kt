package com.examen.websocketejercicio3.websocket

import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_EVENT_HEARTNBEAT
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_EVENT_JOIN
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_EVENT_JOINED
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_EVENT_MESSAGE
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_EVENT_PHX_JOIN
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_EVENT_SEND
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_PHOENIX
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_REF
import com.examen.websocketejercicio3.util.ConstantsApp.SOCKET_TOPIC
import com.examen.websocketejercicio3.util.ConstantsApp.URL_WEBSOCKET
import com.examen.websocketejercicio3.listener.IWebSocketListener
import com.examen.websocketejercicio3.websocket.request.ReqLoginChat
import com.examen.websocketejercicio3.websocket.request.ReqMessageChat
import com.examen.websocketejercicio3.websocket.request.ReqWebSocket
import com.examen.websocketejercicio3.websocket.response.ResJoinedWebSocket
import com.examen.websocketejercicio3.websocket.response.ResMessageWebSocket
import com.examen.websocketejercicio3.websocket.response.ResWebSocket
import com.google.gson.Gson
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class WebSocketRideCallBack : WebSocketListener() {

    lateinit var webSocket: WebSocket
    lateinit var listener: IWebSocketListener
    var event: String? = null

    companion object {
        fun openConection(listener: IWebSocketListener) = WebSocketRideCallBack().apply {
            this.listener = listener

            val mOkHttpClient = OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.SECONDS)
                .writeTimeout(3000, TimeUnit.SECONDS)
                .connectTimeout(3000, TimeUnit.SECONDS)
                .build();

            val request = Request.Builder().url(URL_WEBSOCKET).build()

            webSocket = mOkHttpClient.newWebSocket(request, this)
            mOkHttpClient.dispatcher().executorService().shutdown()
        }
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        webSocket.send(openConnection())
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)

        val response = Gson().fromJson(text, ResWebSocket::class.java)
        if (response.event == SOCKET_EVENT_JOINED) {
            val responseJoined = Gson().fromJson(text, ResJoinedWebSocket::class.java)
            listener.responseJoinWebSocket(responseJoined)
        }

        if (response.event == SOCKET_EVENT_MESSAGE) {
            val responseMessage = Gson().fromJson(text, ResMessageWebSocket::class.java)
            listener.responseMessageWebSocket(responseMessage)
        }


        if (response.topic == SOCKET_EVENT_JOIN || event == SOCKET_EVENT_JOIN) {
            listener.responseJoinLogin(response)
            event = null
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        listener.onCloseConnection()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
         listener.onCloseConnection()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
         listener.onCloseConnection()
    }

    fun openConnection(): String {
        val json = ReqWebSocket(SOCKET_EVENT_HEARTNBEAT, SOCKET_PHOENIX, SOCKET_REF, ReqWebSocket.PayLoad(""))
        return Gson().toJson(json)
    }

    fun loginChatWebSocket(username: String) {
        joinTopic()
        event = SOCKET_EVENT_JOIN
        val json = ReqLoginChat(SOCKET_TOPIC, event!!, ReqLoginChat.PayLoad(username), SOCKET_REF)
        webSocket.send(Gson().toJson(json))
    }

    private fun joinTopic() {
        val json = ReqLoginChat(SOCKET_TOPIC, SOCKET_EVENT_PHX_JOIN, ReqLoginChat.PayLoad(""), SOCKET_REF)
        webSocket.send(Gson().toJson(json))
    }

    fun sendMessageWebSocket(message: String) {
        val json = ReqMessageChat(SOCKET_TOPIC, SOCKET_EVENT_SEND, ReqMessageChat.PayLoad(message), SOCKET_REF)
        webSocket.send(Gson().toJson(json))
    }

}
