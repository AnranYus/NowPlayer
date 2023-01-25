package com.inxtes.nowplayer.helper

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.provider.MusicProvider
import com.inxtes.nowplayer.service.PlayQueue

class Player{
    private val TAG = this::class.java.simpleName
    var engineInitialized = false
    var mPlayer : ExoPlayer = ExoPlayer.Builder(App.context).build()
    var nowPlaybackPosition = -1

//    val mediaPlayer:MediaPlayer by lazy {
//        MediaPlayer().apply {
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .build()
//            )
//            engineInitialized = true
//        }
//    }



    fun play() {
//        Log.e("Player",uri.toString())

//        mediaPlayer.reset()
//
//        if (uri!=null) {
//
//            mediaPlayer.setDataSource(App.context, uri)
//            mediaPlayer.setOnPreparedListener {
//                mediaPlayer.start()
//            }
//            mediaPlayer.prepareAsync()
//        }else{
//            //TODO 空URI异常
//        }
        mPlayer.prepare()
        mPlayer.play()
        nowPlaybackPosition++

    }
    fun playToNext(){
        mPlayer.seekToNextMediaItem()
        play()

    }

    interface OnPlaybackPositionChange{
        fun onPositionChange()
    }

    fun setOnMusicPlaybackPositionListener(onPlaybackPositionChange: OnPlaybackPositionChange){
        onPlaybackPositionChange.onPositionChange()
    }


    fun stop(){
        mPlayer.stop()
        mPlayer.release()
//        if (engineInitialized) {
//            mediaPlayer.stop()
//            mediaPlayer.release()
//        }

    }

    fun pause(){
        mPlayer.pause()

    }

    fun resume(){
        mPlayer.play()

    }



}