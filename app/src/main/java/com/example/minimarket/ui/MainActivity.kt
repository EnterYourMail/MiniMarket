package com.example.minimarket.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.minimarket.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if (Build.VERSION.SDK_INT < 30 ) {
//            binding.mainFragmentsNavHost.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE +
//                View.SYSTEM_UI_FLAG_FULLSCREEN +
//                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//        } else {
//            window.setDecorFitsSystemWindows(false)
//        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}