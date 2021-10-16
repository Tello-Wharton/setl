package scratch2

import SetlType._

class DataFrame(val schema: Seq[(String, SetlType)], val data: Seq[Seq[ColType]]) {

  def select(col: String, cols: String*): DataFrame = select((col +: cols).map(col => new Column(col)) *)

  def select(cols: Column*): DataFrame = {

    val colFuncs = cols.map(_ func this)
    val newSchema = cols.map(_.name)
    val newData = data.map(row => colFuncs.map(_ (row)))

    return new DataFrame(schema, newData)
  }

  def filter(filterCol: Column): DataFrame = {

    val f = filterCol.func(this)

    val newData = data.filter(row => f(row).asInstanceOf[Boolean])

    return new DataFrame(schema, newData)
  }

  def sort(col: Column): DataFrame = {
    val f = col.func(this)

    val setlType = schema.find(_._1 == col.name).map(_._2).get

    val newData = setlType match {
      case StringType => data.sortBy(row => f(row).asInstanceOf[String])
      case IntegerType => data.sortBy(row => f(row).asInstanceOf[Int])
      case LongType => data.sortBy(row => f(row).asInstanceOf[Long])
      case BooleanType => data.sortBy(row => f(row).asInstanceOf[Boolean])
    }

    return new DataFrame(schema, newData)
  }
}
