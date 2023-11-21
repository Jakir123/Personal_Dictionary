package com.jakir.cse24.personaldictionary.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomappbar.BottomAppBar
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity2
import com.jakir.cse24.personaldictionary.data.FirebaseSource
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.databinding.ActivityDashboardBinding
import com.jakir.cse24.personaldictionary.interfaces.LogoutListener
import com.jakir.cse24.personaldictionary.view.fragments.BottomNavigationDrawerFragment


class DashboardActivity : BaseActivity2<ActivityDashboardBinding>(), LogoutListener {
    private lateinit var navController: NavController

    override fun getViewBinding(): ActivityDashboardBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.bottomBar)
        navController = Navigation.findNavController(this, R.id.fragmentContainer)
        setupFragmentsTitle(navController)
    }

    private fun setupFragmentsTitle(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.wordDetailsFragment -> {
//                    bottomBar.replaceMenu(R.menu.menu_word_details)
                    binding.bottomBar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
                    binding.bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    binding.fabAdd.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_edit_white_24dp
                        )
                    )
                }
                R.id.quizFragment -> {
//                    bottomBar.replaceMenu(R.menu.menu_word_details)
                    binding.bottomBar.navigationIcon = null
                    binding.bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    binding.fabAdd.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_refresh_white_24dp
                        )
                    )
                }
                R.id.favouriteFragment -> {
//                    bottomBar.replaceMenu(R.menu.menu_word_details)
                    binding.bottomBar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
                    binding.bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
//                    fabAdd.setImageDrawable(getDrawable(R.drawable.ic_reverse_white_24dp))
                }
                R.id.addVocabularyFragment -> {
                    binding.bottomBar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp)
                    binding.bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_save_white_24dp))
                }
                else -> {
//                    bottomBar.replaceMenu(R.menu.bottom_appbar_menu)
                    binding.bottomBar.navigationIcon =
                        ContextCompat.getDrawable(this, R.drawable.ic_menu_white_24dp)
                    binding.bottomBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    binding.fabAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add))
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
            // TODO: Need to fix this 
//            EasyAlert.showAlertWithChoice(this, "Exit", "Do you want to exit?")
//                .observe(this, Observer {
//                    if (it) {
//                        val intent = Intent(Intent.ACTION_MAIN)
//                        intent.addCategory(Intent.CATEGORY_HOME)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                    }
//                })
            return
        }
        super.onBackPressed()
    }

    fun onNavigationPressed() {
        val bottomNavDrawerFragment = BottomNavigationDrawerFragment(this)
        bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
    }

    override fun onLogoutPressed() {
        // TODO: need to fix this 
//        EasyAlert.showAlertWithChoice(this, "Log out", "Do you want to Log out?")
//            .observe(this, Observer { status ->
//                if (status) {
//                    FirebaseSource.firebaseAuth.signOut()
//                    PreferenceManager.isLoggedIn = false
//                    finish()
//                }
//            })
    }

    override fun onRestart() {
        super.onRestart()
        if (isLanguageChange){
            recreate()
        }
    }
}
