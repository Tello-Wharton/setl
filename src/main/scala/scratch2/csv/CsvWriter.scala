package scratch2.csv

import scratch2.DataFrame
import scratch2.SetlType._
import scratch2.ColType

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.Path

class CsvWriter {

  def writeDataFrame(dataFrame: DataFrame, path: Path): Unit = {


    val bw = new BufferedWriter(new FileWriter(path.toFile))

    val header = dataFrame.schema.map(_._1).reduceLeft((h1, h2) => h1 + "," + h2)

    bw.write(header)
    bw.write("\n")

    val writerFunctions = dataFrame.
      schema.
      map(_._2).
      map(_ match {
        case StringType => (item: ColType) => "\"" + item + "\""
        case _ => (item: ColType) => item.toString
      }).
      zipWithIndex

    dataFrame.data.foreach((row) => {
      val rowText = writerFunctions.map((fun, idx) => fun(row(idx))).reduceLeft((item1, item2) => item1 + "," + item2)
      bw.write(rowText)
      bw.write("\n")
    })

    bw.close()

  }

}
