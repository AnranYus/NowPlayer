package com.inxtes.nowplayer.utils

import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.MimeTypes

object ExtUtil {
    inline val MediaMetadataCompat.mediaUri: Uri
        get() = this.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri()

    /**
     * android.support.v4.media.MediaMetadataCompat
     * TO
     * com.google.android.exoplayer2.MediaItem
     */

    fun MediaMetadataCompat.toMediaItem(): MediaItem {
        return with(MediaItem.Builder()) {
            setMediaId(mediaUri.toString())
            setUri(mediaUri)
            setMimeType(MimeTypes.AUDIO_MPEG)
            setMediaMetadata(toMediaItemMetadata())
        }.build()
    }

    /**
     * android.support.v4.media.MediaBrowserCompat.MediaItem
     * TO
     * android.support.v4.media.MediaMetadataCompat
     *
     */
    fun MediaBrowserCompat.MediaItem.toMediaMetadata():MediaMetadataCompat {
        return with(MediaMetadataCompat.Builder()){
            putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, this@toMediaMetadata.mediaId)
            putString(MediaMetadataCompat.METADATA_KEY_TITLE,this@toMediaMetadata.description.title.toString())
            putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI,this@toMediaMetadata.description.mediaUri.toString())
            putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,this@toMediaMetadata.description.iconUri.toString())
        }.build()

    }


    /**
     * android.support.v4.media.MediaMetadataCompat
     * TO
     * com.google.android.exoplayer2.MediaMetadata
     *
     */
    fun MediaMetadataCompat.toMediaItemMetadata(): MediaMetadata {
        return with(MediaMetadata.Builder()) {
            setTitle(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE))
            setDisplayTitle(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE))
            setAlbumArtist(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST))
            setAlbumTitle(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM))
            setComposer(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_COMPOSER))
            setTrackNumber(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER)?.toInt())
            setTotalTrackCount(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS)?.toInt())
            setDiscNumber(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER)?.toInt())
            setWriter(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_WRITER))
            setArtworkUri(this@toMediaItemMetadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI)?.toUri())
        }.build()
    }
}