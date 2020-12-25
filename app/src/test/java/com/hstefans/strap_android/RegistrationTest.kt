package com.hstefans.strap_android

import com.hstefans.strap_android.activities.RegistrationActivity

import org.junit.Assert.*

import org.junit.Test



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