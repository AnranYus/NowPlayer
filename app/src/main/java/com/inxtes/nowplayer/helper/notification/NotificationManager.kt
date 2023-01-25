package com.inxtes.nowplayer.helper.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.inxtes.nowplayer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class NotificationManager (sessionToken:MediaSessionCompat.Token,
                           service: Service,
                           notificationListener: PlayerNotificationManager.NotificationListener
){
    companion object{
        const val NOTIFICATION_ID = 0X100
        const val CHANNEL_ID = "com.inxtes.nowplayer"
    }
    private var notificationManager: PlayerNotificationManager
    private val TAG = this::class.simpleName
    val channelId = "player_channel"


    init {
//        createNotificationChannel(service)

        val mediaController = MediaControllerCompat(service, sessionToken)

        val builder = PlayerNotificationManager.Builder(service, NOTIFICATION_ID, CHANNEL_ID)
        with (builder) {
            setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            setNotificationListener(notificationListener)
            setChannelNameResourceId(R.string.notification_channel)
            setChannelDescriptionResourceId(R.string.notification_channel_description)
        }
        notificationManager = builder.build()
        notificationManager.setMediaSessionToken(sessionToken)
        notificationManager.setUseRewindAction(false)
        notificationManager.setUseFastForwardAction(false)

    }

    private inner class DescriptionAdapter(val mediaController: MediaControllerCompat) :
        PlayerNotificationManager.MediaDescriptionAdapter {

        override fun getCurrentContentTitle(player: Player): CharSequence =
            mediaController.metadata.description.subtitle.toString()



        override fun createCurrentContentIntent(player: Player): PendingIntent? =
            mediaController.sessionActivity


        override fun getCurrentContentText(player: Player): CharSequence =
            mediaController.metadata.description.subtitle.toString()


        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            //TODO 封面
            return null
        }

    }

    fun showNotification(player:Player){
        notificationManager.setPlayer(player)
    }

    fun hideNotification(){
        notificationManager.setPlayer(null)

    }

}