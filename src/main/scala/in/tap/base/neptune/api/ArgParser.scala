package in.tap.base.neptune.api

import cats.data.{NonEmptyList, Validated}
import cats.implicits._
import com.monovore.decline.{Command, Help, Opts}
import in.tap.base.neptune.api.queries.Query
import in.tap.base.neptune.api.queries.args.Direction.{In, Out}
import in.tap.base.neptune.api.queries.args.{Aggregation, Direction}
import in.tap.base.neptune.api.queries.vertices.{InVertices, OutVertices}

/**
 * Just drop your endpoint here.
 */
abstract class ArgParser(val ENDPOINT: String) {

  def parse(args: Array[String]): Either[Help, Query] = {
    command.parse(args)
  }

  private lazy val command: Command[Query] = {
    Command(name = "we-poli-graph", header = "Traverses Neptune Graph")(
      opts = queryOpts
    )
  }

  private lazy val queryOpts: Opts[Query] = {
    Opts.subcommand(name = "query", help = "Vertex-Centered Graph Query") {
      val vidsOpt: Opts[NonEmptyList[String]] = {
        Opts.options[String](
          long = "vertex-ids",
          short = "vids",
          help = "UID of Vertices"
        )
      }

      val directionOpt: Opts[Direction] = {
        Opts
          .option[String](
            long = "direction",
            short = "dir",
            help = "direction of edges"
          )
          .mapValidated { directionArg: String =>
            Direction.withNameInsensitiveEither(directionArg) match {
              case Left(invalid)    => Validated.invalidNel(invalid.getMessage)
              case Right(direction) => Validated.valid(direction)
            }
          }
      }

      val aggregationOpt: Opts[Aggregation] = {
        Opts
          .option[String](
            long = "aggregation",
            short = "agg",
            help = "aggregation strategy"
          )
          .mapValidated { aggregationArg: String =>
            Aggregation.withNameInsensitiveEither(aggregationArg) match {
              case Left(invalid)      => Validated.invalidNel(invalid.getMessage)
              case Right(aggregation) => Validated.valid(aggregation)
            }
          }
      }

      (vidsOpt, directionOpt, aggregationOpt).mapN {
        (vidsArg: NonEmptyList[String], directionArg: Direction, aggregationArg: Aggregation) =>
          directionArg match {
            case In  => InVertices(vidsArg.toList, aggregationArg, ENDPOINT)
            case Out => OutVertices(vidsArg.toList, aggregationArg, ENDPOINT)
          }
      }
    }
  }

}
