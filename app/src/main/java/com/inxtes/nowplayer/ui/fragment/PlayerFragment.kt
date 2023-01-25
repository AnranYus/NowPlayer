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
import com.inxtes.nowplayer.service.MusicService
import com.inxtes.nowplayer.ui.activity.MainActivity
import com.inxtes.nowplayer.ui.view.PlayerControlView

class PlayerFragment:Fragment() {

    val TAG = this::class.java.simpleName

    lateinit var binding:FragmentPlayerBinding
    lateinit var context:MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context = activity as MainActivity
        binding = FragmentPlayerBinding.inflate(inflater)
//        binding.playerControl.player = context.musicBinder.service.player
//        binding.playerControl.setShowNextButton(true)
//        binding.playerControl.setShowPreviousButton(true)
//        context.musicBinder.service.fragmentContext = this
//        context.musicBinder.service.seekBarHandler = binding.playerControl.handle
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playerControl.player = context.musicBinder.service.player
//        context.musicBinder.service.setOnPlayerPositionChangedListener(object : MusicService.OnPlayerPositionChangedListener{
//            override fun playerPositionChanged(position:Int,max:Int) {
//                binding.playerControl.handle.sendMessage(Message().apply {
//                    what = PlayerControlView.SEEKBAR_POSITION_CHANGED
//                    arg1 = position
//                    arg2 = max
//                })
//            }
//
//        })
    }


}