package scratch2

object Main {

  def main(args: Array[String]): Unit = {

    val schema = Seq("a", "b", "c")
    val data = Seq(
      Seq("a1", "b1", "c1"),
      Seq("a2", "b2", "b2"),
      Seq("a3", "a3", "a3")
    )

    val df1 = new DataFrame(schema, data);


    val df2 = df1.select2(new Column2("c") equals new Column2("b"))

    println(df2.data)
  }

}
