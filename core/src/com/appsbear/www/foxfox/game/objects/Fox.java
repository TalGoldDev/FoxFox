package com.appsbear.www.foxfox.game.objects;


import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion; 
import com.badlogic.gdx.utils.Array;
import com.appsbear.www.foxfox.util.AudioManager;
import com.appsbear.www.foxfox.util.GamePreferences;
import com.appsbear.www.foxfox.util.CharacterSkin;

public class Fox extends AbstractGameObject{
	 public static final String TAG = Fox.class.getName();

	 private final float JUMP_TIME_MAX = 0.3f;
	 private final float JUMP_TIME_MIN = 0.25f;
	 private final float JUMP_TIME_OFFSET_FLYING =JUMP_TIME_MAX - 0.018f;
	 
	 public enum VIEW_DIRECTION {LEFT, RIGHT} 
	 public enum JUMP_STATE {GROUNDED,FALLING,JUMP_RISING,JUMP_FALLING}
	 
	 public VIEW_DIRECTION viewDirection; 
	 public float timeJumping;  
	 public JUMP_STATE jumpState;
	 public boolean hasFeatherPowerup; 
	 public float timeLeftFeatherPowerup;
	 public float cooldown;
	 //animation variables
	 private static final int  FRAME_COLS = 3;         // #1
	 private static final int  FRAME_ROWS = 9;         // #2
	    Animation                       walkAnimation;          // #3
	    public  Animation                       standAnimation;          // #3
	   Animation                       jumpAnimation;          // #3

	    
	    Texture                         walkSheet;              // #4
	    Texture                         standSheet;              // #4
	    Texture                         jumpSheet;              // #4


	    TextureRegion[]                 walkFrames;             // #5
	    TextureRegion[]                 standFrames;             // #5
	    TextureRegion[]                 jumpFrames;             // #5


	    SpriteBatch                     spriteBatch;            // #6
	     public TextureRegion                   currentFrame;           // #7
	     public TextureRegion                   jumping;           // #7
	     public TextureRegion                   shotting;           // #7


	    public float stateTime;                                        // #8

	    public hat foxhat;
	    
		 Texture fireSheet;

		 public Animation fireAnimation;

		  TextureRegion[]  fireFrames;
		  
		  public boolean shooter;

		public int dmg=10;

	    
	    
	 public Fox() { 
		 init(); 
		 }
	 
	private void init() {
		foxhat=new hat(this);
		shooter=false;
		cooldown=0;
		 //creating the animations
		  //walkanimation
		  walkSheet = new Texture(Gdx.files.internal("images/foxspritesheet.png")); // #9
	        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
	        walkFrames = new TextureRegion[FRAME_COLS * 1];
	        int index = 0;
	        for (int i = 1; i < 2; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames[index++] = tmp[i][j];
	            }
	        }
	        walkAnimation = new Animation(0.15f, walkFrames);      // #11
	        stateTime = 0f;   
	        
