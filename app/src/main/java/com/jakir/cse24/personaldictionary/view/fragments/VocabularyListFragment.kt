package com.jakir.cse24.personaldictionary.view.fragments


import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.jakir.cse24.easyalert.EasyAlert
import com.jakir.cse24.easyalert.EasyLog
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
import kotlinx.android.synthetic.main.activity_vocabulary_list.recyclerView
import kotlinx.android.synthetic.main.fragment_vocabulary_list.*


/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019.
 */
class VocabularyListFragment : BaseFragment(), ItemClickListener, ItemSwipeListener {

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
        EasyLog.logE("onViewCreated")
        viewModel.getVocabularies()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        EasyLog.logE("onCreateView")
        return inflater.inflate(R.layout.fragment_vocabulary_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EasyLog.logE("onViewCreated")
        mActivity = activity as DashboardActivity
        mActivity.fabAdd.setOnClickListener {
            view.findNavController().navigate(R.id.addVocabularyFragment)
        }
        mActivity.bottomBar.setNavigationOnClickListener {
            mActivity.onNavigationPressed()
        }

        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()

        recyclerView.adapter = adapter

        EasyAlert.showProgressDialog(requireActivity(),getString(R.string.vocabulary_loading))
        viewModel.vocabularies.observe(viewLifecycleOwner, Observer {
            vocabularyList.clear()
            vocabularyList.addAll(it)
            EasyAlert.hideProgressDialog()
            adapter.notifyDataSetChanged()
        })
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(requireContext(), this))
        itemTouchHelper.attachToRecyclerView(recyclerView)

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
