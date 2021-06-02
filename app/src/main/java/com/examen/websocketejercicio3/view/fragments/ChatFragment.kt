package com.examen.websocketejercicio3.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.examen.websocketejercicio3.util.ConstantsApp.SP_USERNAME
import com.examen.websocketejercicio3.R
import com.examen.websocketejercicio3.websocket.response.ResMessageWebSocket
import com.examen.websocketejercicio3.util.UtilSharedPreferences
import com.examen.websocketejercicio3.databinding.FragmentChatBinding
import com.examen.websocketejercicio3.listener.OnFragmentListener
import com.examen.websocketejercicio3.view.MainActivity

class ChatFragment : Fragment(R.layout.fragment_chat) {

    lateinit var binding: FragmentChatBinding
    lateinit var sharedPreferences: UtilSharedPreferences
    var userName: String = ""

    var listener: OnFragmentListener? = null

    lateinit var mActivity: MainActivity

    companion object {
        fun newInstance() = ChatFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChatBinding.bind(view)

        mActivity = activity as MainActivity

        userName = sharedPreferences.getSharedPreferences(SP_USERNAME, userName)

        binding.tvUser.text = userName

        binding.fabSend.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm() {
        if (binding.tietMessage.text.isNullOrEmpty()) {
            binding.tietMessage.error = getString(R.string.no_empty)
            return
        }

        listener?.sendMessage(binding.tietMessage.text.toString())
    }


    override fun onAttach(context: Context) {
        sharedPreferences = UtilSharedPreferences(context)

        listener = context as? OnFragmentListener
        if (listener == null) {
            throw ClassCastException("$context debe implementar OnLoginChatListener")
        }
        super.onAttach(context)
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    fun setNewMessage(responseMessage: ResMessageWebSocket?) {
        requireActivity().runOnUiThread(Runnable() {
            run() {
                if (responseMessage?.payload?.user_name == userName) printMessage() else receiveMessage(
                    responseMessage
                )
            }
        })

    }

    private fun printMessage() {
        val child: View = layoutInflater.inflate(R.layout.item_send_message, null)
        val user = child.findViewById<TextView>(R.id.tvUser)
        val message = child.findViewById<TextView>(R.id.tvMessage)
        user.text = userName
        message.text = binding.tietMessage.text.toString()
        binding.llMessages.addView(child)
        binding.tietMessage.text = null
    }

    private fun receiveMessage(responseMessage: ResMessageWebSocket?) {
        val child: View = layoutInflater.inflate(R.layout.item_receive_message, null)
        val user = child.findViewById<TextView>(R.id.tvUser)
        val message = child.findViewById<TextView>(R.id.tvMessage)
        user.text = responseMessage?.payload?.user_name
        message.text = responseMessage?.payload?.message
        binding.llMessages.addView(child)
    }

    override fun onResume() {
        super.onResume()

        mActivity.mFragment = this
    }
}
