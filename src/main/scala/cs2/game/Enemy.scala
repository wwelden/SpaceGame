package cs2.game

import scalafx.scene.image.Image
import cs2.util.Vec2

class Enemy(pic:Image, initPos:Vec2, private val bulletPic:Image) extends Sprite(pic, initPos) {

  def shoot():Bullet = {
    var veccy:Vec2 = this.pos.clone()
    var velo:Vec2 = new Vec2(0, 5)///5
    new Bullet(bulletPic, veccy, velo)
  }
  
}
