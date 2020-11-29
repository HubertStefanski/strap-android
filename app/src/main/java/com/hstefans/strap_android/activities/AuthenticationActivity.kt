package com.hstefans.strap_android.activities

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hstefans.strap_android.R


class AuthenticationActivity : AppCompatActivity() {
    private val TAG = "AuthenticationActivity"

    private lateinit var auth: FirebaseAuth


    lateinit var email: EditText
    lateinit var password: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        Log.v(TAG, "Starting Logging");
        setContentView(R.layout.activity_authentication)
        initView()



    }
    /**
     * main login handler
     * @return void
     */
    // TODO implement me
    fun onLogin(){
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

    /**
     * initializes the values used by this activity
     * @return void
     */
    fun initView() {
        email = findViewById(R.id.emailText)
        password = findViewById(R.id.passwordText)

    }


}