	        //standanimation
	        standSheet = new Texture(Gdx.files.internal("images/foxspritesheet.png")); // #9
	        TextureRegion[][] tmp1 = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
	        standFrames = new TextureRegion[FRAME_COLS * 1];
	        int index1 = 0;
	        for (int i = 0; i < 1; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                standFrames[index1++] = tmp1[i][j];
	            }
	        }
	        standAnimation = new Animation(0.5f, standFrames);      // #11
	        stateTime = 0f; 
	        
	        //jumpanimation
	        jumpSheet = new Texture(Gdx.files.internal("images/foxjump/jumpani.png")); // #9
	        TextureRegion[][] tmpjump = TextureRegion.split(jumpSheet, jumpSheet.getWidth()/FRAME_COLS, jumpSheet.getHeight()/1);              // #10
	        jumpFrames = new TextureRegion[FRAME_COLS * 1];
	        int indexjump = 0;
	        for (int i = 0; i < 1; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	            	jumpFrames[indexjump++] = tmpjump[i][j];
	            }
	        }
	        jumpAnimation = new Animation(1f, jumpFrames);      // #11
	        stateTime = 0f; 
		  
	        jumping=jumpFrames[2];
	        
	        
	        
	        //jumpanimation
	        fireSheet = new Texture(Gdx.files.internal("images/foxthrowani.png")); // #9
	        TextureRegion[][] tmpfire = TextureRegion.split(fireSheet, fireSheet.getWidth()/FRAME_COLS, fireSheet.getHeight()/1);              // #10
	        fireFrames = new TextureRegion[FRAME_COLS * 1];
	        int indexfire = 0;
	        for (int i = 0; i < 1; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	            	fireFrames[indexfire++] = tmpfire[i][j];
	            }
	        }
	        fireAnimation = new Animation(1f, fireFrames);      // #11
	        stateTime = 0f; 
	        shotting=fireFrames[2];
		  
	        
	        
	        
	        
		  currentFrame=standFrames[0]; 
		 dimension.set(1, 1); 
		 // Center image on game object 
		 origin.set(dimension.x / 2, dimension.y / 2);
		 // Bounding box for collision detection		
		  bounds.set(0, 0, dimension.x, dimension.y); 
		  // Set physics values 
		  terminalVelocity.set(3.0f, 4.0f); 
		  friction.set(12.0f, 0.0f);  
		  acceleration.set(0.0f, -25.0f); 
		  // View direction
		  viewDirection = VIEW_DIRECTION.RIGHT;  
		  // Jump state 
		  jumpState = JUMP_STATE.FALLING; 
		  timeJumping = 0;  
		  // Power-ups  
		  hasFeatherPowerup = false;  
		  timeLeftFeatherPowerup = 0;
		  terminalVelocity.x=GamePreferences.instance.foxMoveSpeed;
		 
		  
		  
	}


	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg= null;
		 // Apply Skin Color  
		 batch.setColor(CharacterSkin.values()[GamePreferences.instance.charSkin].getColor());
		    stateTime += Gdx.graphics.getDeltaTime();           // #15
			  //currentFrame = standAnimation.getKeyFrame(stateTime, true);  // #16
		  // Set special color when game object has a feather power-up 
		    if(cooldown>0)
		    	batch.setColor(CharacterSkin.values()[1].getColor());
		 if (hasFeatherPowerup)  
			 batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		  // Draw image 
		 reg = currentFrame; 
		 batch.draw(reg.getTexture(),        
				 position.x, position.y,  
				 origin.x, origin.y,     
				 dimension.x, dimension.y, 
				 scale.x, scale.y,         
				 rotation,          
				 reg.getRegionX(), reg.getRegionY(),  
				 reg.getRegionWidth(), reg.getRegionHeight(),
				 viewDirection == VIEW_DIRECTION.LEFT, false); 
		 foxhat.render(batch);
		 // Reset color to white
		 batch.setColor(1, 1, 1, 1); 
	}

	@Override public void update (float deltaTime) {
		 super.update(deltaTime);
		 if(cooldown>0)
		 cooldown-=Gdx.graphics.getDeltaTime();
		 stateTime += Gdx.graphics.getDeltaTime();           // #15
		
		 
		 if (velocity.x != 0) { 
			 if(currentFrame!=jumping)
			 currentFrame = walkAnimation.getKeyFrame(stateTime, true); 
			 viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT; 
			 
			 }else{	
				 if(currentFrame!=jumping)
				currentFrame = standAnimation.getKeyFrame(stateTime, true);  // #16
			 }
		}
			 
	  public void setJumping (boolean jumpKeyPressed) {
		  switch (jumpState) {
		  case GROUNDED: // Character is standing on a platform 
			  if (jumpKeyPressed) {
				  AudioManager.instance.play(Assets.instance.sounds.jump); 
				  // Start counting jump time from the beginning  
				  Gdx.app.debug(TAG, "jumpKeyPressed works");   
				  timeJumping = 0;     
				  jumpState = JUMP_STATE.JUMP_RISING;  
				  }   
			  break;  
			  case JUMP_RISING: // Rising in the air  
				  if (!jumpKeyPressed)   
					  Gdx.app.debug(TAG, "!jumpKeyPressed works");   
					  jumpState = JUMP_STATE.JUMP_FALLING; 
				  break;   
				  case FALLING:// Falling down  
					  case JUMP_FALLING: // Falling down after jump
						  if (jumpKeyPressed && hasFeatherPowerup) {   
							  timeJumping = JUMP_TIME_OFFSET_FLYING;   
							  jumpState = JUMP_STATE.JUMP_RISING;   
							  }    
						  Gdx.app.debug(TAG, "falling works");   
						  break;  
						  } 
		  }
			  
	  
	  
	  
	  @Override
	  protected void updateMotionY (float deltaTime) { 
		  switch (jumpState) { 
		  case GROUNDED:  
			  
			  jumpState = JUMP_STATE.FALLING; 
			  break; 
			  
		  case JUMP_RISING:      // Keep track of jump time 
			  timeJumping += deltaTime;      // Jump time left? 
			  if (timeJumping <= JUMP_TIME_MAX) {   
				  // Still jumping     
				  velocity.y = terminalVelocity.y;
				  }  
			  break;
			  
		  case FALLING: 
			  break;
			  
		  case JUMP_FALLING:      // Add delta times to track jump time   
			  timeJumping += deltaTime;      // Jump to minimal height if jump key was pressed too short
			  if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {   
				  // Still jumping    
				  velocity.y = terminalVelocity.y;  
				  } 
			  
		  }
	    if (jumpState != JUMP_STATE.GROUNDED)
	    	{
	    	super.updateMotionY(deltaTime);
	    	}
	    	
	    
	  }
	  
	  
}
