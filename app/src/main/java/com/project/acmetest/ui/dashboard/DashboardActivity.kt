package com.project.acmetest.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.project.acmetest.R
import com.project.acmetest.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * DashboardActivity
 *
 * @property binding The object that references (immutably) the view and contains the objects that are displayed in the view.
 * @property navController Object that manages app navigation.
 *
 * @constructor Empty constructor.
 */
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.dashboard, R.id.newTicket, R.id.workTicket))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}