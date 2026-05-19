package com.aksharadeepa.tutor.utils

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object SecurityUtils {
    fun sha256(input: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
            val builder = StringBuilder()
            for (b in hash) {
                builder.append(String.format("%02x", b))
            }
            builder.toString()
        } catch (e: NoSuchAlgorithmException) {
            input.hashCode().toString()
        }
    }
}
