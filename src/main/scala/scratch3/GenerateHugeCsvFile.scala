package scratch3

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.Paths
import scala.util.Using

object GenerateHugeCsvFile {

  def main(args: Array[String]): Unit = {

    Using.Manager(use => {

      val path = Paths.get("D:\\huge.csv")
      val fw = use(new FileWriter(path.toFile))
      val bw = use(new BufferedWriter(fw))


      bw.write(s"${0},${0},${0}")

      (0L to 1_00_000_000L).foreach((n) => {

        bw.write("\n")

        bw.write(s"${n},${n + 1},${n + 2}")
      })
    })
  }
}
