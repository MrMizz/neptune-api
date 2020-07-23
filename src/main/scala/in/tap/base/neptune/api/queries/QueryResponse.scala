package in.tap.base.neptune.api.queries

final case class QueryResponse(
  operand_vertex_ids: List[String],
  result_vertex_ids: List[String]
) {

  lazy val prettyPrint: String = {
    result_vertex_ids.reduce(_ + "," + _)
  }

}
