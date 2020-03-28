package com.jakir.cse24.personaldictionary.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomappbar.BottomAppBar
import com.jakir.cse24.easyalert.EasyAlert
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.data.FirebaseSource
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.view.fragments.BottomNavigationDrawerFragment
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity() {
    override fun getContentView() {
        setContentView(R.layout.activity_dashboard)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setSupportActionBar(bottomBar)
        val navController = Navigation.findNavController(this, R.id.fragmentContainer)
//        setupBottomNavMenu(navController)
        setupFragmentsTitle(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
//        bottomNavigation?.let {
//            NavigationUI.setupWithNavController(it, navController)
//        }
    }

    private fun setupFragmentsTitle(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // compare destination id
//            title = when (destination.id) {
//                R.id.vocabularyListFragment -> "Vocabulary List"
//                R.id.addVocabularyFragment -> "Chat Box"
//                R.id.wordDetailsFragment -> {"Chat Box"}
//                else -> "Default title"
//            }

            when (destination.id) {
                R.id.wordDetailsFragment -> {
//                    bottomBar.replaceMenu(R.menu.menu_word_details)
                    bottomBar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_white_24dp)
                    bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fabAdd.setImageDrawable(getDrawable(R.drawable.ic_edit_white_24dp))
                }
                R.id.addVocabularyFragment -> {
                    bottomBar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_white_24dp)
//                    bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                    fabAdd.setImageDrawable(getDrawable(R.drawable.ic_edit_white_24dp))
                }
                else -> {
//                    bottomBar.replaceMenu(R.menu.bottom_appbar_menu)
                    bottomBar.navigationIcon = getDrawable(R.drawable.ic_menu_white_24dp)
                    bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fabAdd.setImageDrawable(getDrawable(R.drawable.ic_add))
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_appbar_menu, menu)
        menu!!.findItem(R.id.delete).isVisible = false
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_search -> showToast("Search clicked")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (Navigation.findNavController(this, R.id.fragmentContainer)
                .currentDestination?.id == R.id.vocabularyListFragment
        ) {
            EasyAlert.showAlertWithChoice(this, "Exit", "Do you want to exit?")
                .observe(this, Observer {
                    if (it) {
                        finish()
                    }
                })
            return
        }
        super.onBackPressed()
    }

    fun onNavigationPressed() {
        val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
        bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
    }
}
