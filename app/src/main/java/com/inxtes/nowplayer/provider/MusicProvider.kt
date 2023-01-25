package com.inxtes.nowplayer.provider

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.util.Log


class MusicProvider {
    val TAG = this::class.simpleName

    fun requestMusic(context:Context): MutableList<MediaBrowserCompat.MediaItem>{

        val itemList:MutableList<MediaBrowserCompat.MediaItem> = mutableListOf()

        val cr: Cursor? = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
            null, MediaStore.Audio.AudioColumns.IS_MUSIC
        )

        if (cr!=null) {
            while (cr.moveToNext()) {
                val path: String =
                    cr.getString(cr.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)) // 路径
                val name: String =
                    cr.getString(cr.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)) // 歌曲名
                val album: String =
                    cr.getString(cr.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)) // 专辑
                val artist: String =
                    cr.getString(cr.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)) // 作者
                val size: Long =
                    cr.getLong(cr.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)) // 大小
                val duration: Long =
                    cr.getLong(cr.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)) // 时长
                val id: String =
                    cr.getString(cr.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)) // 歌曲的id
                val title: String = cr.getString(cr.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                Log.e(TAG,name)

                val extras = Bundle().apply {
                    putString("album",album)
                    putLong("size",size)
                    putLong("duration",duration)
                    putString("artist",artist)
                }
//                val item = MediaItem.fromUri(path)
                val item =  MediaBrowserCompat.MediaItem(
                    MediaDescriptionCompat.Builder()
                        .setMediaId(id)
                        .setTitle(name)
                        .setExtras(extras)
                        .setMediaUri(Uri.parse(path))
                        .build(), FLAG_PLAYABLE
                )


                itemList.add(item)
//                val music = Music(name, duration, id, name, artist, null, album, path)
//                musics.add(music)
            }
        }
        cr?.close()

        return itemList

    }


}