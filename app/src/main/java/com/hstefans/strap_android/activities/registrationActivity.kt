//@author Hubert Stefanski

package com.hstefans.strap_android.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hstefans.strap_android.R
import com.hstefans.strap_android.handlers.AuthHandler


class registrationActivity : AppCompatActivity() {
    private val TAG = "RegistrationActivty"


    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var confirmPassword: EditText
    lateinit var phoneNo: EditText
    lateinit var registerButton: Button
    lateinit var returnButton: Button
    var authHandler: AuthHandler = AuthHandler()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration)
        Log.v(TAG, "Starting Logging");

        // Initialize view components
        email = findViewById<EditText>(R.id.registerEmailText)
        password = findViewById<EditText>(R.id.registerPasswordText)
        confirmPassword = findViewById<EditText>(R.id.registerConfirmPasswordText)
        phoneNo = findViewById<EditText>(R.id.registerPhoneNoText)
        // Initialize other variables
//        authHandler = AuthHandler()


        auth = FirebaseAuth.getInstance()

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            handleRegistration()

        }
        findViewById<Button>(R.id.returnButton).setOnClickListener {
            setContentView(R.layout.activity_authentication)
        }


    }

    fun handleRegistration() {
        Log.v(TAG, "${password.text.toString()}, ${confirmPassword.text.toString()}")
        if (validateData(
                email.text.toString(),
                password.text.toString(),
                confirmPassword.text.toString()
            )
        ) {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.v(TAG, "createUserWithEmail:success")
                        val user: FirebaseUser? = auth.getCurrentUser()
//                            SwitchToMainView(user)
                    } else {
                        // If sign in fails, display a message to the u-ser.
                        Log.v(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@registrationActivity, "registration failed.",
                            Toast.LENGTH_SHORT
                        ).show()
//                            updateUI(null)
                    }
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
        if (strPass.length < 6 || strConfirmPass.length < 6) {
            val errorToast = Toast.makeText(
                this@registrationActivity,
                "Password is too short, must be at least 6 characters long!",
                Toast.LENGTH_SHORT
            )
            errorToast.show()
            return false
        }
        if (strPass != strConfirmPass) {
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
