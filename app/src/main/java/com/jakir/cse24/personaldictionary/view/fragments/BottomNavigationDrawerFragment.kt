package com.jakir.cse24.personaldictionary.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.jakir.cse24.personaldictionary.BuildConfig
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.data.repositories.UserRepository
import com.jakir.cse24.personaldictionary.interfaces.LogoutListener
import com.jakir.cse24.personaldictionary.view.activities.AppInfoActivity
import com.jakir.cse24.personaldictionary.view.activities.SettingActivity

/**
 * A simple [BottomSheetDialogFragment] subclass.
 */
class BottomNavigationDrawerFragment(private val logoutListener: LogoutListener) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_navigation_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val tvAppVersion = view.findViewById<TextView>(R.id.tvAppVersion)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val navigationView = view.findViewById<NavigationView>(R.id.navigationView)

        UserRepository().getUserInfo().observe(this, Observer {user->
            tvName.text = user.name
            tvEmail.text = user.email
            progressBar.visibility = View.GONE
        })
        tvAppVersion.text = "Version: ${BuildConfig.VERSION_NAME}"
        navigationView.setNavigationItemSelectedListener {
            dismiss()
            when(it.itemId){
                R.id.actionSetting->{
                    requireContext().startActivity(Intent(requireContext(),SettingActivity::class.java))
                }
                R.id.actionRate->{
//                    EasyToast.showToast(requireContext(),"Long way to go...") Todo need to fix
                }
                R.id.actionAboutDeveloper->{
                    requireContext().startActivity(Intent(requireContext(),AppInfoActivity::class.java))
                }
                R.id.actionLogout->{
                    logoutListener.onLogoutPressed()
                }
            }
            true
        }
    }

}
