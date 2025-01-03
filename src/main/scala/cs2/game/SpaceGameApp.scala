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


object SpaceGameApp extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "Space Game"
    scene = new Scene(800,800) {
      val canvas = new Canvas(width.value, height.value)
      content = canvas
      val g = canvas.graphicsContext2D

      //Image paths
      val playerPath = getClass().getResource("/images/gImages2/player.png")
      val bulletPath = getClass().getResource("/images/gImages2/bulletB.png")
      val logoPath = getClass().getResource("/images/gImages2/logo.png")
      val bGPath = getClass().getResource("/images/gImages2/gBG.png")
      val bossPath = getClass().getResource("/images/gImages2/boss.png")
      val ebulletPath = getClass().getResource("/images/gImages2/ebullet.png")
      //Images
      val bulletImg = new Image(bulletPath.toString)
      val playerImg = new Image(playerPath.toString)
      val logoImg = new Image(logoPath.toString)
      val bGImg = new Image(bGPath.toString)
      val bossImg = new Image(bossPath.toString)
      val ebulletImg = new Image(ebulletPath.toString)
      //Fonts
      val galF:Font = Font.loadFont(getClass().getResourceAsStream("/fonts/galagaFont/galaga.ttf"), 15)
      //Game componatnes
      var enR:Int = 5//5
      var enC:Int = 3//3
      var spaceShip:Player = new Player(playerImg, new Vec2(400,700), bulletImg)
      var enSwarm:EnemySwarm = new EnemySwarm(enR, enC)
      var bulletList:Buffer[Bullet] = Buffer[Bullet]()
      var codes:Set[KeyCode] = Set[KeyCode]()
      var playerShootCounter:Int = 0
      var enemyShootCounter:Int = 0
      var score:Int = 0
      var lives:Int = 3
      var lvl:Int = 1
      var gameStarted:Boolean = false
      var gameOver:Boolean = false
      var wobT:Int = 50
      var flip:Vec2 = new Vec2(1,0)
      var prog:Double = 0
      var pause:Int = 1
      var actions:Stack[GameState] = new Stack[GameState]
      var safeSpace:Vec2 = new Vec2(-200,-200)//-100, -100
      var boss:BossEnemy = new BossEnemy(bossImg, safeSpace, ebulletImg)
      var bossLives:Int = 5
      var bossIsLive:Boolean = false
      var bossTimer:Int = 0
      var bossShootTimer:Int = 0
      var bossBs:Queue[Bullet] = new Queue[Bullet]()
      var quickTest:Boolean = false
      var quickTestShots:Int = 0
      if(quickTest){
        quickTestShots = 1000
      }
      var ss:Int = quickTestShots//0
      var ssTaken:Boolean = false
      var pauseButtonHit:Boolean = false

      def background(): Unit = {
        g.setFill(Color.Black)
        g.fillRect(0,0, width.value,height.value)
      }

      def movements (keys:Set[KeyCode]): Unit = {
        if(codes.contains(KeyCode.LEFT)||codes.contains(KeyCode.A)) { // move left
          spaceShip.moveLeft()
          if(bossIsLive){boss.moveLeft()}
          bossTimer = 0
        }
        if(codes.contains(KeyCode.RIGHT)||codes.contains(KeyCode.D)) { // move right
          spaceShip.moveRight()
          if(bossIsLive){boss.moveRight()}
          bossTimer = 0
        }
        if(codes.contains(KeyCode.UP)||codes.contains(KeyCode.W)) { // move up
          spaceShip.moveUp()
        }
        if(codes.contains(KeyCode.DOWN)||codes.contains(KeyCode.S)) { // move down
          spaceShip.moveDown()
        }
        if(codes.contains(KeyCode.Space)&& playerShootCounter >= 20) { // shoot
          if(lvl > 5){
            var temp:Buffer[Bullet] = spaceShip.doubleShot()
            for(i <- temp){bulletList += i}
          }else{
            bulletList += spaceShip.shoot()
          }
          playerShootCounter = 0
          bossShootTimer = 0
          if(bossIsLive){bossBs.enqueue(boss.shoot())}//enSwarm.enemiesBs += boss.shoot()}
        }
        if(codes.contains(KeyCode.Escape)){ //exit
          System.exit(0)
        }
        if(codes.contains(KeyCode.P)&& !pauseButtonHit){ // play/pause //Added this for fun
          pauseButtonHit = true
          pause *= -1
          enSwarm.paws *= -1
        }
        if(!codes.contains(KeyCode.P)){
          pauseButtonHit = false
        }
        if(codes.contains(KeyCode.B)&& ss > 0 && ! ssTaken){ //Blast // added this for testing and fun
          ssTaken = true
          var temp:Buffer[Bullet] = spaceShip.superShot()
          for(i <- temp){bulletList += i}
          ss -= 1
          //score -= 1500
          playerShootCounter = 0
          bossShootTimer = 0
          if(bossIsLive){bossBs.enqueue(boss.shoot())}
        }
        if(!codes.contains(KeyCode.B)){
          ssTaken = false
        }
      }

      def startScreen(): Unit = {
        if(!gameStarted){
            movements(codes)
            g.drawImage(logoImg, (width.value/2)-(logoImg.width.value/4),(height.value/2)-(logoImg.height.value/4),logoImg.width.value/2,logoImg.height.value/2)
            val customFont:Font = Font.loadFont(getClass().getResourceAsStream("/fonts/galagaFont/galaga.ttf"), 40)
            g.setFont(customFont)
            g.setFill(Color.Green)
            g.fillText("Start Game", (width.value/2)-(logoImg.width.value/4),(height.value/2)-(logoImg.height.value/4))
          }

      }
      def bossActions(): Unit ={
        bossTimer += 1
        bossShootTimer += 1
        if(bossTimer >= 5 && boss.moves.length > 0){
          boss.makeMove()
          //bossTimer = 0
          boss.display(g)
        }
        if(bossShootTimer >= 20 && bossBs.length > 0){
          var temp:Bullet = bossBs.dequeue()
          temp.moveTo(boss.getPos().clone())
          enSwarm.enemiesBs += temp
          bossShootTimer = 0
          boss.display(g)
        }

      }
      def enemyMovement(): Unit = {
        if(wobT == 100){
            flip.x = flip.x * -1
            wobT = 0
           }
           wobT += 1//inverse
           for(eMove <- enSwarm.enemies){
            eMove.move(flip)
           }
      }
      def enemyShooting(): Unit = {
          if(enemyShootCounter >= 5){
            enSwarm.shoot()
            enSwarm.display(g)
            spaceShip.display(g)
            enemyShootCounter = 0
          }

          breakable{
          for(eSearch <- enSwarm.enemies){
                if(spaceShip.intersection(eSearch)){
                  spaceShip.moveTo(new Vec2(400,700))
                  lives -= 1
                  break
                }
              }
              if(spaceShip.intersection(boss)){
                  spaceShip.moveTo(new Vec2(400,700))
                  lives -= 1
                  break
                }

              for(eSearch <- enSwarm.enemiesBs){
                if(spaceShip.intersection(eSearch)){
                  enSwarm.enemiesBs -= eSearch
                  spaceShip.moveTo(new Vec2(400,700))
                  lives -= 1
                  break
                }
                if(eSearch.outOfBounds(width.value, height.value)){
                enSwarm.enemiesBs -= eSearch
              break
              }
              }
            }
            if(enSwarm.enemies.knownSize == 0 && !bossIsLive){
              enSwarm = new EnemySwarm(enR, enC)
              wobT = 50
              flip = new Vec2(1,0)
              lvl +=1
              lvlChanges()
              bulletList.clear()
              if(lvl%3 == 0){newBoss()}
              //lives = 3 // might remove
            }
        }

        def enemyCollisions(): Unit = {
          breakable{
            for(bully <- bulletList){
              for(eSearch <- enSwarm.enemies){ // maybe change to while loops
                if(bully.intersection(eSearch)){
                  enSwarm.enemies -= eSearch
                  bulletList -= bully
                  score += 100
                  highScoreTrack()
                  break
                }
              }
              for(eSearch <- enSwarm.enemiesBs){
                if(bully.intersection(eSearch)){
                  enSwarm.enemiesBs -= eSearch
                  bulletList -= bully
                  break
                }
              }

              if(bully.intersection(boss)){
                bossLives -= 1
                if(bossLives == 0 && bossIsLive){
                  bossKilled()
                  highScoreTrack()
                  score += 1000
                }
                bulletList -= bully
                break
              }

              if(bully.outOfBounds(width.value, height.value)){
                bulletList -= bully
              break
              }
            }}
        }

        def lvlChanges(): Unit  ={
          if(lvl % 5 == 0){ss += 1}
          //if(lvl == 2){ss += 1}
          //if((lvl % 2 == 0)&& enR < 6){enR +=1}
          //if((lvl % 2 == 1)&& enC < 3){enC +=1}
        }

        def playerShooting(): Unit = {
          spaceShip.display(g)
          //player shooting stuff
          for(bully <- bulletList){
              bully.display(g)
              spaceShip.display(g)
              bully.timeStep()
            }
        }

        def displayStuff(): Unit = {
          if(lives <= 0){
            gameOver = true
          }
          g.setFont(galF)
          g.setFill(Color.White)
          g.fillText(score.toString() +"\nBlasts " +ss.toString(), 10, 35)
          g.setFont(galF)
          g.fillText("lives " +lives.toString()+"\nLevel "+lvl.toString(), 680, 35)
            spaceShip.display(g)
            enSwarm.display(g)
            playerShootCounter += 1
            enemyShootCounter += 1
            spaceShip.display(g)
            movements(codes)
            boss.display(g)
        }

        def progressBar(): Unit = {
          if (prog < width.value-20){
            prog += 0.05
          }
          g.setFill(Color.Grey)
          g.fillRect(10,height.value-10,width.value-20,5)
          g.setFill(Color.White)
          g.fillRect(10,height.value-10,prog,5)
        }

        def gameRunning(): Unit ={
          if(gameStarted && !gameOver){
            displayStuff()
            if(pause == 1){
              if(!codes.contains(KeyCode.R)){forward()}else{backward()}
              progressBar()
          } else{
            pauseButton()
          }
        }
        }

        def forward():Unit ={
            addGameState()
            enemyMovement()
            playerShooting()
            enemyCollisions()
            enemyShooting()
            addGameState()
            bossActions()
        }
        def backward():Unit ={
            undo()
            playerShooting()
        }
        def gameEnds():Unit = {
          if(gameOver){
            movements(codes)
            prog = 0
            val customFont:Font = Font.loadFont(getClass().getResourceAsStream("/fonts/galagaFont/galaga.ttf"), 20)
            g.setFont(customFont)
            g.setFill(Color.Red)
            g.fillText(" Game Over \n\nScore " +score.toString()+"\nLevel "+ lvl +"\n\n Play again?\n High score \n\n   "+highScoreTrack().toString(), 300, 320)
          }
        }

        def highScoreTrack():Int = {//Added this for fun
          var highScore:Int = score
          try{
          val hsFile = Source.fromFile("src/main/resources/highScoreFile.txt")
          val hs = hsFile.getLines
          while(hs.hasNext){
           highScore = hs.next().toInt
          }
          if(score > highScore){
            highScore = score
          }
          hsFile.close()
          val nhsFile = new PrintWriter("src/main/resources/highScoreFile.txt")
          nhsFile.print(highScore)
          nhsFile.close()
          }catch{
            	case e:java.io.FileNotFoundException => {
		            println(e.getStackTrace())
		            println("ERROR File not found")
	            }
            case e:java.io.IOException => print("ERROR IO Exception")
            }
            highScore
        }
        var bckGrnd:Background = new Background(bGImg, new Vec2(0,0) ,new Vec2(0,1))
        var bckGrnd2:Background = new Background(bGImg, new Vec2(0,-height.value) ,new Vec2(0,1))
        def movingBackground():Unit = {

          bckGrnd.display(g)
          bckGrnd2.display(g)
          bckGrnd.timeStep()
          bckGrnd2.timeStep()
          if(bckGrnd.bottomOfScreen(height.value)){
            bckGrnd.moveTo(new Vec2(0,-height.value))
          }

          if(bckGrnd2.bottomOfScreen(height.value)){
            bckGrnd2.moveTo(new Vec2(0,-height.value))
          }
        }

        def undo():Unit = {
          if(actions.length >= 1){
            var lastAct:GameState = actions.pop()
            enSwarm.enemiesBs = lastAct.undoEB()
            bulletList = lastAct.undoPB()
            enSwarm.enemies = lastAct.undoE()
            lvl = lastAct.level
            lives = lastAct.livs
            score = lastAct.scos
            wobT = lastAct.wob
            flip = lastAct.fli
            playerShootCounter = lastAct.plSC
            enemyShootCounter = lastAct.enSC
            spaceShip.moveTo(lastAct.pLoc)
            prog = lastAct.progr
            bckGrnd.moveTo(lastAct.bckG)
            bckGrnd2.moveTo(lastAct.bckG2)
            boss.moveTo(lastAct.bossLoc)
            bossIsLive = lastAct.bossLive
            enC = lastAct.enC
            enR = lastAct.enR
            bossLives = lastAct.bossLives
        } else{
          reInitialize()
        }
        }
        def vecList[A <% Sprite](things:Buffer[A]):Buffer[Vec2]={
          var ret:Buffer[Vec2] = Buffer[Vec2]()
          for(i <- things){
            ret += i.getPos().clone()
          }
          ret
        }
        def picList[A <% Sprite](things:Buffer[A]):Buffer[Image]={
          var ret:Buffer[Image] = Buffer[Image]()
          for(i <- things){
            ret += i.getImg()
          }
          ret
        }

        def newBoss():Unit ={ // I helpped Eli and Elizabeth with their bosses
          boss.moveTo(new Vec2(400,100))
          bossLives = 5
          bossIsLive = true
          boss.display(g)
        }
        def bossKilled():Unit ={
          boss.moveTo(safeSpace)
          bossIsLive = false
        }



        def addGameState():Unit ={
          actions.push(new GameState(spaceShip.getPos().clone(), vecList(enSwarm.enemies), picList(enSwarm.enemies), vecList(enSwarm.enemiesBs), vecList(bulletList), bulletImg, enSwarm.ebulletImg, lvl, lives, score, wobT, flip, playerShootCounter, enemyShootCounter, prog, bckGrnd.getPos().clone(), bckGrnd2.getPos().clone(), boss.getPos().clone(), bossIsLive, enC, enR, bossLives))
        }

        def pauseButton():Unit ={
          g.setFill(Color.White)
          g.fillRect((width.value/2) -40, (height.value/2) -50, 25, 100)
          g.fillRect((width.value/2) +10, (height.value/2) -50, 25, 100)
        }
        def reInitialize():Unit ={
          //enC = 1
          //enR = 3
          lvl = 1
          enSwarm = new EnemySwarm(enR, enC)
            spaceShip.moveTo(new Vec2(400,700))
            bossKilled()
            lives = 3
            score = 0
            //lvl = 1
            enSwarm.enemiesBs.clear()
            bulletList.clear()
            prog = 0
            wobT = 50
            flip = new Vec2(1,0)
            bckGrnd.moveTo(new Vec2(0,0))
            bckGrnd2.moveTo(new Vec2(0,-height.value))
            ss = 0

        }

        canvas.onKeyPressed = (e:KeyEvent) => {
            codes += e.code
          }
          canvas.onKeyReleased = (e:KeyEvent) => {
            codes -= e.code
          }
          canvas.onMouseClicked = (e:MouseEvent) => {
            gameStarted = true
            if(gameOver){
            gameOver = false
            reInitialize()
            }
          }
          canvas.requestFocus()

      val timer = AnimationTimer(t => {
        if(!codes.contains(KeyCode.R)){movingBackground()}else{
          bckGrnd.display(g)
          bckGrnd2.display(g)
        }
          //background()
          startScreen()
          gameRunning()
          gameEnds()
      })
      timer.start
    }
  }
}
