package scratch2

import java.util.regex.Pattern
import scala.io.{BufferedSource, Source};

import SetlType._

class CsvReader(val soruce: Source) {

  val commaSplitter = Pattern.compile(",")

  val lines = soruce.getLines()
  val header = commaSplitter.split(lines.next()).map(s => s.trim).toSeq.map(colName => (colName, StringType))


  val rows = lines.map(commaSplitter.split(_).map(_.trim).toSeq).toSeq

  def getDataframe() : DataFrame = new DataFrame(header, rows)

}
