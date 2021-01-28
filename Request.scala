import akka.actor.ActorRef

abstract class Request

case class StartGame() extends Request
case class StopGame() extends Request
case class Connect(server: ActorRef) extends Request
case class Connected(player: Player) extends Request
case class Disconnect() extends Request
case class ChooseMove() extends Request
case class MoveChosen(move: Int) extends Request
case class UpdateData(fields: Array[Int], base: Int) extends Request

