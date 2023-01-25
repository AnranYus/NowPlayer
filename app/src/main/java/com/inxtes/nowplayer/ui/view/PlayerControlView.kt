package com.inxtes.nowplayer.ui.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaControllerCompat.TransportControls
import android.support.v4.media.session.MediaSessionCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import com.google.android.exoplayer2.Player
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.R
import com.inxtes.nowplayer.service.PlayQueue
import com.inxtes.nowplayer.ui.fragment.PlayerFragment
import com.inxtes.nowplayer.utils.ExtUtil.toMediaItem
import com.inxtes.nowplayer.utils.ExtUtil.toMediaMetadata

class PlayerControlView(context:Context, attr:AttributeSet):LinearLayout(context,attr) {
    companion object{
        const val SEEKBAR_POSITION_CHANGED = 1
    }

    lateinit var token : MediaSessionCompat.Token

//    private lateinit var player : Player

    val prev:Button by lazy {
        findViewById<Button>(R.id.prev)
    }

    val next:Button by lazy {
        findViewById<Button>(R.id.next)
    }

    val seekBar:SeekBar by lazy {
        findViewById<SeekBar>(R.id.seek)
    }


    init {
        LayoutInflater.from(context).inflate(R.layout.view_player_control,this)
    }

    fun setSessionToken(token: MediaSessionCompat.Token){
        this.token = token
        val mediaController = MediaControllerCompat(App.context,token)

        prev.setOnClickListener {
            mediaController.transportControls.skipToPrevious()
        }

        next.setOnClickListener {
            mediaController.transportControls.skipToNext()

        }

    }

    val handle = object : Handler(Looper.myLooper()!!){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                SEEKBAR_POSITION_CHANGED -> {
                    seekBar.progress = msg.arg1
                    seekBar.max = msg.arg2
                }

            }
        }
    }

}