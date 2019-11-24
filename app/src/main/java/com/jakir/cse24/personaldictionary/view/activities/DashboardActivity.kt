package com.jakir.cse24.personaldictionary.view.activities

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {
    override fun getContentView() {
        setContentView(R.layout.activity_dashboard)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        val navController = Navigation.findNavController(this, R.id.fragmentContainer)
        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNavigation?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    override fun onBackPressed() {
        if (Navigation.findNavController(this,R.id.fragmentContainer)
                .currentDestination?.id == R.id.vocabularyListFragment) {
            showAlert("Do you want ot exit? ")
            return
        }
        super.onBackPressed()
    }
}
