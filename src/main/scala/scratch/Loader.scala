package scratch

import java.io.{BufferedInputStream, FileReader}
import scala.jdk.StreamConverters._
import java.nio.file.{Files, Path}

import scala.util.Using

class Loader(path: Path, intermediateBuffer: IntermediateBuffer) extends Runnable{
    def loadAll(): Unit = {
      Using(Files.lines(path)) { lines =>
        lines.toScala(Iterator).filter(_.length > 0).map(_.toLong).foreach(intermediateBuffer.add)
        intermediateBuffer.drySignal()
      }
    }

  override def run(): Unit = {
    loadAll()
  }
}
