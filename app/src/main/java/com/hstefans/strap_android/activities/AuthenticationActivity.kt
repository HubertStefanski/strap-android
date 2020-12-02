package com.hstefans.strap_android.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hstefans.strap_android.R


class AuthenticationActivity : AppCompatActivity() {
    private val TAG = "AuthenticationActivity"

    private lateinit var auth: FirebaseAuth


    lateinit var emailText: EditText
    lateinit var passwordText: EditText
    lateinit var registerRouteButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)

//        // Initialize variables
//        emailText = findViewById(R.id.emailText)
//        passwordText = findViewById(R.id.passwordText)


        Log.v(TAG, "Starting Logging")
        setContentView(R.layout.activity_authentication)

      findViewById<Button>(R.id.registerRouteButton).setOnClickListener() {
            val intent1 = Intent(this, registrationActivity::class.java)
            startActivity(intent1)
        }

    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser: FirebaseUser? = auth.getCurrentUser()
//        updateUI(currentUser)
    }

    /**
     * main login handler
     * @return void
     */
// TODO implement me
    fun onLogin() {
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
//            if(task.isSuccessful) {
//                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }else {
//                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
//            }
//        })
    }


}


/**
 * initializes the values used by this activity
 * @return void
 */
fun initView() {

}

