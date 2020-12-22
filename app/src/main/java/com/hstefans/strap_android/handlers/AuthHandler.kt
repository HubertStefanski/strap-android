//@author Hubert Stefanski

package com.hstefans.strap_android.handlers

class AuthHandler {

    /**
     * generates a new hash for for a string
     * @param str , the string to be hashed
     * @return hashed string in sha512
     */


     fun isValidEmail(str: String): Boolean {
        if ("""^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$""".toRegex().containsMatchIn(str)) {
            return true
        }
        return false
    }
}
