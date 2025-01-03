package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2

class Bullet(pic:Image, initPos:Vec2, private var vel:Vec2) extends Sprite(pic, initPos) {

  def timeStep():Unit = { 
    this.move(vel)
  }
  
}
