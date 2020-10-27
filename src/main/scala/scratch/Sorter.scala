package scratch

import scala.collection.mutable.ListBuffer

class Sorter(intermediateBuffer: IntermediateBuffer) extends Runnable {

  private val items = ListBuffer.empty[Long]

  def getAndSort(): Unit = {
    val newSortedItems = intermediateBuffer.take().sorted
    if (newSortedItems.isEmpty) {
      Thread.sleep(1000)
    } else {
      items ++= newSortedItems
      items.sorted
      println(s"Sorted: ${newSortedItems.size}")
    }
  }

  override def run(): Unit = {
    while (!intermediateBuffer.isDry) {
      getAndSort()
    }
  }
}
