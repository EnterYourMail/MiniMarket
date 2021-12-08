package com.example.minimarket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.minimarket.R
import com.example.minimarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val MY_LOGS = "MY_LOGS"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        /*binding.toolbar.setOnMenuItemClickListener { item ->
            Log.d(MY_LOGS, "MainActivity.setOnMenuItemClickListener")
            item.onNavDestinationSelected(findNavController(R.id.nav_host))
                    || super.onOptionsItemSelected(item)
        }*/

        Log.d(MY_LOGS, "MainActivity.onCreate")
        /*var ed: EditText
        ed.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }
        })*/
    }

 /*   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("MY_LOGS", "MainActivity.onCreateOptionsMenu")
        menuInflater.inflate(R.menu.toolbar_start_menu, menu)
        return true
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(MY_LOGS, "MainActivity.onOptionsItemSelected")
        return item.onNavDestinationSelected(findNavController(R.id.nav_host))
                || super.onOptionsItemSelected(item)
    }


}