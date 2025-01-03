package cs2.game

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color
import scalafx.scene.input.MouseEvent
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scalafx.animation.AnimationTimer
import scalafx.scene.image.Image
import cs2.util.Vec2
import scala.util.control.Breaks._
import scala.collection.mutable._
import scalafx.scene.text.Font
import scala.io.Source
import java.io.PrintWriter
import io.StdIn._

class GameState(var pL: Vec2, var enL:Buffer[Vec2], var enP:Buffer[Image], var eBL:Buffer[Vec2], var pBL:Buffer[Vec2], var pB:Image, var eB:Image, var lev:Int, var liv:Int, var sco:Int, var wb:Int, var fl:Vec2, var psc:Int, var esc:Int, var pro:Double, var bk:Vec2, var bk2:Vec2, var bssL:Vec2, var bLiv:Boolean, var eC:Int, var eR:Int, var bossLs:Int){
  var pLoc:Vec2 = pL
  var enLoc:Buffer[Vec2] =  enL
  var enPics:Buffer[Image] = enP
  var eBLoc:Buffer[Vec2] = eBL
  var pBLoc:Buffer[Vec2] = pBL
  var level = lev
  var livs = liv
  var scos = sco
  var wob = wb
  var fli = fl
  var plSC = psc
  var enSC = esc
  var progr = pro
  val pBI:Image = pB
  val eBI:Image = eB
  var bckG = bk
  var bckG2 = bk2
  var bossLoc = bssL
  var bossLive = bLiv
  var enC = eC
  var enR = eR
  var bossLives = bossLs
  
  def undoPB():Buffer[Bullet] = {
      var ret:Buffer[Bullet] = Buffer[Bullet]()
      var velo:Vec2 = new Vec2(0, -5)
      for(i <- pBLoc){
        ret += new Bullet(pBI, i, velo)
      }
      ret
  }
  def undoEB():Buffer[Bullet] = {
      var ret:Buffer[Bullet] = Buffer[Bullet]()
      var velo:Vec2 = new Vec2(0, 5)
      for(i <- eBLoc){
        ret += new Bullet(eBI, i, velo)
      }
      ret
  }
  def undoE():Buffer[Enemy] = {
      var ret:Buffer[Enemy] = Buffer[Enemy]()
      var tI = eBI
    
      var temp1:Array[Vec2] = new Array[Vec2](enLoc.length)
      var temp2:Array[Image] = new Array[Image](enPics.length)

      var count:Int = 0

      for(i <- enLoc){
          temp1(count) = i
          count +=1
      }
      count = 0
      for(i <- enPics){
          temp2(count) = i
          count +=1
      }
      for(i <- 0 until enLoc.length){
        ret += new Enemy(temp2(i),temp1(i),eBI)
      }
      ret
  }
}
