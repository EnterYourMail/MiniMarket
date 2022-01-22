package com.example.minimarket.base

import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets
import androidx.annotation.AttrRes
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.minimarket.R
import com.example.minimarket.utils.Metric


open class BaseFragment : Fragment() {

    protected fun initToolbar(toolbar: Toolbar, navigateUp: Boolean = true) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        if (navigateUp) {
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            toolbar.setNavigationOnClickListener { navController.navigateUp() }
        }

        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val sysInserts = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = sysInserts.top)
            insets
        }
    }

    protected fun setInserts(view: View, bottom: Boolean = true) {
        val oldLeft = view.paddingLeft
        val oldRight = view.paddingRight
        val oldBottom = view.paddingBottom
        val isBottomInt = if (bottom) 1 else 0

        ViewCompat.setOnApplyWindowInsetsListener(view) { viewCompat, insets ->
            val sysInserts = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            viewCompat.updatePadding(
                left = oldLeft + sysInserts.left,
                right = oldRight + sysInserts.right,
                bottom = oldBottom + sysInserts.bottom * isBottomInt
            )

            WindowInsetsCompat.Builder().run {
                setInsets(
                    //left and right = 0, because a rootView has already set paddings.
                    WindowInsetsCompat.Type.systemBars(), Insets.of(
                        0,
                        sysInserts.top,
                        0,
                        sysInserts.bottom,
                    )
                )
                build()
            }
        }
    }

    protected fun getMetric(): Metric {
        val height: Int
        val width: Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            height = windowMetrics.bounds.height() - insets.top - insets.bottom
            width = windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels
        }

        return Metric(width, height)
    }

    protected fun getThemeColor(@AttrRes attrColor: Int): Int {
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(attrColor, typedValue, true)
        return typedValue.data
    }

}