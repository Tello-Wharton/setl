package scratch2

import scratch2.SetlType.SetlType

import scala.io.Source

class Setl {

  def csv(resource: String): DataFrame = {
    val csvReader = new CsvReader()
    val source = Source.fromResource(resource)
    csvReader.getDataframe(source)
  }

  def csv(resource: String, schema: Seq[(String, SetlType)]) : DataFrame = {
    val csvReader = new CsvReader()
    val source = Source.fromResource(resource)
    csvReader.getDataframe(source, schema)
  }

}
