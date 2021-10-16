package scratch2




object Main {

  def main(args: Array[String]): Unit = {

    val schema = Seq("a", "b", "c").map(col => (col, SetlType.StringType))
    val data : Seq[Seq[ColType]] = Seq(
      Seq("a1", "b1", "c1"),
      Seq("a2", "b2", "b2"),
      Seq("a3", "a3", "a3")
    )

    val df1 = new DataFrame(schema, data);


    val df2 = df1.select(new Column("c") === new Column("b"))

    println(df2.data)


    val df3 = df1.filter(new Column("a") === new Column("b"))

    println(df3.data)

    import functions._

    val df4 = df1.sort($"a")
    val df5 = df1.sort($"c")

    println(df4.data)
    println(df5.data)


  }

}
