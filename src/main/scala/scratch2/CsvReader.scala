package scratch2

import java.util.regex.Pattern
import scala.io.{BufferedSource, Source};

import SetlType._

class CsvReader() {

  val commaSplitter = Pattern.compile(",")


  def getDataframe(soruce: Source): DataFrame = {

    val lines = soruce.getLines()
    val schema = commaSplitter.split(lines.next()).map(s => s.trim).toSeq.map(colName => (colName, StringType))

    getDataframe(lines, schema)
  }

  def getDataframe(source: Source, schema: Seq[(String, SetlType)]): DataFrame = {
    val lines = source.getLines()
    lines.next()
    getDataframe(lines, schema)
  }

  private def getDataframe(lines: Iterator[String], schema: Seq[(String, SetlType)]): DataFrame = {

    val converters = schemaToConverters(schema).zipWithIndex

    val rows = lines.map(commaSplitter.split(_).map(_.trim).toSeq).toSeq

    val convertedRows = rows.map(row => converters.map((converter, idx) => converter(row(idx))))

    new DataFrame(schema, convertedRows)
  }

  private def schemaToConverters(schema: Seq[(String, SetlType)]): Seq[Function[String, ColType]] = schema.
    map(_._2).
    map(coltype => coltype match {
      case StringType => string => string
      case IntegerType => _.toInt
      case LongType => _.toLong
      case BooleanType => _.toBoolean
    })

}
