package com.hstefans.strap_android.fragments

import RecyclerItemClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hstefans.strap_android.R
import com.hstefans.strap_android.models.Report
import com.hstefans.strap_android.models.Task


class ReportFragment : Fragment() {
    val TAG = "TaskFragment"


    private var adapter: ReportAdapter? = null
    private val dbRef = FirebaseDatabase.getInstance().getReference("users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid).child("reports")

    private lateinit var newReportLocation: EditText
    private lateinit var newReportDamage: EditText
    private lateinit var newReportPhotoref: EditText
    private lateinit var reportDateTextView: TextView
    private lateinit var newReportButton: Button
    private lateinit var updateReportButton: Button
    private lateinit var deleteReportButton: Button
    private lateinit var attachPhotoReportButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var chosenReport: Report

    //    private lateinit var chosenUID: String
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(
            R.layout.fragment_report,
            container,
            false
        )

        //Buttons & Views
        newReportButton = view.findViewById(R.id.newReportButton)
        newReportDamage = view.findViewById(R.id.updateReportButton)
        deleteReportButton = view.findViewById(R.id.deleteReportButton)
        attachPhotoReportButton = view.findViewById(R.id.reportAttachPhotoButton)
        recyclerView = view.findViewById(R.id.reportRecyclerView)
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        chosenReport = adapter!!.getItem(position)
                        newReportDamage.setText(chosenReport.damage)
                        newReportLocation.setText(chosenReport.location)
                        reportDateTextView.text = chosenReport.date
                        updateReportButton.isEnabled = true
                        deleteReportButton.isEnabled = true
                        attachPhotoReportButton.isEnabled = true
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        // do whatever
                    }
                })
        )

        //TextFields
        newReportLocation = view.findViewById(R.id.reportLocationTextField)
        newReportDamage = view.findViewById(R.id.reportDamageTextField)
//        newReportPhotoref = view.findViewById(R.id.reportPhotoImageView)

        updateReportButton.isEnabled = false
        attachPhotoReportButton.isEnabled = false

        attachPhotoReportButton.setOnClickListener() {
            //TODO
            //handleAttachPhoto()
        }

        val options = FirebaseRecyclerOptions.Builder<Report>()
            .setQuery(dbRef, Report::class.java).build()

        adapter = ReportAdapter(options)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        recyclerView.adapter = adapter


        // Disable this button until a task is selected
        deleteReportButton.isEnabled = false


        newReportButton.setOnClickListener()
        {
            //TODO handle newReport
            //            handleNewReport()
        }
        updateReportButton.setOnClickListener()
        {
//            TODO handleUpdateReport
//            handleUpdateReport()
        }
        deleteReportButton.setOnClickListener()
        {
            //TODO
//            Handle report deletion

//            dbRef.child(chosenReport.uid).removeValue()
        }

        return view
    }



    //TODO implement ifTaskExists logic to prevent duplicate entries
    private fun handleNewReport() {
        if (validateData()) {
            val report = Report("",
                newReportLocation.text.toString(),
                newReportDamage.text.toString(),
                java.util.Calendar.getInstance().toString(),
                newReportPhotoref.text.toString()
                )

            val key = dbRef.child("reports").push().key
            if (key != null) {
                report.uid = key
                dbRef.child(key).setValue(report)
            }
        }
        clearFields()
    }


    private fun handleUpdateTask() {
        if (validateData()) {
            val report = Report("",
                newReportLocation.text.toString(),
                newReportDamage.text.toString(),
                java.util.Calendar.getInstance().toString(),
                newReportPhotoref.text.toString()
            )
            dbRef.child(chosenReport.uid).setValue(report)
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
        if (newReportLocation.text.toString() == "" ||
            newReportDamage.text.toString() == "" ||
            newReportPhotoref.text.toString() == ""
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
        newReportLocation.setText("")
        newReportDamage.setText("")
        newReportPhotoref.setText("")
    }
}

