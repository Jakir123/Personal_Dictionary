package com.jakir.cse24.personaldictionary.view.fragments


import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.jakir.cse24.easyalert.EasyAlert
import com.jakir.cse24.easyalert.EasyToast
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.databinding.FragmentWordDetailsBinding
import com.jakir.cse24.personaldictionary.utils.ToolbarView
import com.jakir.cse24.personaldictionary.view.activities.DashboardActivity
import com.jakir.cse24.personaldictionary.view_model.WordDetailsViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_word_details.*
import kotlin.math.abs

/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 24/11/2019
 */
class WordDetailsFragment : BaseFragment() {
    private lateinit var favIcon: MenuItem
    private lateinit var binding: FragmentWordDetailsBinding
    private lateinit var viewModel: WordDetailsViewModel

    private lateinit var vocabulary: Vocabulary

    private lateinit var mView: View

    private lateinit var mActivity: DashboardActivity
    private lateinit var mBottomAppBar: BottomAppBar

//    private var isHideToolbarView = false
//    private lateinit var _toolbarView: ToolbarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_word_details, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
//        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as DashboardActivity
        mView = view
        viewModel = ViewModelProviders.of(this)[WordDetailsViewModel::class.java]
        vocabulary = arguments?.getParcelable("vocabulary")!!
        binding.data = vocabulary

        mActivity.fabAdd.setOnClickListener {
            val bundle = bundleOf("vocabulary" to vocabulary)
            view.findNavController()
                .navigate(R.id.action_wordDetailsFragment_to_addVocabularyFragment, bundle)
        }
        mActivity.bottomBar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        fabSpeak.setOnClickListener {
            EasyToast.showToast(requireContext(),"Long way to go...")
        }

        collapsingToolbarSetup()
    }

    private fun collapsingToolbarSetup() {
        appbarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {

            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = vocabulary.word
                    collapsingToolbar.setCollapsedTitleTextAppearance(R.style.coll_toolbar_title)
                    isShow = true
                } else if (verticalOffset >= 0 || verticalOffset < -230) {
                    collapsingToolbar.title = vocabulary.word
                    collapsingToolbar.setExpandedTitleTextAppearance(R.style.exp_toolbar_title)
                } else if (isShow) {
                    collapsingToolbar.title =
                        vocabulary.word //carefull there should a space between double quote otherwise it wont work
                    collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
                    isShow = false
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.app_bar_search).isVisible = false
        menu.findItem(R.id.quiz).isVisible = false
        menu.findItem(R.id.delete).isVisible = true
        favIcon = menu.findItem(R.id.add_favourite)
        favIcon.isVisible = true
        if (vocabulary.favourite) favIcon.icon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_white_24dp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_favourite -> {
                viewModel.addRemoveFavourite(vocabulary.id, !vocabulary.favourite)
                    .observe(viewLifecycleOwner,
                        Observer {
                            if (it.status) {
                                vocabulary.favourite = !vocabulary.favourite
                                val icon =
                                    if (vocabulary.favourite) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border
                                favIcon.icon = ContextCompat.getDrawable(requireContext(), icon)
                            }
                        })
            }
            R.id.delete -> {
                EasyAlert.showAlertWithChoice(
                    requireContext(),
                    getString(R.string.delete),
                    getString(R.string.delete_warning),
                    R.drawable.ic_delete_white_24dp
                ).observe(this,
                    Observer { it ->
                        if (it) {
                            EasyAlert.showProgressDialog(requireActivity(), "Deleting...")
                            viewModel.removeVocabulary(vocabulary.id)
                                .observe(viewLifecycleOwner, Observer { response ->
                                    EasyAlert.hideProgressDialog()
                                    if (response.status) {
                                        Navigation.findNavController(mView).navigateUp()
                                    }
                                })
                        }
                    })

            }
        }
        return super.onOptionsItemSelected(item)
    }


}
