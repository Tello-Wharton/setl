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

    setl.
      csv("example3.csv", Seq(
        ("a", StringType),
        ("b", StringType),
        ("c", StringType),
        ("d", IntegerType),
        ("e", IntegerType)
      )).
      withColumn("f", $"d" + $"e").
      show()

  }
}
