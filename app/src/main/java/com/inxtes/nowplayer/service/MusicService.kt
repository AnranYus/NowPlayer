package com.inxtes.nowplayer.service

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.KeyEvent
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.bean.Player
import com.inxtes.nowplayer.helper.notification.NotificationManager


private const val MEDIA_ROOT_ID = "com.inxtes.nowplayer"

class MusicService: MediaBrowserServiceCompat() {

    private lateinit var mediaSession: MediaSessionCompat


    private lateinit var stateBuilder: PlaybackStateCompat.Builder
    lateinit var notificationManager:NotificationManager


    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        Log.e("PlayBack","onGetRoot: $clientPackageName   $clientUid    $rootHints")

        return BrowserRoot(MEDIA_ROOT_ID,null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        Log.e("PlayBack","onLoadChildren")
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("PlayBack","onCreate")
        mediaSession = MediaSessionCompat(baseContext, "browser_service").apply {
            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY or
                            PlaybackStateCompat.ACTION_PAUSE or
                            PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            setPlaybackState(stateBuilder.build())
            setCallback(MySessionCallback())
            setSessionToken(sessionToken)
        }
        notificationManager = NotificationManager(mediaSession.sessionToken, this)


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSession, intent);
        return super.onStartCommand(intent, flags, startId)
    }

    inner class MySessionCallback : MediaSessionCompat.Callback() {
        private val intent = Intent(App.context, MusicService::class.java)
        private lateinit var audioFocusRequest: AudioFocusRequest
//        private lateinit var afChangeListener: AudioManager.OnAudioFocusChangeListener

        val am = baseContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
            val ke: KeyEvent? = mediaButtonEvent?.getParcelableExtra(Intent.EXTRA_KEY_EVENT)
            Log.e("Service",ke?.action.toString())
            return super.onMediaButtonEvent(mediaButtonEvent)

        }

        override fun onPlay() {
            super.onPlay()
            Log.e("PlayBack","onPlay")

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
                setAudioAttributes(AudioAttributes.Builder().run {
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    build()
                })
                build()
            }
            val result = am.requestAudioFocus(audioFocusRequest)
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                //启动服务，保证Activity解除绑定后服务不会停止
                baseContext.startService(intent)
                //更新mediaSession状态
                mediaSession.isActive = true
                //TODO 暂定顺序播放

                Player.play(mediaSession)
                if (mediaSession.controller.metadata==null){
                    Log.e("MusciService","NULLLL")
                }
                //显示通知
                notificationManager.showNotify()

            }

        }

        override fun onStop() {
            Log.e("PlayBack","onStop")

            super.onStop()
            am.abandonAudioFocusRequest(audioFocusRequest)
            baseContext.stopService(intent)
            mediaSession.isActive = false
            Player.stop()
            stopSelf()

        }

        override fun onPause() {
            Log.e("TAG","Pause")
            super.onPause()
            Player.pause()
            stopSelf()
        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            //TODO
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
            Log.e("Playback","onSkipToNext")
            //TODO
        }


    }


}