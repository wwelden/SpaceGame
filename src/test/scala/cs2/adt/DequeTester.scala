package cs2.adt

import org.junit._
import org.junit.Assert._

class DequeTester {
  //Include your thorough tester code here, including @Begin and @Test methods
  //and any fields needed for testing

  var d:Deque[Int] = new TheDeque[Int]()

  @Before def init():Unit = {
    d = new TheDeque[Int]()
  }
  @Test def checkNotEmptyAfterAppend():Unit = {
    d.append(1)
    assertTrue(!d.isEmpty())
  }
  @Test def checkNotEmptyAfterPrepend():Unit = {
    d.prepend(1)
    assertTrue(!d.isEmpty())
  }
  @Test def checkInitialEmpty():Unit = {
    assertTrue(d.isEmpty())
  }

  @Test def AppendBack():Unit = {
    d.append(1)
    assertTrue(d.peekBack() == 1)
    assertTrue(d.peekFront() == 1)
    assertTrue(!d.isEmpty())
    d.append(2)
    assertTrue(d.peekBack() == 2)
    assertTrue(d.peekFront() == 1)
    assertTrue(d.back() == 2)
    assertTrue(d.back() == 1)
    assertTrue(d.isEmpty())
  }
  @Test def AppendFront():Unit = {
    d.append(1)
    assertTrue(d.peekBack() == 1)
    assertTrue(d.peekFront() == 1)
    assertTrue(!d.isEmpty())
    d.append(2)
    assertTrue(d.peekBack() == 2)
    assertTrue(d.peekFront() == 1)
    assertTrue(d.front() == 1)
    assertTrue(d.front() == 2)
    assertTrue(d.isEmpty())
  }
  @Test def PrependFront():Unit = {
    d.prepend(1)
    assertTrue(d.peekBack() == 1)
    assertTrue(d.peekFront() == 1)
    assertTrue(!d.isEmpty())
    d.prepend(2)
    assertTrue(d.peekBack() == 1)
    assertTrue(d.peekFront() == 2)
    assertTrue(d.front() == 2)
    assertTrue(d.front() == 1)
    assertTrue(d.isEmpty())
  }
  @Test def PrependBack():Unit = {
    d.prepend(1)
    assertTrue(d.peekBack() == 1)
    assertTrue(d.peekFront() == 1)
    assertTrue(!d.isEmpty())
    d.prepend(2)
    assertTrue(d.peekBack() == 1)
    assertTrue(d.peekFront() == 2)
    assertTrue(d.back() == 1)
    assertTrue(d.back() == 2)
    assertTrue(d.isEmpty())
  }

  @Test def checkManyAppendBack():Unit = {
    for(i <- 1 to 50) {
      d.append(i)
    }
    assertTrue(!d.isEmpty())
    for(i <- 50 to 1 by -1) {
      assertTrue(d.peekBack == i)
      assertTrue(d.back == i)
    }
    assertTrue(d.isEmpty())
  }
  
  @Test def checkManyPrependFront():Unit = {
    for(i <- 1 to 50) {
      d.prepend(i)
    }
    assertTrue(!d.isEmpty())
    for(i <- 50 to 1 by -1) {
      assertTrue(d.peekFront == i)
      assertTrue(d.front == i)
    }
    assertTrue(d.isEmpty())
  }
  @Test def checkManyAppendFront():Unit = {
    for(i <- 1 to 50) {
      d.append(i)
    }
    assertTrue(!d.isEmpty())
    for(i <- 1 to 50 by 1) {
      assertTrue(d.peekFront == i)
      assertTrue(d.front == i)
    }
    assertTrue(d.isEmpty())
  }
  
  @Test def checkManyPrependBack():Unit = {
    for(i <- 1 to 50) {
      d.prepend(i)
    }
    assertTrue(!d.isEmpty())
    for(i <- 1 to 50 by 1) {
      assertTrue(d.peekBack == i)
      assertTrue(d.back == i)
    }
    assertTrue(d.isEmpty())
  }

}