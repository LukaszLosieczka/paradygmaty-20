class Game(player1:Player, player2: Player)
{
  var finished = true

  def makeMove(playerNumber: Int, field: Int): Boolean ={
    var currentPlayer: Player = null
    var oppositePlayer: Player = null

    if(playerNumber == 1) {
      currentPlayer = player1
      oppositePlayer = player2
    }
    else {
      currentPlayer = player2
      oppositePlayer = player1
    }

    var rocksNumber = currentPlayer.fields(field)
    currentPlayer.fields(field) = 0
    var repeatMove = false
    var currentField = field + 1
    var oppositeSide = false

    while(rocksNumber > 0){
      rocksNumber -= 1

      if(currentField == 6){
        currentPlayer.base = currentPlayer.base + 1
        if(rocksNumber == 0 && !oppositeSide)
          repeatMove = true
      }

      else if(currentField > 6) {
        currentField = 0
        val tmp = currentPlayer
        currentPlayer = oppositePlayer
        oppositePlayer = tmp
        currentPlayer.fields(currentField) = currentPlayer.fields(currentField) + 1
        if(oppositeSide)
          oppositeSide = false
        else
          oppositeSide = true
      }

      else{
        if(currentPlayer.fields(currentField) == 0 && rocksNumber == 0){
          currentPlayer.base = currentPlayer.base + oppositePlayer.fields(5 - currentField)
          oppositePlayer.fields(5 - currentField) = 0
        }
        currentPlayer.fields(currentField) = currentPlayer.fields(currentField) + 1
      }

      currentField += 1

    }
    repeatMove
  }

  def getPlayer(number: Int):Player ={
    if(number == 1)
      player1
    else
      player2
  }

  def startGame(): Unit =
    finished = false

  def finishGame(): Unit =
    finished = true

  def isGameFinished:Boolean =
    player1.getPossibleMoves.length == 0 || player2.getPossibleMoves.length == 0

  def winner: Player = {
    if (finished && player1 != null && player2 != null) {
      if (player1.base > player2.base)
        player1
      else
        player2
    }
    else
      null
  }
}
