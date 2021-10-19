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

    new Column(s"udf(${col.name})", AnyType, df => {
      col.func(df).andThen(function)
    })
  }

}

