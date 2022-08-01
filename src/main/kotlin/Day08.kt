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
        val stringPairs = input.map { line ->
            line
                .split("|").zipWithNext()[0]
        }.map { pair ->
            Pair<List<String>, List<String>>(pair.first.split(" ").filter { it.isNotBlank() },
                pair.second.split(" ").filter { it.isNotBlank() })
        }
        var mapOfOneToTen: Map<Int, String> = mapOf()
        for (pairs in stringPairs) {

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
            for (i in 0..9) {
                var min4: String = pairs.first[i]
                for (k in 0 until mapOfOneToTen.getValue(4).length) {
                    min4 = min4.replace("${mapOfOneToTen.getValue(4)[k]}", "")
                }
                mapOfOneToTen =
                    when (pairs.first[i].length) {
                        5
                        -> if (pairs.first[i].contains(mapOfOneToTen.getValue(1)[0])
                            && pairs.first[i].contains(mapOfOneToTen.getValue(1)[1])
                        ) {
                            mapOfOneToTen.plus(Pair(3, pairs.first[i]))
                        } else if (min4.length == 2) mapOfOneToTen.plus(Pair(5, pairs.first[i]))
                        else mapOfOneToTen.plus(Pair(2, pairs.first[i]))

                        6 -> if (!pairs.first[i].contains(mapOfOneToTen.getValue(1)[0])
                            || !pairs.first[i].contains(mapOfOneToTen.getValue(1)[1])
                        ) {
                            mapOfOneToTen.plus(Pair(6, pairs.first[i]))
                        } else if (min4.length == 2) mapOfOneToTen.plus(Pair(9, pairs.first[i]))
                        else mapOfOneToTen.plus(Pair(0, pairs.first[i]))


                        else -> mapOfOneToTen
                    }
            }

            val toEncode = pairs.second
            var candidates = mapOfOneToTen
            var notIncluded = "abcdefg"
            var numString = ""
            for(e in toEncode){
                notIncluded = "abcdefg"
                candidates = mapOfOneToTen
                for (pos in e){
                    candidates = candidates.filterValues { it.contains(pos) }
                    notIncluded = notIncluded.replace("$pos","")
                }
                for (notIn in notIncluded){
                    candidates = candidates.filterValues { !it.contains(notIn) }
                }
                numString += candidates.keys.joinToString(separator = "")

                if(numString.length == 4){
                    sum += numString.toInt()
                }
            }



        }

        return sum

    }
}