package in.tap.base.neptune.api.queries.vertices

import in.tap.base.neptune.api.queries.Query
import in.tap.base.neptune.api.queries.args.Aggregation

import scala.collection.JavaConverters._

final case class OutVertices(vertexIds: List[String], aggregation: Aggregation, ENDPOINT: String)
    extends Query(vertexIds, aggregation, ENDPOINT) {

  override protected def singleQuery(vertexId: String): Set[String] = {
    g.V(vertexId.asInstanceOf[Object])
      .outE()
      .asScala
      .map(_.inVertex().id().asInstanceOf[String])
      .toSet
  }

}
