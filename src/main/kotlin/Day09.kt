class Day09:Puzzle<Int>("Day09",15,0) {

    override fun part1(input: Input): Int {
        val cave2DArray = input.map { line -> line.map { char -> char.digitToInt() } }
        return cave2DArray.onlyLowPoints().sumOf { it + 1 }

    }

    //fun that searches for lowpoints
    private fun List<List<Int>>.onlyLowPoints(): List<Int>{
        val mapWithIsLow = this.toListwithPairs()
        return mapWithIsLow.flatMap { l -> l.filter { (x,y) -> y }.map { (x,y) -> x } }
    }

    private fun List<List<Int>>.toListwithPairs(): MutableList<MutableList<Pair<Int,Boolean>>>{
        val mapWithIsLow: MutableList<MutableList<Pair<Int,Boolean>>> =
            this.map { line -> line.map{ Pair(it,true)}.toMutableList() }.toMutableList()
        for (lineNr in this.indices) {
            for(columnNr in this[lineNr].indices){
                if(lineNr - 1 in this.indices && this[lineNr-1][columnNr] <= this[lineNr][columnNr]){
                    mapWithIsLow[lineNr][columnNr] = Pair(mapWithIsLow[lineNr][columnNr].first, false) }
                if(columnNr- 1 in this[lineNr].indices && this[lineNr][columnNr - 1] <= this[lineNr][columnNr]){
                    mapWithIsLow[lineNr][columnNr] = Pair(mapWithIsLow[lineNr][columnNr].first, false)}
                if(lineNr + 1 in this.indices && this[lineNr+1][columnNr] <= this[lineNr][columnNr]){
                    mapWithIsLow[lineNr][columnNr] = Pair(mapWithIsLow[lineNr][columnNr].first, false)}
                if(columnNr + 1 in this[lineNr].indices && this[lineNr][columnNr+1] <= this[lineNr][columnNr]){
                    mapWithIsLow[lineNr][columnNr] = Pair(mapWithIsLow[lineNr][columnNr].first, false)}
            }
        }
        return mapWithIsLow
    }

    override fun part2(input: Input): Int {
        val cave2DArray = input.map { l-> l.map { it.digitToInt() } }
        val allBasins = cave2DArray.toListwithPairs().createBasins()
        val listOfBasins: List<List<Int>> = allBasins.groupBy { }

    }

    // a data Class that hold 4 Booleans each is supposed
    data class AllAround(val left:Boolean = false,val right: Boolean = false , val top: Boolean= false, val bot:Boolean= false)


    //represents a point in a grid
    data class Point2d(val x:Int,val y : Int)

    // fun Checks If all Around are a Nine are true
    private fun AllAroundAreNine(Pos:AllAround): Boolean{
        return Pos.left && Pos.right && Pos.bot && Pos.top

    }
    //fun Checks if any Surrounding the Obj is true
    private fun AnyAroundIsNine(Pos:AllAround):Boolean{
        return Pos.left || Pos.right || Pos.bot || Pos.top
    }

    private fun MutableList<MutableList<Pair<Int,Boolean>>>.createBasins():
            MutableList<MutableList<Pair<Pair<Int,Boolean>,Point2d?>>>{
        val BasinsgettingScanned : MutableList<MutableList<Pair<Pair<Int,AllAround>,Point2d?>>> =
            this.map { it.map { (number,isCounted) ->
                if(isCounted) Pair(Pair(number, AllAround(true,true,true,true)),Point2d(this.indexOf(it),it.indexOf(Pair(number,isCounted))))
                else {Pair(Pair(number,AllAround()),null)} }.toMutableList() }.toMutableList()
        val tmp = BasinsgettingScanned.createPartialBasins()
            .map { it
                .map {(nr,Bool)->
                    Pair(Pair(nr.first, AnyAroundIsNine(nr.second)), Bool)
                }.toMutableList()
        }.toMutableList()

        return tmp
    }

    private fun MutableList<MutableList<Pair<Pair<Int,AllAround>,Point2d?>>>.createPartialBasins() :
            MutableList<MutableList<Pair<Pair<Int,AllAround>,Point2d?>>>{
        var tmpGrid = this
        var flag = true

        for (lineNr in this.indices) {
            for(columnNr in this[lineNr].indices){
                if(lineNr - 1 in this.indices
                    && this[lineNr-1][columnNr].first.first != 9
                    && AnyAroundIsNine(this[lineNr][columnNr].first.second)){
                    tmpGrid[lineNr-1][columnNr] = Pair(Pair(tmpGrid[lineNr-1][columnNr].first.first, AllAround(right = true)),tmpGrid[lineNr][columnNr].second)
                } else if(columnNr - 1 in this.indices
                        && this[lineNr][columnNr - 1].first.first != 9
                        && AnyAroundIsNine(this[lineNr][columnNr].first.second)){
                    tmpGrid[lineNr][columnNr-1] = Pair(Pair(tmpGrid[lineNr][columnNr-1].first.first, AllAround(top= true)),tmpGrid[lineNr][columnNr].second)
                }else if(lineNr + 1 in this.indices
                    && this[lineNr+1][columnNr].first.first != 9
                    && AnyAroundIsNine(this[lineNr][columnNr].first.second)){
                    tmpGrid[lineNr+1][columnNr] = Pair(Pair(tmpGrid[lineNr+1][columnNr].first.first, AllAround(left = true)),tmpGrid[lineNr][columnNr].second)
                }else if(columnNr +  1 in this.indices
                        && this[lineNr][columnNr+1].first.first != 9
                        && AnyAroundIsNine(this[lineNr][columnNr].first.second)){
                    tmpGrid[lineNr][columnNr+1] = Pair(Pair(tmpGrid[lineNr][columnNr+1].first.first, AllAround(bot = true)),tmpGrid[lineNr][columnNr].second)
                }else{
                    flag= false
                }
            }
        }

        if(flag){
            tmpGrid = tmpGrid.createPartialBasins()
        }

        return tmpGrid
    }

}