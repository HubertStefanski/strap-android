package com.hstefans.strap_android.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.hstefans.strap_android.R
import com.hstefans.strap_android.models.Task
import org.jetbrains.anko.find


class TaskFragment : Fragment() {
    val TAG = "TaskFragment"
    private val mListView: ListView? = null
    private lateinit var taskListView: ListView;
    private var searchMode = false;
    private var itemSelected = false;
    private var selectedPosition = 0;
    var listItems = ArrayList<String>()
    var listKeys = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null

    val database = FirebaseDatabase.getInstance();
    val dbRef = FirebaseDatabase.getInstance().getReference("users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid).child("tasks")


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(
            R.layout.fragment_task,
            container,
            false
        )

        val taskListView: ListView = view.findViewById(R.id.taskListView);
        val newTaskButton: View = view.findViewById(R.id.newTaskButton)
        val updateTaskButton: Button = view.findViewById(R.id.updateTaskButton)
        val deleteTaskButton: Button = view.findViewById(R.id.deleteTaskButton)
        val doneTaskButton: Button = view.findViewById(R.id.toggleTaskDoneStatus)
        updateTaskButton.setEnabled(false)
        doneTaskButton.setEnabled(false)


        adapter = this.context?.let {
            ArrayAdapter<String>(
                it.applicationContext,
                android.R.layout.simple_list_item_single_choice,
                listItems
            )
        }
        taskListView.setAdapter(adapter);
        taskListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        taskListView.setOnItemClickListener(
            OnItemClickListener { parent, view, position, id ->
                selectedPosition = position
                itemSelected = true
                deleteTaskButton.setEnabled(true)
            })

        addChildEventListener()
        // Disable this button until a task is selected
        deleteTaskButton.setEnabled(false)

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
            taskListView.setItemChecked(selectedPosition, false);
            dbRef.child(listKeys.get(selectedPosition)).removeValue();
            Log.v(TAG,"Task deleted $selectedPosition")
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

    private fun addChildEventListener() {
        val childListener: ChildEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                adapter!!.add(
                    dataSnapshot.child("description").value as String?
                )
                listKeys.add(dataSnapshot.key!!)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val key = dataSnapshot.key
                val index = listKeys.indexOf(key)
                if (index != -1) {
                    listItems.removeAt(index)
                    listKeys.removeAt(index)
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        dbRef.addChildEventListener(childListener)
    }
}

