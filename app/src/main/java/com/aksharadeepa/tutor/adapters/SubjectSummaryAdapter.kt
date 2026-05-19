package com.aksharadeepa.tutor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aksharadeepa.tutor.databinding.ItemSubjectSummaryBinding
import com.aksharadeepa.tutor.models.SubjectProgress

class SubjectSummaryAdapter : RecyclerView.Adapter<SubjectSummaryAdapter.Holder>() {
    private val items = mutableListOf<SubjectProgress>()

    fun submit(data: List<SubjectProgress>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemSubjectSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            textName.text = "${item.subjectName} - ${item.completedChapters}/${item.totalChapters} chapters"
            progress.setProgress(item.percent(), true)
        }
    }

    override fun getItemCount(): Int = items.size

    class Holder(val binding: ItemSubjectSummaryBinding) : RecyclerView.ViewHolder(binding.root)
}
