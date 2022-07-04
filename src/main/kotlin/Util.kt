import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/inputs/$name.txt").readLines()

fun convertBinary(binary : Long):Int{
    var num = binary
    var decimalNumber = 0
    var i = 0
    var remainder: Long

    while (num.toInt() != 0) {
        remainder = num % 10
        num /= 10
        decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}
