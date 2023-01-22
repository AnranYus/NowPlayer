package com.inxtes.nowplayer.helper

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.service.PlayQueue

class Player{
    private val TAG = this::class.java.simpleName
    var engineInitialized = false
    val mediaPlayer:MediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            engineInitialized = true
        }
    }


    val queue : PlayQueue by lazy {
        PlayQueue()
    }


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
        }else{
            //TODO 空URI异常
        }

    }
    fun playToNext(mediaSession: MediaSessionCompat){
        queue.headPosition++
        play(mediaSession)

    }

    interface OnPlaybackPositionChange{
        fun onPositionChange()
    }

    fun setOnMusicPlaybackPositionListener(onPlaybackPositionChange: OnPlaybackPositionChange){
        onPlaybackPositionChange.onPositionChange()
    }

    fun play(mediaSession:MediaSessionCompat){
        val item = queue.getHeadItem()
        if (item!=null) {
            playMusic(item.description.mediaUri)
            mediaSession.setMetadata(MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                    item.mediaId).build())
        }
    }

    fun stop(){
        if (engineInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }

    }

    fun pause(){
        mediaPlayer.pause()

    }

    fun resume(){
        mediaPlayer.start()

    }



}