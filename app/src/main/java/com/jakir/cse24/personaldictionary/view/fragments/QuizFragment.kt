package com.jakir.cse24.personaldictionary.view.fragments


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.databinding.FragmentQuizBinding
import com.jakir.cse24.personaldictionary.view.activities.DashboardActivity
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_quiz.*


/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019
 */
class QuizFragment : BaseFragment() {
    private var isBackVisiable: Boolean = false
    private lateinit var mSetLeftIn: AnimatorSet
    private lateinit var mSetRightOut: AnimatorSet
    private lateinit var binding: FragmentQuizBinding
    private val viewModel: VocabularyListViewModel by activityViewModels()
    private lateinit var mActivity: DashboardActivity
    private  lateinit var favIcon: MenuItem
    private lateinit var vocabulary: Vocabulary
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        return binding.root
    }

    private fun changeCameraDistance() {
        val distance = 8000
        val scale = resources.displayMetrics.density * distance
        card_front.cameraDistance = scale
        card_back.cameraDistance = scale
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProviders.of(this)[VocabularyListViewModel::class.java]
        loadAnimations()
        changeCameraDistance()
        viewModel.randomVocabulary.observe(viewLifecycleOwner, Observer {
            binding.data = it
            vocabulary = it
            updateFavIcon()
        })
        viewModel.generateRandomVocabulary()

        mActivity = activity as DashboardActivity

        mActivity.fabAdd.setOnClickListener {
            viewModel.generateRandomVocabulary()
        }
        mActivity.bottomBar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        flipCard.setOnClickListener {
            logD("Quiz", "Flip click listener....")
            isBackVisiable = if (!isBackVisiable) {
                mSetRightOut.setTarget(card_front)
                mSetLeftIn.setTarget(card_back)
                mSetRightOut.start()
                mSetLeftIn.start()
                true
            } else {
                mSetRightOut.setTarget(card_back)
                mSetLeftIn.setTarget(card_front)
                mSetRightOut.start()
                mSetLeftIn.start()
                false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.app_bar_search).isVisible = false
        menu.findItem(R.id.quiz).isVisible = false
        menu.findItem(R.id.delete).isVisible = false
        favIcon = menu.findItem(R.id.add_favourite)
        favIcon.isVisible = true
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateFavIcon() {
        val icon =
            if (vocabulary.favourite) R.drawable.ic_favorite_yellow_24dp else R.drawable.ic_favorite_border
        favIcon.icon = ContextCompat.getDrawable(requireContext(), icon)
    }


}
