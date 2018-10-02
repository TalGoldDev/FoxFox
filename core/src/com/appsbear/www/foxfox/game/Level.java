package com.appsbear.www.foxfox.game;
import com.appsbear.www.foxfox.game.objects.AbstractGameObject;
import com.appsbear.www.foxfox.game.objects.EnemySpawner;
import com.appsbear.www.foxfox.game.objects.Mountains;
import com.appsbear.www.foxfox.game.objects.Rock;
import com.appsbear.www.foxfox.game.objects.ShotManager;
import com.appsbear.www.foxfox.game.objects.WaterOverlay;
import com.appsbear.www.foxfox.game.objects.door;
import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.graphics.Pixmap; 
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch; 
import com.badlogic.gdx.utils.Array; 
import com.appsbear.www.foxfox.game.objects.Fox;
import com.appsbear.www.foxfox.game.objects.GoldCoin;
import com.appsbear.www.foxfox.util.GamePreferences;

public class Level {
	  public static final String TAG = Level.class.getName();
	  
	  public enum BLOCK_TYPE {
		  EMPTY(0, 0, 0),                   // black 
		  ROCK(0, 255, 0),                  // green 
		  PLAYER_SPAWNPOINT(255, 255, 255), // white 
		  ITEM_GOLD_COIN(255, 255, 0),      // yellow
		  DOOR(255, 0, 255); 				//purple
	  
		    private int color;
		    
		    private BLOCK_TYPE (int r, int g, int b) {
		    	color = r << 24 | g << 16 | b << 8 | 0xff;   
		    	}
		    
		    public boolean sameColor (int color) { 
		    	return this.color == color; 
		    	}
		    
		    public int getColor () {      return color;    
		    } 
	}
	  
	  public Fox fox; 
	  public door Door;
	  public Array<GoldCoin> goldcoins;
	  // objects  
	  public Array<Rock> rocks;
	// decoration  
	  public Mountains mountains;
	  public WaterOverlay waterOverlay;
	  public EnemySpawner spawnerA;
	 public int mapwidth ;
	 public ShotManager shotmanager;
	  public Level (String filename) { 
		  init(filename); 
		  }
	  
	  public void init (String filename) {
		  // player character 
		  fox = null; 
		  Door=null;
		  // objects   
		  rocks = new Array<Rock>();
		  goldcoins = new Array<GoldCoin>(); 
		  // load image file that represents the level data   
		  Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));  
		 mapwidth = pixmap.getWidth();
		  // scan pixels from top-left to bottom-right  
		  int lastPixel = -1; 
		   for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {  
			   for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) { 
				   AbstractGameObject obj = null;  
				   float offsetHeight = 0;  
				   // height grows from bottom to top    
				   float baseHeight = pixmap.getHeight() - pixelY;      
				   // get color of current pixel as 32-bit RGBA value     
				   int currentPixel = pixmap.getPixel(pixelX, pixelY);  
				   // find matching color value to identify block type at (x,y)   
				   // point and create the corresponding game object if there is  
				   // a match
				    // empty space
				   if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) { 
					   // do nothing    
					   } 
				   // rock    
				   else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {  
					   if (lastPixel != currentPixel) {   
						   obj = new Rock();    
						   float heightIncreaseFactor = 0.25f;   
						   offsetHeight = -2.5f;    
						   obj.position.set(pixelX,         
								   baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);       
						   rocks.add((Rock)obj); 
						   } else {     
							   rocks.get(rocks.size - 1).increaseLength(1); 
							   } 
				   } 
				   // player spawn point       
				   else if(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel))
				   { 
					   obj = new Fox();   
					   offsetHeight = -3.0f;    
					   obj.position.set(2,1);
					 //  obj.position.set(pixelX,baseHeight * obj.dimension.y           + offsetHeight); 
					   fox = (Fox)obj; 
					   Gdx.app.debug(TAG,""+pixelX+" "+""+pixelY) ;   
				   } 
				   // door       
			/*	   else if(BLOCK_TYPE.DOOR.sameColor(currentPixel))
				   { 
					   obj = new door(mapwidth-1,0);   
					   offsetHeight = -3.0f;    
					 //  obj.position.set(pixelX,baseHeight * obj.dimension.y           + offsetHeight); 
					   Door = (door)obj; 
					   Gdx.app.debug(TAG,""+pixelX+" "+""+pixelY) ;   
				   }  		*/
				   // gold coin  
				   else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {  
					   obj = new GoldCoin();  
					   offsetHeight = -1.5f;   
					   obj.position.set(pixelX,baseHeight * obj.dimension.y + offsetHeight); 
					   goldcoins.add((GoldCoin)obj);
				   } 
				   // unknown object/pixel color 
				   else {
					   int r = 0xff & (currentPixel >>> 24); //red color channel 
				       int g = 0xff & (currentPixel >>> 16); //green color channel   
				       int b = 0xff & (currentPixel >>> 8);  //blue color channel    
				       int a = 0xff & currentPixel;          //alpha channel    
				       Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r
				    		   + "> g<" + g + "> b<" + b + "> a<" + a + ">");       
				       } 
				   lastPixel = currentPixel; 
				   }
		   }  
		   // decoration    
		   mountains = new Mountains(pixmap.getWidth()); 
		   mountains.position.set(-1, -1);   
		   if(GamePreferences.instance.levelnum==0)
		   Door = new door(mapwidth-1,0,this);
		   if(GamePreferences.instance.levelnum==1)
			   Door = new door(mapwidth-1,2,this);
		   if(GamePreferences.instance.levelnum==2)
			   Door = new door(mapwidth-1,0,this);
		   if(GamePreferences.instance.levelnum==3)
			   Door = new door(mapwidth-1,0,this);
		   waterOverlay = new WaterOverlay(pixmap.getWidth());
		   waterOverlay.position.set(0, -3.75f);
		    // free memory  
		   pixmap.dispose();  
		   Gdx.app.debug(TAG, "level '" + filename + "' loaded");   
		   if(GamePreferences.instance.levelnum==2)
		   spawnerA = new EnemySpawner(this);
		   Texture shotsfired = new Texture("images/foxshots.png");
		   shotmanager= new ShotManager(this);
	  } 
	  
	  public void render (SpriteBatch batch) {
		    // Draw Mountains   
		  mountains.render(batch);  
		  // Draw Rocks   
		  Door.render(batch);
		  for (Rock rock : rocks) 
			  rock.render(batch); 
		  // Draw Gold Coins  
		  for (GoldCoin goldCoin : goldcoins) 
			  goldCoin.render(batch);
		  // Draw Player Character 
		  fox.render(batch); 
		  if(GamePreferences.instance.levelnum==2)
		  spawnerA.drawenemies(batch);
		  // Draw Water Overlay 
		  waterOverlay.render(batch); 
		  shotmanager.renderShots(batch);
	  } 
	  
	  public void update (float deltaTime) { 
		  fox.update(deltaTime); 
		  if(GamePreferences.instance.levelnum==2)
		  spawnerA.update(deltaTime);
		  shotmanager.updateshots(deltaTime);
		  for(Rock rock : rocks) 
			  rock.update(deltaTime); 
		  for(GoldCoin goldCoin : goldcoins)
			  goldCoin.update(deltaTime); 
		  
		  }
}
		 
		 

