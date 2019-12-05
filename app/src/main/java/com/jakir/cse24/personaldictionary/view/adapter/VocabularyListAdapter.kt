package com.jakir.cse24.personaldictionary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakir.cse24.personaldictionary.databinding.RvSampleBinding
import com.jakir.cse24.personaldictionary.interfaces.ItemClickListener
import com.jakir.cse24.personaldictionary.data.model.Vocabulary

class VocabularyListAdapter(private var vocabularyList: List<Vocabulary>, private val listener: ItemClickListener) :
    RecyclerView.Adapter<VocabularyListAdapter.VHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvSampleBinding.inflate(inflater,parent,false)
        return VHolder(binding)
    }

    override fun getItemCount(): Int = vocabularyList.size

    override fun onBindViewHolder(holder: VHolder, position: Int) = holder.bind(vocabularyList[position])

    inner class VHolder(private val binding: RvSampleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Vocabulary) {
            binding.item = item
            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
            binding.executePendingBindings()
        }
    }


}