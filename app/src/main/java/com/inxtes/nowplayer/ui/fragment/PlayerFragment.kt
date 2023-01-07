package com.inxtes.nowplayer.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.inxtes.nowplayer.bean.Music
import com.inxtes.nowplayer.databinding.FragmentPlayerBinding
import com.inxtes.nowplayer.service.PlayerService
import com.inxtes.nowplayer.ui.activity.MainActivity

class PlayerFragment(val music: Music?):Fragment() {
    private lateinit var binding:FragmentPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (music!=null)
//            Log.e("Player",music.path)
            (activity as MainActivity).playerBinder.play(Uri.parse(music.path))
    }

    override fun onResume() {
        super.onResume()
    }
}