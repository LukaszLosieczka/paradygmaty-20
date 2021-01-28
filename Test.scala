import akka.actor.{ActorSystem, Props}

object Test extends App {
    val p1 = new Player()
    val p2 = new Player()

   val system = ActorSystem()
   val server = system.actorOf(Props[Server])
   val c1 = system.actorOf(Props(classOf[Client], p1))
   val c2 = system.actorOf(Props(classOf[Client], p2))

    c1 ! Connect(server)
    c2 ! Connect(server)

}
