package com.appsbear.www.foxfox.game.objects;

import com.appsbear.www.foxfox.game.Level;
import com.appsbear.www.foxfox.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class door extends AbstractGameObject{
	 public static final String TAG = door.class.getName();
	 
	 Texture door;
	 Level lev;
	 public door(int x,int y,Level level){
		 lev= level;
		 door= new Texture("images/door.png");
		 init();
		 position.x=x;
		 position.y=y;
	 }
	 
	private void init() {
		 dimension.set(1, 1); 
		 // Center image on game object 
		 origin.set(dimension.x / 2, dimension.y / 2);
		 // Bounding box for collision detection		
		  bounds.set(0, 0, dimension.x, dimension.y); 
		  // Set physics values 
		  terminalVelocity.set(3.0f, 4.0f); 
		  friction.set(12.0f, 0.0f);  
		  acceleration.set(0.0f, -25.0f); 		
	}

	public void nextlevel(){
		GamePreferences.instance.loadfox();
		GamePreferences.instance.levelnum++;
		GamePreferences.instance.savefoxlevel(GamePreferences.instance.levelnum);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(door, position.x-1, position.y-1, 2, 3);
	}
	

}
