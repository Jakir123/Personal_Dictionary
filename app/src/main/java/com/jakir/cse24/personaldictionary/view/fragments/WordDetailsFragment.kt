package com.jakir.cse24.personaldictionary.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.databinding.FragmentWordDetailsBinding
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 24/11/2019
 */
class WordDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentWordDetailsBinding

    private lateinit var vocabulary: Vocabulary

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_word_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vocabulary = arguments?.getParcelable("vocabulary")!!
        binding.data = vocabulary
    }


}
