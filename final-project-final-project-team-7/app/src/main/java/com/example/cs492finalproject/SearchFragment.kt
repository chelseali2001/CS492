package com.example.cs492finalproject

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.cs492finalproject.R

class SearchFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}