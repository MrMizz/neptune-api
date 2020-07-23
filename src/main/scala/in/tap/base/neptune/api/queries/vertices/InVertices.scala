package in.tap.base.neptune.api.queries.vertices

import in.tap.base.neptune.api.queries.Query
import in.tap.base.neptune.api.queries.args.Aggregation
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import org.apache.tinkerpop.gremlin.structure.Vertex

import scala.collection.JavaConverters._

final case class InVertices(vertexIds: List[String], aggregation: Aggregation, ENDPOINT: String)
    extends Query(vertexIds, aggregation, ENDPOINT) {

  override protected def singleQuery(gt: GraphTraversal[Vertex, Vertex]): Set[String] = {
    gt.inE()
      .asScala
      .map(_.outVertex().id().asInstanceOf[String])
      .toSet
  }

}
