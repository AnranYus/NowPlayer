package com.inxtes.nowplayer.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.inxtes.nowplayer.R
import com.inxtes.nowplayer.databinding.FragmentMusicBinding
import com.inxtes.nowplayer.ui.activity.MainActivity
import com.inxtes.nowplayer.ui.adapter.MusicAdapter

class MusicFragment():Fragment() {
    private val TAG = this::class.simpleName
    private lateinit var binding: FragmentMusicBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(this.context)
        binding.recyclerviewMusic.layoutManager = layoutManager
        val adapter = MusicAdapter(activity as MainActivity)
        binding.recyclerviewMusic.adapter = adapter

    }
}