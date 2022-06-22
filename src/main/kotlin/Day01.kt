class Day01 : Puzzle<Int>("Day01", 7, 5) {

    override fun part1(input: Input): Int{
        var count = 0
        val listOfNumbers = input.map{ it.toInt()}
        for( i in 0.. listOfNumbers.size-2){
            if (listOfNumbers[i] < listOfNumbers[i+1]){
                count +=1
            }
        }
        return count
    }

    override fun part2(input: Input): Int {
        var countInc = 0
        val listOfNumbers = input.map{ it.toInt()}
        var doubleArray : Array<Array<Int>> = arrayOf()
        for(i in 0.. input.size-3){
            doubleArray = doubleArray.plus(
                arrayOf(listOfNumbers[i],
                    listOfNumbers[i+1],
                    listOfNumbers[i+2]
                )
            )
            println("${doubleArray[i].sum()}")
            if(i>=1 && doubleArray[i-1].sum() < doubleArray[i].sum()){
                countInc+=1
            }
        }
        return countInc
    }


}