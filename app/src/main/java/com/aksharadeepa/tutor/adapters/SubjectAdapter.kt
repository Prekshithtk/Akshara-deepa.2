package com.aksharadeepa.tutor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aksharadeepa.tutor.databinding.ItemSubjectBinding
import com.aksharadeepa.tutor.models.ChapterWithProgress
import com.aksharadeepa.tutor.models.Subject
import kotlin.math.roundToInt

class SubjectAdapter(private val listener: ChapterAdapter.Listener) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {
    private val subjects = mutableListOf<Subject>()
    private val chapters = mutableListOf<ChapterWithProgress>()
    private val expanded = mutableSetOf<Int>()

    fun submit(newSubjects: List<Subject>, newChapters: List<ChapterWithProgress>) {
        subjects.clear()
        subjects.addAll(newSubjects)
        chapters.clear()
        chapters.addAll(newChapters)
        subjects.forEach { expanded.add(it.id) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val subject = subjects[position]
        val own = chapters.filter { it.subjectId == subject.id }
        val completed = own.count { it.completed }
        val percent = if (own.isEmpty()) 0 else (completed * 100f / own.size).roundToInt()

        holder.binding.apply {
            textSubject.text = subject.name
            textPercent.text = "$percent%"
            progressSubject.setProgress(percent, true)
            recyclerChapters.layoutManager = LinearLayoutManager(holder.itemView.context)
            val adapter = ChapterAdapter(listener)
            recyclerChapters.adapter = adapter
            adapter.submit(own)
            recyclerChapters.visibility = if (expanded.contains(subject.id)) View.VISIBLE else View.GONE
            header.setOnClickListener {
                if (expanded.contains(subject.id)) expanded.remove(subject.id) else expanded.add(subject.id)
                val adapterPosition = holder.bindingAdapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun getItemCount(): Int = subjects.size

    class SubjectViewHolder(val binding: ItemSubjectBinding) : RecyclerView.ViewHolder(binding.root)
}
