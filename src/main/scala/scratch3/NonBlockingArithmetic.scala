package scratch3


import java.io.{BufferedWriter, FileWriter, RandomAccessFile}
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import java.nio.file.Path
import java.nio.file.Files
import java.util.regex.Pattern
import scala.util.{Failure, Success, Using}
import scala.jdk.CollectionConverters.*


object NonBlockingArithmetic {


  def main(args: Array[String]): Unit = {

    val start = System.currentTimeMillis()

    val res = Using.Manager(use => {

      val in = Paths.get("D:\\huge.csv").toFile
      val out = Paths.get("D:\\huge-out-2.csv").toFile

      val commaSplitter = Pattern.compile(",")


      val reader = use(new RandomAccessFile(in, "r"))
      val readerChannel = use(reader.getChannel)
      val readbuff = ByteBuffer.allocate(1024)

      val writer = use(new RandomAccessFile(out, "rw"))
      val writerChannel = use(writer.getChannel)
      val writebuff = ByteBuffer.allocate(1024)


      println("cake")
      val sb = new StringBuilder()

      val longBuff = new Array[Long](3)
      var longBuffIdx = 0


      var count = 0L
      while (readerChannel.read(readbuff) > 0) {
        readbuff.flip()

        while (readbuff.remaining() > 0) {
          val c = readbuff.get().toChar

          if (c == ',') {
            val n = sb.result().toLong
            sb.clear()

            longBuff.update(longBuffIdx, n)
            longBuffIdx += 1
          } else if (c == '\n') {
            val n = sb.result().toLong
            sb.clear()

            longBuff.update(longBuffIdx, n)
            longBuffIdx = 0

            val res = longBuff(0) * 2 + longBuff(1) * 3 + longBuff(2) * 4

            writebuff.clear()
            writebuff.put(s"${res}\n".getBytes(StandardCharsets.UTF_8))
            writebuff.flip()
            writerChannel.write(writebuff)

            count += 1

            if (count % 100_000 == 0) println((count * 100).toDouble / 1_00_000_000L)

          } else {
            sb.append(c)
          }
        }
        readbuff.clear()
      }
    })

    res match {
      case Success(value) => ""
      case Failure(exception) => exception.printStackTrace()
    }

    val end = System.currentTimeMillis()

    val seconds = (end - start) / 1000

    println(s"Took ${seconds} seconds")

  }

}
