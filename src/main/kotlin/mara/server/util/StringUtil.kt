package mara.server.util

import kotlin.random.Random

class StringUtil {

    companion object {
        fun generateRandomString(from: Int, to: Int): String {
            val charPool = ('A'..'Z') + ('a'..'z') + ('0'..'9')

            return (1..Random.nextInt(from, to))
                .map { charPool.random() }
                .joinToString("")
        }
    }
}
