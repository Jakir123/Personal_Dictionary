package com.jakir.cse24.personaldictionary.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakir.cse24.personaldictionary.R
import com.jakir.cse24.personaldictionary.adapter.VocabularyListAdapter
import com.jakir.cse24.personaldictionary.base.BaseActivity
import com.jakir.cse24.personaldictionary.databinding.ActivityVocabularyListBinding
import com.jakir.cse24.personaldictionary.view_model.VocabularyListViewModel
import kotlinx.android.synthetic.main.activity_vocabulary_list.*

class VocabularyListActivity : BaseActivity() {
    private lateinit var binding: ActivityVocabularyListBinding
    private lateinit var viewModel: VocabularyListViewModel

    override fun onViewReady(savedInstanceState: Bundle?) {
        // here initialize views
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vocabulary_list)
        viewModel = ViewModelProviders.of(this)[VocabularyListViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
//        recyclerView.adapter = VocabularyListAdapter(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }

    override fun getContentView(): Int {
        return R.layout.activity_vocabulary_list
    }
}
