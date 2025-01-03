package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.animation.AnimationTimer
import scala.collection.mutable._

class Player(avatar:Image, initPos:Vec2, private val bulletPic:Image) extends Sprite(avatar, initPos) {
    var speed:Int = 8
  def moveLeft():Unit = { 
          if(this.pos.x > 40){
            this.move(new Vec2((-1*speed),0))//10
          }
  }

  def moveRight():Unit = { 
        if(this.pos.x < 760){
          this.move(new Vec2(speed,0)) //10
        }
  }

  def moveUp():Unit = { 
        if(this.pos.y > 50){
          this.move(new Vec2(0,(-1*speed))) //10
        }
  }

  def moveDown():Unit = { 
          if(this.pos.y < 750){
            this.move(new Vec2(0,speed))//10
          }
  }

  def shoot():Bullet = { 
    var veccy:Vec2 = this.pos.clone()
    var velo:Vec2 = new Vec2(0, -5)//-5
    new Bullet(bulletPic, veccy, velo)
   }
   def superShot():Buffer[Bullet]={
     var velo:Vec2 = new Vec2(0, -5)//-5
     var ret:Buffer[Bullet] = Buffer[Bullet]()
     for(i <- 0 to 800 by 30){
     var veccy:Vec2 = new Vec2(i,this.pos.clone().y)
     ret += new Bullet(bulletPic, veccy, velo)
    }
    ret
   }
   def doubleShot():Buffer[Bullet]={
     var hW = (this.w /2)- 5
     var velo:Vec2 = new Vec2(0, -5)//-5
     var ret:Buffer[Bullet] = Buffer[Bullet]()
     ret += new Bullet(bulletPic, new Vec2(this.pos.clone().x - hW ,this.pos.clone().y), velo)
     ret += new Bullet(bulletPic, new Vec2(this.pos.clone().x + hW ,this.pos.clone().y), velo)
     ret
    }
    
}
