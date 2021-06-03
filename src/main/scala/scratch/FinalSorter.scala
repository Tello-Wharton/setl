package scratch

import java.util

import scala.collection.mutable.ListBuffer

class FinalSorter(sorters: Seq[Sorter]) extends Runnable {

  private var items = ListBuffer.empty[Long]

  def sort() : Unit = {
    sorters.map(_.getAll).foreach(items.addAll)
    items = items.sorted
    println("Final Sorted!")
  }

  def canSort: Boolean = sorters.map(_.isFinished).reduce(_ && _)

 private var finished = false;

  override def run(): Unit = {
    while (!canSort) {
      Thread.sleep(1000)
    }
    sort()
    finished = true
  }

  def isFinished : Boolean = finished
  def getAll : ListBuffer[Long] = items

}
