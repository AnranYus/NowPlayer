package com.inxtes.nowplayer.helper.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.R

class NotificationManager (sessionToken:MediaSessionCompat.Token,val service: Service){
    private var notificationState = false //通知创建状态
    lateinit var notificationBuilder:NotificationCompat.Builder
    val mediaController = MediaControllerCompat(service,sessionToken)
    val channelId = "player_channel"

    init {
        createNotificationChannel(service)
    }

    private fun createNotificationChannel(service:Service){
        val manager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "Player", NotificationManager.
        IMPORTANCE_LOW)
        manager.createNotificationChannel(channel)
    }

    fun showNotify(){

        val mediaMetadata = mediaController.metadata
        val description = mediaMetadata.description

        notificationBuilder = NotificationCompat.Builder(service, channelId).apply {
            Log.e("manager",description?.title.toString())
            setContentTitle(description?.title)

            setContentIntent(mediaController.sessionActivity)

            setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    service,
                    PlaybackStateCompat.ACTION_STOP
                )
            )

            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            setSmallIcon(R.drawable.ic_baseline_next_24)
            color = ContextCompat.getColor(service, androidx.appcompat.R.color.primary_dark_material_dark)

            // Add a pause button
             addAction( NotificationCompat.Action(
                 R.drawable.pause,
                 service.getString(R.string.pause),
                 MediaButtonReceiver.buildMediaButtonPendingIntent(
                     service,
                     PlaybackStateCompat.ACTION_PLAY_PAUSE
                 )
             ))

            setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaController.sessionToken)
                .setShowActionsInCompactView(0)
                .setShowCancelButton(true)
                .setCancelButtonIntent(
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        service,
                        PlaybackStateCompat.ACTION_STOP
                    )
                )
            )
        }
        service.startForeground(10, notificationBuilder.build())

    }

    private fun changeNotificationInfo(){
        val mediaMetadata = mediaController.metadata
        val description = mediaMetadata.description

        notificationBuilder.apply {
            setContentTitle(description?.title)
            setSubText(description?.description)

            setContentIntent(mediaController.sessionActivity)
        }

    }

}