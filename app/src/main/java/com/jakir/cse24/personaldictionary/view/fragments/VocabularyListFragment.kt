package com.jakir.cse24.personaldictionary.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.view.adapter.VocabularyListAdapter
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.interfaces.ItemClickListener
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel
import kotlinx.android.synthetic.main.activity_vocabulary_list.*

/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019.
 */
class VocabularyListFragment : BaseFragment(), ItemClickListener {

    private lateinit var viewModel: VocabularyListViewModel

    override fun onItemClick(vocabulary: Vocabulary) {
//        showToast(vocabulary.translation.meaning)
        val bundle = bundleOf("vocabulary" to vocabulary)
        view?.findNavController()?.navigate(R.id.action_wordDetails,bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[VocabularyListViewModel::class.java]


        val layoutManager = LinearLayoutManager(requireContext())

        fabAdd.setOnClickListener {
            showSnack("Snack message!")
            Navigation.findNavController(it).navigate(R.id.action_addVocabulary)
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
//        recyclerView.adapter = VocabularyListAdapter(viewModel.vocabularies.value!!, this)
//        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))

        viewModel.getVocabularies().observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = VocabularyListAdapter(it,this)
        })

    }


}
