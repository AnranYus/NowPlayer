package com.inxtes.nowplayer.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v4.media.session.MediaControllerCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inxtes.nowplayer.bean.Music
import com.inxtes.nowplayer.databinding.FragmentPlayerBinding
import com.inxtes.nowplayer.ui.activity.MainActivity

class PlayerFragment:Fragment() {
    lateinit var binding:FragmentPlayerBinding
    lateinit var context:MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context = activity as MainActivity
        binding = FragmentPlayerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.text.setOnClickListener {
            context.transportControls.play()
        }
    }
}