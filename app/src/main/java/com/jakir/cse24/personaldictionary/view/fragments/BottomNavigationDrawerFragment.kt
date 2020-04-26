package com.jakir.cse24.personaldictionary.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakir.cse24.easyalert.EasyAlert
import com.jakir.cse24.easyalert.EasyLog
import com.jakir.cse24.easyalert.EasyToast
import com.jakir.cse24.personaldictionary.BuildConfig

import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.base.BaseRepository
import com.jakir.cse24.personaldictionary.data.FirebaseSource
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import com.jakir.cse24.personaldictionary.data.repositories.UserRepository
import com.jakir.cse24.personaldictionary.interfaces.LogoutListener
import com.jakir.cse24.personaldictionary.view.activities.AppInfoActivity
import com.jakir.cse24.personaldictionary.view.activities.SettingActivity
import kotlinx.android.synthetic.main.fragment_bottom_navigation_drawer.*

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
                    EasyToast.showToast(requireContext(),"Long way to go...")
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
