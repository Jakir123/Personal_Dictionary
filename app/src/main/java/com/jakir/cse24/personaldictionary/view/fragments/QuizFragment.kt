package com.jakir.cse24.personaldictionary.view.fragments


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.databinding.FragmentQuizBinding
import com.jakir.cse24.personaldictionary.data.model.Translation
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.view_model.QuizViewModel
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
    private lateinit var viewModel: QuizViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[QuizViewModel::class.java]
        loadAnimations()
        changeCameraDistance()
        val vocabulary: Vocabulary =
            Vocabulary("Hello", "noun", Translation("Ohe", "HI", "Hello, How are you?"))
        binding.data = vocabulary

        flipCard.setOnClickListener {
            logD("Quiz","Flip click listener....")
            isBackVisiable = if (!isBackVisiable){
                mSetRightOut.setTarget(card_front)
                mSetLeftIn.setTarget(card_back)
                mSetRightOut.start()
                mSetLeftIn.start()
                true
            }else{
                mSetRightOut.setTarget(card_back)
                mSetLeftIn.setTarget(card_front)
                mSetRightOut.start()
                mSetLeftIn.start()
                false
            }
        }
    }


}
