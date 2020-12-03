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
    lateinit var loginButton: Button
    lateinit var registerRouteButton: Button


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
            val intent1 = Intent(this, registrationActivity::class.java)
            startActivity(intent1)
        }

    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = auth.currentUser
//        updateUI(currentUser)
    }

    /**
     * main login handler
     * @return void
     */
    private fun authenticateUser() {
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
                            "Login Failed, invalid credentials or non-existent user",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
    }


    fun validateData(strEmail: String, strPass: String): Boolean {
        if (strEmail == "" || strPass == "") {
            val errorToast = Toast.makeText(
                this@AuthenticationActivity,
                "Some fields are left empty!",
                Toast.LENGTH_SHORT
            )
            errorToast.show()
            return false
        }
        if (strPass.length < 6) {
            val errorToast = Toast.makeText(
                this@AuthenticationActivity,
                "Password is too short, must be at least 6 characters long!",
                Toast.LENGTH_SHORT
            )
            errorToast.show()
            return false
        }
        val authHandler = AuthHandler()
        if (!authHandler.isValidEmail(strEmail)) {
            val errorToast = Toast.makeText(
                this@AuthenticationActivity,
                "Email is not valid, try again with a valid email",
                Toast.LENGTH_SHORT
            )
            errorToast.show()
            return false
        }
        //TODO implement another check for phone length and validity
        return true
    }


    /**
     * initializes the values used by this activity
     * @return void
     */
    fun initView() {

    }
}

