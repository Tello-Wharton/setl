package scratch2

import scala.language.implicitConversions

import SetlType._
import ColSortDir._

import scratch2.Column.colTypeToSetlType

//TODO consider if mutable sortDir is appropriate
class Column(val name: String, val setlType: Function[DataFrame, SetlType], val func: Function[DataFrame, Function[Seq[ColType], ColType]], var sortDir: ColSortDir = Asc) {
  def this(name: String) = {
    this(name, df => {
      df.schema.find(_._1 == name).map(_._2).get
    }, df => {

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

    new Column(name, df => BooleanType, df => {
      val f1 = this.func(df)
      val f2 = col.func(df)

      row => f1(row) == f2(row)
    })
  }

  def +(col: Column): Column = {
    val name = s"${this.name} equals ${col.name}"

    new Column(name, df => LongType, df => {
      val f1 = this.func(df)
      val f2 = col.func(df)

      val t1 = df.schema.find(_._1 == this.name).map(_._2).get
      val t2 = df.schema.find(_._1 == col.name).map(_._2).get

      val isT1number = t1 match {
        case IntegerType | LongType => true
        case _ => false
      }

      val isT2Number = t2 match {
        case IntegerType | LongType => true
        case _ => false
      }

      val areNumbers = isT1number && isT2Number

      if (!areNumbers) throw new RuntimeException("Not numbers!")

      val t1Function = t1 match {
        case IntegerType => (item: ColType) => item.asInstanceOf[Int].toLong
        case LongType => (item: ColType) => item.asInstanceOf[Long]
      }

      val t2Function = t2 match {
        case IntegerType => (item: ColType) => item.asInstanceOf[Int].toLong
        case LongType => (item: ColType) => item.asInstanceOf[Long]
      }


      row => t1Function(f1(row)) + t2Function(f2(row))
    })
  }

  def asc: Column = {
    this.sortDir = Asc
    this
  }

  def desc: Column = {
    this.sortDir = Desc
    this
  }
}

object Column {
  private def colTypeToSetlType(colType: ColType): Function[DataFrame, SetlType] = colType match {
    case _: String => df => StringType
    case _: Int => df => IntegerType
    case _: Long => df => LongType
    case _: Boolean => df => BooleanType
  }
}
