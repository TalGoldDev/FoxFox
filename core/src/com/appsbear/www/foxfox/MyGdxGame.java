package com.appsbear.www.foxfox;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game; 
import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Interpolation;
import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.screens.FoxCreationScreen;
import com.appsbear.www.foxfox.screens.MenuScreen;
import com.appsbear.www.foxfox.screens.ShopScreen;
import com.appsbear.www.foxfox.util.AudioManager;
import com.appsbear.www.foxfox.util.GamePreferences;

public class MyGdxGame extends Game{
	  @Override  public void create () { 
		  // Set Libgdx log level  
		  Gdx.app.setLogLevel(Application.LOG_DEBUG);   
		  // Load assets    
		  Assets.instance.init(new AssetManager()); 
		  
		  // Load preferences for audio settings and start playing music  
		  GamePreferences.instance.load();
		  AudioManager.instance.play(Assets.instance.music.song01);
		  		  
		  // Start game at menu screen 
		 this.setScreen(new FoxCreationScreen(this));		  } 
	  }

//new MenuScreen(this)