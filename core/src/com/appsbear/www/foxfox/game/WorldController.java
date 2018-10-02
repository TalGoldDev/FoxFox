package com.appsbear.www.foxfox.game;

import java.util.ArrayList;
import java.util.List;

import com.appsbear.www.foxfox.util.CameraHelper;
import com.appsbear.www.foxfox.util.GamePreferences;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.appsbear.www.foxfox.game.objects.Rock;
import com.appsbear.www.foxfox.util.Constants;
import com.appsbear.www.foxfox.game.objects.Enemy;
import com.appsbear.www.foxfox.game.objects.Fox.JUMP_STATE;
import com.appsbear.www.foxfox.game.objects.Fox;
import com.appsbear.www.foxfox.game.objects.GoldCoin;
import com.appsbear.www.foxfox.game.objects.Shot;
import com.badlogic.gdx.Game; 
import com.appsbear.www.foxfox.screens.GameScreen;
import com.appsbear.www.foxfox.screens.MenuScreen;
import com.appsbear.www.foxfox.util.AudioManager;

public class WorldController extends InputAdapter{
	 private static final String TAG = WorldController.class.getName();
	 public CameraHelper cameraHelper;
	 private Game game;
	 public Level level; 
	  public int lives; 
	  public int score;
	  private float timeLeftGameOverDelay;
	// Rectangles for collision detection
	  Rectangle r2 = new Rectangle();
	  Rectangle r1= new Rectangle ();
	  Rectangle r3= new Rectangle ();
	  Rectangle r4= new Rectangle ();
	  Rectangle r5= new Rectangle ();
	  public float livesVisual; 
	  public float scoreVisual;
	public float camMoveSpeed;
	public Fox fox;
	 public WorldController (Game game) { 
		 this.game = game;
		 init(); 
		 }
	 
	 
	  private void initLevel () {
		  GamePreferences.instance.loadfox();
		  score = GamePreferences.instance.foxgold;
		  scoreVisual = score; 
		  GamePreferences.instance.loadfox();
		  level = new Level( GamePreferences.instance.maps[ GamePreferences.instance.levelnum]);
		  cameraHelper.setTarget(level.fox); 
		  }
	  
	  private void init () {
		  Gdx.input.setInputProcessor(this);
		  cameraHelper = new CameraHelper();
		  lives = Constants.LIVES_START; 
		  livesVisual = lives;
		  timeLeftGameOverDelay = 0;
		  initLevel(); 
		  
	  }
	  
	  public void backToMenu () {   
		  // switch to menu screen
		  Gdx.app.debug(TAG, "workingbutton");
		  GamePreferences.instance.savefoxgold(score);
		  game.setScreen(new MenuScreen(game)); 
		  }
	  
	  public void nextlev(){
		  if(level.fox.position.x>=level.Door.position.x-2&&level.fox.position.x<=level.Door.position.x+2)
		  {  
			  GamePreferences.instance.savefoxgold(score);
			  level.Door.nextlevel();
			  initLevel();
		  }
	  }


	  public boolean isGameOver () {
		  return lives < 0; 
		  }

	  public boolean isPlayerInWater () {
		  return level.fox.position.y < -5; 
		  }
	  
