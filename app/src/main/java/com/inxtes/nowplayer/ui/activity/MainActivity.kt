package com.inxtes.nowplayer.ui.activity

import android.database.Cursor
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.inxtes.nowplayer.App
import com.inxtes.nowplayer.R
import com.inxtes.nowplayer.databinding.ActivityMainBinding
import com.inxtes.nowplayer.ui.adapter.FragmentCollectionAdapter
import com.inxtes.nowplayer.utils.PermissionUtil
import java.io.File

class MainActivity : BaseActivity() {
    lateinit var viewPager:ViewPager2
    lateinit var binding: ActivityMainBinding

    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        val adapter = FragmentCollectionAdapter(this)
        viewPager = binding.viewpager
        viewPager.adapter = adapter

        //BottomNavigationButtonListener
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.main_page -> {
                    viewPager.currentItem = 0
                }
                R.id.setting_page -> {
                    viewPager.currentItem = 1
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
}