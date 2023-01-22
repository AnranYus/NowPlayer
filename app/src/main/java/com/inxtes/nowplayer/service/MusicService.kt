package com.inxtes.nowplayer.service

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.*
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.helper.Player
import com.inxtes.nowplayer.helper.notification.NotificationManager
import com.inxtes.nowplayer.provider.MusicProvider
import com.inxtes.nowplayer.ui.fragment.PlayerFragment
import kotlin.concurrent.thread

class MusicService: MediaBrowserServiceCompat() {
    companion object{
        const val ACTION_CUSTOM_BINDER = "action_custom_binder"
        const val MEDIA_ROOT_ID = "com.inxtes.nowplayer"
    }
    private val TAG = this::class.simpleName

    private lateinit var mediaSession: MediaSessionCompat
    var fragmentContext:PlayerFragment? = null

    val notificationManager:NotificationManager by lazy {
        NotificationManager(mediaSession.sessionToken, this)
    }

    val player: Player by lazy {
        Player()
    }

    val stateBuilder: PlaybackStateCompat.Builder by lazy {
        PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_PLAY_PAUSE or
                        PlaybackStateCompat.ACTION_STOP
            )
            .setState(PlaybackStateCompat.STATE_STOPPED,0,0f)
    }

    inner class MusicBinder:Binder() {
        val service: MusicService
            get() = this@MusicService

    }

    override fun onBind(intent: Intent?): IBinder? {
        return if (intent?.action.equals(ACTION_CUSTOM_BINDER))
            MusicBinder()
        else
            super.onBind(intent)

    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot(MEDIA_ROOT_ID,null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        result.sendResult(MusicProvider.requestMusic(this))
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"onCreate")
        mediaSession = MediaSessionCompat(baseContext, "browser_service").apply {
            setPlaybackState(stateBuilder.build())
            setCallback(SessionCallback())
            setSessionToken(sessionToken)
        }

        player.mediaPlayer.setOnCompletionListener {
            mediaSession.controller.transportControls.pause()
        }


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSession, intent)
        return super.onStartCommand(intent, flags, startId)
    }

    inner class SessionCallback  : MediaSessionCompat.Callback() {
        private val intent = Intent(App.context, MusicService::class.java)
        private lateinit var audioFocusRequest: AudioFocusRequest
//        private lateinit var afChangeListener: AudioManager.OnAudioFocusChangeListener

        val am = baseContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        override fun onPlay() {
            super.onPlay()
            Log.e(TAG,"onPlay")

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
                setAudioAttributes(AudioAttributes.Builder().run {
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    build()
                })
                build()
            }
            val result = am.requestAudioFocus(audioFocusRequest)

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                if (mediaSession.controller.playbackState.state == PlaybackStateCompat.STATE_PAUSED){
                    //暂停 恢复播放+
                    player.resume()

                }else{
                    //启动服务，保证Activity解除绑定后服务不会停止
                    baseContext.startService(intent)
                    //更新mediaSession状态
                    mediaSession.isActive = true
                    //TODO 暂定顺序播放

                    player.play(mediaSession)

                    //显示通知
                    notificationManager.showNotify()
                }

                switchMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING)

                player.setOnMusicPlaybackPositionListener(object : Player.OnPlaybackPositionChange{
                    override fun onPositionChange() {
                        thread {
                            Looper.prepare()
                            Log.e(TAG,"Is ${player.mediaPlayer.isPlaying}")
                            while (!player.mediaPlayer.isPlaying){
                                Thread.sleep(100)
                            }

                            while (player.mediaPlayer.isPlaying){
                                fragmentContext

                                fragmentContext?.handler?.sendMessage(Message().apply {
                                    what = PlayerFragment.PROGRESS_SEEK
                                    arg1 =  player.mediaPlayer.currentPosition //当前播放进度
                                    arg2 = player.mediaPlayer.duration  //最大长度
                                })
                                Thread.sleep(1000) //一秒钟更新一次

                            }
                            Looper.loop()
                        }
                    }
                })


            }


        }

        override fun onStop() {
            Log.e(TAG,"onStop")

            super.onStop()

            stateBuilder.setState(PlaybackStateCompat.STATE_STOPPED,0,1F)
            mediaSession.setPlaybackState(stateBuilder.build())
//            am.abandonAudioFocusRequest(audioFocusRequest)
            baseContext.stopService(intent)
            mediaSession.isActive = false
            player.stop()
            stopSelf()

            switchMediaPlaybackState(PlaybackStateCompat.STATE_STOPPED)



        }

        override fun onPause() {
            Log.e(TAG,"Pause")

            super.onPause()
            player.pause()

            switchMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED)

        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            //TODO
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
            //TODO
        }

        private fun switchMediaPlaybackState(state:Int){
            stateBuilder.setState(state,mediaSession.controller.playbackState.position,mediaSession.controller.playbackState.playbackSpeed)
            mediaSession.setPlaybackState(stateBuilder.build())

        }



    }



}