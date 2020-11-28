package com.hstefans.strap_android.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hstefans.strap_android.R


class AuthenticationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val TAG = "AuthenticationActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        Log.v(TAG, "Starting Logging");

        //initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                }
            })
    }
}