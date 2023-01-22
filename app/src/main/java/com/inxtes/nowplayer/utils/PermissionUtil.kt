package com.inxtes.nowplayer.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.inxtes.nowplayer.App

object PermissionUtil {

//    private fun have(permission:String):Boolean =
//        ContextCompat.checkSelfPermission(App.context,permission) == PackageManager.PERMISSION_GRANTED
//
    fun haveReadMediaAudioPermission():Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            (ContextCompat.checkSelfPermission(App.context,Manifest.permission.READ_MEDIA_AUDIO)
                    == PackageManager.PERMISSION_GRANTED)
        } else {
            (ContextCompat.checkSelfPermission(App.context,Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)        }
}

}