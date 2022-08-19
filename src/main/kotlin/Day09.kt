class Day09 : Puzzle<Int>("Day09", 15, 1134) {

    override fun part1(input: Input): Int {
        val cave2DArray = input.map { line -> line.map { char -> char.digitToInt() } }
        return cave2DArray.onlyLowPoints().sumOf { it + 1 }

    }

    //fun that searches for lowpoints
    private fun List<List<Int>>.onlyLowPoints(): List<Int> {
        val mapWithIsLow = this.toListwithPairs()
        return mapWithIsLow.flatMap { l -> l.filter { (_, y) -> y }.map { (x, _) -> x } }
    }

    private fun List<List<Int>>.toListwithPairs(): MutableList<MutableList<Pair<Int, Boolean>>> {
        val mapWithIsLow: MutableList<MutableList<Pair<Int, Boolean>>> =
            this.map { line -> line.map { Pair(it, true) }.toMutableList() }.toMutableList()
        for (lineNr in this.indices) {
            for (columnNr in this[lineNr].indices) {
                if (lineNr - 1 in this.indices && this[lineNr - 1][columnNr] <= this[lineNr][columnNr]) {
                    mapWithIsLow[lineNr][columnNr] = Pair(mapWithIsLow[lineNr][columnNr].first, false)
                }
                if (columnNr - 1 in this[lineNr].indices && this[lineNr][columnNr - 1] <= this[lineNr][columnNr]) {
                    mapWithIsLow[lineNr][columnNr] = Pair(mapWithIsLow[lineNr][columnNr].first, false)
                }
                if (lineNr + 1 in this.indices && this[lineNr + 1][columnNr] <= this[lineNr][columnNr]) {
                    mapWithIsLow[lineNr][columnNr] = Pair(mapWithIsLow[lineNr][columnNr].first, false)
                }
                if (columnNr + 1 in this[lineNr].indices && this[lineNr][columnNr + 1] <= this[lineNr][columnNr]) {
                    mapWithIsLow[lineNr][columnNr] = Pair(mapWithIsLow[lineNr][columnNr].first, false)
                }
            }
        }
        return mapWithIsLow
    }

    override fun part2(input: Input): Int {
        val cave2DArray = input.map { l -> l.map { it.digitToInt() } }
        val allBasins = cave2DArray.toListwithPairs().createBasins()
        val listOfBasins =
            allBasins.flatten().groupBy { it.second }.map { m -> Pair(m.key, m.value.size) }.sortedBy { it.second }
                .filter { it.first != Point2d(-1, -1) }
        println(listOfBasins.drop( listOfBasins.size -3).map { it.second })
        return listOfBasins.drop( listOfBasins.size -3).map { it.second }.reduce{acc, i -> acc * i }

    }

    //represents a point in a grid
    data class Point2d(val x: Int, val y: Int)


    private fun MutableList<MutableList<Pair<Int, Boolean>>>.createBasins():
            MutableList<MutableList<Pair<Int, Point2d>>> {
        var basinsGettingScanned: MutableList<MutableList<Pair<Int, Point2d>>> =
            this.map { it.map { Pair(it.first, Point2d(0, 0)) }.toMutableList() }.toMutableList()

        for (x in this.indices) {
            for (y in this[x].indices) {
                if (this[x][y].second) {
                    basinsGettingScanned[x][y] = Pair(this[x][y].first, Point2d(x, y))
                } else {
                    basinsGettingScanned[x][y] = Pair(this[x][y].first, Point2d(-1, -1))
                }
            }
        }
        while (basinsGettingScanned.flatMap { line -> line.filter { it.first != 9 && it.second == Point2d(-1, -1) } }
                .isNotEmpty()) {
            basinsGettingScanned = basinsGettingScanned.scanBasins()
        }

        return basinsGettingScanned

    }

    private fun MutableList<MutableList<Pair<Int, Point2d>>>.scanBasins():
            MutableList<MutableList<Pair<Int, Point2d>>> {
        val tmpGrid = this

        for (lineNr in this.indices) {
            for (columnNr in this[lineNr].indices) {
                if (this[lineNr][columnNr].second != Point2d(-1, -1)) {
                    if (lineNr - 1 in this.indices
                        && this[lineNr - 1][columnNr].first != 9) {
                        tmpGrid[lineNr - 1][columnNr] =
                            Pair(this[lineNr - 1][columnNr].first, this[lineNr][columnNr].second)
                    }
                    if (columnNr - 1 in this[lineNr].indices
                        && this[lineNr][columnNr - 1].first != 9) {
                        tmpGrid[lineNr][columnNr - 1] =
                            Pair(this[lineNr][columnNr - 1].first, this[lineNr][columnNr].second)
                    }
                    if (lineNr + 1 in this.indices
                        && this[lineNr + 1][columnNr].first != 9) {
                        tmpGrid[lineNr + 1][columnNr] =
                            Pair(this[lineNr + 1][columnNr].first, this[lineNr][columnNr].second)
                    }
                    if (columnNr + 1 in this[lineNr].indices
                        && this[lineNr][columnNr + 1].first != 9) {
                        tmpGrid[lineNr][columnNr + 1] =
                            Pair(this[lineNr][columnNr + 1].first, this[lineNr][columnNr].second)
                    }
                }
            }
        }
        return tmpGrid
    }

}
