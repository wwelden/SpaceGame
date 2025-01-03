package cs2.util

class Vec2 (var x:Double, var y:Double) {
  //Clone method overriden to retur a copy of the calling object
  override def clone():Vec2 = new Vec2(this.x, this.y)

  //Provided toString method simplifies printing, e.g. println(vec.toString) OR println(vec)
	override def toString():String = "("+x+","+y+")"

	//Methods for addition and subtraction of vectors
	def + (other:Vec2):Vec2 = { new Vec2(x + other.x, y + other.y) }
	def - (other:Vec2):Vec2 = { new Vec2(x - other.x, y - other.y) }

	def += (other:Vec2):Unit = { 
  this.x = this.x + other.x 
  this.y = this.y + other.y}
	def -= (other:Vec2):Unit = {  
  this.x = this.x - other.x 
  this.y = this.y - other.y }
	//Methods for multiplication and division of vectors by a scalar (non-vector)
	def * (scalar:Double):Vec2 = { new Vec2(x * scalar, y * scalar) }
	def / (scalar:Double):Vec2 = { new Vec2(x / scalar, y / scalar) }

	def *= (scalar:Double):Unit = {  
  this.x = this.x * scalar 
  this.y = this.y * scalar }
	def /= (scalar:Double):Unit = {  
  this.x = this.x / scalar 
  this.y = this.y / scalar }

	//Methods to determine the length of a vector (magnitude and length should return the same value)
	def magnitude():Double = { math.sqrt(x*x + y*y) }
	def length():Double = { magnitude }

	//Method to return a new vector that is in the same direction, but length 1
	def normalize():Vec2 = { this / magnitude }
	def unary_~():Vec2 = { this / magnitude }

	//Methods to calculate the dot product, and determine the angle between 2 vectors
	def dot(other:Vec2):Double = { x * other.x + y * other.y }
	def **(other:Vec2):Double = this.dot(other)

	def angleBetween(other:Vec2):Double = { math.acos(dot(other) / (magnitude * other.magnitude)) }
	def <>(other:Vec2):Double = this.angleBetween(other)

}

object Vec2 {
  def apply(myX:Double, myY:Double):Vec2 = { new Vec2(myX, myY) }
  def apply(toCopy:Vec2):Vec2 = { new Vec2(toCopy.x, toCopy.y) }
  def apply():Vec2 = { new Vec2(0, 0) }

  def main(args:Array[String]):Unit = {
    val san:Vec2 = Vec2(29.4241,98.4936)
    val aus:Vec2 = Vec2(30.2672,97.7431)
    val dir = aus - san
    println(san + (dir / 1.5) * 4)
  }
}



/*
My Code

class Vec2 (var x:Double, var y:Double) {
  override def toString():String = "("+x+","+y+")"
  
  def +  (other:Vec2):Vec2 = {new Vec2(this.x + other.x, this.y + other.y) }
  def += (other:Vec2):Unit = { 
  this.x = this.x + other.x 
  this.y = this.y + other.y}
  
  def -  (other:Vec2):Vec2 = { new Vec2(this.x - other.x, this.y - other.y) }
  def -= (other:Vec2):Unit = {  
  this.x = this.x - other.x 
  this.y = this.y - other.y }

  def *  (scalar:Double):Vec2 = { new Vec2(this.x * scalar, this.y * scalar) }
  def *= (scalar:Double):Unit = {  
  this.x = this.x * scalar 
  this.y = this.y * scalar }

  def /  (scalar:Double):Vec2 = { new Vec2(this.x / scalar, this.y / scalar) }
  def /= (scalar:Double):Unit = {  
  this.x = this.x / scalar 
  this.y = this.y / scalar }

  def magnitude():Double = {Math.abs(Math.sqrt(Math.pow(this.x, 2)+ Math.pow(this.y, 2))) }
  def length():Double = {Math.abs(Math.sqrt(Math.pow(this.x, 2)+ Math.pow(this.y, 2))) }
  
  def dot(other:Vec2):Double = { (this.x*other.x) + (this.y*other.y) }
  def **(other:Vec2):Double = { (this.x*other.x) + (this.y*other.y) }
  
  def angleBetween(other:Vec2):Double = { Math.acos(Math.abs(this.x* other.x)/(Math.abs(this.y)*Math.abs(other.y)))}
  def <>(other:Vec2):Double = { Math.acos(Math.abs(this.x* other.x)/(Math.abs(this.y)*Math.abs(other.y))) }

  def normalize():Vec2 = { 
  var mg:Double = Math.abs(Math.sqrt(Math.pow(this.x, 2)+ Math.pow(this.y, 2))) 
  new Vec2(this.x *mg, this.y *mg)}
  def unary_~ : Vec2 = { 
  var mg:Double = Math.abs(Math.sqrt(Math.pow(this.x, 2)+ Math.pow(this.y, 2))) 
  new Vec2(this.x *mg, this.y *mg)}

  override def clone():Vec2 = {  new Vec2(this.x, this.y) }
}

object Vec2 {
  def apply(myX:Double, myY:Double):Vec2 = { new Vec2(myX, myY) }
  def apply(toCopy:Vec2):Vec2 = { new Vec2(toCopy.x, toCopy.y) }
  def apply():Vec2 = { new Vec2(0, 0) }

  def main(args:Array[String]):Unit = {
    var SA:Vec2 = new Vec2(29.4241, 98.4936)
    var A:Vec2 = new Vec2(30.2672, 97.7431)

    var vec:Vec2 = A-SA
    println("Step 1: " +vec)
    vec /=(1.5)
    println("Step 2: " +vec)
    vec *=(2.5)
    println("Step 3: "+ vec)
    vec +=(A)
    println("Step 4: "+ vec)
    println("The answer is: "+ vec)
  }
}
*/

