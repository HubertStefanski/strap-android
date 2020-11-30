//@author Hubert Stefanski

package com.hstefans.strap_android.handlers

import org.abstractj.kalium.crypto.Hash
import java.util.*


class AuthHandler {

    /**
     * generates a new hash for for a string
     * @param str , the string to be hashed
     * @return hashed string in sha512
     */

    //FIXME
    public fun hashString(str: String): String {
        var hash = Hash()
        return hash.sha256(str.toByteArray()).toString()

    }

    public fun isValidEmail(str: String): Boolean {
        if ("""^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$""".toRegex().containsMatchIn(str)) {
            return true
        }
        return false
    }
}
