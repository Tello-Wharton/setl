package scratch

import java.nio.charset.Charset
import java.nio.file.{Files, Path}
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.Executors

import scala.collection.mutable.ListBuffer
import scala.util.Using

object Main {
  def main(args: Array[String]): Unit = {

//    val es = Executors.newFixedThreadPool(8)
//
//    (0 until 4).foreach(i => es.submit[Int](() => {
//      val output = Path.of(f"D:\\${i}.txt")
//      Using(Files.newOutputStream(output)) { outputStream =>
//        (0 until 10000000).foreach(num => {
//          val randNum = (Math.random() * 10000000).toInt
//          outputStream.write(f"$randNum\n".getBytes(Charset.forName("UTF-8")))
//        })
//      }
//      0
//    }))
//
//    es.shutdown()

    val threads = ListBuffer.empty[Thread]

    (0 until 4).foreach(i => {

      val intermediateBuffer = new IntermediateBuffer()

      val path = Path.of(f"D:\\${i}.txt")
      val loaderThread = new Thread(new Loader(path, intermediateBuffer))
      val sorterThread = new Thread(new Sorter(intermediateBuffer))

      loaderThread.start()
      sorterThread.start()

      threads+= loaderThread
      threads+= sorterThread

      println("Started")
    })

    threads.foreach(_.join())
  }
}
