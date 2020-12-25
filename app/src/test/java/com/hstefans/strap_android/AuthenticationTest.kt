package com.hstefans.strap_android

import android.R
import android.app.Activity
import android.content.Intent
import android.widget.EditText
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.hstefans.strap_android.activities.AuthenticationActivity
import com.hstefans.strap_android.activities.RegistrationActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.internals.AnkoInternals.createAnkoContext
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowToast
import java.lang.Exception


class AuthenticationTest() {
    private val activity: AuthenticationActivity = AuthenticationActivity()
    private val regActivity: RegistrationActivity  = RegistrationActivity()

    private val emptyString = ""
    private val testEmail = "test@gmail.com"
    private val testPassword = "testPassword123456"
    private val goodEmail = "something@gmail.com"
    private val badEmail = "badmail.com"
    private val goodLengthPw =
        "123456" // Passwords have to be 6 chars long to be considered as valid
    private val badLengthPw = "12345"



    @Test
    fun testAuthValidDataIsTrue() {
        assert(activity.validateData(goodEmail, goodLengthPw))
    }

    @Test
    fun testAuthValidDataIsFalseWithBadEmail() {
        assert(!activity.validateData(badEmail, goodLengthPw))
    }

    @Test
    fun testAuthValidDataIsFalseWithBadPassword() {
        assertEquals(false, activity.validateData(goodEmail, badLengthPw))
    }

    @Test
    fun testAuthValidDataIsFalseWithBadEmailAndPassword() {
        assertFalse(activity.validateData(badEmail, badLengthPw))
    }
    @Test
    fun testAuthValidDataIsFalseWithEmptyEmail() {
        assertFalse(activity.validateData(emptyString,goodLengthPw))
    }
    @Test
    fun testAuthValidDataIsFalseWithEmptyPassword() {
        assertFalse(activity.validateData(emptyString,emptyString))
    }


}