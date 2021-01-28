import akka.actor.{Actor, PoisonPill}

class Client(val player: Player) extends Actor {
  override def receive: Receive = {
    case Connect(server) => server ! Connected(player)
    case Disconnect() => self ! PoisonPill
    case ChooseMove() => sender() ! MoveChosen(player.chooseNextMove)
    case UpdateData(fields, base) =>
      player.fields = fields
      player.base = base
  }
}
