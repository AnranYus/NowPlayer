package com.inxtes.nowplayer.service

import android.support.v4.media.MediaBrowserCompat
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.provider.MusicProvider

object PlayQueue {
    var headPosition = -1

    private val totalQueue : MutableList<MediaBrowserCompat.MediaItem> by lazy {
        MusicProvider().requestMusic(App.context)
    }

    private val playQueue = totalQueue

    val recentPlayQueue : MutableList<MediaBrowserCompat.MediaItem> = mutableListOf()

    fun getNextItem(): MediaBrowserCompat.MediaItem {
        if (headPosition+1< totalQueue.size){
            headPosition++
        }else{
            headPosition=0
        }
        recentPlayQueue.add(playQueue[headPosition])
        return playQueue[headPosition]
    }

    fun getPrevItem():MediaBrowserCompat.MediaItem{
        if (headPosition>-1){
            headPosition--
            return playQueue[headPosition]
        }else{
            headPosition = playQueue.size-1
            recentPlayQueue.remove(playQueue[headPosition])
            recentPlayQueue.add(playQueue[headPosition])
            return playQueue[headPosition]
        }

    }

    fun getItemIndex(index:Int):MediaBrowserCompat.MediaItem?{
        return if(index>-1){
            headPosition = index
            playQueue[headPosition]
        }else{
            null
        }

    }


}