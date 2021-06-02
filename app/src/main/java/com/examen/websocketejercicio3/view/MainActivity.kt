package com.examen.websocketejercicio3.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.examen.websocketejercicio3.R
import com.examen.websocketejercicio3.databinding.ActivityMainBinding
import com.examen.websocketejercicio3.view.fragments.ChatFragment
import com.examen.websocketejercicio3.view.fragments.LoginChatFragment
import com.examen.websocketejercicio3.listener.IMainPresenter
import com.examen.websocketejercicio3.listener.OnFragmentListener
import com.examen.websocketejercicio3.websocket.response.ResJoinedWebSocket
import com.examen.websocketejercicio3.websocket.response.ResMessageWebSocket
import com.examen.websocketejercicio3.websocket.response.ResWebSocket

class MainActivity : BaseActivity(), IMainPresenter.VIEW, OnFragmentListener {

    lateinit var binding: ActivityMainBinding

    var loginChatFragment: LoginChatFragment = LoginChatFragment.newInstance()

    lateinit var chatFragment: ChatFragment

    lateinit var interactor: MainInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interactor = MainInteractor(this)

        mFragmentLayoutId = binding.flContent.id
        loadFragment(loginChatFragment)
    }

    override fun loginChat(username: String) {
        interactor.loginChatWebSocket(username)
    }

    override fun sendMessage(message: String) {
        interactor.sendMessage(message)
    }

    override fun responseJoinedWebSocket(value: ResJoinedWebSocket?) {
        runOnUiThread(Runnable() {
            run() {
                Toast.makeText(this, value?.payload?.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun successLoginChat(response: ResWebSocket) {
        chatFragment = ChatFragment.newInstance()
        replaceFragment(chatFragment)
    }

    override fun responseMessageWebSocket(responseMessage: ResMessageWebSocket?) {
        chatFragment.setNewMessage(responseMessage)
    }

    override fun errorLoginChat() {
        runOnUiThread(Runnable() {
            run() {
                Toast.makeText(this, getString(R.string.no_pudiste_unirte_al_chat), Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun failConection() {
        if (mFragment is LoginChatFragment) return

        runOnUiThread(Runnable() {
            run() {
                onBackPressed()
                Toast.makeText(this, getString(R.string.conexion_cerrada), Toast.LENGTH_LONG).show()
            }
        })
    }

}