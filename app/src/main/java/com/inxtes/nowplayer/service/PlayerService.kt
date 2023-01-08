package com.inxtes.nowplayer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.ui.activity.MainActivity


class PlayerService : Service() {

    private val TAG = this::class.java.simpleName
    private lateinit var mediaPlayer:MediaPlayer
    private val playerBinder = PlayerBinder()

    override fun onBind(intent: Intent): IBinder {
        return playerBinder
    }

    inner class PlayerBinder : Binder() {
        val service: PlayerService
            get() = this@PlayerService
    }

    override fun onCreate() {
        super.onCreate()

        initService()

    }

    private fun initService(){

        createMediaPlayer()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        val channel = NotificationChannel("player_service", "播放器通知",
            NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, "player_service")
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setContentIntent(pi)
            .build()
        startForeground(1, notification)
    }



    /**
     * 播放音乐，并返回mediaPlayer
     * @param uri 要播放的音乐的uri
     */
    fun play(uri: Uri): MediaPlayer {

        //如果存在正在播放的音乐，则停止
        if (this::mediaPlayer.isInitialized){
            mediaPlayer.reset()
        }else {
        }

        mediaPlayer.setDataSource(App.context, uri)
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        mediaPlayer.prepareAsync()

        return mediaPlayer
    }

    fun createMediaPlayer(){
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
        }
    }

    fun releaseMediaPlayer(){
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