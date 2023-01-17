package com.inxtes.nowplayer.service

import android.support.v4.media.MediaBrowserCompat
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.provider.MusicProvider

class PlayQueue(order:Int = DEFAULT) {
    companion object{
        val DEFAULT = 1
    }

    private var queue : MutableList<MediaBrowserCompat.MediaItem> = MusicProvider.requestMusic(App.context)

    var headPosition :Int = 0

    fun getHeadItem():MediaBrowserCompat.MediaItem?{
        return if (headPosition < queue.size) {

            val item = queue[headPosition]
            headPosition++
            item

        }else{
            null
        }

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