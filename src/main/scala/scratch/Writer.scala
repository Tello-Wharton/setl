package scratch

import java.io.BufferedOutputStream
import java.nio.charset.Charset
import java.nio.file.{Files, Path}

import scala.jdk.StreamConverters._
import scala.util.Using

class Writer(path: Path, finalSorter: FinalSorter) extends Runnable{

  def save(): Unit = {
    Using(Files.newOutputStream(path)) { outputStream =>
      val bufferedOutputStream = new BufferedOutputStream(outputStream)
      finalSorter.getAll.foreach(num => {
        bufferedOutputStream.write(f"$num\n".getBytes(Charset.forName("UTF-8")))
        })
    }
  }

  def canSave : Boolean = finalSorter.isFinished

  override def run(): Unit = {
    while (!canSave) {
      Thread.sleep(1000)
    }
    save()
    println("Done!")
  }
}
