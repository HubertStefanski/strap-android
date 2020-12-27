package com.hstefans.strap_android.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.hstefans.strap_android.R
import com.hstefans.strap_android.activities.AuthenticationActivity

class UserSettingsFragment : Fragment() {

    private val TAG = "UserSettingsFragment"
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_user_settings, container, false)
        val logoutButton: View = view.findViewById(R.id.logoutButton)

        logoutButton.setOnClickListener() {
            handleLogout()
        }
        // Inflate the layout for this fragment
        return view
    }


    // sign the current user out and direct the user back to the login screen
    private fun handleLogout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        if (auth.currentUser == null) {
            Toast.makeText(this.context, "Successfully logged out", Toast.LENGTH_LONG).show()
            Log.v(TAG, "userLoggedOut")
            val intent = Intent(activity, AuthenticationActivity::class.java)
            startActivity(intent)
        }
    }


}
