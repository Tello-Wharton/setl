package scratch2

class Column(val name: String, val func: Function[DataFrame, Function[Seq[ColType], ColType]]) {
  def this(name: String) = {
    this(name, df => {

      val schema = df.schema
      val data = df.data

      val idx: Int = schema.zipWithIndex.find((el, idx) => el == name).map((el, idx) => idx).getOrElse(throw new RuntimeException("Can't."))

      row => row(idx)
    })
  }

  def this(name: String, lit: ColType) = {
    this(name, df => {
      row => lit
    })
  }

  def ===(col: Column): Column = {

    val name = s"${this.name} equals ${col.name}"

    new Column(name, df => {
      val f1 = this.func(df)
      val f2 = col.func(df)

      row => f1(row) == f2(row)
    })
  }
}
