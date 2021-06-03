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
    val sorters = ListBuffer.empty[Sorter]

    (0 until 4).foreach(i => {

      val intermediateBuffer = new IntermediateBuffer()

      val path = Path.of(f"D:\\${i}.txt")
      val loaderThread = new Thread(new Loader(path, intermediateBuffer))

      val sorter = new Sorter(intermediateBuffer)
      sorters += sorter

      val sorterThread = new Thread(sorter)


      loaderThread.start()
      sorterThread.start()

      threads+= loaderThread
      threads+= sorterThread

      println("Started")
    })

    val finalSorter = new FinalSorter(sorters.toSeq)
    val finalSorterThread = new Thread(finalSorter)
    finalSorterThread.start()
    threads += finalSorterThread

    val writerThread = new Thread(new Writer(Path.of("D:\\done.txt"), finalSorter))
    writerThread.start()
    threads += writerThread

    threads.foreach(_.join())
  }
}
