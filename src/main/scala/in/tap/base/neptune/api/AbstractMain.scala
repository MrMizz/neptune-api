package in.tap.base.neptune.api

trait AbstractMain { main: ArgParser =>

  def main(args: Array[String]): Unit = {
    main.parse(args) match {
      case Left(help) => println(help); sys.exit(1)
      case Right(query) =>
        query.execute() match {
          case Some(response) => println(response.prettyPrint); sys.exit(0)
          case None           => println("Invalid Query!"); sys.exit(1)
        }
    }
  }

}
