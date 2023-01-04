package com.inxtes.nowplayer.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.inxtes.nowplayer.App

object PermissionUtil {

    private fun have(permission:String):Boolean =
        ContextCompat.checkSelfPermission(App.context,permission) == PackageManager.PERMISSION_GRANTED

    fun haveReadAndWriteExternalPermission() =
        have(Manifest.permission.READ_EXTERNAL_STORAGE) && have(Manifest.permission.WRITE_EXTERNAL_STORAGE)
}