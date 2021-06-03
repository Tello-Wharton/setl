package scratch

import scala.collection.mutable.ListBuffer

class Sorter(intermediateBuffer: IntermediateBuffer) extends Runnable {

  private var items = ListBuffer.empty[Long]
  private var finished = false

  def getAndSort(): Unit = {
    val newSortedItems = intermediateBuffer.take().sorted
    if (newSortedItems.isEmpty) {
      Thread.sleep(1000)
    } else {
      items ++= newSortedItems
      items = items.sorted
      println(s"Sorted: ${newSortedItems.size}")
      println(s"Sorted Total: ${items.size}")
    }
  }

  override def run(): Unit = {
    while (!intermediateBuffer.isDry) {
      getAndSort()
    }
    finished = true
  }

  def isFinished: Boolean = {
    finished
  }

  def getAll: ListBuffer[Long] = items

}
