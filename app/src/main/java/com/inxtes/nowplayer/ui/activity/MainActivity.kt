package com.inxtes.nowplayer.ui.activity

import android.content.ComponentName
import android.media.AudioManager
import android.media.session.MediaController
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.inxtes.nowplayer.R
import com.inxtes.nowplayer.bean.Music
import com.inxtes.nowplayer.databinding.ActivityMainBinding
import com.inxtes.nowplayer.service.MusicService
import com.inxtes.nowplayer.ui.adapter.FragmentAdapter
import com.inxtes.nowplayer.ui.fragment.PlayerFragment

class MainActivity : BaseActivity() {
    lateinit var viewPager:ViewPager2
    lateinit var binding: ActivityMainBinding
    lateinit var pageAdapter:FragmentAdapter

    private val TAG = this::class.simpleName
    lateinit var mediaBrowser: MediaBrowserCompat
    lateinit var mediaController:MediaControllerCompat

    lateinit var transportControls:MediaControllerCompat.TransportControls


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaBrowser = MediaBrowserCompat(
            this,
            ComponentName(this, MusicService::class.java),
            connectionCallbacks,
            null
        )



//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)


//        val intent = Intent(this, PlayerService::class.java)
//        startService(intent)
//        bindService(intent,connection,Context.BIND_AUTO_CREATE)

        binding.start.setOnClickListener {
            Log.e(TAG,"start")
            mediaController.transportControls.play()
        }

        binding.stop.setOnClickListener {
            mediaController.transportControls.stop()
            Log.e(TAG,"stop")


        }

        //创建MediaBrowserServiceCompat

        pageAdapter = FragmentAdapter(this)
        viewPager = binding.viewpager
        viewPager.adapter = pageAdapter

        //BottomNavigationButtonListener
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.main_page -> {
                    viewPager.currentItem = FragmentAdapter.MUSIC_FRAGMENT
                }
                R.id.player_page -> {
                    viewPager.currentItem = FragmentAdapter.PLAYER_FRAGMENT
                }
                R.id.setting_page -> {
                    viewPager.currentItem = FragmentAdapter.SETTING_FRAGMENT
                }
            }

            return@setOnItemSelectedListener true
        }

        //viewpager滑动监听
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

    }

    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback(){
        override fun onConnected() {
            super.onConnected()

            mediaBrowser.sessionToken.also { token ->
                mediaController = MediaControllerCompat(this@MainActivity,token)
                MediaControllerCompat.setMediaController(this@MainActivity,mediaController)
            }

            buildTransportControls()

        }
    }

    fun buildTransportControls(){
        //注册控制器回调
        mediaController.registerCallback(controllerCallback)
        transportControls = mediaController.transportControls

    }

    private val controllerCallback = object : MediaControllerCompat.Callback(){
        //TODO
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            Log.e(TAG,"state ${state.toString()}")
            super.onPlaybackStateChanged(state)
        }
    }


    override fun onStart() {
        super.onStart()
        mediaBrowser.connect()

    }

    override fun onResume() {
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun onStop() {
        super.onStop()
        MediaControllerCompat.getMediaController(this)?.unregisterCallback(controllerCallback)
        mediaBrowser.disconnect()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun switchToPlayer(mediaId:String) {
        viewPager.currentItem = FragmentAdapter.PLAYER_FRAGMENT
        pageAdapter.changePlayerFragment(PlayerFragment())

    }

}