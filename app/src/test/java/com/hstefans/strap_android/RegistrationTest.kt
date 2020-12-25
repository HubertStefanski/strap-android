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


class RegistrationTest() {
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
    fun testRegValidDataIsTrue() {
        assert(regActivity.validateData(goodEmail, goodLengthPw,goodLengthPw))
    }

    @Test
    fun testRegValidDataIsFalseWithBadEmail() {
        assert(!regActivity.validateData(badEmail, goodLengthPw,goodLengthPw))
    }

    @Test
    fun testRegValidDataIsFalseWithBadPassword() {
        assertEquals(false, regActivity.validateData(goodEmail, badLengthPw,badLengthPw))
    }

    @Test
    fun testRegValidDataIsFalseWithBadEmailAndPassword() {
        assertFalse(regActivity.validateData(badEmail, badLengthPw,badLengthPw))
    }
    @Test
    fun testRegValidDataIsFalseWithEmptyEmail() {
        assertFalse(regActivity.validateData(emptyString,goodLengthPw,goodLengthPw))
    }
    @Test
    fun testRegValidDataIsFalseWithEmptyPassword() {
        assertFalse(regActivity.validateData(emptyString,emptyString,emptyString))
    }
    @Test
    fun testRegValidDataIsFalseWhenPasswordsDontMatch(){
        assertFalse(regActivity.validateData(goodEmail,goodLengthPw,"somethingElse"))
    }


}