package com.example.minimarket.base

import android.util.DisplayMetrics
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.minimarket.R

open class BaseFragment : Fragment() {

    protected data class Metric(val width: Int, val height: Int)

    protected fun initToolbar(toolbar: Toolbar, navigateUp: Boolean = true) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        if (navigateUp) {
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            toolbar.setNavigationOnClickListener { navController.navigateUp() }
        }
    }

    protected fun getMetric(): Metric {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.getDefaultDisplay().getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        return Metric(width = width, height = height)
    }
}