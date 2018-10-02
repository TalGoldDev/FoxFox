package com.appsbear.www.foxfox.game.objects;

import com.appsbear.www.foxfox.game.Level;
import com.appsbear.www.foxfox.game.objects.Fox.JUMP_STATE;
import com.appsbear.www.foxfox.game.objects.Fox.VIEW_DIRECTION;
import com.appsbear.www.foxfox.util.CharacterSkin;
import com.appsbear.www.foxfox.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends AbstractGameObject{
	 public static final String TAG = Fox.class.getName();
	 private Level level ;
	 public int hitpoints;
	 public boolean agressive;
	 public int movetimer=0;
	 public enum VIEW_DIRECTION {LEFT, RIGHT} 
	 public VIEW_DIRECTION viewDirection; 
	 public boolean dead;
	 int mapwidth;
	 float xerath;
	 //animation
	    private static final int        FRAME_COLS = 2;         // #1
	    private static final int        FRAME_ROWS = 1;         // #2

	    Texture                         walkSheet;              // #4
	    TextureRegion[]                 walkFrames;             // #5
	    TextureRegion                   currentFrame;           // #7

	    float stateTime;                                        // #8
	 
	 
	 
	 public Enemy(Level level) {
		 this.level=level;
		 init();
		 
	 }
	 
	public void init() {
		dead=false;
		  walkSheet = new Texture(Gdx.files.internal("images/enemysheet.png")); // #9
	        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
	        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	                walkFrames[index++] = tmp[i][j];
	            }
	        }
	        stateTime = 0f;         
		
		
		currentFrame =  walkFrames[1];
		 dimension.set(1, 1); 
		 // Center image on game object 
		 origin.set(dimension.x / 2, dimension.y / 2);
		 // Bounding box for collision detection		
		  bounds.set(0, 0, dimension.x, dimension.y); 
		  // Set physics values 
		  //terminalVelocity.set(3.0f, 4.0f); 
		  friction.set(12.0f, 0.0f);  
		  acceleration.set(0.0f, -25.0f); 
		  // View direction
		  viewDirection = VIEW_DIRECTION.RIGHT;  
		  agressive= false;
		  hitpoints=20;
		  mapwidth=level.mapwidth;
		  position.x=(int) (Math.random() * (mapwidth-5))+2;
		  position.y=level.fox.position.y;
	}

	public void newposition(float x){
		if((position.x<x && position.x > x-50)|| (position.x>x && position.x<x+50));
		{position.x =(int) (Math.random() * (mapwidth-5))+2;
		//newposition(level.fox.position.x);
		}
	}
	
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg= null;
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
		 
		 // Reset color to white
		 batch.setColor(1, 1, 1, 1); 
	}
	@Override
	public void update (float deltaTime) {
		if(dead){ 
			return;}
		 super.update(deltaTime);
		if(hitpoints<=0)
			dead=true;
		 if(movetimer<=0)
		 enemymove();
		 else
			 velocity.x=xerath;
		 movetimer--;
		 if (velocity.x != 0) { 
			 currentFrame = walkFrames[1];
			 viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT; 
			 
			 }else{	
				 currentFrame = walkFrames[0];
				 }
		 
		}

	private void enemymove() {
		int num = (int) (Math.random() * 101);
		updatemotionY();
		movetimer=100;
		
		if(agressive==false){
			terminalVelocity.x=(float) 0.8;
		if(num<50){
			//move left
			 xerath = -terminalVelocity.x; 
		}if(num>50){
			//move right
			xerath=terminalVelocity.x;
		}
		if(num==50){
			velocity.x=0;
		}
		}
		if(agressive){
			movetimer=100;
			terminalVelocity.x=4;
			if(position.x<level.fox.position.x)
				xerath=terminalVelocity.x;
			else
				xerath = -terminalVelocity.x; 
		}
		
		if(position.x<=1)
		{//move right
			xerath=terminalVelocity.x;}
		if(position.x>=mapwidth-1){
			//move left
			 xerath = -terminalVelocity.x; 
		}
	}

	private void updatemotionY() {
		//if(position.y > 1)
			 velocity.y =-1;
	}
	
	public int getX(){
		return (int) this.position.x;
	}
	public int getY(){
		return (int) this.position.y;
	}
	
	
}
