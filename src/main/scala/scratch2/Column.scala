package scratch2

import scala.language.implicitConversions
import SetlType.*
import scratch2.Column.colTypeToSetlType

class Column(val name: String, val setlType: SetlType, val func: Function[DataFrame, Function[Seq[ColType], ColType]]) {
  def this(name: String) = {
    this(name, SetlType.AnyType, df => {

      val schema = df.schema
      val data = df.data


      val idx: Int = schema.map(_._1).zipWithIndex.find((el, idx) => el == name).map((el, idx) => idx).getOrElse(throw new RuntimeException("Can't."))


      row => row(idx)
    })
  }

  def this(name: String, lit: ColType) = {

    this(name, colTypeToSetlType(lit), df => {
      row => lit
    })
  }

  def ===(col: Column): Column = {

    val name = s"${this.name} equals ${col.name}"

    new Column(name, SetlType.BooleanType, df => {
      val f1 = this.func(df)
      val f2 = col.func(df)

      row => f1(row) == f2(row)
    })
  }
}

object Column {
  private def colTypeToSetlType(colType: ColType): SetlType = colType match {
    case _: String => SetlType.StringType
    case _: Int => SetlType.StringType
    case _: Long => SetlType.LongType
    case _: Boolean => SetlType.BooleanType
  }
}
