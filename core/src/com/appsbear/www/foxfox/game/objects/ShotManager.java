package com.appsbear.www.foxfox.game.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.game.Level;
import com.appsbear.www.foxfox.util.AudioManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShotManager {
	
	private Level lev;
	public List<Shot> Shots = new ArrayList<Shot>();
	int cooldown=0;
	public ShotManager(Level level){
		lev = level;
		init();
	}

	private void init() {
		
	}
	
	public void fireshot(float x,float y){
		if(cooldown<0)
		{
			Shot a = new Shot(x,y,lev.fox.viewDirection);
			lev.fox.shooter=true;
		Shots.add(a);
		cooldown=20;
		AudioManager.instance.play(Assets.instance.sounds.hit); 
		}
		
		
	}
	
	public void updateshots(float deltaTime){
		cooldown--;
		Iterator<Shot>i = Shots.iterator();
		while(i.hasNext()){
			Shot a=i.next();
			a.update(deltaTime);
			if(a.positionfromstart>=200)
				{i.remove();
				
				}
		}
	}
	
	public void renderShots(SpriteBatch batch){
		for(Shot a: Shots){
			a.render(batch);
		}
	}
}
