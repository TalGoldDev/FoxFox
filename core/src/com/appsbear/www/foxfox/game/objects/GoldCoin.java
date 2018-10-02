package com.appsbear.www.foxfox.game.objects;

import com.appsbear.www.foxfox.game.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GoldCoin extends AbstractGameObject {

	 private TextureRegion regGoldCoin;  
	 public boolean collected;
	 
	 private static final int     FRAME_COLS = 8;         // #1
	 private static final int     FRAME_ROWS = 1;         // #2

	 Animation                       coinAnimation;          // #3
	 Texture                         coinSheet;              // #4
	 TextureRegion[]                 coinFrames;             // #5
	 TextureRegion                   currentFrame;           // #7
	 public int velocityint;
	  float stateTime;                                        // #8
	  
	 public GoldCoin () { 
		 init();  
		 }
	 
	 public GoldCoin (float x,float f) { 
		 Gdx.app.debug(null, "goldcoincreated");
		 position.x=x;
		 position.y=f;
		 Gdx.app.debug(null, "goldcoincreated" + position.x + " " + position.y);
		 init();  
		 }
	 

	 private void init() {  
		 velocityint=-1;
		    coinSheet = new Texture("images/coinspritesheet.png"); // #9
	        TextureRegion[][] tmp = TextureRegion.split(coinSheet, coinSheet.getWidth()/FRAME_COLS, coinSheet.getHeight()/FRAME_ROWS);              // #10
	        coinFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	            	coinFrames[index++] = tmp[i][j];
	            }
	        }
	        coinAnimation = new Animation(0.09f, coinFrames);      // #11
	        stateTime = 0f;    
		 
		 
		 dimension.set(0.5f, 0.5f);  
		 regGoldCoin = Assets.instance.goldCoin.goldCoin;
		 // Set bounding box for collision detection 
		 bounds.set(0, 0, dimension.x, dimension.y);  
		 collected = false;
		 }
	 
	 @Override
	 public void render (SpriteBatch batch) {
		 if (collected) return;
	    TextureRegion reg = null;  
	    stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = coinAnimation.getKeyFrame(stateTime, true);  // #16
	    reg = currentFrame; 
	    batch.draw(reg.getTexture(), 
	    		position.x, position.y,  
	    		origin.x, origin.y,    
	    		dimension.x, dimension.y,  
	    		scale.x, scale.y,      
	    		rotation,           
	    		reg.getRegionX(), reg.getRegionY(),  
	    		reg.getRegionWidth(), reg.getRegionHeight(), 
	    		false, false); 
	    }
	 
	 public int getScore() { 
		 return 100; 
		 } 
	 
	 public void update(float deltaTime){
		 super.update(deltaTime);
		 velocity.y=velocityint;
	 }

}
