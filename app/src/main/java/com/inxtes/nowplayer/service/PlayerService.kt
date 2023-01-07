package com.inxtes.nowplayer.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.inxtes.nowplayer.App


class PlayerService : Service() {
    private val binder = PlayerBinder()
    private val TAG = this::class.java.simpleName
    private lateinit var mediaPlayer:MediaPlayer


    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        binder.stop()
    }

    inner class PlayerBinder : Binder(){
        /**
         * 播放音乐，并返回mediaPlayer
         * @param uri 要播放的音乐的uri
         */
        fun play(uri: Uri):MediaPlayer{

            //如果存在正在播放的音乐，则停止
            if (this@PlayerService::mediaPlayer.isInitialized){
                mediaPlayer.reset()
            }else {
                mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                }
            }


            mediaPlayer.setDataSource(App.context, uri)
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
            }
            mediaPlayer.prepareAsync()

            return mediaPlayer
        }

        fun stop(){

            mediaPlayer.stop()
            mediaPlayer.release()

        }

        fun pause(){
            mediaPlayer.stop()
        }

        fun resume(){
            mediaPlayer.start()
        }
    }


}