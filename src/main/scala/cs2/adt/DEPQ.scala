package cs2.adt

import java.util.EmptyStackException

class DEPQ[A <% Ordered[A]] extends DoubleEndPriorityQueue[A] {
  //Place your implementation here
  private class Node(val data:A, var next:Node)
  private var head:Node = null
  private var tail:Node = null
  private var len:Int = 0
  def isEmpty():Boolean = {len == 0}
  def add(elem: A):Unit = {//I helpped Elizabeth and Eli with their add method and stuff
    var pointer = head
    var prev = head
    if(isEmpty()){
        tail = new Node(elem, head)
        head = new Node(elem, head)
    }else{
      if(elem > pointer.data){
          head = new Node(elem, head)
      }else if(elem < tail.data){
          tail.next = new Node(elem, null)
          tail = tail.next
      }else{
          pointer = pointer.next
      
      while(elem < pointer.data){
        //println(pointer.data)
          pointer = pointer.next
          prev = prev.next
      }
      if(elem > pointer.data){
          var temp = new Node(elem, pointer)
          prev.next = temp
      }
    }
    }
      len += 1 
  }
  def peekMax():A = {
       if(isEmpty()) throw new EmptyQueueException()
       head.data
  }
  def max():A = {
      if(isEmpty()) throw new EmptyStackException()
    val ret = head.data
    if(len == 1){
      tail = null
    }
    head = head.next
    len -= 1
    ret

  }
  def peekMin():A = {
  if(isEmpty()) throw new EmptyQueueException()
       tail.data
    }
  def min():A = {
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
}