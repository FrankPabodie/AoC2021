import kotlin.math.absoluteValue

class Day06 : Puzzle<Long>("Day06", 5934, 26984457539) {

    override fun part1(input: Input): Long {
        val waitDays = 80
        var fishis: List<Int> = input.first().split(",").map { it.toInt() }

        for (i in 1..waitDays) {
            val fishisZero = fishis.filter { e -> e == 0 }.size
            for (t in 1..fishisZero) {
                fishis = fishis.plus(9)
            }
            fishis = fishis.map { e ->
                if (e <= 0) 6 else e - 1
            }
        }

        return fishis.size.toLong()
    }

    override fun part2(input: Input): Long {
        val waitDays = 256
        val fishis: List<Int> = input.first().split(",").map { it.toInt() }
        val mapOfFish = mutableMapOf<Int, Long>(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0,
            7 to 0,
            8 to 0,
        )

        for (i in fishis.indices) {
            mapOfFish[fishis[i]] = mapOfFish.getValue(fishis[i]) + 1
        }
        for (i in 1..waitDays) {
            val fishOn8 = mapOfFish[8]
            val fishOn6 = mapOfFish[6]
            for (entry in mapOfFish) {
                when (entry.key) {
                    0 -> {
                        mapOfFish[8] = mapOfFish.getValue(entry.key)
                        mapOfFish[6] = mapOfFish.getValue(entry.key) + mapOfFish.getValue(7)
                    }
                    7 -> {
                    }
                    6 -> {
                        mapOfFish[5] = fishOn6 ?: 0
                    }
                    8 -> {
                        mapOfFish[7] = fishOn8 ?: 0
                    }
                    else -> {
                        mapOfFish[entry.key - 1] = mapOfFish.getValue(entry.key)
                    }
                }
            }
        }
        return mapOfFish.values.sum()
    }

}