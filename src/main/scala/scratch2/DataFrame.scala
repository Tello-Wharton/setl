package scratch2

class DataFrame(val schema: Seq[String], val data: Seq[Seq[ColType]]) {

  def select(cols: String*): DataFrame = select2(cols.map(col => new Column2(col)) *)

  def select2(cols: Column2*): DataFrame = {

    val colFuncs = cols.map(_ func this)
    val newSchema = cols.map(_.name)
    val newData = data.map(row => colFuncs.map(_(row)))

    return new DataFrame(schema, newData)
  }

  def filter2(filterCol: Column2): DataFrame = {

    val f = filterCol.func(this)

    val newData = data.filter(row => f(row).asInstanceOf[Boolean])

    return new DataFrame(schema, newData)
  }
}
