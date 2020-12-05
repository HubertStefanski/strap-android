package com.hstefans.strap_android.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hstefans.strap_android.R
import com.hstefans.strap_android.models.Task


class TaskFragment : Fragment() {
    val TAG = "TaskFragment"
    private lateinit var taskListView: ListView;

    //    private var recyclerView: RecyclerView? = null
    private var adapter: TaskAdapter? = null
//    private var itemSelected = false
//    private var selectedPosition = 0
//    var listItems = ArrayList<String>()
//    var listKeys = ArrayList<String>()
////    private lateinit var adapter: DataAdapter

    private val dbRef = FirebaseDatabase.getInstance().getReference("users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid).child("tasks")


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(
            R.layout.fragment_task,
            container,
            false
        )


//        taskListView = view.findViewById(R.id.taskListView);
        val newTaskButton: View = view.findViewById(R.id.newTaskButton)
        val updateTaskButton: Button = view.findViewById(R.id.updateTaskButton)
        val deleteTaskButton: Button = view.findViewById(R.id.deleteTaskButton)
        val doneTaskButton: Button = view.findViewById(R.id.toggleTaskDoneStatus)
        val recyclerView: RecyclerView = view.findViewById(R.id.taskRecyclerView);

        updateTaskButton.isEnabled = false
        doneTaskButton.isEnabled = false


        val options: FirebaseRecyclerOptions<Task> = FirebaseRecyclerOptions.Builder<Task>()
            .setQuery(dbRef, Task::class.java)
            .build()
        adapter = TaskAdapter(options)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        recyclerView.adapter = adapter

        // Disable this button until a task is selected
        deleteTaskButton.isEnabled = false

        newTaskButton.setOnClickListener()
        {
//            handleNewTask()
            createData(dbRef)
        }
        updateTaskButton.setOnClickListener()
        {
            handleUpdateTask()
        }
        deleteTaskButton.setOnClickListener()
        {
//            taskListView.setItemChecked(selectedPosition, false)
//            dbRef.child(listKeys[selectedPosition]).removeValue()
//            Log.v(TAG, "Task deleted $selectedPosition")
        }

        return view
    }

    private fun handleNewTask() {
        // Write a message to the database
//        Log.v(TAG, myRef.key.toString())
//        createData(myRef)

    }

    private fun populateListView() {

    }


    private fun handleUpdateTask() {

    }


    fun createData(ref: DatabaseReference) {
        val ref = FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("tasks")
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

}

