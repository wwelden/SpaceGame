package cs2.adt

import java.util.EmptyStackException

class TheDeque[A : Manifest] extends Deque[A] {
  //Define the required methods and create any neccessary fields here
  //I helpped Elizabeth McLauchin and Eli Greene with this

  private class Node (var data:A, var next:Node)
  private var head:Node = null
  private var tail:Node = null
  private var len:Int = 0

def append (elem: A): Unit = {
  if(tail == null) {
      head = new Node(elem, null)
      tail = head

    } else {
      tail.next = new Node(elem, null)
      tail = tail.next
    }
    len += 1
}
def back(): A = {
  if(isEmpty()) throw new EmptyStackException()
    val ret = tail.data
    var pointer = head
    if(len == 1){
      head = null
      tail = null
    }else if(len == 2){
      tail = pointer
      pointer.next = null 
    }else{
      for(i <- 1 until len-1){
        pointer = pointer.next
      }
      tail = pointer
      pointer.next = null 
    }
    len -= 1
    ret
}
def front(): A = {
  if(isEmpty()) throw new EmptyStackException()
    val ret = head.data
    if(len == 1){
      tail = null
    }
    head = head.next
    len -= 1
    ret
}
def isEmpty(): Boolean = {
  len == 0
}

def peekBack(): A = {
  if(isEmpty()) throw new EmptyStackException()
    tail.data
}
def peekFront (): A = {
  if(isEmpty()) throw new EmptyStackException()
    head.data
}
def prepend (elem: A): Unit = {
  if(tail == null){
  tail = new Node(elem, head)
  }
  head = new Node(elem, head)
  len += 1
}
}