class Day08 : Puzzle<Int>("Day08", 26, 61229) {
    override fun part1(input: Input): Int {
        val fourDigitCodes = input
            .map { line ->
                line
                    .split("|")
                    .drop(0)[1]
            }
            .map { four ->
                four
                    .split(" ")
                    .map { i -> i.length }.drop(1)
            }


        return fourDigitCodes.sumOf { list -> list.filter { l -> l == 2 || l == 3 || l == 4 || l == 7 }.size }
    }

    override fun part2(input: Input): Int {

        var sum = 0
        // List of Pairs of List of 7segments of 1..10 and the 4 to decode digits
        val stringPairs = input.map { line ->
            line
                .split("|").zipWithNext()[0]
        }.map { pair ->
            Pair<List<String>, List<String>>(pair.first.split(" ").filter { it.isNotBlank() },
                pair.second.split(" ").filter { it.isNotBlank() })
        }
        //map of decoded Strings the num to the 7 Segment
        var mapOfOneToTen: Map<Int, String> = mapOf()

        //goes through every Pair
        for (pairs in stringPairs) {

            //adds the through length defined codes
            for (i in 0..9) {
                mapOfOneToTen =
                    when (pairs.first[i].length) {
                        2 -> mapOfOneToTen.plus(Pair(1, pairs.first[i]))
                        3 -> mapOfOneToTen.plus(Pair(7, pairs.first[i]))
                        4 -> mapOfOneToTen.plus(Pair(4, pairs.first[i]))
                        7 -> mapOfOneToTen.plus(Pair(8, pairs.first[i]))
                        else -> mapOfOneToTen
                    }
            }

            //regex for all letters in 1
            val segmentsInOne = Regex("[${mapOfOneToTen.getValue(1)}]")
            // regex for all letters in 4
            val segmentsInFour = Regex("[${mapOfOneToTen.getValue(4)}]")

            //deducing 5 and 6 Segment long numbers
            for (i in 0..9) {
                val min4: String = pairs.first[i].replace(segmentsInFour, "")
                mapOfOneToTen =
                    when (pairs.first[i].length) {
                        5
                        -> if (pairs.first[i].replace(segmentsInOne,"").length == 3) {
                            mapOfOneToTen.plus(Pair(3, pairs.first[i]))
                        } else if (min4.length == 2) mapOfOneToTen.plus(Pair(5, pairs.first[i]))
                        else mapOfOneToTen.plus(Pair(2, pairs.first[i]))

                        6 -> if (pairs.first[i].replace(segmentsInOne, "").length == 5) {
                            mapOfOneToTen.plus(Pair(6, pairs.first[i]))
                        } else if (min4.length == 2) mapOfOneToTen.plus(Pair(9, pairs.first[i]))
                        else mapOfOneToTen.plus(Pair(0, pairs.first[i]))

                        else -> mapOfOneToTen
                    }
            }

            var numString = ""
            //uses mapOfTen to deduct the 4Digit number in the second of the pair

            for (e in pairs.second) {
                val allFromE = Regex("[${e}]")
                val x = mapOfOneToTen.filterValues { value ->

                    allFromE.replace(value, "").isEmpty()&&
                            value.length == e.length
                }
                numString += (x.keys.joinToString(separator = ""))

            }

            sum += numString.toInt()
        }


        return sum
    }
}