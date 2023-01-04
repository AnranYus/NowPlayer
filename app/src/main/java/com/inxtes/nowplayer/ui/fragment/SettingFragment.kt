package com.inxtes.nowplayer.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.inxtes.nowplayer.R

class SettingFragment :PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}