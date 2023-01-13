package com.inxtes.nowplayer.service

import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.provider.MusicProvider

class PlayQueue(order:Int = DEFAULT) {
    companion object{
        val DEFAULT = 1
    }

    private lateinit var queue : MutableList<MediaBrowserCompat.MediaItem>

    init {
        queue = MusicProvider.requestMusic(App.context)
    }

    var headPosition :Int = 0

    fun getHeadItem():MediaBrowserCompat.MediaItem{
        val item = queue[headPosition]
        headPosition++
        return item
    }


//    private fun playByOrder(){
//        Log.e("Queue",position.toString())
//        val mediaPlayer = Player.play(repository[position].description.mediaUri!!)
//        mediaPlayer.setOnCompletionListener {
//            position++
//        }
//
////        while (position<repository.size){
////            Log.e("Queue",position.toString())
////
////        }
//
//    }







}