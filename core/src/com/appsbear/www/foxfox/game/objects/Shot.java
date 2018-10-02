package com.appsbear.www.foxfox.game.objects;

import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.game.objects.Fox.JUMP_STATE;
import com.appsbear.www.foxfox.game.objects.Fox.VIEW_DIRECTION;
import com.appsbear.www.foxfox.util.CharacterSkin;
import com.appsbear.www.foxfox.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.game.objects.Fox.VIEW_DIRECTION;

public class Shot extends AbstractGameObject{
	 public static final String TAG = Shot.class.getName();
	 public VIEW_DIRECTION viewDirection; 
	 TextureRegion reg= Assets.instance.fox.head;
	 
	  private static final int        FRAME_COLS = 4;         // #1
	  private static final int        FRAME_ROWS = 1;         // #2
	 
	    Animation                       shotAnimation;          // #3
	    Texture                         shotSheet;              // #4
	    TextureRegion[]                 shotFrames;             // #5
	    TextureRegion                   currentFrame;           // #7
	  
	    float stateTime;                                        // #8
	    public int positionfromstart;
	    public int startx;
	    
	 public Shot(float x,float y,VIEW_DIRECTION S){
		 viewDirection=S;
		 init();
		 position.x=x;
		 position.y=y;
		 terminalVelocity.y=0;
		 velocity.y=0;
		 Gdx.app.debug(TAG, "shotcreated at" + position.x + " " + position.y);
		 positionfromstart=0;
		 startx=(int) position.x;
	 }
	 
	private void init() {
		
		 shotSheet = new Texture("images/kunaishots.png"); // #9
	        TextureRegion[][] tmp = TextureRegion.split(shotSheet, shotSheet.getWidth()/FRAME_COLS, shotSheet.getHeight()/FRAME_ROWS);              // #10
	        shotFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	            	shotFrames[index++] = tmp[i][j];
	            }
	        }
	        shotAnimation = new Animation(0.025f, shotFrames);      // #11
	        stateTime = 0f;  
		
		
		dimension.set(1,1); 
		 // Center image on game object 
		 origin.set(dimension.x / 2, dimension.y / 2);
		 // Bounding box for collision detection		
		  bounds.set(0, 0, dimension.x, dimension.y); 
		  // Set physics values 
	//	  terminalVelocity.set(3.0f, 4.0f); 
		  friction.set(12.0f, 0.0f);  
		  acceleration.set(0.0f, -25.0f); 
		  // View direction
		  
	}

	@Override
	public void render(SpriteBatch batch) {
		update(Gdx.graphics.getDeltaTime());
		currentFrame=null;
		
		  stateTime += Gdx.graphics.getDeltaTime();           // #15
	     currentFrame = shotAnimation.getKeyFrame(stateTime, true);  // #16
	     reg=currentFrame;
		  // Draw image 
		 batch.draw(reg.getTexture(),        
				 position.x, position.y,  
				 origin.x, origin.y,     
				 dimension.x/3, dimension.y/3, 
				 scale.x, scale.y,         
				 rotation,          
				 reg.getRegionX(), reg.getRegionY(),  
				 reg.getRegionWidth(), reg.getRegionHeight(),
				 viewDirection == VIEW_DIRECTION.LEFT, false); 
		 
		 // Reset color to white
		 batch.setColor(1, 1, 1, 1); 		
	}
			@Override
			public void update (float deltaTime) {
				 super.update(deltaTime);
				 positionfromstart++;
				 Gdx.app.debug(TAG, "position"+position.x + " " +position.y);
				terminalVelocity.x=3;
				if(viewDirection==VIEW_DIRECTION.LEFT)
				 velocity.x=-terminalVelocity.x;
				if(viewDirection==VIEW_DIRECTION.RIGHT)
					velocity.x=terminalVelocity.x;
			}
			
		}
