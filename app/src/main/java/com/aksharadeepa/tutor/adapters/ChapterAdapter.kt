package com.aksharadeepa.tutor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aksharadeepa.tutor.databinding.ItemChapterBinding
import com.aksharadeepa.tutor.models.ChapterWithProgress

class ChapterAdapter(private val listener: Listener) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {
    private val chapters = mutableListOf<ChapterWithProgress>()

    fun submit(newChapters: List<ChapterWithProgress>) {
        chapters.clear()
        chapters.addAll(newChapters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = chapters[position]
        holder.binding.apply {
            textChapter.text = "${chapter.position}. ${chapter.title}"
            checkCompleted.setOnCheckedChangeListener(null)
            checkCompleted.isChecked = chapter.completed
            checkCompleted.setOnCheckedChangeListener { _, isChecked ->
                listener.onCompletedChanged(chapter, isChecked)
            }
            buttonOpen.setOnClickListener { listener.onOpen(chapter) }
            buttonComplete.text = if (chapter.completed) "Completed" else "Mark Complete"
            buttonComplete.isEnabled = !chapter.completed
            buttonComplete.setOnClickListener { listener.onCompletedChanged(chapter, true) }
            buttonQuiz.setOnClickListener { listener.onQuiz(chapter) }
        }
    }

    override fun getItemCount(): Int = chapters.size

    class ChapterViewHolder(val binding: ItemChapterBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onOpen(chapter: ChapterWithProgress)
        fun onCompletedChanged(chapter: ChapterWithProgress, completed: Boolean)
        fun onQuiz(chapter: ChapterWithProgress)
    }
}
