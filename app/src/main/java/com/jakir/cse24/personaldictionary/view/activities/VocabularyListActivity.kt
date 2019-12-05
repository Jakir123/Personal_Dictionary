package com.jakir.cse24.personaldictionary.view.activities


import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.view.adapter.VocabularyListAdapter
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.interfaces.ItemClickListener
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel
import kotlinx.android.synthetic.main.activity_vocabulary_list.*

class VocabularyListActivity : BaseActivity(), ItemClickListener {
    override fun onItemClick(vocabulary: Vocabulary) {
        showToast(vocabulary.translation.meaning)
    }

    private lateinit var viewModel: VocabularyListViewModel

    override fun getContentView() {
        setContentView(R.layout.activity_vocabulary_list)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[VocabularyListViewModel::class.java]


        val layoutManager = LinearLayoutManager(this)

        fabAdd.setOnClickListener {
            startActivity(Intent(this@VocabularyListActivity, LoginActivity::class.java))
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = VocabularyListAdapter(viewModel.vocabularies.value!!, this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        recyclerView
    }
}
