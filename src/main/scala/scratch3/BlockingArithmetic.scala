package scratch3

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.Paths
import java.nio.file.Path
import java.nio.file.Files
import java.util.regex.Pattern
import scala.util.Using
import scala.jdk.CollectionConverters.*

object BlockingArithmetic {

  def main(args: Array[String]): Unit = {

    val start = System.currentTimeMillis()

    Using.Manager(use => {

      val in = Paths.get("D:\\huge.csv")
      val lines = use(Files.lines(in)).iterator().asScala

      val commaSplitter = Pattern.compile(",")


      val out = Paths.get("D:\\huge-out-1.csv")
      val writer = use(Files.newBufferedWriter(out))


      lines.
        map(commaSplitter.split(_)).
        map(numArray => numArray.map(_.toLong)).
        map(numArray => numArray(0) * 2 + numArray(0) * 3 + numArray(0) * 4).
        map("\n" + _).
        foreach(writer.write(_))
    })

    val end = System.currentTimeMillis()

    val seconds = (end - start) / 1000

    println(s"Took ${seconds} seconds")

  }
}
