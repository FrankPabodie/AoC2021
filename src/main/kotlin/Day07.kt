import kotlin.math.abs

class Day07 : Puzzle<Int>("Day07", 37, 168) {


    override fun part1(input: Input): Int {
        val listOfHorizontal = input[0].split(",").map { e -> e.toInt() }.sorted()
        return listOfHorizontal.sumOf { e -> abs(e - listOfHorizontal[listOfHorizontal.size / 2]) }
    }

    override fun part2(input: Input): Int {
        val listOfHorizontal = input[0].split(",").map { e -> e.toInt() }.sorted()
        var bestPossiblePos = -1

        fun  sumOfPosChange(Pos : Int , def : Int): Int  {
            var sum = 0
                for(Steps in 0 ..abs(Pos - def)){
                    sum += Steps
                }
            return sum
        }

        for (i in listOfHorizontal[0]..listOfHorizontal[listOfHorizontal.size - 1]) {

            bestPossiblePos = if (listOfHorizontal.sumOf { e -> sumOfPosChange(e, i) } <=
                listOfHorizontal.sumOf { e -> sumOfPosChange(e, bestPossiblePos) }) {
                i
            } else {
                break
            }
        }

        return listOfHorizontal.sumOf { e-> sumOfPosChange(e, bestPossiblePos) }
    }


}