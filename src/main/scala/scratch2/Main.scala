package scratch2

object Main {

  def main(args: Array[String]): Unit = {

    val schema = Seq("a", "b", "c")
    val data = Seq(
      Seq("a1", "b1", "c1"),
      Seq("a2", "b2", "c2"),
      Seq("a3", "b3", "c3")
    )

    val df1 = new DataFrame(schema, data);


    val df2 = df1.select("a")
    val df3 = df1.select("b")
    val df4 = df1.select("a", "b")
    val df5 = df1.select("b", "c")
    val df6 = df1.select("c", "a")
    val df7 = df1.select("a", "b", "c")
    val df8 = df1.select("c", "b", "a")

    println(df1.data)
    println(df2.data)
    println(df3.data)
    println(df4.data)
    println(df5.data)
    println(df6.data)
    println(df7.data)
    println(df8.data)

    val dff1 = df1.filter(new Column("a", "a", _ == "a1"))
    val dff2 = df1.filter(new Column("b", "a", _ == "b1"))
    val dff3 = df1.filter(new Column("c", "a", _ == "b1"))
    val dff4 = df6.filter(new Column("a", "a", _ == "a1"))

    println(dff1.data)
    println(dff2.data)
    println(dff3.data)
    println(dff4.data)



    val cake = df1.select2(new Column2("b"))
    println(cake.data)

  }

}
