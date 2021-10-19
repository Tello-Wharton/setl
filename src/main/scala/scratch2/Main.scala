package scratch2

import scala.io.Source
import SetlType.*
import functions.*

import java.nio.file.{Path, Paths}

object Main {

  def main(args: Array[String]): Unit = {

    val setl = new Setl()

    val df1 = setl.csv("example.csv")

    val df2 = setl.csv("example2.csv", Seq(
      ("a", StringType),
      ("b", StringType),
      ("c", StringType),
      ("d", IntegerType),
      ("e", IntegerType)
    ))


//    println(df2.sort($"e").data)

    val df3 = setl.csv("example3.csv", Seq(
      ("a", StringType),
      ("b", StringType),
      ("c", StringType),
      ("d", IntegerType),
      ("e", IntegerType)
    ))

    println(df3.sort($"b", $"e").data)
    println(df3.sort($"c", $"e").data)
    println(df3.sort($"c", $"b").data)
    println(df3.sort($"c", $"a", $"b").data)

    println()
    println()

    println(df3.sort($"b".desc, $"e".desc).data)

    val df4 = df3.sort($"b".desc, $"e".desc)

    setl.writeCsv(df4, Paths.get("D:\\example.csv"))


    println(df3.select($"e" + $"d").data)


    val time2udf = udf((item) => item.asInstanceOf[Int] * 2)

    println(df3.select(time2udf($"e")).data)


  }
}
