class Day03 : Puzzle<Int>("Day03", 198, 230) {
    override fun part1(input: Input): Int {


        var gamma: List<String> = emptyList()
        for (i in 0 until input[0].length) {
            gamma = if (input.count { it[i].code == 49 } > (input.size / 2)) {
                gamma.plus("1")
            } else {
                gamma.plus("0")
            }
        }

        var epsilon: List<String> = emptyList()
        for (element in gamma) {
            epsilon = if (element.toInt() == 1) {
                epsilon.plus("0")
            } else {
                epsilon.plus("1")
            }
        }

        return convertBinary(
            gamma.joinToString(separator = "").toLong()
        ) * convertBinary(epsilon.joinToString(separator = "").toLong())
    }

    override fun part2(input: Input): Int {
        var oxyGenRating = input
        var co2ScrubberRatting = input

        for (i in 0..input[0].length) {
            if (oxyGenRating.size > 1) {
                oxyGenRating = if (oxyGenRating.count { it[i].code == 49 } >= oxyGenRating.count { it[i].code == 48 }) {
                    oxyGenRating.filter { it[i].code == 49 }
                } else {
                    oxyGenRating.filter { it[i].code == 48 }
                }
            }
        }

        for (i in 0..input[0].length) {
            if (co2ScrubberRatting.size > 1) {
                co2ScrubberRatting =
                    if (co2ScrubberRatting.count { it[i].code == 49 } >= co2ScrubberRatting.count { it[i].code == 48 }) {
                        co2ScrubberRatting.filter { it[i].code == 48 }
                    } else {
                        co2ScrubberRatting.filter { it[i].code == 49 }
                    }
            }
        }
        return convertBinary(oxyGenRating[0].toLong()) * convertBinary(co2ScrubberRatting[0].toLong())

    }

}

