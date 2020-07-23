package in.tap.base.neptune.api

import gremlin.scala.Key

package object schema {

  val pageId: Key[String] = Key[String]("page_id")

}
