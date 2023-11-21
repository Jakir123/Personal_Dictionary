package com.jakir.cse24.personaldictionary.view.activities


import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.base.BaseActivity2
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.databinding.ActivityVocabularyListBinding
import com.jakir.cse24.personaldictionary.interfaces.ItemClickListener
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel

class VocabularyListActivity : BaseActivity2<ActivityVocabularyListBinding>(), ItemClickListener {
    override fun onItemClick(vocabulary: Vocabulary) {
        showToast(vocabulary.translation.meaning)
    }

    private lateinit var viewModel: VocabularyListViewModel


    override fun getViewBinding(): ActivityVocabularyListBinding {
        return DataBindingUtil.setContentView(this,R.layout.activity_vocabulary_list)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[VocabularyListViewModel::class.java]


        val layoutManager = LinearLayoutManager(this)

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this@VocabularyListActivity, LoginActivity::class.java))
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()
//        recyclerView.adapter = VocabularyListAdapter(viewModel.vocabularies.value!!, this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }
}
