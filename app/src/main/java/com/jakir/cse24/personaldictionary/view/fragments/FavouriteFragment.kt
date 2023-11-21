package com.jakir.cse24.personaldictionary.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.base.BaseFragment2
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.databinding.FragmentVocabularyListBinding
import com.jakir.cse24.personaldictionary.interfaces.ItemClickListener
import com.jakir.cse24.personaldictionary.interfaces.ItemSwipeListener
import com.jakir.cse24.personaldictionary.utils.SwipeToDeleteCallback
import com.jakir.cse24.personaldictionary.view.activities.DashboardActivity
import com.jakir.cse24.personaldictionary.view.adapter.VocabularyListAdapter
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel

/**
 * A simple [BaseFragment] subclass.
 */
class FavouriteFragment : BaseFragment2<FragmentVocabularyListBinding>(), ItemClickListener, ItemSwipeListener {
    private lateinit var viewModel: VocabularyListViewModel
    private lateinit var adapter: VocabularyListAdapter
    private lateinit var vocabularyList: ArrayList<Vocabulary>
    private lateinit var mActivity: DashboardActivity

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVocabularyListBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_vocabulary_list, container, false)
    }

    override fun onViewReady(savedInstanceState: Bundle?, view: View) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.app_bar_search).isVisible = false
        menu.findItem(R.id.quiz).isVisible = false
        menu.findItem(R.id.delete).isVisible = false
        menu.findItem(R.id.add_favourite).isVisible = false
        menu.findItem(R.id.show_hide_answer).isVisible = false
        menu.findItem(R.id.reverse).isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHeader.text = getString(R.string.favourite_vocab_list)
        viewModel = ViewModelProvider(this)[VocabularyListViewModel::class.java]

        mActivity = activity as DashboardActivity
//
//        mActivity.fabAdd.setOnClickListener {
//            view.findNavController().navigate(R.id.addVocabularyFragment)
//        }
//        mActivity.bottomBar.setNavigationOnClickListener {
//            Navigation.findNavController(view).navigateUp()
//        }

        val layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()
        vocabularyList = ArrayList()
        adapter = VocabularyListAdapter(vocabularyList, this)
        binding.recyclerView.adapter = adapter

        viewModel.getFavouriteVocabularies().observe(viewLifecycleOwner, Observer {
            vocabularyList.addAll(it)
            adapter.notifyDataSetChanged()
        })
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(requireContext(), this))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onItemClick(vocabulary: Vocabulary) {
        val bundle = bundleOf("vocabulary" to vocabulary)
        view?.findNavController()?.navigate(R.id.action_wordDetails, bundle)
    }

    override fun onItemSwiped(position: Int) {
        val vocabulary = vocabularyList[position]
        adapter.removeItem(position)
        showUndoSnackbar(vocabulary, position)
    }

    private fun showUndoSnackbar(vocabulary: Vocabulary, position: Int) {
        val snack = Snackbar.make(binding.container, "${vocabulary.word} deleted...", Snackbar.LENGTH_LONG)
        snack.setAction(R.string.undo, View.OnClickListener {
            adapter.restoreItem(vocabulary, position)
        }).addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if (event != DISMISS_EVENT_ACTION) {
                    viewModel.removeItem(vocabulary)
                }
            }
        })
        snack.show()
    }

}
