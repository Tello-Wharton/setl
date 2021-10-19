package scratch2

import SetlType._

object functions {

  implicit class StringToColumn(val sc: StringContext) {
    def $(args: Any*): Column = {
      new Column(sc.s(args: _*))
    }
  }

  def lit(lit : ColType) : Column = new Column("", lit)

  def udf(function: Function[ColType, ColType]) : Function[Column, Column] = col => {

    //TODO does this work at runtime?
    val typeFunction: Function[DataFrame, SetlType] = function match {
      case _ : Function[ColType, String] => df => StringType
      case _ : Function[ColType, Long] => df => LongType
      case _ : Function[ColType, Int] => df => IntegerType
      case _ : Function[ColType, Boolean] => df => BooleanType
      case _ => throw new RuntimeException("Type not supported!")
    }

    new Column(s"udf(${col.name})", typeFunction, df => {
      col.func(df).andThen(function)
    })
  }

}

