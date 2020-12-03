package com.hstefans.strap_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hstefans.strap_android.R

lateinit var logoutButton: Button

class TaskFragment : Fragment() {
    override fun onCreateView(
//        logoutButton = R.
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }
}