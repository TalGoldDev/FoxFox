package com.appsbear.www.foxfox.util;

import com.appsbear.www.foxfox.game.objects.Fox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GamePreferences {
	 public static final String TAG = GamePreferences.class.getName();
	  public static final GamePreferences instance = new GamePreferences();
	 
	  public boolean sound; 
	  public boolean music; 
	  public float volSound;  
	  public float volMusic; 
	  public int charSkin; 
	  public boolean showFpsCounter;
	  //saving the fox
	  public int hatNum;
	  public int foxdmg;
	  public int foxMoveSpeed;
	  public String foxName;
	  public int foxHealth;
	  public int foxgold;
	  
	  private Preferences prefs;
	  public Skin skinLibgdx;
	  
	  public String[] maps;
	  public int levelnum;
	  // singleton: prevent instantiation from other classes 
	  private GamePreferences () {   
		  prefs = Gdx.app.getPreferences(Constants.PREFERENCES); 
		  maps = new String[11];
		  maps[0]= "levels/level0.png";
		  maps[1]= "levels/level1.png";
		 // maps[2]= "levels/level2.png";
		  maps[2]= "levels/level3.png";
		  maps[3]= "levels/level4.png";
		  
		  this.loadfox();
		  skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
	  }
	  
	  public void load () {
		  sound = prefs.getBoolean("sound", true);
		  music = prefs.getBoolean("music", true);
		  volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.5f),     0.0f, 1.0f); 
		  volMusic = MathUtils.clamp(prefs.getFloat("volMusic", 0.5f),     0.0f, 1.0f); 
		  charSkin = MathUtils.clamp(prefs.getInteger("charSkin", 0),     0, 2);  
	  } 
	  
	  public void loadfox(){
		  hatNum = prefs.getInteger("hat", 0);
		  foxdmg = prefs.getInteger("foxdmg", 5);
		  foxMoveSpeed = prefs.getInteger("foxmovespeed", 3);
		  foxName = prefs.getString("foxname", "fox");
		  foxHealth = prefs.getInteger("foxhp", 3);
		  foxgold= prefs.getInteger("foxgold",0);
		  levelnum=prefs.getInteger("foxlevel",0);
	  }
	  public void savefoxlevel(int num){
		  levelnum = num;
		  prefs.putInteger("foxlevel", levelnum);
		  prefs.flush(); 
	  }
	  
	  public void savefox(int newhat,int foxdmgnew,int foxmovespeednew,String foxnamenew,int foxhealthnew){
		  hatNum=newhat;
		  foxdmg=foxdmgnew;
		  foxMoveSpeed=foxmovespeednew;
		  foxName=foxnamenew;
		  foxHealth=foxhealthnew;
		  prefs.putInteger("hat",hatNum);
		  prefs.putInteger("foxdmg", foxdmg);
		  prefs.putInteger("foxmovespeed", foxMoveSpeed);
		  prefs.putString("foxname", foxName);
		  prefs.putInteger("foxhp", foxHealth);
		  prefs.putInteger("foxlevel", levelnum);
		  prefs.flush(); 
	  }
	  
	  public void savefoxgold(int goldcount){
		  foxgold=goldcount;
		  prefs.putInteger("foxgold",foxgold);
		  prefs.putInteger("foxlevel", levelnum); 
		  prefs.flush(); 
	  }
	  
	  public void savefoxHat(int hatnum2){
		  hatNum=hatnum2;
		  prefs.putInteger("hat",hatNum);
		  prefs.flush(); 
	  }
	  
	  public void savefoxHealth(int health){
		  foxHealth=health;
		  prefs.putInteger("foxhp", foxHealth);
		  prefs.flush(); 
	  }
	  public void savefoxcreation(int newhat,String foxnamenew,int charSkin2){
		  hatNum=newhat;
		  foxName=foxnamenew;
		  levelnum=0;
		  prefs.putInteger("hat",hatNum);
		  prefs.putString("foxname", foxName);
		  prefs.putInteger("charSkin", charSkin2); 
		  prefs.putInteger("foxlevel", levelnum); 
		  prefs.flush(); 
	  }
	  
	  public void save () { 
		  prefs.putBoolean("sound", sound);  
		  prefs.putBoolean("music", music);
		  prefs.putFloat("volSound", volSound);
		  prefs.putFloat("volMusic", volMusic); 
		  prefs.putInteger("charSkin", charSkin); 
		  prefs.putBoolean("showFpsCounter", showFpsCounter);
		  prefs.flush(); 
	  }
	  
	  
}
