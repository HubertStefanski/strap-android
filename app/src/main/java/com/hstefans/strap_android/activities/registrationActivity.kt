package com.hstefans.strap_android.activities

import android.R.attr.password
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hstefans.strap_android.R


class registrationActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth? = null
    private val TAG = "AuthenticationActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        Log.v(TAG, "Starting Logging");



        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(FragmentActivity.TAG, "createUserWithEmail:success")
                        val user: FirebaseUser = mAuth.getCurrentUser()
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(
                            FragmentActivity.TAG,
                            "createUserWithEmail:failure",
                            task.exception
                        )
                        Toast.makeText(
                            this@EmailPasswordActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }

                    // ...
                })
    }
}