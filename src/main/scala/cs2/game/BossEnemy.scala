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
import scala.collection.mutable.Queue

class BossEnemy(pic:Image, initPos:Vec2, private val bulletPic:Image) extends Enemy(pic, initPos, bulletPic){
    var speed:Int = 8
    var moves:Queue[Vec2] = new Queue[Vec2]()
    //var shots:Quene[]
    def moveLeft():Unit = { 
        if(this.pos.x > 50){
                moves.enqueue(new Vec2((-1*speed),0))
        }
    }

    def moveRight():Unit = { 
        if(this.pos.x < 750){
                moves.enqueue(new Vec2(speed,0))
        }
    }
    def makeMove():Unit = {
        //if(this.pos.x < 770 && this.pos.x > 30){
        var temp:Vec2 = moves.dequeue()
        if(this.pos.x+temp.x < 760 && this.pos.x+temp.x > 40){
        this.move(temp)
        }
    }
    //override def shoot():Unit = {}

    
  
}