	private Pixmap createProceduralPixmap(int width, int height) {
		 Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);   
		 // Fill square with red color at 50% opacity  
		 pixmap.setColor(1, 0, 0, 0.5f);  
		 pixmap.fill();    
		 // Draw a yellow-colored X shape on square 
		 pixmap.setColor(1, 1, 0, 1);
		 pixmap.drawLine(0, 0, width, height);  
		 pixmap.drawLine(width, 0, 0, height);  
		 // Draw a cyan-colored border around square 
		 pixmap.setColor(0, 1, 1, 1);  
		 pixmap.drawRectangle(0, 0, width, height);    
		 return pixmap; 
		 }
	
		 public void update (float deltaTime) {
			 joystickinput(deltaTime);
			  handleDebugInput(deltaTime);
			  nextlev();
			 if (isGameOver()) { 
				  GamePreferences.instance.savefoxgold(score);
				 timeLeftGameOverDelay -= deltaTime;
				  if (timeLeftGameOverDelay <= 0)
					  backToMenu(); 
				 } else { 
			// handleInputGame(deltaTime);
			 } 
			 level.update(deltaTime);
			 testCollisions();
			 cameraHelper.update(deltaTime); 
			 if (!isGameOver() && isPlayerInWater()) {  
				 lives--; 
				  GamePreferences.instance.savefoxgold(score);
				 if (isGameOver())   {
				  timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER; 
				  AudioManager.instance.play(Assets.instance.sounds.liveLost);
				 } else    
					 initLevel();  
				 }
			 level.mountains.updateScrollPosition(cameraHelper.getPosition()); 
			 if (livesVisual > lives) 
				 livesVisual = Math.max(lives, livesVisual - 1 * deltaTime);
			  if (scoreVisual < score) 
				  scoreVisual = Math.min(score, scoreVisual + 250 * deltaTime);
		 }
		private void handleDebugInput(float deltaTime) {
			 if (Gdx.app.getType() != ApplicationType.Desktop) return;
			    // Selected Sprite Controls  
			 float sprMoveSpeed = 5 * deltaTime; 
			 if (Gdx.input.isKeyPressed(Keys.A))
				 level.fox.velocity.x = -level.fox.terminalVelocity.x;  
			 if (Gdx.input.isKeyPressed(Keys.D)) 
				 level.fox.velocity.x = level.fox.terminalVelocity.x;   
			 if (Gdx.input.isKeyPressed(Keys.W))
			 { level.fox.setJumping(true);
			 level.fox.currentFrame = level.fox.jumping;} 
			 if (Gdx.input.isKeyPressed(Keys.S))
				 level.shotmanager.fireshot(level.fox.position.x, level.fox.position.y+level.fox.bounds.height/4);
			 
				  if (!cameraHelper.hasTarget(level.fox)) {
			  // Camera Controls (move)  
			    camMoveSpeed = 5 * deltaTime; 
			 float camMoveSpeedAccelerationFactor = 5;  
			 if (Gdx.input.isKeyPressed(Keys.SPACE)) 
			 {	 level.fox.setJumping(true);
			 level.fox.currentFrame = level.fox.jumping; }
			 if (Gdx.input.isKeyPressed(Keys.LEFT)) 
				 level.fox.velocity.x = -level.fox.terminalVelocity.x;  
			 if (Gdx.input.isKeyPressed(Keys.RIGHT))
				 level.fox.velocity.x = level.fox.terminalVelocity.x;   
			 if (Gdx.input.isKeyPressed(Keys.UP)) 
				 moveCamera(0,camMoveSpeed); 
			 if (Gdx.input.isKeyPressed(Keys.DOWN))
				 moveCamera(0,-camMoveSpeed);
			 if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
				 cameraHelper.setPosition(0, 0);
			 
			    // Camera Controls (zoom)  
			 float camZoomSpeed = 1 * deltaTime;
			 float camZoomSpeedAccelerationFactor = 5;  
			 if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;  
			 if (Gdx.input.isKeyPressed(Keys.COMMA))cameraHelper.addZoom(camZoomSpeed);   
			 if (Gdx.input.isKeyPressed(Keys.PERIOD))cameraHelper.addZoom(-camZoomSpeed);
			 if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
				  }
			 
			 
		}
		
	 public void joystickinput(float deltaTime) {
			// if (Gdx.app.getType() != ApplicationType.Android) return;
		     int x = Gdx.input.getX();
		     int y = Gdx.input.getY();
		     float sprMoveSpeed = 5 * deltaTime;
		     int buttonwidth=Gdx.graphics.getWidth()/10;
		    if (Gdx.input.isTouched()) {
		         // move left
		         if  (x>0 && x<buttonwidth && y>Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/8&&y<Gdx.graphics.getHeight()) {
		        	 level.fox.velocity.x = -level.fox.terminalVelocity.x;  
		         }
		       
		         //move right
		         if (x>buttonwidth && x<buttonwidth*2 && y>Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/8&&y<Gdx.graphics.getHeight())  {
		        	 level.fox.velocity.x = level.fox.terminalVelocity.x;  
		         }
		         // move up
		         } 
		}
		
	 public void jumparrow(){
		 level.fox.setJumping(true);
		 level.fox.currentFrame = level.fox.jumping;
		 }
	 
		  private void moveCamera (float x, float y) {  
			  x += cameraHelper.getPosition().x;   
			  y += cameraHelper.getPosition().y;  
			  cameraHelper.setPosition(x, y);  
			  }
		  
		 @Override  public boolean keyUp (int keycode) { 
			 // Reset game world 
			 if (keycode == Keys.R) {    
				 init();  
				 Gdx.app.debug(TAG, "Game world resetted");    
				 } 
			 // Back to Menu    
			 else if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {  
				 backToMenu(); 
			 }
			 return false; 
			 }
			 
		 
		
		 
		
		 private void onCollisionFoxWithRock(Rock rock) {
			  Fox fox = level.fox;
			  float stateTime = Gdx.graphics.getDeltaTime();      
			  if(level.fox.currentFrame==level.fox.jumping)
			  level.fox.currentFrame = level.fox.standAnimation.getKeyFrame(stateTime, true); 
			  float heightDifference = Math.abs(fox.position.y - (  rock.position.y + rock.bounds.height)); 
			  if (heightDifference > 0.25f) { 
				  boolean hitLeftEdge = fox.position.x > (  rock.position.x + rock.bounds.width / 2.0f); 
				  if (hitLeftEdge) {fox.position.x = rock.position.x + rock.bounds.width; 
				  }
				  else {fox.position.x = rock.position.x- fox.bounds.width;   
				  } 
				   return; 
				   }
			  
			  switch (fox.jumpState) { 
			  case GROUNDED: 
				  break; 
				  case FALLING: 
					  case JUMP_FALLING:  
						  fox.position.y = rock.position.y + fox.bounds.height + fox.origin.y; 
						  fox.jumpState = JUMP_STATE.GROUNDED;   
						  break; 
						  case JUMP_RISING: 
							  fox.position.y = rock.position.y + fox.bounds.height+ fox.origin.y;
							  break;
							  } 
		 }	
		 
		 
		 
		 
		 private void onCollisionRockWithCoin(Rock rock,GoldCoin a) {
			  float stateTime = Gdx.graphics.getDeltaTime();
			  float heightDifference = Math.abs(a.position.y - (  rock.position.y + rock.bounds.height)); 
			  if (heightDifference > 0f) { 
				  a.velocityint=0;
				   }
			  
		 }	
		 
		 
		 
		 private void onCollisionEnemyWithRock(Rock rock,int ene) {
			 List<Enemy> enemies = new ArrayList<Enemy>();
			 enemies = level.spawnerA.enemies;
			  float stateTime = Gdx.graphics.getDeltaTime();
			  Enemy a = level.spawnerA.enemies.get(ene);
			  float heightDifference = Math.abs(a.position.y - (  rock.position.y + rock.bounds.height)); 
			  if (heightDifference > 0.25f) { 
				  a.position.y=(  rock.position.y + rock.bounds.height);
				  a.terminalVelocity.y=0;
				/*  boolean hitLeftEdge = a.position.x > (  rock.position.x + rock.bounds.width / 2.0f); 
				  if (hitLeftEdge) {a.position.x = rock.position.x + rock.bounds.width; 
				  }
				  else {a.position.x = rock.position.x -a.bounds.width;   
				  } */ 
				   return; 
				   }
			  
		 }	
		 
		 
		 
		 private void onCollisionFoxWithGoldCoin(GoldCoin goldcoin) {
			  goldcoin.collected = true;
			  score += goldcoin.getScore(); 
			  Gdx.app.log(TAG, "Gold coin collected"); 
		 };
		 
		 
		 private void testCollisions () { 
			 r1.set(level.fox.position.x,  
					 level.fox.position.y, 
					 level.fox.bounds.width, 
					 level.fox.bounds.height);
			 
			 // Test collision: Fox <-> Rocks 
			 for (Rock rock : level.rocks) {   
				 r2.set(rock.position.x, rock.position.y,    
						 rock.bounds.width, rock.bounds.height);
				 
				 if (!r1.overlaps(r2)) continue; 
				 onCollisionFoxWithRock(rock);   
				 // IMPORTANT: must do all collisions for valid 
				 // edge testing on rocks. 
				 }
			
			 //test collision : enemy <-> shuriken
			  if(GamePreferences.instance.levelnum==2){
			 for(Enemy ene:level.spawnerA.enemies){
				 r2.set(ene.position.x, ene.position.y,    
						 ene.bounds.width, ene.bounds.height);
				 for(Shot s:level.shotmanager.Shots){
					 r3.set(s.position.x, s.position.y,    
							 s.bounds.width, s.bounds.height);
					 if (!r2.overlaps(r3)) continue; 
					 onCollisionShurikenWithEnemy(ene,s);
				 }
			 }}
			 
			 
			  if(GamePreferences.instance.levelnum==2){ 
			 for(int enemynum=0;enemynum<3;enemynum++){
			 r3.set(level.spawnerA.getpositionX(enemynum),level.spawnerA.getpositionY(enemynum),
					 level.spawnerA.enemies.get(enemynum).bounds.width,level.spawnerA.enemies.get(enemynum).bounds.height);
			 
			 // Test collision: enemy <-> Rocks 
			 for (Rock rock : level.rocks) {   
				 r2.set(rock.position.x, rock.position.y,    
						 rock.bounds.width, rock.bounds.height);
				 if (!r3.overlaps(r2)) continue; 
				 onCollisionEnemyWithRock(rock,enemynum); 
				 // IMPORTANT: must do all collisions for valid 
				 // edge testing on rocks. 
				 }
			 }}
			 
			 for (Rock rock : level.rocks) {  
				  Array <GoldCoin> coins = level.goldcoins;
				 for(GoldCoin a: coins){
					 r3.set(a.position.x,a.position.y,
							a.bounds.width,a.bounds.height);
					 
				 r2.set(rock.position.x, rock.position.y,    
						 rock.bounds.width, rock.bounds.height);
				 if (!r3.overlaps(r2)) continue; 
				 onCollisionRockWithCoin(rock,a);
				 }
				 }
			 
		/*	 r4.set(level.spawnerA.getpositionX(enemynum),level.spawnerA.getpositionY(enemynum),
					 level.spawnerA.enemies.get(enemynum).bounds.width,level.spawnerA.enemies.get(enemynum).bounds.height);
			 for (Rock rock : level.rocks) {   
				 r2.set(rock.position.x, rock.position.y,    
						 rock.bounds.width, rock.bounds.height);
				 if (!r4.overlaps(r2)) continue; 
				 onCollisionEnemyWithRock(rock,enemynum);   
				 }
			 
			 r5.set(level.spawnerA.getpositionX(enemynum),level.spawnerA.getpositionY(enemynum),
					 level.spawnerA.enemies.get(enemynum).bounds.width,level.spawnerA.enemies.get(enemynum).bounds.height);
			 for (Rock rock : level.rocks) {   
				 r2.set(rock.position.x, rock.position.y,    
						 rock.bounds.width, rock.bounds.height);
				 if (!r5.overlaps(r2)) continue; 
				 onCollisionEnemyWithRock(rock,enemynum);   
				 } */
			 
			 //test collision fox <-> enemies
			if(level.fox.cooldown<=0){
				  if(GamePreferences.instance.levelnum==2)
			 if(level.spawnerA.enemyhit(r1))
			 {	lives--;
			 level.fox.cooldown=3;
			 }
			}
			 
			  // Test collision: Fox <-> Gold Coins 
			 for (GoldCoin goldcoin : level.goldcoins) {  
				 if (goldcoin.collected) continue;  
				 r2.set(goldcoin.position.x, goldcoin.position.y,  
						 goldcoin.bounds.width, goldcoin.bounds.height);
				 if (!r1.overlaps(r2)) continue; 
				 onCollisionFoxWithGoldCoin(goldcoin); 
				 break;  
				 }
			
			 }
			 
		 
		 
		 private void onCollisionShurikenWithEnemy(Enemy ene, Shot s) {
			 s.positionfromstart=1000;
			 ene.hitpoints-=level.fox.dmg;
			 ene.agressive=true;
		}


		private void handleInputGame (float deltaTime) { 
			 if (cameraHelper.hasTarget(level.fox)) {   
				 // Player Movement   
				 if (Gdx.input.isKeyPressed(Keys.LEFT)) {    
					 level.fox.velocity.x = -level.fox.terminalVelocity.x;  
					 } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {  
						 level.fox.velocity.x = level.fox.terminalVelocity.x;  
						 } else {    
							 // Execute auto-forward movement on non-desktop platform 
							 if (Gdx.app.getType() != ApplicationType.Desktop) {    
								// level.fox.velocity.x = level.fox.terminalVelocity.x;    
								 }   
							 }
				 
				 
			    // fox Jump 
				 if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) 
				 { Gdx.app.debug(TAG, "touchcontroller works");   
					 level.fox.setJumping(true);
				 } else {    
					 level.fox.setJumping(false);  
					 }
			 } 
		 } 
		 
		 
		 
		 
		 
		 
}
		 
		 
		 
		 

