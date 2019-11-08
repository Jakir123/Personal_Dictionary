package com.jakir.cse24.personaldictionary.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.adapter.VocabularyListAdapter
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel
import kotlinx.android.synthetic.main.activity_vocabulary_list.*

class VocabularyListActivity : AppCompatActivity() {
    private lateinit var viewModel: VocabularyListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocabulary_list)
        viewModel = ViewModelProviders.of(this)[VocabularyListViewModel::class.java]


        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = VocabularyListAdapter(viewModel.vocabularies.value!!)
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }
}
