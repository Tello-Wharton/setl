package scratch2

import SetlType.*

import java.util.function.BiFunction

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

    val newData = data.sortWith(sortColFunction(col))

    return new DataFrame(schema, newData)
  }

  private def sortColFunction(col: Column): (Seq[ColType], Seq[ColType]) => Boolean = {
    val f = col.func(this)

    val setlType = schema.find(_._1 == col.name).map(_._2).get

    setlType match {
      case StringType => (row1, row2) => f(row1).asInstanceOf[String] < f(row2).asInstanceOf[String]
      case IntegerType => (row1, row2) => f(row1).asInstanceOf[Int] < f(row2).asInstanceOf[Int]
      case LongType => (row1, row2) => f(row1).asInstanceOf[Long] < f(row2).asInstanceOf[Long]
      case BooleanType => (row1, row2) => f(row1).asInstanceOf[Boolean] < f(row2).asInstanceOf[Boolean]
    }
  }

  def sort2(cols: Column*): DataFrame = {

    val masterFunction = cols.
      map(sortColIntFunction(_)).
      foldLeft((row1: Seq[ColType], row2: Seq[ColType]) => 0)(
        (f1, f2) => {
          (row1: Seq[ColType], row2: Seq[ColType]) => {
            val res = f1(row1, row2)
            if (res == 1) 1
            else if (res == -1) -1
            else f2(row1, row2)
          }
        })

    val masterFunctionBoolean = (row1: Seq[ColType], row2: Seq[ColType]) => {
      val res = masterFunction(row1, row2)
      if (res == 1) true
      else false
    }

    val newData = data.sortWith(masterFunctionBoolean)

    return new DataFrame(schema, newData)
  }

  private def sortColIntFunction(col: Column): (Seq[ColType], Seq[ColType]) => Int = {
    val f = col.func(this)

    val setlType = schema.find(_._1 == col.name).map(_._2).get

    setlType match {
      case StringType => (row1, row2) => {
        val a = f(row1).asInstanceOf[String]
        val b = f(row1).asInstanceOf[String]

        if (a.equals(b)) 0
        else if (a < b) 1
        else -1
      }
      case IntegerType => (row1, row2) => {
        val a = f(row1).asInstanceOf[Int]
        val b = f(row1).asInstanceOf[Int]

        if (a == b) 0
        else if (a < b) 1
        else -1
        
      }
      case LongType => (row1, row2) => {
        val a = f(row1).asInstanceOf[Long]
        val b = f(row1).asInstanceOf[Long]

        if (a == b) 0
        else if (a < b) 1
        else -1
      }
      case BooleanType => (row1, row2) => {
        val a = f(row1).asInstanceOf[Boolean]
        val b = f(row1).asInstanceOf[Boolean]

        if (a == b) 0
        else if (a < b) 1
        else -1
      }
    }
  }
}
