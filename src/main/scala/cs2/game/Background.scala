package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color
import scala.collection.mutable._

class Background(pic:Image, initPos:Vec2, private var vel:Vec2) extends Sprite(pic, initPos) {
    //val w2:Double = (this.img.width.value)
    //val h2:Double = (this.img.height.value)
    def timeStep():Unit = { 
    this.move(vel)
  }

  override def display (g:GraphicsContext):Unit = { 
    var hW:Double = (w/2)
    var hH:Double = (h/2)
    //g.setStroke(Color.Red)
    //g.strokeRect(pos.x-hW,pos.y-hH,w,h)
    g.drawImage(img, pos.x-hW,pos.y,2*w,400)
    g.drawImage(img, pos.x-hW,pos.y+400,2*w, 400)
    //g.strokeLine(pos.x,0,pos.x,800)
  }
  def bottomOfScreen(height:Double):Boolean = {
    var ret = false
    if(this.pos.y > height){
      ret = true
    }
    ret
  }
  def topOfScreen(height:Double):Boolean = {
    var ret = false
    if(this.pos.y > 0){
      ret = true
    }
    ret
  }
}
