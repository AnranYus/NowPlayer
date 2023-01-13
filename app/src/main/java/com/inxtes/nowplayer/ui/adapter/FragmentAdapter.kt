package com.inxtes.nowplayer.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.inxtes.nowplayer.ui.activity.MainActivity
import com.inxtes.nowplayer.ui.fragment.MusicFragment
import com.inxtes.nowplayer.ui.fragment.PlayerFragment
import com.inxtes.nowplayer.ui.fragment.SettingFragment

class FragmentAdapter(fragment: MainActivity) :FragmentStateAdapter(fragment){
    companion object{
        const val MUSIC_FRAGMENT = 0
        const val PLAYER_FRAGMENT = 1
        const val SETTING_FRAGMENT = 2
    }

    private val fragmentList:ArrayList<Fragment> = arrayListOf(MusicFragment(),PlayerFragment(),SettingFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun changePlayerFragment(fragment: PlayerFragment){
        fragmentList[PLAYER_FRAGMENT] = fragment
        notifyItemChanged(PLAYER_FRAGMENT)
    }

}