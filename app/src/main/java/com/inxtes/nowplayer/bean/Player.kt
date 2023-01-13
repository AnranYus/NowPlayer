package com.inxtes.nowplayer.bean

import android.media.AudioAttributes
import android.media.AudioMetadata
import android.media.MediaDataSource
import android.media.MediaPlayer
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.service.PlayQueue

object Player{
    private val TAG = this::class.java.simpleName
    private val mediaPlayer:MediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
        }
    }
    val PLAYING = 1
    val STOP = 0
    val PAUSE = -1

    val queue : PlayQueue by lazy {
        PlayQueue()
    }

    var playerState = STOP//播放器状态


    /**
     *
     * @param uri 要播放的音乐的uri
     */
    private fun playMusic(uri:Uri?) {
//        Log.e("Player",uri.toString())

        mediaPlayer.reset()

        if (uri!=null) {

            mediaPlayer.setDataSource(App.context, uri)
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
            }
            mediaPlayer.prepareAsync()
            playerState = PLAYING
        }else{
            //TODO 空URI异常
        }

    }

    fun play(mediaSession:MediaSessionCompat){
        val item = queue.getHeadItem()
        playMusic(item.description.mediaUri)
        mediaSession.setMetadata(MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                item.mediaId).build())
    }

    fun stop(){
        mediaPlayer.stop()
        mediaPlayer.release()
        playerState = STOP
    }

    fun pause(){
        mediaPlayer.pause()
        playerState = PAUSE

    }

    fun resume(){
        mediaPlayer.start()
        playerState = PLAYING

    }



}