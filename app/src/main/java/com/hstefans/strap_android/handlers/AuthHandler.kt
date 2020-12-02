//@author Hubert Stefanski

package com.hstefans.strap_android.handlers

import android.system.Os.bind
import org.abstractj.kalium.crypto.Hash
import org.abstractj.kalium.encoders.Encoder.HEX
import java.security.MessageDigest

import java.util.*


class AuthHandler {

    /**
     * generates a new hash for for a string
     * @param str , the string to be hashed
     * @return hashed string in sha512
     */

    //FIXME
    public fun hashString(str: String): String {
        val hash = Hash()
        val data: String = str
        val tb = hash.sha512(str.toByteArray())
        //TODO change back to return hash
        return str
    }


    public fun isValidEmail(str: String): Boolean {
        if ("""^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$""".toRegex().containsMatchIn(str)) {
            return true
        }
        return false
    }
}
