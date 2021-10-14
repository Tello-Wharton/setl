package scratch2

class DataFrame(val schema: Seq[String], val data: Seq[Seq[String]]) {

  def select(cols: String*): DataFrame = select2(cols.map(col => new Column2(col)) *)

  def select2(cols: Column2*): DataFrame = {

    val colFuncs = cols.map(_ func this)
    val newSchema = cols.map(_.name)
    val newData = data.map(row => colFuncs.map(_(row)))

    return new DataFrame(schema, newData)
  }

  def filter(filterCol: Column[String, Boolean]): DataFrame = {

    val use: Int = schema.zipWithIndex.find((el, idx) => el == filterCol.in).map((el, idx) => idx).get
    val newData = data.filter(row => filterCol.func(row(use)))

    return new DataFrame(schema, newData)

  }
}
