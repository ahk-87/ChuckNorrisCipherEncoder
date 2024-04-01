package chucknorris

fun encode(sentence: String): String {
    val binary = sentence.map { it.code.toString(2).padStart(7, '0') }.joinToString("")
    return buildString {
        val map = mapOf('1' to " 0 0", '0' to " 00 0")
        var prevChar = '2'
        binary.forEach {
            if (it == prevChar) {
                append("0")
            } else {
                prevChar = it
                append("${map[it]}")
            }
        }
        deleteCharAt(0)
    }
}

fun decode(zeros: String): String {
    val map = mapOf("0" to "1", "00" to "0")
    val binary = zeros.split(" ")
        // chunk every group and using transform to convert each list to 0's or 1's
        .chunked(2) { map[it[0]]!!.repeat(it[1].length) }
        .joinToString("")
    if (binary.length % 7 != 0) throw IllegalArgumentException()
    return binary.chunked(7) { it.toString().toInt(2).toChar() }.joinToString("")
}

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        val command = readln().trim()
        val text: String
        when (command) {
            "encode" -> {
                println("Input string:")
                text = readln()
                println("Encoded string:")
                println(encode(text))
            }

            "decode" -> {
                println("Input encoded string:")
                text = readln()
                try {
                    decode(text).run {
                        println("Decoded string:")
                        println(this)
                    }
                } catch (e: Exception) {
                    println("Encoded string is not valid.")
                }
            }

            "exit" -> {
                println("Bye!"); break
            }

            else -> println("There is no '$command' operation")
        }
    }
}