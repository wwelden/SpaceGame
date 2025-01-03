package cs2.adt

import org.junit._
import org.junit.Assert._

class DoubleEndPriorityQueueTester {
  
    var d:DoubleEndPriorityQueue[Int] = new DEPQ[Int]()

    @Before def init():Unit = {
        d = new DEPQ[Int]()
    }
    @Test def checkNotEmptyAfterAdd():Unit = {
        d.add(1)
        assertTrue(!d.isEmpty())
    }
    @Test def checkInitialEmpty():Unit = {
        assertTrue(d.isEmpty())
    }
    @Test def add():Unit = {
        d.add(2)
        d.add(5)
        d.add(1)
        d.add(4)
        //println("Max: " + d.peekMax())
        //println("Min: " + d.peekMin())
        assertTrue(d.peekMax == 5)
        assertTrue(d.peekMin == 1)
    }
    @Test def Max():Unit = {
        d.add(2)
        d.add(5)
        d.add(1)
        d.add(4)
        //println("Max: " + d.peekMax())
        //println("Min: " + d.peekMin())
        assertTrue(d.peekMax == 5)
        assertTrue(d.peekMin == 1)
        assertTrue(d.max()== 5)
        assertTrue(d.min()== 1)
        assertTrue(d.peekMax == 4)
        assertTrue(d.peekMin == 2)
        d.add(125)
        assertTrue(d.peekMax()==125)
        assertTrue(d.max()==125)
        d.add(521)
        //println("Max: " + d.peekMax())
        //println("Min: " + d.peekMin())
    }

    @Test def Min():Unit = {
        d.add(2)
        d.add(5)
        d.add(1)
        d.add(4)
        //println("Max: " + d.peekMax())
        //println("Min: " + d.peekMin())
        assertTrue(d.peekMax == 5)
        assertTrue(d.peekMin == 1)
        assertTrue(d.max()== 5)
        assertTrue(d.min()== 1)
        assertTrue(d.peekMax == 4)
        assertTrue(d.peekMin == 2)
        d.add(-125)
        assertTrue(d.peekMin()== -125)
        assertTrue(d.min()== -125)
        d.add(-521)
        //println("Max: " + d.peekMax())
        //println("Min: " + d.peekMin())
    }



}
