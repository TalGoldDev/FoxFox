package com.appsbear.www.foxfox.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.appsbear.www.foxfox.game.Assets;
import com.badlogic.gdx.math.MathUtils; 
import com.badlogic.gdx.math.Vector2; 

public class Rock extends AbstractGameObject{
	 private TextureRegion regEdge;
	 private TextureRegion regMiddle;
	 private int length;
	 
	// private final float FLOAT_CYCLE_TIME = 2.0f; 
	// private final float FLOAT_AMPLITUDE = 0.25f;
	// private float floatCycleTimeLeft;
	// private boolean floatingDownwards; 
	// private Vector2 floatTargetPosition; 
	 
	 public Rock () { 
		 init();
		 }
	 
	 private void init () { 
		 dimension.set(1, 1.5f);
		 regEdge = Assets.instance.rock.edge;  
		 regMiddle = Assets.instance.rock.middle;
		 // Start length of this rock   
		 setLength(1); 
		// floatingDownwards = false; 
		// floatCycleTimeLeft = MathUtils.random(0,     FLOAT_CYCLE_TIME / 2);  
		// floatTargetPosition = null; 
		 }
	 
	 /*
	 @Override 
	 public void update (float deltaTime) { 
		 super.update(deltaTime);
	  floatCycleTimeLeft -= deltaTime;
	  if (floatTargetPosition == null) 
		  floatTargetPosition = new Vector2(position); 
	  if (floatCycleTimeLeft <= 0) {  
		  floatCycleTimeLeft = FLOAT_CYCLE_TIME;    
		  floatingDownwards = !floatingDownwards; 
		  floatTargetPosition.y += FLOAT_AMPLITUDE       * (floatingDownwards ? -1 : 1); 
		  } 
	  position.lerp(floatTargetPosition, deltaTime); 
	  }
	*/ 
	 
	 public void setLength (int length) {  
		 this.length = length;  
		  // Update bounding box for collision detection
		 bounds.set(0, 0, dimension.x * length, dimension.y);
		 }
	 
	 public void increaseLength (int amount) {
		 setLength(length + amount);
		 } 
	 
	@Override
	public void render(SpriteBatch batch) {
		 TextureRegion reg = null;
		 float relX = 0;    
		 float relY = 0;
		 
		 //This method cuts out a rectangle (defined by srcX, srcY, srcWidth, and srcHeight)
		 //from texture (here: our texture atlas) and draws it to a given position (x, y).
		 
		 // Draw left edge 
		 reg = regEdge; 
		 relX -= dimension.x / 4;  
		 batch.draw(reg.getTexture(),
				 position.x + relX, position.y + relY,
				 origin.x, origin.y,
				 dimension.x / 4, dimension.y,
				 scale.x, scale.y,
				 rotation,
				 reg.getRegionX(), reg.getRegionY(),
				 reg.getRegionWidth(), reg.getRegionHeight(),
				 false, false);
		 
		
		 // Draw middle   
		 relX = 0;   
		 reg = regMiddle;
		 for (int i = 0; i < length; i++) { 
			 batch.draw(reg.getTexture(),  // texture of the rock
					 position.x + relX, position.y + relY,  //
					 origin.x, origin.y, // The origin (originX, originY) defines a relative position to where the rectangle is shifted.
					 dimension.x, dimension.y, // The width and height define the dimension of the image to be displayed
					 scale.x, scale.y, // The scaling factor (scaleX, scaleY) defines the scale of the rectangle around the origin
					 rotation, // The angle of rotation defines the rotation of the rectangle around the origin
					 reg.getRegionX(), reg.getRegionY(),//This method cuts out a rectangle (defined by srcX, srcY, srcWidth, and srcHeight) 
					 reg.getRegionWidth(), reg.getRegionHeight(),
					 false, false);  //The flipping of one or both the axes (flipX, flipY) means to mirror the corresponding axis of that image
			 relX += dimension.x;   
			 }
		 
		 // Draw right edge
		 reg = regEdge;  
		 batch.draw(reg.getTexture(),
				 position.x + relX, position.y + relY,
				 origin.x + dimension.x / 8, origin.y,
				 dimension.x / 4, dimension.y,
				 scale.x, scale.y,
				 rotation,
				 reg.getRegionX(), reg.getRegionY(),
				 reg.getRegionWidth(), reg.getRegionHeight(),
				 true, false);
		 
	}

}
