package com.hstefans.strap_android.fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hstefans.strap_android.R
import com.hstefans.strap_android.models.Report
import java.io.InputStream


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
class ReportAdapter(
    options: FirebaseRecyclerOptions<Report>
) : FirebaseRecyclerAdapter<Report, ReportAdapter.ReportViewholder>(options) {
    private val context: Context? = null
    private val itemListener: RecyclerViewClickListener? = null
    var storage = FirebaseStorage.getInstance()


    override fun onBindViewHolder(
        holder: ReportViewholder,
        position: Int, model: Report
    ) {
        holder.uid.text = model.uid

        holder.damage.text = model.damage

        holder.location.text = model.location

        holder.date.text = model.date
//        Log.d("SOMETHING", model.photoRef)
//        this.context?.let {
//            Glide.with(it).load(model.photoRef).into(holder.reportCardImagePreview)
//        }
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
        var reportCardImagePreview: ImageView = itemView.findViewById(R.id.reportCardImagePreview)
    }
}

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(v: View?, position: Int)
}

@GlideModule
class Glide: AppGlideModule(){

    override fun registerComponents(
        context: android.content.Context,
        glide: Glide,
        registry: Registry
    ) {
        super.registerComponents(context, glide, registry)
        registry.append(
            StorageReference::class.java, InputStream::class.java,
            FirebaseImageLoader.Factory()
        )

    }
}

