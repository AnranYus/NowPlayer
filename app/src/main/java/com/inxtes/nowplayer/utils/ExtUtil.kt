package com.inxtes.nowplayer.utils

import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.util.MimeTypes

object ExtUtil {
    inline val MediaMetadataCompat.mediaUri: Uri
        get() = this.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri()

    fun MediaMetadataCompat.toMediaItem(): com.google.android.exoplayer2.MediaItem {
        return with(MediaItem.Builder()) {
            setMediaId(mediaUri.toString())
            setUri(mediaUri)
            setMimeType(MimeTypes.AUDIO_MPEG)
            setMediaMetadata(toMediaItemMetadata())
        }.build()
    }


    fun MediaMetadataCompat.toMediaItemMetadata(): com.google.android.exoplayer2.MediaMetadata {
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