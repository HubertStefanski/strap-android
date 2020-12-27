package com.hstefans.strap_android.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hstefans.strap_android.R
import com.hstefans.strap_android.handlers.AuthHandler


class AuthenticationActivity : AppCompatActivity() {
    private val TAG = "AuthenticationActivity"

    private lateinit var auth: FirebaseAuth


    lateinit var email: EditText
    lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)

        Log.v(TAG, "Starting Logging")
        setContentView(R.layout.activity_authentication)

//        // Initialize variables
        email = findViewById<EditText>(R.id.emailText)
        password = findViewById<EditText>(R.id.passwordText)

        findViewById<Button>(R.id.loginButton).setOnClickListener() {
            authenticateUser()
        }

        findViewById<Button>(R.id.registerRouteButton).setOnClickListener() {
            val intent1 = Intent(this, RegistrationActivity::class.java)
            startActivity(intent1)
        }

    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = auth.currentUser
    }
    // check if the user exists, if exists then log in and send notifier to the application about which uid to use for model storage
    fun authenticateUser() {
        if (validateData(email.text.toString(), password.text.toString())) {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Login Failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        } else {
            Toast.makeText(
                this,
                "Invalid data filled in or empty fields",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Ensure the data is valid and not empty
    fun validateData(strEmail: String, strPass: String): Boolean {
        if (strEmail == "" || strPass == "") {
            return false
        }
        if (strPass.length < 6) {
            return false
        }
        val authHandler = AuthHandler()
        if (!authHandler.isValidEmail(strEmail)) {
            return false
        }
        return true
    }


    /**
     * initializes the values used by this activity
     * @return void
     */
    fun initView() {

    }
}

