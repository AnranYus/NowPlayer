package com.inxtes.nowplayer.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.viewpager2.widget.ViewPager2
import com.inxtes.nowplayer.R
import com.inxtes.nowplayer.bean.Music
import com.inxtes.nowplayer.databinding.ActivityMainBinding
import com.inxtes.nowplayer.service.PlayerService
import com.inxtes.nowplayer.ui.adapter.FragmentAdapter
import com.inxtes.nowplayer.ui.fragment.PlayerFragment

class MainActivity : BaseActivity() {
    lateinit var viewPager:ViewPager2
    lateinit var binding: ActivityMainBinding
    lateinit var playerBinder:PlayerService.PlayerBinder
    lateinit var pageAdapter:FragmentAdapter

    private val connection = object :ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            playerBinder = p1 as PlayerService.PlayerBinder
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            TODO("Not yet implemented")
        }

    }

    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)


        val intent = Intent(this, PlayerService::class.java)
        startService(intent)
        bindService(intent,connection,Context.BIND_AUTO_CREATE)

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

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    fun switchToPlayer(music: Music) {
        viewPager.currentItem = FragmentAdapter.PLAYER_FRAGMENT
        pageAdapter.changePlayerFragment(PlayerFragment(music))

    }
}