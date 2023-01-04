package com.inxtes.nowplayer.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.inxtes.nowplayer.ui.activity.MainActivity
import com.inxtes.nowplayer.ui.fragment.MusicFragment
import com.inxtes.nowplayer.ui.fragment.SettingFragment

class FragmentCollectionAdapter(fragment: MainActivity) :FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                MusicFragment()
            }
            1->{
                SettingFragment()
            }

            else -> MusicFragment()
        }

    }

}