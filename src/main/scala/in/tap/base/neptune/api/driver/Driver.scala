package in.tap.base.neptune.api.driver

import org.apache.tinkerpop.gremlin.driver.Cluster
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource

/**
 * Gremlins Driver.
 *
 * @param ENDPOINT YOUR NEPTUNE CLUSTER ENDPOINT
 */
final case class Driver(ENDPOINT: String) {

  /** Graph Traversal Source */
  lazy val g: GraphTraversalSource = {
    traversal().withRemote(DriverRemoteConnection.using(cluster))
  }

  private lazy val PORT: Int = {
    8182
  }

  private lazy val builder: Cluster.Builder = {
    Cluster
      .build()
      .addContactPoint(ENDPOINT)
      .port(PORT)
      .enableSsl(true)
      .keyCertChainFile("SFSRootCAG2.pem")
  }

  private lazy val cluster: Cluster = {
    builder.create()
  }

}
