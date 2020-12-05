package com.hstefans.strap_android.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.hstefans.strap_android.R
import com.hstefans.strap_android.models.Task


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
class TaskAdapter(
    options: FirebaseRecyclerOptions<Task>
) : FirebaseRecyclerAdapter<Task, TaskAdapter.TaskViewholder>(options) {
    private val context: Context? = null
    private val itemListener: RecyclerViewClickListener? = null

    override fun onBindViewHolder(
        holder: TaskViewholder,
        position: Int, model: Task
    ) {
        holder.uid.text = model.uid

        holder.title.text = model.title

        holder.location.text = model.location

        holder.description.text = model.title

        holder.doneStatus.text = model.doneStatus.toString()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewholder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task, parent, false)

        return TaskViewholder(view)
    }

    class TaskViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var uid: TextView = itemView.findViewById(R.id.taskCardUID)
        var title: TextView = itemView.findViewById(R.id.taskCardTitle)
        var location: TextView = itemView.findViewById(R.id.taskCardLocation)
        var description: TextView = itemView.findViewById(R.id.taskCardDescription)
        var doneStatus: TextView = itemView.findViewById(R.id.taskCardDoneStatus)
    }
}

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(v: View?, position: Int)
}
