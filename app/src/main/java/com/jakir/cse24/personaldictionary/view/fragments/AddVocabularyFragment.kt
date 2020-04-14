package com.jakir.cse24.personaldictionary.view.fragments


import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.jakir.cse24.easyalert.EasyAlert
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseFragment
import com.jakir.cse24.personaldictionary.data.model.ResponseModel
import com.jakir.cse24.personaldictionary.data.model.Translation
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.databinding.FragmentAddVocabularyBinding
import com.jakir.cse24.personaldictionary.view.activities.DashboardActivity
import com.jakir.cse24.personaldictionary.view.adapter.SpinnerAdapter
import com.jakir.cse24.personaldictionary.view_model.VocabularyAddViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_add_vocabulary.*


/**
 * A simple [BaseFragment] subclass.
 * Created by Md. Jakir Hossain on 02/05/2019
 */
class AddVocabularyFragment : BaseFragment() {
    private var vocabulary: Vocabulary? = null
    private lateinit var type: String
    private lateinit var viewModel: VocabularyAddViewModel
    private lateinit var binding: FragmentAddVocabularyBinding
    private lateinit var mActivity: DashboardActivity
    private var isUpdate: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_vocabulary, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[VocabularyAddViewModel::class.java]
        mActivity = activity as DashboardActivity
        vocabulary = arguments?.getParcelable("vocabulary")
        if (vocabulary != null) {
            viewModel.setValue(vocabulary!!)
            isUpdate = true
            tvHeader.text = getString(R.string.header_update_vocabulary)
            if (vocabulary!!.antonyms != "" || vocabulary!!.synonyms != "" ||
                vocabulary!!.translation.description != "" || vocabulary!!.translation.example != ""){
                group.visibility = View.VISIBLE
            }
        }
        binding.viewModel = viewModel

        mActivity.bottomBar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
        mActivity.fabAdd.setOnClickListener {
            saveOrUpdateVocabulary()
        }

        val types = arrayOf(
            "Select word type..",
            "Noun",
            "Pronoun",
            "Verb",
            "Adverb",
            "Adjective",
            "Preposition",
            "Conjunction"
        )
        val adapter = SpinnerAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            types.toList()
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerType.adapter = adapter

        val selectedPosition = when (vocabulary?.type) {
            "Noun" -> 1
            "Pronoun" -> 2
            "Verb" -> 3
            "Adverb" -> 4
            "Adjective" -> 5
            "Preposition" -> 6
            "Conjunction" -> 7
            else -> 0
        }
        spinnerType.setSelection(selectedPosition)

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Display the selected item text on text view
                type = "${parent.getItemAtPosition(position)}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

        btnAddAdditional.setOnClickListener {
            it.visibility = View.GONE
            group.visibility = View.VISIBLE
        }
    }

    private fun saveOrUpdateVocabulary(){
        val word = viewModel.word.value
        val meaning = viewModel.meaning.value
        var description = viewModel.description.value
        var example = viewModel.example.value
        var synonyms = viewModel.synonyms.value
        var antonyms = viewModel.antonyms.value

        if (word == null || word == "") {
            binding.etWord.error = getString(R.string.word_hint)
            binding.etWord.requestFocus()
            return
        }
        if (meaning == null || meaning == "") {
            binding.etMeaning.error = getString(R.string.meaning_hint)
            binding.etMeaning.requestFocus()
            return
        }
        if (type == "Select word type..") {
            showToast("You have to select word type!")
            return
        }
        if (synonyms == null || synonyms == "") {
            synonyms = ""
        }
        if (antonyms == null || antonyms == "") {
            antonyms = ""
        }
        if (description == null) {
            description = ""
        }
        if (example == null) {
            example = ""
        }

        if (isUpdate) {
            EasyAlert.showProgressDialog(requireActivity(),"Updating vocabulary...")
            viewModel.updateVocabulary(vocabulary!!.id,
                Vocabulary(
                    word, type,
                    Translation(meaning, description, example),
                    synonyms,
                    antonyms
                )).observe(this, Observer {
                updateViews(it)
            })
        } else {
            EasyAlert.showProgressDialog(requireActivity(),"Adding new vocabulary...")
            viewModel.addVocabulary(Vocabulary(
                word, type,
                Translation(meaning, description, example),
                synonyms,
                antonyms
            )).observe(this, Observer {
                updateViews(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.app_bar_search).isVisible = false
        menu.findItem(R.id.quiz).isVisible = false
        menu.findItem(R.id.delete).isVisible = false
        menu.findItem(R.id.add_favourite).isVisible = false
        menu.findItem(R.id.show_hide_answer).isVisible = false

    }

    private fun updateViews(response:ResponseModel) {
        EasyAlert.hideProgressDialog()
        showToast(response.message)
        if (response.status) {
            val navController = view?.let { it -> Navigation.findNavController(it) }
            navController?.navigateUp()
        }
    }
}
