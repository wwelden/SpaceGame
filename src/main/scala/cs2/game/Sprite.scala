package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scala.collection.mutable._


abstract class Sprite (protected val img:Image,  protected var pos:Vec2) {
    val w:Double = (this.img.width.value)
    val h:Double = (this.img.height.value)

  def getPos():Vec2 = {pos}

  def getImg():Image ={img}
    
  def move (direction:Vec2):Unit = { 
    pos += direction
  }

  def moveTo (location:Vec2):Unit = { 
    pos = location
  }

  def intersection[A <% Sprite](thing:A):Boolean = {
    var ret = false
    var hW:Double = (w/2)
    var hH:Double = (h/2)
    var ohW:Double = (thing.w/2)
    var ohH:Double = (thing.h/2)

    if((this.pos.x - hW) <= (thing.pos.x+ ohW) && (this.pos.x+ hW) >= (thing.pos.x- ohW) && (this.pos.y-hH) <= (thing.pos.y+ohH) && (this.pos.y+ hH) >= (thing.pos.y-ohH)){
      ret = true
    }
    ret
  }

  def outOfBounds(width:Double, height:Double):Boolean = {
    var ret = false
    if(this.pos.x < 0 || this.pos.x > width || this.pos.y < 0 || this.pos.y > height){
      ret = true
    }
    ret
  }
  
  def display (g:GraphicsContext):Unit = { 
    var hW:Double = (w/2)
    var hH:Double = (h/2)
    //g.setStroke(Color.Red)
    //g.strokeRect(pos.x-hW,pos.y-hH,w,h)
    g.drawImage(img, pos.x-hW,pos.y-hH,w,h)
    //g.drawImage(img, pos.x-hW,pos.y-hH,w,h)
    //g.strokeLine(pos.x,0,pos.x,800)
  }
  
}
