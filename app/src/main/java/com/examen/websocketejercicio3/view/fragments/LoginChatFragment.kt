package com.examen.websocketejercicio3.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.examen.websocketejercicio3.R
import com.examen.websocketejercicio3.databinding.FragmentLoginChatBinding
import com.examen.websocketejercicio3.listener.OnFragmentListener
import com.examen.websocketejercicio3.view.MainActivity
import java.lang.ClassCastException

class LoginChatFragment : Fragment(R.layout.fragment_login_chat) {

    lateinit var binding: FragmentLoginChatBinding

    var listener: OnFragmentListener? = null


    lateinit var mActivity: MainActivity

    companion object {
        fun newInstance() = LoginChatFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginChatBinding.bind(view)

        mActivity = activity as MainActivity

        binding.mbLoginChat.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm() {
        if (binding.tietUser.text.isNullOrEmpty()) {
            binding.tietUser.error = getString(R.string.no_empty)
            return
        }
        listener?.loginChat(binding.tietUser.text.toString())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnFragmentListener
        if (listener == null) {
            throw ClassCastException("$context debe implementar OnLoginChatListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onResume() {
        super.onResume()

        mActivity.mFragment = this
    }
}