//@author Hubert Stefanski

package com.hstefans.strap_android.activities

import android.R.attr.password
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.hstefans.strap_android.R


class registrationActivity : AppCompatActivity() {
    private val TAG = "AuthenticationActivity"


    // TODO Implement me
//    lateinit var email: EditText
//    lateinit var password: EditText
//    lateinit var confirmPassword: EditText
//    lateinit var phoneNo: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        Log.v(TAG, "Starting Logging");
        initView()

        auth = FirebaseAuth.getInstance()


    }
    //TODO handle the registration action
//    fun onRegister(){
//     validateData & ensure the passwords hash matches
//    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener{ task ->
//        if(task.isSuccessful){
//            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }else {
//            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
//        }
//    })
//    }

    /**
     * initializes the values used by this activity
     * @return void
     */
    fun initView() {
//        TODO Implement me
//        email = findViewById(R.id.registerEmailText)
//        password = findViewById(R.id.registerPasswordText)
//        confirmPassword = FindViewById(R.id.registerConfirmPasswordText)
//        phoneNo = FindViewbyId(r.id.registerPhoneNoText)
    }
        fun validateData() {
//            TODO Implement me
//            function to ensure data is a valid length and type, username is unique and that password & confirmPassword match i.e generate the same hash

    }
}