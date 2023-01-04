package com.inxtes.nowplayer.utils

import android.content.Context
import android.database.Cursor
import android.media.MediaScannerConnection
import android.provider.MediaStore
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.bean.Music
import java.io.File


object FileUtil {
    val TAG = this::class.simpleName

    fun getMusicInMediaStore(context:Context): ArrayList<Music> {

        val musics: ArrayList<Music> = ArrayList()

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
                val id: Long =
                    cr.getLong(cr.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)) // 歌曲的id

                /*
            class Music(
                val title:String,
                val length:String,
                val id: Long?,
                val name:String,
                val artist:String,
                val artistId: Long?,
                val album:String,
                val path:String
            )
             */
                val music = Music(name, duration, id, name, artist, null, album, path)
                musics.add(music)
            }
        }
        cr?.close()

        return musics

    }

    fun notifyMediaScanner(path:String){
        val fileTree = File(path).walk()
        fileTree.maxDepth(Int.MAX_VALUE)
            .filter { it.isFile }
            .filter { it.extension == "mp3" || it.extension=="flc"}
            .forEach {
                MediaScannerConnection.scanFile(App.context
                    , arrayOf(it.absolutePath)
                    , arrayOf("*/*"),null
                )
            }


    }

}