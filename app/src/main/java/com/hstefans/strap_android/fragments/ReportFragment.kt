package com.hstefans.strap_android.fragments

import RecyclerItemClickListener
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hstefans.strap_android.R
import com.hstefans.strap_android.adapters.ReportAdapter
import com.hstefans.strap_android.models.Report
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ReportFragment : Fragment() {
    val TAG = "TaskFragment"


    private var storagePhotoRef: String = ""
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 71
    private var adapter: ReportAdapter? = null
    private val dbRef = FirebaseDatabase.getInstance().getReference("users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid).child("reports")

    //Firebase
    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null


    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    private val currentDate: String = sdf.format(Date())
    private lateinit var reportPhotoImageView: ImageView
    private lateinit var newReportLocation: EditText
    private lateinit var newReportDamage: EditText
    private lateinit var newReportPhotoref: String
    private lateinit var reportDateTextView: TextView
    private lateinit var newReportButton: Button
    private lateinit var updateReportButton: Button
    private lateinit var deleteReportButton: Button
    private lateinit var attachPhotoReportButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var chosenReport: Report

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(
            R.layout.fragment_report,
            container,
            false
        )

        storage = FirebaseStorage.getInstance();
        storageReference = storage!!.reference;

        //Buttons & Views
        reportPhotoImageView = view.findViewById(R.id.reportPhotoImageView)
        newReportButton = view.findViewById(R.id.newReportButton)
        newReportDamage = view.findViewById(R.id.reportDamageTextField)
        updateReportButton = view.findViewById(R.id.updateReportButton)
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
                        storagePhotoRef = chosenReport.photoRef
                        updateReportButton.isEnabled = true
                        deleteReportButton.isEnabled = true
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                        // do whatever
                    }
                })
        )

        //TextFields
        newReportLocation = view.findViewById(R.id.reportLocationTextField)
        newReportDamage = view.findViewById(R.id.reportDamageTextField)

        updateReportButton.isEnabled = false

        attachPhotoReportButton.setOnClickListener() {
            chooseImage();
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
            handleNewReport()
        }
        updateReportButton.setOnClickListener()
        {
            handleUpdateReport()
        }
        deleteReportButton.setOnClickListener()
        {
            storage!!.getReferenceFromUrl(chosenReport.photoRef).delete()
            dbRef.child(chosenReport.uid).removeValue()
            clearFields()
        }

        return view
    }

    // handle report creation, upload image, wait for downloadable URL and proceed to create the model reffering to the uploaded image
    private fun handleNewReport() {
        if (validateData()) {
            uploadImage()
            val report =
                Report("",
                    newReportLocation.text.toString(),
                    newReportDamage.text.toString(),
                    currentDate,
                    storagePhotoRef
                )

            // set the key as the uid to prevent conflict
            val key = dbRef.child("reports").push().key
            if (key != null) {
                report.uid = key
                dbRef.child(key).setValue(report)
            }
        }
        clearFields()
    }

    // read new data from reports, update date and time and push new values to the uid.
    private fun handleUpdateReport() {
        if (validateData()) {
            val report =
                Report("",
                    newReportLocation.text.toString(),
                    newReportDamage.text.toString(),
                    currentDate,
                    storagePhotoRef
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

    // Function to tell the app to stop getting data from the database
    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    //    data validation function, to be run before updates and create
    private fun validateData(): Boolean {
        if (newReportLocation.text.toString() == "" ||
            newReportDamage.text.toString() == ""
        ) {
            val errorToast = Toast.makeText(
                this.context,
                "some fields are left empty or no photo!",
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
        newReportPhotoref = ""
        reportPhotoImageView.setImageBitmap(null)
    }

    // choose an image from the phones filesystem
    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    // wait for image and set it as the preview image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.context?.contentResolver,
                    filePath)
                reportPhotoImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // check if the filepath exists, if it does then start upload and set random uid
    private fun uploadImage() {
        if (filePath != null) {
            val progressDialog = ProgressDialog(this.context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val ref = storageReference!!.child("images").child(UUID.randomUUID().toString())
            storagePhotoRef = ref.toString()
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this.context, "Uploaded", Toast.LENGTH_SHORT).show()
                    // wait for downloadable link (required for image preview in reportCardHolder)
                    ref.downloadUrl.addOnCompleteListener {
                        if (it.isComplete) {
                            storagePhotoRef = it.result.toString()
                        }
                    };
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this.context, "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }
}



