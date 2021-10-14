package scratch2

class Column2(val name: String, val func: Function[DataFrame, Function[Seq[String], String]]) {
  def this(name: String) = {
    this(name, df => {

      val schema = df.schema
      val data = df.data

      val idx : Int = schema.zipWithIndex.find((el, idx) => el == name).map((el, idx) => idx).getOrElse(throw new RuntimeException("Can't."))

      row => row(idx)
    })
  }
}
