package com.jakir.cse24.personaldictionary.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakir.cse24.easyalert.EasyAlert
import com.jakir.cse24.easyalert.EasyToast

import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.data.FirebaseSource
import com.jakir.cse24.personaldictionary.data.PreferenceManager
import kotlinx.android.synthetic.main.fragment_bottom_navigation_drawer.*

/**
 * A simple [BottomSheetDialogFragment] subclass.
 */
class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_navigation_drawer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView.setNavigationItemSelectedListener {
            dismiss()
            when(it.itemId){
                R.id.actionSetting->{
                    EasyToast.showToast(requireContext(),"Long way to go...")
                }
                R.id.actionRate->{
                    EasyToast.showToast(requireContext(),"Long way to go...")
                }
                R.id.actionLogout->{
                    EasyAlert.showAlertWithChoice(requireContext(), "Log out", "Do you want to Log out?")
                        .observe(this, Observer {
                            if (it) {
                                FirebaseSource.firebaseAuth.signOut()
                                PreferenceManager.isLoggedIn = false
                                activity?.finish()
                            }
                        })
                }
            }
            true
        }
    }

}
