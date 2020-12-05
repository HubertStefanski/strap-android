package com.hstefans.strap_android.fragments

import RecyclerItemClickListener
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hstefans.strap_android.R
import com.hstefans.strap_android.models.Task


class TaskFragment : Fragment() {
    val TAG = "TaskFragment"


    private var adapter: TaskAdapter? = null
    private val dbRef = FirebaseDatabase.getInstance().getReference("users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid).child("tasks")

    private lateinit var newTaskTitle: EditText
    private lateinit var newTaskLocation: EditText
    private lateinit var newTaskDescription: EditText
    private lateinit var newTaskButton: Button
    private lateinit var updateTaskButton: Button
    private lateinit var deleteTaskButton: Button
    private lateinit var doneTaskButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var chosenUID: String
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(
            R.layout.fragment_task,
            container,
            false
        )

        //Buttons & Views
        newTaskButton = view.findViewById(R.id.newTaskButton)
        updateTaskButton = view.findViewById(R.id.updateTaskButton)
        deleteTaskButton = view.findViewById(R.id.deleteTaskButton)
        doneTaskButton = view.findViewById(R.id.toggleTaskDoneStatus)
        recyclerView = view.findViewById(R.id.taskRecyclerView)
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val chosenTask = adapter!!.getItem(position)
                        newTaskTitle.setText(chosenTask.title)
                        newTaskLocation.setText(chosenTask.location)
                        newTaskDescription.setText(chosenTask.location)
                        chosenUID = chosenTask.uid
                        updateTaskButton.isEnabled = true
                        deleteTaskButton.isEnabled = true
                        doneTaskButton.isEnabled = true
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        // do whatever
                    }
                })
        )

        //TextFields
        newTaskTitle = view.findViewById(R.id.taskTitleTextField)
        newTaskLocation = view.findViewById(R.id.taskLocationTextField)
        newTaskDescription = view.findViewById(R.id.newDescriptionTextField)

        updateTaskButton.isEnabled = false
        doneTaskButton.isEnabled = false


        val options = FirebaseRecyclerOptions.Builder<Task>()
            .setQuery(dbRef, Task::class.java).build()

        adapter = TaskAdapter(options)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        recyclerView.adapter = adapter


        // Disable this button until a task is selected
        deleteTaskButton.isEnabled = false


        newTaskButton.setOnClickListener()
        {
            handleNewTask()
        }
        updateTaskButton.setOnClickListener()
        {
            handleUpdateTask()
        }
        deleteTaskButton.setOnClickListener()
        {
            dbRef.child(chosenUID).removeValue()
        }

        return view
    }

    //TODO implement ifTaskExists logic to prevent duplicate entries
    private fun handleNewTask() {
        if (validateData()) {
            val task = Task("",
                newTaskTitle.text.toString(),
                newTaskDescription.text.toString(),
                newTaskLocation.text.toString(),
                false)

            val key = dbRef.child("tasks").push().key
            if (key != null) {
                task.uid = key
                dbRef.child(key).setValue(task)
            }
        }
        clearFields()
    }


    private fun handleUpdateTask() {

    }


    fun createData() {

        val tasks: List<Task> = mutableListOf(
            Task("", "Title1", "WIT", "do something", false),
            Task("", "Title2", "WIT", "do something", false),
            Task("", "Title3", "WIT", "do something", false),
            Task("", "Title4", "WIT", "do something", false)
        )
        tasks.forEach {
            val key = dbRef.child("tasks").push().key
            if (key != null) {
                it.uid = key

                dbRef.child(key).setValue(it)
            }
        }
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }


    private fun validateData(): Boolean {
        if (newTaskTitle.text.toString() == "" ||
            newTaskDescription.text.toString() == "" ||
            newTaskLocation.text.toString() == ""
        ) {
            val errorToast = Toast.makeText(
                this.context,
                "Some fields are left empty!",
                Toast.LENGTH_SHORT
            )
            errorToast.show()
            return false
        }
        return true
    }

    private fun clearFields() {
        newTaskDescription.setText("")
        newTaskTitle.setText("")
        newTaskLocation.setText("")
    }
}

