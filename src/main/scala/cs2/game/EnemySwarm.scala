package cs2.game

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image
import cs2.util.Vec2
import scala.collection.mutable._

class EnemySwarm(private val nRows:Int, private val nCols:Int) {
    var enemies = Buffer[Enemy]()
    var enemiesBs = Buffer[Bullet]()

    val enemyPath1 = getClass().getResource("/images/gImages2/enemy1.png")
    val enemyImg1 = new Image(enemyPath1.toString)
    val enemyPath2 = getClass().getResource("/images/gImages2/enemy2.png")
    val enemyImg2 = new Image(enemyPath2.toString)
    val ebulletPath = getClass().getResource("/images/gImages2/ebullet.png")
    val ebulletImg = new Image(ebulletPath.toString)
    var tempEnImg:Image = enemyImg1

    var sepW = 800/(nRows+1)
    var sepH = 500/(nCols+1)
    var paws = 1

    for(i <- 0 until nRows){
      for(j <- 0 until nCols){
      if(j % 2 ==0){
        tempEnImg = enemyImg2
      }else{
        tempEnImg = enemyImg1
      }
      //enemies += new Enemy(tempEnImg, Vec2(((i)*(750/nRows))+100, ((j)*(450/nCols))+100), ebulletImg)
      enemies += new Enemy(tempEnImg, Vec2(((i+1)*(sepW)), ((j+1)*(sepH))+100), ebulletImg)
    }
  }
  
  def display(g:GraphicsContext):Unit = { 
  for(e <- enemies){
    e.display(g)
  }
  for(eB <- enemiesBs){
    eB.display(g)
    if(paws == 1){eB.timeStep()}
  }
  }

  def shoot() = { //:Bullet 
    for(e <- enemies){
      if(math.random < 0.01){
      enemiesBs += e.shoot()
      }
    }
  }

}
