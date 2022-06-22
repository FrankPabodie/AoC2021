class Day02 : Puzzle<Int>("Day02", 150, 900) {

    override fun part1(input: List<String>): Int {
        var count1 = 0
        var count2 = 0
        input.forEach {
            if ("forward" == it.split(" ")[0]) {
                count1 += it.split(" ")[1].toInt()
            }
            if ("down" == it.split(" ")[0]) {
                count2 += it.split(" ")[1].toInt()
            }
            if ("up" == it.split(" ")[0]) {
                count2 -= it.split(" ")[1].toInt()
            }
        }

        return count1 * count2
    }


    override fun part2(input: Input): Int {
        val moves = input
            .map {
                it.split(" ")[0] to
                        it.split(" ")[1].toInt()
            }
        var horizontalPos = 0
        var depth = 0
        var aim = 0
        moves.forEach{
            val direction = it.first
            val amount = it.second
            if ("forward" == direction){
                horizontalPos += amount
                depth += aim*amount
            }
            if ("down" == direction) {
                aim += amount
            }
            if ("up" == direction) {
                aim -= amount
            }
        }
        return depth * horizontalPos
    }


}