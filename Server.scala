import akka.actor.{Actor, ActorRef, PoisonPill}

import scala.util.Random

class Server extends Actor {
  var currentGame: Game = null
  var connected: Array[(ActorRef, Player)] = Array.empty
  var currentPlayer: Player = null
  var currentClient: ActorRef = null
  var currentPlayerNumber: Int = -1

  def startGame():Unit ={
    currentGame = new Game(connected(0)._2,connected(1)._2)
    currentPlayerNumber = Random.nextInt(2) + 1
    currentPlayer = currentGame.getPlayer(currentPlayerNumber)
    currentClient = connected(currentPlayerNumber - 1)._1
    currentGame.startGame()
    print("Player 1: ")
    currentGame.getPlayer(1).printCurrentState(true)
    print("Player 2: ")
    currentGame.getPlayer(2).printCurrentState(false)
    println()
    currentClient ! ChooseMove()
  }

  def stopGame():Unit = {
    val winner = currentGame.winner
    if(winner == currentGame.getPlayer(1))
      print("Player 1 ")
    else
      print("Player 2 ")
    println("wygrał")
    currentGame.finishGame()
    for(client<- connected)
      client._1 ! PoisonPill
    connected = Array.empty
    self ! PoisonPill
    context.system.terminate()
  }

  def sendUpdateToClients():Unit ={
    for(client <- connected) {
      val (id, player) = client
      id ! UpdateData(player.fields, player.base)
    }
    println()
  }

  def connect(client: ActorRef, player: Player):Unit ={
    connected = connected.appended((client, player))
    context.watch(client)
    if(connected.length == 2)
      self ! StartGame()
  }

  override def receive: Receive = {
    case StartGame() =>
      startGame()
    case MoveChosen(move:Int) =>
      if (!currentGame.makeMove(currentPlayerNumber, move)) {
        currentPlayerNumber = (currentPlayerNumber % 2) + 1
        currentPlayer = currentGame.getPlayer(currentPlayerNumber)
        currentClient = connected(currentPlayerNumber - 1)._1
      }
      sendUpdateToClients()
      currentGame.getPlayer(1).printCurrentState(true)
      currentGame.getPlayer(2).printCurrentState(false)
      if (currentGame.isGameFinished)
        stopGame()
      else
        currentClient ! ChooseMove()
    case Connected(player: Player) => connect(sender(), player)
  }
}
