import scala.util.Random

class Player {
  var fields: Array[Int] = Array.fill[Int](6)(6)
  var base = 0

  def getPossibleMoves: Array[Int] = {
    var index = 0
    var result = Array.empty[Int]

    for (field <- fields) {
      if (field != 0)
        result = result.appended(index)
      index = index + 1
    }
  result
}

  def chooseNextMove: Int ={
    val availableMoves = getPossibleMoves
    availableMoves(Random.nextInt(availableMoves.length))
  }

  def printCurrentState(reverse: Boolean): Unit = {
    if (reverse) {
      print("Baza: " + base + "   ")
      var i = fields.length - 1
      while (i >= 0) {
        print(fields(i) + " ")
        i -= 1
      }
      println()
    }
    else {
      for (field <- fields) {
        print(field + " ")
      }
      println("  Baza: " + base)
    }
  }

}
