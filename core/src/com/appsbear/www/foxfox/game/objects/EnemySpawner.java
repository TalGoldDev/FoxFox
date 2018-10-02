package com.appsbear.www.foxfox.game.objects;

import java.util.*;

import com.appsbear.www.foxfox.game.Level;
import com.appsbear.www.foxfox.game.objects.Enemy.VIEW_DIRECTION;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class EnemySpawner {
	
	public List<Enemy> enemies = new ArrayList<Enemy>();
	private Enemy Alpha;
	private Enemy Beta;
	private Enemy Gama;
	private Level level;
	Rectangle rect = new Rectangle();
	private GoldCoin GoldCoin;
	
	public EnemySpawner(Level level){
		this.level=level;
		init();
	}
	
	public int getpositionX(int num){
		int a=enemies.get(num).getX();
		return a;
	}
	public int getpositionY(int num){
		int a=enemies.get(num).getY();
		return a;
	}
	
	
	private void init() {
		Alpha = new Enemy(level);
		Beta = new Enemy(level);
		Gama = new Enemy(level);
		enemies.add(Alpha);
		enemies.add(Beta);
		enemies.add(Gama);
	}

	public void update (float deltaTime) {
		
		Iterator<Enemy>i=enemies.iterator();
		while(i.hasNext()){
			Enemy a = i.next();
			a.update(deltaTime);
			if(a.dead)
			{	
				GoldCoin = new GoldCoin(a.position.x,a.position.y+1);
				level.goldcoins.add(GoldCoin);
				a.init();
				a.newposition(level.fox.position.x);
			}
		}
		
		
		/*for(Enemy a :enemies){
			if(a.dead==true)
				a = new Enemy(level);
		
			a.update(deltaTime);
		} */
	}
	public void drawenemies(SpriteBatch batch){
		for(Enemy a :enemies){
			a.render(batch);
		}
	}
	
	public boolean enemyhit(Rectangle r1){
		for(Enemy a :enemies){
			rect.set(a.position.x, a.position.y,    
					 a.bounds.width, a.bounds.height);
			if (rect.overlaps(r1)) {
				return true;
			}
		}
		
		return false;
	}

}
