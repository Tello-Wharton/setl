package scratch

import java.util.concurrent.locks.{ReentrantLock, ReentrantReadWriteLock}

import scala.collection.mutable.ListBuffer

class IntermediateBuffer {

  private val lock = new ReentrantReadWriteLock();
  private val readLock = lock.readLock()
  private val writeLock = lock.writeLock()

  private val items = ListBuffer.empty[Long]
  private var dry = false

  def add(item: Long): Unit = {
    try {
      writeLock.lock()
      items += item
    } finally {
      writeLock.unlock()
    }
  }

  def drySignal(): Unit = {
    try {
      writeLock.lock()
      dry = true
    } finally {
      writeLock.unlock()
    }
  }

  def take(): ListBuffer[Long] = {
    try {
      readLock.lock()
      val toTake = items.take(items.length)
      items.clear()
      return toTake
    } finally {
      readLock.unlock()
    }
  }

  def isDry: Boolean = {
    try {
      readLock.lock()
      if (dry) items.isEmpty else false
    } finally {
      readLock.unlock()
    }
  }
}
