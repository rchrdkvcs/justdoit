package com.example.justdoit

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val onTaskChecked: (Task) -> Unit) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textview_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textview_description)
        private val deadlineTextView: TextView = itemView.findViewById(R.id.textview_deadline)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_completed)

        fun bind(task: Task) {
            titleTextView.text = task.title
            
            if (task.description.isNullOrEmpty()) {
                descriptionTextView.visibility = View.GONE
            } else {
                descriptionTextView.visibility = View.VISIBLE
                descriptionTextView.text = task.description
            }

            if (task.deadline.isNullOrEmpty()) {
                deadlineTextView.visibility = View.GONE
            } else {
                deadlineTextView.visibility = View.VISIBLE
                deadlineTextView.text = itemView.context.getString(R.string.task_deadline_format, task.deadline)
            }

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = task.isCompleted
            
            updateStrikeThrough(task.isCompleted)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onTaskChecked(task.copy(isCompleted = isChecked))
            }
        }

        private fun updateStrikeThrough(isCompleted: Boolean) {
            if (isCompleted) {
                titleTextView.paintFlags = titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                titleTextView.paintFlags = titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem
    }
}
