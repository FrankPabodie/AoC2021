class Day05 : Puzzle<Int>("Day05", 5, 12) {

    override fun part1(input: Input): Int {
        val mapOfSmoke = mapAllVertical(input)
        return mapOfSmoke.count { it.value >= 2 }
    }

    private fun mapAllVertical(input: Input): MutableMap<Pair<Int, Int>, Int> {

        val f = { s: String ->
            s.split(",").map(String::toInt).zipWithNext().first()
        }

        val inputInList = input.map {
            it.split(" -> ").map(f).zipWithNext().first()
        }.filter { (p1, p2) -> p1.first == p2.first || p1.second == p2.second }

        val mapOfSmoke = mutableMapOf<Pair<Int, Int>, Int>()

        for (i in inputInList) {

            var diff = diffOfTwo(i.first, i.second)

            mapOfSmoke.merge(i.first, 1, Int::plus)
            mapOfSmoke.merge(i.second, 1, Int::plus)

            while (diff != 1 && diff != -1) {

                diff = if (diff < 0) diff + 1 else diff - 1
                if (i.first.first == i.second.first) {
                    val toAd = Pair(i.first.first, i.first.second + diff)
                    mapOfSmoke.merge(toAd, 1, Int::plus)
                } else {
                    val toAd = Pair(i.first.first + diff, i.first.second)
                    mapOfSmoke.merge(toAd, 1, Int::plus)
                }

            }

        }
        return mapOfSmoke
    }

    private fun diffOfTwo(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
        return if (p1.first == p2.first) {
            p2.second - p1.second
        } else {
            p2.first - p1.first
        }
    }

    private fun diffOfTwoDiagonals(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Pair<Int, Int> {
        return (Pair(p2.first - p1.first, p2.second - p1.second))
    }


    override fun part2(input: Input): Int {
        val mapOfSmoke = mapAllVertical(input)

        val f = { s: String ->
            s.split(",").map(String::toInt).zipWithNext().first()
        }

        val inputInList = input.map {
            it.split(" -> ").map(f).zipWithNext().first()
        }
            .filter { (p1, p2) ->
                (p1.first - p2.first) * (p1.first - p2.first) == (p1.second - p2.second) * (p1.second - p2.second)
            }

        for (i in inputInList) {
            var diff = diffOfTwoDiagonals(i.first, i.second)
            mapOfSmoke.merge(i.first, 1, Int::plus)
            mapOfSmoke.merge(i.second, 1, Int::plus)

            while (diff.first != 1 && diff.first != -1) {
                diff = if (diff.first < 0) Pair(diff.first + 1, diff.second) else Pair(diff.first - 1, diff.second)
                diff = if (diff.second < 0) Pair(diff.first, diff.second + 1) else Pair(diff.first, diff.second - 1)

                val toAd = Pair(i.first.first + diff.first, i.first.second + diff.second)

                mapOfSmoke.merge(toAd, 1, Int::plus)
            }

        }

        return mapOfSmoke.count { it.value >= 2 }
    }


}