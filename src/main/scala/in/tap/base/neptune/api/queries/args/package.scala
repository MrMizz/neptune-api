package in.tap.base.neptune.api.queries

import enumeratum.{Enum, EnumEntry}
import enumeratum.EnumEntry.Hyphencase

import scala.collection.immutable

package object args {

  sealed trait Direction extends EnumEntry with Hyphencase

  object Direction extends Enum[Direction] {

    val values: immutable.IndexedSeq[Direction] = findValues

    case object In extends Direction

    case object Out extends Direction

  }

  sealed trait Aggregation extends EnumEntry with Hyphencase

  object Aggregation extends Enum[Aggregation] {

    val values: immutable.IndexedSeq[Aggregation] = findValues

    case object And extends Aggregation

    case object Or extends Aggregation

  }

}
