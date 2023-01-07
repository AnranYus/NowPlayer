package com.inxtes.nowplayer.ui.activity

import android.Manifest
import android.app.UiModeManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import com.inxtes.nowplayer.utils.PermissionUtil

open class BaseActivity:AppCompatActivity() {
    var haveAllPermission = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        haveAllPermission = PermissionUtil.haveReadAndWriteExternalPermission()
        if (!haveAllPermission){
            requestAllPermission()
        }


        //状态栏设置为透明
        window.statusBarColor = Color.TRANSPARENT

        val uiModManager = getSystemService(UI_MODE_SERVICE) as UiModeManager

        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars =
            uiModManager.nightMode != UiModeManager.MODE_NIGHT_YES

    }

    private fun requestAllPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),0)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            0 -> {
                haveAllPermission = PermissionUtil.haveReadAndWriteExternalPermission()

                if (!haveAllPermission){
                    requestAllPermission()
                }

            }
        }
    }

}