package in.tap.base.neptune.api.queries

import in.tap.base.neptune.api.driver.Driver
import in.tap.base.neptune.api.queries.args.Aggregation
import in.tap.base.neptune.api.queries.args.Aggregation.{And, Or}
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource

/**
 * You'll need to instantiate a new [[Query]] for each traversal/request.
 *
 * @param vertexIds starting point of your vertex-oriented graph traversal
 * @param aggregation AND/OR
 * @param ENDPOINT Your Neptune Cluster URL
 */
abstract class Query(vertexIds: List[String], aggregation: Aggregation, ENDPOINT: String) {

  protected def singleQuery(vertexId: String)(g: GraphTraversalSource): Set[String]

  final def execute(): Option[QueryResponse] = {
    val response: Option[QueryResponse] = {
      val queries: List[Set[String]] = {
        vertexIds.map(vertexId => singleQuery(vertexId)(g))
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

  /**
   * In practice, I've found that leaving [[GraphTraversalSource]] open
   * will eventually crash your application, and it's best to close after each request.
   */
  final private lazy val g: GraphTraversalSource = {
    Driver(ENDPOINT).g
  }

}
