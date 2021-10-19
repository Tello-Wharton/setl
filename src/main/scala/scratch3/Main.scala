package scratch3


import scala.util.Using

import java.io.ByteArrayOutputStream
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.nio.ByteBuffer

import java.nio.charset.StandardCharsets

object Main {
  def main(args: Array[String]): Unit = {


    Using.Manager(use => {

      val reader = use(new RandomAccessFile("D:\\cake.txt", "r"))
      val readerChannel = use(reader.getChannel)



      val writer = use(new RandomAccessFile("D:\\cake2.txt", "rw"))
      val writerChannel = use(writer.getChannel)

      val out = new ByteArrayOutputStream


      val bufferSize = 20
      val buff = ByteBuffer.allocate(bufferSize)


      while (readerChannel.read(buff) > 0) {
        buff.flip()
        writerChannel.write(buff)
        buff.clear()
        println(s"copied ${bufferSize} bytes!")
      }
    })

  }
}
