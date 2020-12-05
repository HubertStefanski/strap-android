package com.hstefans.strap_android.fragments

import android.view.LayoutInflater
import com.hstefans.strap_android.R
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.hstefans.strap_android.models.Task

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
class TaskAdapter(
    options: FirebaseRecyclerOptions<Task>
) : FirebaseRecyclerAdapter<Task, TaskAdapter.TaskViewholder>(options) {
    override fun onBindViewHolder(
        holder: TaskViewholder,
        position: Int, model: Task
    ) {

        holder.taskTitle.text = model.title
        
        holder.taskLocation.text = model.location

        holder.taskDescription.text = model.title
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewholder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task, parent, false)
        return TaskViewholder(view)
    }

    inner class TaskViewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var taskTitle: TextView = itemView.findViewById(R.id.taskCardTitle)
        var taskLocation: TextView = itemView.findViewById(R.id.taskCardLocation)
        var taskDescription: TextView = itemView.findViewById(R.id.TaskCardDescription)

    }
}