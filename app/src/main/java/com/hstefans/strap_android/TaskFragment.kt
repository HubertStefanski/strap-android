package com.hstefans.strap_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.hstefans.strap_android.models.Task

class TaskFragment : Fragment() {
    val TAG = "TaskFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_task, container, false)

        val newTaskButton: View = view.findViewById(R.id.newTaskButton)
        val updateTaskButton: Button = view.findViewById(R.id.updateTaskButton)
        val deleteTaskButton: Button = view.findViewById(R.id.deleteTaskButton)

        newTaskButton.setOnClickListener() {
            handleNewTask()
        }
        updateTaskButton.setOnClickListener() {
            handleUpdateTask()
        }
        deleteTaskButton.setOnClickListener() {
            handleDeleteTask()
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun handleNewTask() {
        // Write a message to the database
        val database = FirebaseDatabase.getInstance();
        var myRef = database.getReference("/users/tasks/exampleOwner/tasks")
        Log.v(TAG, myRef.key.toString())
        createData(myRef)

    }

    private fun handleUpdateTask() {

    }

    private fun handleDeleteTask() {

    }

    fun createData(ref: DatabaseReference) {
        var ref = FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
        val tasks: List<Task> = mutableListOf(
            Task("someuid", "Title1", "WIT", "do something", "exampleOwner", false),
            Task("someuid2", "Title2", "WIT", "do something", "exampleOwner", false),
            Task("someuid3", "Title3", "WIT", "do something", "exampleOwner", false),
            Task("someuid4", "Title4", "WIT", "do something", "exampleOwner", false)
        )
        tasks.forEach {
            val key = ref.child("tasks").push().key
            if (key != null) {
                it.uid = key

                ref.child("tasks").child(key).setValue(it)
            }
        }
    }
}

