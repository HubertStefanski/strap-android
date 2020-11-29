//@author Hubert Stefanski

package com.hstefans.strap_android.handlers

import org.abstractj.kalium.crypto.Hash
import org.abstractj.kalium.encoders.Encoder.HEX



class AuthHandler {

    /**
     * generates a new hash for for a string
     * @param str , the string to be hashed
     * @return hashed string in sha512
     */
    public fun hashString(str: String): String {
        val hash = Hash()
        return HEX.encode(hash.sha512(str.toByteArray()))
    }
}