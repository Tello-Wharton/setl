package scratch2

class DataFrame(val schema: Seq[String], val data: Seq[Seq[String]]) {

  def select(cols: String*): DataFrame = {

    val keep: Seq[(String, Int)] = cols.flatMap(col => schema.zipWithIndex.find((el, idx) => el == col))
    val newSchema = keep.map((col, idx) => col)
    val newData = data.map(row => keep.map((col, idx) => row(idx)))

    return new DataFrame(newSchema, newData)
  }

  def filter(filterCol: Column[String, Boolean]): DataFrame = {

    val use: Int = schema.zipWithIndex.find((el, idx) => el == filterCol.in).map((el, idx) => idx).get
    val newData = data.filter(row => filterCol.func(row(use)))

    return new DataFrame(schema, newData)

  }
}
