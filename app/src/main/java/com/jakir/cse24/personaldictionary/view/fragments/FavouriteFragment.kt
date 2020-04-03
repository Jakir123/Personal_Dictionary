package com.jakir.cse24.personaldictionary.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.interfaces.ItemClickListener
import com.jakir.cse24.personaldictionary.interfaces.ItemSwipeListener
import com.jakir.cse24.personaldictionary.utils.SwipeToDeleteCallback
import com.jakir.cse24.personaldictionary.view.activities.DashboardActivity
import com.jakir.cse24.personaldictionary.view.adapter.VocabularyListAdapter
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.fabAdd
import kotlinx.android.synthetic.main.activity_vocabulary_list.*
import kotlinx.android.synthetic.main.activity_vocabulary_list.recyclerView
import kotlinx.android.synthetic.main.fragment_vocabulary_list.*

/**
 * A simple [BaseFragment] subclass.
 */
class FavouriteFragment : BaseFragment(), ItemClickListener, ItemSwipeListener {
    private lateinit var viewModel: VocabularyListViewModel
    private lateinit var adapter: VocabularyListAdapter
    private lateinit var vocabularyList: ArrayList<Vocabulary>
    private lateinit var mActivity: DashboardActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvHeader.text = getString(R.string.favourite_vocab_list)
        viewModel = ViewModelProviders.of(this)[VocabularyListViewModel::class.java]

        mActivity = activity as DashboardActivity
        mActivity.fabAdd.setOnClickListener {
            view.findNavController().navigate(R.id.addVocabularyFragment)
        }
        mActivity.bottomBar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        vocabularyList = ArrayList()
        adapter = VocabularyListAdapter(vocabularyList, this)
        recyclerView.adapter = adapter

        viewModel.getFavouriteVocabularies().observe(viewLifecycleOwner, Observer {
            vocabularyList.addAll(it)
            adapter.notifyDataSetChanged()
        })
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(requireContext(), this))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(vocabulary: Vocabulary,view: View) {
        val bundle = bundleOf("vocabulary" to vocabulary)
        view?.findNavController()?.navigate(R.id.action_wordDetails, bundle)
    }

    override fun onItemSwiped(position: Int) {
        val vocabulary = vocabularyList[position]
        adapter.removeItem(position)
        showUndoSnackbar(vocabulary, position)
    }

    private fun showUndoSnackbar(vocabulary: Vocabulary, position: Int) {
        val snack = Snackbar.make(container, "${vocabulary.word} deleted...", Snackbar.LENGTH_LONG)
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
