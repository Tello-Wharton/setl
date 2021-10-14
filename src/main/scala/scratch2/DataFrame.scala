package scratch2

class DataFrame(val schema: Seq[String], val data: Seq[Seq[ColType]]) {

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

    val newData = data.sortBy(row => f(row).asInstanceOf[String])

    return new DataFrame(schema, newData)
  }
}
