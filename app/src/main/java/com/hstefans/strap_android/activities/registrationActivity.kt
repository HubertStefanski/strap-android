//@author Hubert Stefanski

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
import com.hstefans.strap_android.R
import com.hstefans.strap_android.handlers.AuthHandler
import org.jetbrains.anko.find
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt


class registrationActivity : AppCompatActivity() {
    private val TAG = "AuthenticationActivity"


    // TODO Implement me
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var confirmPassword: EditText
    lateinit var phoneNo: EditText
    lateinit var registerButton: Button
    private lateinit var authHandler: AuthHandler
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)
        Log.v(TAG, "Starting Logging");

        // Initialize view components
        email = findViewById(R.id.registerEmailText)
        password = findViewById(R.id.registerPasswordText)
        confirmPassword = findViewById(R.id.registerConfirmPasswordText)
        phoneNo = findViewById(R.id.registerPhoneNoText)
        registerButton = findViewById(R.id.registerButton)
        // Initialize other variables
        authHandler = AuthHandler()


        auth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener() {
            if (validateData(email.toString(), password.toString(), confirmPassword.toString())) {
                auth.createUserWithEmailAndPassword(
                    email.toString(),
                    authHandler.hashString(password.toString())
                ).addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }


    }

    fun validateData(strEmail: String, strPass: String, strConfirmPass: String): Boolean {
        if (strEmail == "" || strPass == "" || strConfirmPass == "") {
            val errorToast = Toast.makeText(
                this@registrationActivity,
                "Some fields are left empty!",
                Toast.LENGTH_SHORT
            )
            errorToast.show()
            return false
        }
        if (authHandler.hashString(strPass) != authHandler.hashString(strConfirmPass)
        ) {
            val errorToast = Toast.makeText(
                this@registrationActivity,
                "Passwords do not match!",
                Toast.LENGTH_SHORT
            )
            errorToast.show()
            return false
        }
        if (!authHandler.isValidEmail(strEmail)) {
            val errorToast = Toast.makeText(
                this@registrationActivity,
                "Email is not valid, try again with a valid email",
                Toast.LENGTH_SHORT
            )
            errorToast.show()
            return false
        }
        //TODO implement another check for phone length and validity
        return true
    }
}
