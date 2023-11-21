package com.jakir.cse24.personaldictionary.view.fragments


import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.base.BaseFragment2
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.databinding.FragmentWordDetailsBinding
import com.jakir.cse24.personaldictionary.view.activities.DashboardActivity
import com.jakir.cse24.personaldictionary.view_model.WordDetailsViewModel
import java.util.Locale

/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 24/11/2019
 */
class WordDetailsFragment : BaseFragment2<FragmentWordDetailsBinding>() {
    private lateinit var favIcon: MenuItem
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWordDetailsBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_word_details, container, false)
    }
    override fun onViewReady(savedInstanceState: Bundle?, view: View) {
    }

    private lateinit var viewModel: WordDetailsViewModel

    private lateinit var vocabulary: Vocabulary

    private lateinit var mView: View

    private lateinit var mActivity: DashboardActivity
    private lateinit var tts: TextToSpeech


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        tts = TextToSpeech(requireContext(), TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                tts.language = Locale.UK
            }
        })
//        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onDetach() {
        super.onDetach()
        tts.stop()
        tts.shutdown()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as DashboardActivity
        mView = view
        viewModel = ViewModelProvider(this)[WordDetailsViewModel::class.java]
        vocabulary = arguments?.getParcelable("vocabulary")!!
        binding.data = vocabulary

        // TODO: Need to solve the following lines error
//        mActivity.fabAdd.setOnClickListener {
//            val bundle = bundleOf("vocabulary" to vocabulary)
//            view.findNavController()
//                .navigate(R.id.action_wordDetailsFragment_to_addVocabularyFragment, bundle)
//        }
//        mActivity.bottomBar.setNavigationOnClickListener {
//            Navigation.findNavController(view).navigateUp()
//        }

        binding.fabSpeak.setOnClickListener {
            tts.speak(vocabulary.word,TextToSpeech.QUEUE_FLUSH,null,null);
        }

//        collapsingToolbarSetup()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.app_bar_search).isVisible = false
        menu.findItem(R.id.quiz).isVisible = false
        menu.findItem(R.id.delete).isVisible = true
        menu.findItem(R.id.show_hide_answer).isVisible = false
        menu.findItem(R.id.reverse).isVisible = false
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
                // TODO: Need to fix
//                EasyAlert.showAlertWithChoice(
//                    requireContext(),
//                    getString(R.string.delete),
//                    getString(R.string.delete_warning),
//                    R.drawable.ic_delete_white_24dp
//                ).observe(this,
//                    Observer { it ->
//                        if (it) {
//                            EasyAlert.showProgressDialog(requireActivity(), "Deleting...")
//                            viewModel.removeVocabulary(vocabulary.id)
//                                .observe(viewLifecycleOwner, Observer { response ->
//                                    EasyAlert.hideProgressDialog()
//                                    if (response.status) {
//                                        Navigation.findNavController(mView).navigateUp()
//                                    }
//                                })
//                        }
//                    })

            }
        }
        return super.onOptionsItemSelected(item)
    }


}
