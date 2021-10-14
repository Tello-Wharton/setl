package scratch2

object functions {


  implicit class StringToColumn(val sc: StringContext) {
    def $(args: Any*): Column = {
      new Column(sc.s(args: _*))
    }
  }

}

