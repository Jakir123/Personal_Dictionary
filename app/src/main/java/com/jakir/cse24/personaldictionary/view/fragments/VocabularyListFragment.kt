package com.jakir.cse24.personaldictionary.view.fragments


import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.base.BaseFragment2
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.data.repositories.FilterType
import com.jakir.cse24.personaldictionary.databinding.FragmentVocabularyListBinding
import com.jakir.cse24.personaldictionary.interfaces.ItemClickListener
import com.jakir.cse24.personaldictionary.interfaces.ItemSwipeListener
import com.jakir.cse24.personaldictionary.utils.SwipeToDeleteCallback
import com.jakir.cse24.personaldictionary.view.activities.DashboardActivity
import com.jakir.cse24.personaldictionary.view.adapter.VocabularyListAdapter
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel


/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019.
 */
class VocabularyListFragment : BaseFragment2<FragmentVocabularyListBinding>(), ItemClickListener, ItemSwipeListener {

    private  val viewModel: VocabularyListViewModel by activityViewModels()
//    private val viewModel: VocabularyListViewModel by navGraphViewModels(R.id.nav_graph)
    private lateinit var adapter: VocabularyListAdapter
    private lateinit var vocabularyList: ArrayList<Vocabulary>
    private lateinit var mActivity: DashboardActivity

    override fun onItemClick(vocabulary: Vocabulary) {
//        showToast(vocabulary.translation.meaning)
        val bundle = bundleOf("vocabulary" to vocabulary)
        view?.findNavController()?.navigate(R.id.action_wordDetails, bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getVocabularies(FilterType.ALL)
        vocabularyList = ArrayList()
        adapter = VocabularyListAdapter(vocabularyList, this)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.delete).isVisible = false
        menu.findItem(R.id.app_bar_search).isVisible = false
        menu.findItem(R.id.show_hide_answer).isVisible = false
        menu.findItem(R.id.reverse).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_search -> showToast("Search clicked")
            R.id.add_favourite -> {
                view?.findNavController()?.navigate(R.id.action_favouriteFragment)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVocabularyListBinding {
        return DataBindingUtil.inflate(inflater,R.layout.fragment_vocabulary_list,container,false)
    }

    override fun onViewReady(savedInstanceState: Bundle?, view: View) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as DashboardActivity
        // TODO: need to solve the following commented lines
//        mActivity.fabAdd.setOnClickListener {
//            view.findNavController().navigate(R.id.addVocabularyFragment)
//        }
//        mActivity.bottomBar.setNavigationOnClickListener {
//            mActivity.onNavigationPressed()
//        }

        val layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()

        binding.recyclerView.adapter = adapter

//        EasyAlert.showProgressDialog(requireActivity(),getString(R.string.vocabulary_loading)) todo need to fix
        viewModel.vocabularies.observe(viewLifecycleOwner, Observer {
            vocabularyList.clear()
            vocabularyList.addAll(it)
//            EasyAlert.hideProgressDialog() todo need to fix
            adapter.notifyDataSetChanged()
        })
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(requireContext(), this))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.tvHeader.setOnClickListener {
            registerForContextMenu(it)
            requireActivity().openContextMenu(it)
            unregisterForContextMenu(it)
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle("Filter your vocabulary");
        // add menu items
        menu.add(0, v.id, 0, "All");
        menu.add(0, v.id, 0, "Last 7 Days")
        menu.add(0, v.id, 0, "Last 30 Days")
        menu.add(0, v.id, 0, "Last 3 Months")
        menu.add(0, v.id, 0, "Last 6 Months")
        menu.add(0, v.id, 0, "Last 1 Year")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "All"->{
                viewModel.getVocabularies(FilterType.ALL)
            }
            "Last 7 Days"->{
                viewModel.getVocabularies(FilterType.SEVEN_DAYS)
            }
            "Last 30 Days"->{
                viewModel.getVocabularies(FilterType.THIRTY_DAYS)
            }
            "Last 3 Months"->{
                viewModel.getVocabularies(FilterType.THREE_MONTHS)
            }
            "Last 6 Months"->{
                viewModel.getVocabularies(FilterType.SIX_MONTHS)
            }
            "Last 1 Year"->{
                viewModel.getVocabularies(FilterType.ONE_YEAR)
            }

        }
        return true
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
