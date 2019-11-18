package com.jakir.cse24.personaldictionary.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jakir.cse24.personaldictionary.R

/**
 * A simple [Fragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019
 */
class MeaningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meaning, container, false)
    }


}
