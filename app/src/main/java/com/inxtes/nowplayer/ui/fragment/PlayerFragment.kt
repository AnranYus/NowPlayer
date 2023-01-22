package com.inxtes.nowplayer.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inxtes.nowplayer.databinding.FragmentPlayerBinding
import com.inxtes.nowplayer.ui.activity.MainActivity

class PlayerFragment:Fragment() {
    val TAG = this::class.java.simpleName
    companion object{
        const val PROGRESS_SEEK = 1
    }

    lateinit var binding:FragmentPlayerBinding
    lateinit var context:MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context = activity as MainActivity
        binding = FragmentPlayerBinding.inflate(inflater)

        context.musicBinder.service.fragmentContext = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.text.setOnClickListener {
            context.transportControls.play()
        }

    }

    val handler = object : Handler(Looper.myLooper()!!){

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){

                PROGRESS_SEEK -> {
                    binding.musicPro.progress = msg.arg1
                    if (binding.musicPro.max!= msg.arg2) {
                        binding.musicPro.max = msg.arg2
                    }

                }

            }

        }
    }

}