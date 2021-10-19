package scratch2

import SetlType.*
import ColSortDir._

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

  def sort(cols: Column*): DataFrame = {

    val masterFunction = cols.
      map(sortColIntFunction(_)).
      foldLeft((row1: Seq[ColType], row2: Seq[ColType]) => 0)(
        (f1, f2) => {
          (row1: Seq[ColType], row2: Seq[ColType]) => {
            val res = f1(row1, row2)
            if (res > 0) 1
            else if (res < 0) -1
            else f2(row1, row2)
          }
        })

    val masterFunctionBoolean = (row1: Seq[ColType], row2: Seq[ColType]) => {
      val res = masterFunction(row1, row2)
      if (res < 0) true
      else false
    }

    val newData = data.sortWith(masterFunctionBoolean)

    return new DataFrame(schema, newData)
  }

  private def sortColIntFunction(col: Column): (Seq[ColType], Seq[ColType]) => Int = {
    val f = col.func(this)

    val setlType = schema.find(_._1 == col.name).map(_._2).get

    val sortDir = col.sortDir match {
      case Asc => 1
      case Desc => -1
    }

    setlType match {
      case StringType => (row1, row2) => {
        val a = f(row1).asInstanceOf[String]
        val b = f(row2).asInstanceOf[String]

        sortDir * a.compareTo(b)
      }
      case IntegerType => (row1, row2) => {
        val a = f(row1).asInstanceOf[Int]
        val b = f(row2).asInstanceOf[Int]

        sortDir * a.compareTo(b)

      }
      case LongType => (row1, row2) => {
        val a = f(row1).asInstanceOf[Long]
        val b = f(row2).asInstanceOf[Long]

        sortDir * a.compareTo(b)
      }
      case BooleanType => (row1, row2) => {
        val a = f(row1).asInstanceOf[Boolean]
        val b = f(row2).asInstanceOf[Boolean]

        sortDir * a.compareTo(b)
      }
    }
  }

  def show(): Unit = {

    println(schema.map(_._1).reduceLeft((h1, h2) => h1 + "\t" + h2))
    data.foreach(row => println(row.reduceLeft((r1, r2) => r1.toString + "\t" + r2.toString)) )

  }
}
