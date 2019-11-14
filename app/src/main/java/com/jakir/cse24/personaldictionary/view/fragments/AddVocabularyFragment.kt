package com.jakir.cse24.personaldictionary.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.databinding.FragmentAddVocabularyBinding
import com.jakir.cse24.personaldictionary.view_model.VocabularyAddViewModel
import kotlinx.android.synthetic.main.fragment_add_vocabulary.*


/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019
 */
class AddVocabularyFragment : BaseFragment() {

    private lateinit var type: String
    private lateinit var viewModel: VocabularyAddViewModel
    private lateinit var binding: FragmentAddVocabularyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_vocabulary,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[VocabularyAddViewModel::class.java]
        binding.viewModel = viewModel

        val types = arrayOf("Select word type..","Noun","Pronoun","Verb","Adverb","Adjective","Preposition","Conjunction")
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,types)

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerType.adapter = adapter

        spinnerType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                type = "${parent.getItemAtPosition(position)}"
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }


        btnSave.setOnClickListener {
            val word = viewModel.word.value
            val meaning = viewModel.meaning.value
            val description = viewModel.description.value
            val example = viewModel.example.value

            logD("Personal Dictionary","Type: "+type)

            if (word == null || word == "") {
                binding.etWord.error = getString(R.string.word_hint)
                binding.etWord.requestFocus()
                return@setOnClickListener
            }
            if (meaning == null || meaning == "") {
                binding.etMeaning.error = getString(R.string.meaning_hint)
                binding.etMeaning.requestFocus()
                return@setOnClickListener
            }
            if (type == "Select word type.."){
                showToast("You have to select word type!")
            }

        }

    }


}
