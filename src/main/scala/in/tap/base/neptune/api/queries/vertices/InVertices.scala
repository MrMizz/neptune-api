package in.tap.base.neptune.api.queries.vertices

import in.tap.base.neptune.api.queries.Query
import in.tap.base.neptune.api.queries.args.Aggregation
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource

import scala.collection.JavaConverters._

final case class InVertices(vertexIds: List[String], aggregation: Aggregation, ENDPOINT: String)
    extends Query(vertexIds, aggregation, ENDPOINT) {

  override protected def singleQuery(vertexId: String)(g: GraphTraversalSource): Set[String] = {
    g.V(vertexId.asInstanceOf[Object])
      .inE()
      .asScala
      .map(_.outVertex().id().asInstanceOf[String])
      .toSet
  }

}
