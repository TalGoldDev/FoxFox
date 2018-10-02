package com.appsbear.www.foxfox.game.objects;

import com.appsbear.www.foxfox.game.objects.Fox.JUMP_STATE;
import com.appsbear.www.foxfox.game.objects.Fox.VIEW_DIRECTION;
import com.appsbear.www.foxfox.util.GamePreferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class hat extends AbstractGameObject{
	public static final String TAG = hat.class.getName();

	private Texture hatsheet;

	private TextureRegion[] hats;
    
	  TextureRegion currenthat;           // #7
	  int positiony;

	private VIEW_DIRECTION viewDirection;
	//hat splitting
	  private static final int        FRAME_COLS = 4;         // #1
	  private static final int        FRAME_ROWS = 1;         // #2
	  public Fox fox;
	  public hat(Fox fox1){
		  fox = fox1;
		  init();
		  
	  }
	  
	  
	  public void init(){
			 dimension.set(1, 1); 
			 // Center image on game object 
			 origin.set(dimension.x / 2, dimension.y / 2);
			 // Bounding box for collision detection		
			  bounds.set(0, 0, dimension.x, dimension.y); 
			  // Set physics values 
			  terminalVelocity.set(3.0f, 4.0f); 
			  friction.set(12.0f, 0.0f);  
			  acceleration.set(0.0f, -25.0f); 
		  
			//hat
			hatsheet = new Texture("images/readyforcutter.png"); // #9
	        TextureRegion[][] tmphat = TextureRegion.split(hatsheet, hatsheet.getWidth()/FRAME_COLS, hatsheet.getHeight()/FRAME_ROWS);              // #10
	        hats = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int indexhat = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	            	hats[indexhat++] = tmphat[i][j];
	            }
	        }
	        GamePreferences.instance.loadfox();
	        if(GamePreferences.instance.hatNum!=4)
	        currenthat=hats[GamePreferences.instance.hatNum];
	  }
	  
	@Override
	public void render(SpriteBatch batch) {
		if(GamePreferences.instance.hatNum==4)
			return;
		  // Draw image 
		 batch.draw(currenthat.getTexture(),        
					 fox.position.x, fox.position.y+fox.bounds.height/2,  
					 origin.x, origin.y,     
					 dimension.x, dimension.y, 
					 scale.x, scale.y,         
					 rotation,          
					 currenthat.getRegionX(), currenthat.getRegionY(),  
					 currenthat.getRegionWidth(), currenthat.getRegionHeight(),
					 viewDirection == VIEW_DIRECTION.LEFT, false); 
		
		
			 // Reset color to white
			 batch.setColor(1, 1, 1, 1); 
	}

}
