package com.jakir.cse24.personaldictionary.view.fragments


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.switchmaterial.SwitchMaterial
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.base.BaseFragment2
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.databinding.FragmentQuizBinding
import com.jakir.cse24.personaldictionary.view.activities.DashboardActivity
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel
import java.util.*


/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019
 */
class QuizFragment : BaseFragment2<FragmentQuizBinding>() {
    private var isBackVisiable: Boolean = false
    private lateinit var mSetLeftIn: AnimatorSet
    private lateinit var mSetRightOut: AnimatorSet
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentQuizBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
    }
    override fun onViewReady(savedInstanceState: Bundle?, view: View) {

    }

    private val viewModel: VocabularyListViewModel by activityViewModels()
//    private val viewModel: VocabularyListViewModel by navGraphViewModels(R.id.nav_graph)
    private lateinit var mActivity: DashboardActivity
    private var favIcon: MenuItem? = null
    private lateinit var vocabulary: Vocabulary
    private lateinit var tts: TextToSpeech


    private fun changeCameraDistance() {
        val distance = 8000
        val scale = resources.displayMetrics.density * distance
        binding.cardFront.cameraDistance = scale
        binding.cardBack.cameraDistance = scale
    }

    private fun loadAnimations() {
        mSetRightOut =
            AnimatorInflater.loadAnimator(requireContext(), R.animator.out_animation) as AnimatorSet
        mSetLeftIn =
            AnimatorInflater.loadAnimator(requireContext(), R.animator.in_animation) as AnimatorSet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        tts = TextToSpeech(requireContext(), TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                tts.language = Locale.UK
            }
        })
    }


    override fun onDetach() {
        super.onDetach()
        tts.stop()
        tts.shutdown()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProviders.of(this)[VocabularyListViewModel::class.java]
        loadAnimations()
        changeCameraDistance()

        binding.cardBack.visibility = View.INVISIBLE

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.generateRandomVocabulary()
        viewModel.getRandomVocabulary().observe(viewLifecycleOwner, Observer {
            vocabulary = it
//            collapsingToolbar.title = vocabulary.word
            updateFavIcon()
        })

        mActivity = activity as DashboardActivity

//        mActivity.fabAdd.setOnClickListener {
//            viewModel.generateRandomVocabulary()
//        }
//        mActivity.bottomBar.setNavigationOnClickListener {
//            Navigation.findNavController(view).navigateUp()
//        }
        // TODO: need to find a solution of the above code

        binding.fragmentWordDetails.fabSpeak.setOnClickListener {
            speakWord()
        }

        binding.fragmentWord.imvSpeaker.setOnClickListener {
            speakWord()
        }

//        collapsingToolbarSetup()
    }

    private fun speakWord() {
        tts.speak(vocabulary.word, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private fun showHideAnswer() {
        isBackVisiable = if (!isBackVisiable) {
            binding.cardBack.visibility =View.VISIBLE
            mSetRightOut.setTarget(binding.cardFront)
            mSetLeftIn.setTarget(binding.cardBack)
            mSetRightOut.start()
            mSetLeftIn.start()
            true
        } else {
            mSetRightOut.setTarget(binding.cardBack)
            mSetLeftIn.setTarget(binding.cardFront)
            mSetRightOut.start()
            mSetLeftIn.start()
            false
        }
    }
    

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        favIcon = menu.findItem(R.id.add_favourite)
        favIcon?.isVisible = true
        menu.findItem(R.id.app_bar_search).isVisible = false
        menu.findItem(R.id.quiz).isVisible = false
        menu.findItem(R.id.delete).isVisible = false
        val switchItem = menu.findItem(R.id.show_hide_answer)
        switchItem.isVisible = true
        val switch: SwitchMaterial = switchItem.actionView as SwitchMaterial
        switch.setOnCheckedChangeListener { compoundButton, isChecked ->
            showHideAnswer()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_favourite -> {
                viewModel.addRemoveFavourite(vocabulary.id, !vocabulary.favourite)
                    .observe(viewLifecycleOwner,
                        Observer {
                            if (it.status) {
                                vocabulary.favourite = !vocabulary.favourite
                                updateFavIcon()
                            }
                        })
            }
            R.id.reverse -> {
                viewModel.setReverseEnable()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateFavIcon() {
        val icon =
            if (vocabulary.favourite) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border
        favIcon?.icon = ContextCompat.getDrawable(requireContext(), icon)
    }


}
