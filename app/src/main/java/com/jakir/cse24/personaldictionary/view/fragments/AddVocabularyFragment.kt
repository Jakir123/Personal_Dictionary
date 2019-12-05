package com.jakir.cse24.personaldictionary.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.view.adapter.SpinnerAdapter
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.databinding.FragmentAddVocabularyBinding
import com.jakir.cse24.personaldictionary.data.model.Translation
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
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

//        (activity as AppCompatActivity).supportActionBar?.title = "Example 1"
//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

        val types = arrayOf("Select word type..","Noun","Pronoun","Verb","Adverb","Adjective","Preposition","Conjunction")
        val adapter = SpinnerAdapter(requireContext(),android.R.layout.simple_spinner_item,
            types.toList()
        )

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
            var description = viewModel.description.value
            var example = viewModel.example.value

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
            if (description == null){
                description = ""
            }
            if (example == null){
                example = ""
            }

            viewModel.addVocabulary(Vocabulary(word,type, Translation(meaning,description,example))).observe(this,
                Observer <Boolean>{
                    if (it) {
                        showToast("Vocabulary added successfully!")
                        val navController = Navigation.findNavController(view)
                        navController.navigateUp()
                    }
                    else
                        showToast("Vocabulary not added!")
                })
        }
    }
}
