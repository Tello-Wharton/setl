package scratch2

import scala.io.Source

import SetlType._

import functions._

object Main {

  def main(args: Array[String]): Unit = {


    val csvReader = new CsvReader()

    val source1 = Source.fromResource("example.csv")
    val df1 = csvReader.getDataframe(source1)


    val source2 = Source.fromResource("example2.csv")
    val df2 = csvReader.getDataframe(source2, Seq(
      ("a", StringType),
      ("b", StringType),
      ("c", StringType),
      ("d", IntegerType),
      ("e", IntegerType)
    ))


//    println(df2.sort($"e").data)
//    println(df2.sort2($"e").data)


    val source3 = Source.fromResource("example3.csv")


    val df3 = csvReader.getDataframe(source3, Seq(
      ("a", StringType),
      ("b", StringType),
      ("c", StringType),
      ("d", IntegerType),
      ("e", IntegerType)
    ))

    println(df3.sort2($"b", $"e").data)
    println(df3.sort2($"c", $"e").data)




  }
}
