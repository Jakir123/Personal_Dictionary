package com.jakir.cse24.personaldictionary.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
    private lateinit var navController: NavController
    override fun getContentView() {
        setContentView(R.layout.activity_dashboard)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setSupportActionBar(bottomBar)
        navController = Navigation.findNavController(this, R.id.fragmentContainer)
        setupFragmentsTitle(navController)
    }

    private fun setupFragmentsTitle(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.wordDetailsFragment -> {
//                    bottomBar.replaceMenu(R.menu.menu_word_details)
                    bottomBar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_white_24dp)
                    bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fabAdd.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_edit_white_24dp))
                }
                R.id.quizFragment -> {
//                    bottomBar.replaceMenu(R.menu.menu_word_details)
                    bottomBar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_white_24dp)
                    bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fabAdd.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_autorenew_white_24dp))
                }
                R.id.favouriteFragment -> {
//                    bottomBar.replaceMenu(R.menu.menu_word_details)
                    bottomBar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_white_24dp)
//                    bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
//                    fabAdd.setImageDrawable(getDrawable(R.drawable.ic_autorenew_white_24dp))
                }
                R.id.addVocabularyFragment -> {
                    bottomBar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_white_24dp)
//                    bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                    fabAdd.setImageDrawable(getDrawable(R.drawable.ic_edit_white_24dp))
                }
                else -> {
//                    bottomBar.replaceMenu(R.menu.bottom_appbar_menu)
                    bottomBar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.ic_menu_white_24dp)
                    bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fabAdd.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add))
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_appbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.quiz -> {
                navController.navigate(R.id.action_quizFragment)
            }
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
