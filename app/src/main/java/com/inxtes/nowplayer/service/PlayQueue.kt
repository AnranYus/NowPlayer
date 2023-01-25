package com.inxtes.nowplayer.service

import android.support.v4.media.MediaBrowserCompat
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.provider.MusicProvider

class PlayQueue {
    var headPosition = -1

    val totalQueue : MutableList<MediaBrowserCompat.MediaItem> = MusicProvider().requestMusic(App.context)

    val recentPlayQueue : MutableList<MediaBrowserCompat.MediaItem> = mutableListOf()

    fun getTotalQueueHeadItem(): MediaBrowserCompat.MediaItem {
        headPosition++
        return totalQueue[headPosition]
    }


}