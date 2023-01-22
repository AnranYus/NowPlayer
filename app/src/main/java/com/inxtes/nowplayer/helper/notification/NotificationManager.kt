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
import com.inxtes.nowplayer.R

class NotificationManager (val sessionToken:MediaSessionCompat.Token, private val service: Service){
    private val TAG = this::class.simpleName
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
        if (mediaController.metadata == null){
            Log.e(TAG,"meta is null")
        }


        notificationBuilder = NotificationCompat.Builder(service, channelId).apply {
//            Log.e("manager",mediaMetadata.description.title.toString())
//            setContentTitle(description.extras?.getString("name").toString())
//            setSubText(description.title)

            setContentIntent(mediaController.sessionActivity)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setSmallIcon(R.drawable.ic_baseline_music_note_24)
            color = ContextCompat.getColor(service, androidx.appcompat.R.color.primary_dark_material_dark)

            setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    service,
                    PlaybackStateCompat.ACTION_STOP
                )
            )

//            // Add a pause/play button
//             addAction( NotificationCompat.Action(
//                    0
//                 ,
//                 service.getString(R.string.pause_play),
//                 MediaButtonReceiver.buildMediaButtonPendingIntent(
//                     service,
//                     PlaybackStateCompat.ACTION_PLAY_PAUSE
//                 )
//             ))

            //TODO next skip
//            addAction( NotificationCompat.Action(
//                R.drawable.ic_baseline_next_24,
//                service.getString(R.string.play),
//                MediaButtonReceiver.buildMediaButtonPendingIntent(
//                    service,
//                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT
//                )
//            ))

            setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(sessionToken)
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

}