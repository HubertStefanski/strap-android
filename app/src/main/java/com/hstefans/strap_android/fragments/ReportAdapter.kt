package com.hstefans.strap_android.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hstefans.strap_android.R
import com.hstefans.strap_android.models.Report


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
class ReportAdapter(
    options: FirebaseRecyclerOptions<Report>
) : FirebaseRecyclerAdapter<Report, ReportAdapter.ReportViewholder>(options) {
    private val context: Context? = null
    private val itemListener: RecyclerViewClickListener? = null
    //Firebase
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null


    override fun onBindViewHolder(
        holder: ReportViewholder,
        position: Int, model: Report
    ) {
        holder.uid.text = model.uid

        holder.damage.text = model.damage

        holder.location.text = model.location

        holder.date.text = model.date
        holder.photoRef.text = "could not find image in storage"
        if (model.photoRef != "") {
            holder.photoRef.text = ""
            storage = FirebaseStorage.getInstance();
            storageReference = storage!!.reference;
            val gsReference = storage!!.getReferenceFromUrl(model.photoRef)

            val ONE_MEGABYTE: Long = 1024 * 1024
            gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                holder.reportCardImagePreview.setImageBitmap(Bitmap.createScaledBitmap(bmp,
                    holder.reportCardImagePreview.width,
                    holder.reportCardImagePreview.height,
                    false))
            }.addOnFailureListener {

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportViewholder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.report, parent, false)

        return ReportViewholder(view)
    }

    class ReportViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var uid: TextView = itemView.findViewById(R.id.reportCardUID)
        var damage: TextView = itemView.findViewById(R.id.reportCardDamage)
        var location: TextView = itemView.findViewById(R.id.reportCardLocation)
        var date: TextView = itemView.findViewById(R.id.reportDateText)
        var photoRef: TextView = itemView.findViewById(R.id.reportCardPhotoRef)
        var reportCardImagePreview: ImageView = itemView.findViewById(R.id.reportCardImagePreview)
    }
}

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(v: View?, position: Int)
}
