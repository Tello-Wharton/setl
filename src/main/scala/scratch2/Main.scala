package scratch2

import scala.io.Source


object Main {

  def main(args: Array[String]): Unit = {

    val source = Source.fromResource("example.csv")
    val csvReader = new CsvReader(source)

    val df1 = csvReader.getDataframe()


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
