package com.inxtes.nowplayer.ui.activity

import android.Manifest
import android.app.UiModeManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import com.inxtes.nowplayer.service.MusicService
import com.inxtes.nowplayer.utils.PermissionUtil

open class BaseActivity:AppCompatActivity() {
    var haveAllPermission = false
    lateinit var musicBinder: MusicService.MusicBinder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        haveAllPermission = PermissionUtil.haveReadMediaAudioPermission()
        if (!haveAllPermission){
            requestAllPermission()
        }


        //状态栏设置为透明
        window.statusBarColor = Color.TRANSPARENT

        val uiModManager = getSystemService(UI_MODE_SERVICE) as UiModeManager

        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars =
            uiModManager.nightMode != UiModeManager.MODE_NIGHT_YES

        //绑定服务
        val intent  = Intent(this, MusicService::class.java)
        intent.action = MusicService.ACTION_CUSTOM_BINDER
        bindService(intent,connection, Context.BIND_AUTO_CREATE)


    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            musicBinder = service as MusicService.MusicBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    private fun requestAllPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_AUDIO ,
                Manifest.permission.READ_EXTERNAL_STORAGE),0)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            0 -> {
                haveAllPermission = PermissionUtil.haveReadMediaAudioPermission()

                if (!haveAllPermission){
                    requestAllPermission()
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }



}