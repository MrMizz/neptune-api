package in.tap.base.neptune.api.queries

import in.tap.base.neptune.api.driver.Driver
import in.tap.base.neptune.api.queries.args.Aggregation
import in.tap.base.neptune.api.queries.args.Aggregation.{And, Or}
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource

abstract class Query(vertexIds: List[String], aggregation: Aggregation, ENDPOINT: String) {

  protected def singleQuery(vertexId: String): Set[String]

  final def execute(): Option[QueryResponse] = {
    val response: Option[QueryResponse] = {
      val queries: List[Set[String]] = {
        vertexIds.map(singleQuery)
      }
      val aggregatedQueries: Set[String] = aggregation match {
        case And => queries.reduce(_.intersect(_))
        case Or  => queries.reduce(_.union(_))
      }
      aggregatedQueries.toList match {
        case Nil => None
        case result =>
          Some(
            QueryResponse(
              operand_vertex_ids = vertexIds,
              result_vertex_ids = result
            )
          )
      }
    }
    g.close()
    response
  }

  final protected lazy val g: GraphTraversalSource = {
    Driver(ENDPOINT).g
  }

}
