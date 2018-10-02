package com.appsbear.www.foxfox.util;

public class Constants {
	 // Visible game world is 5 meters wide 
	public static final float VIEWPORT_WIDTH = 7.0f;
	  // Visible game world is 5 meters tall
	public static final float VIEWPORT_HEIGHT = 7.0f; 
		// setting up the path of the texture atlas
	 public static final String TEXTURE_ATLAS_OBJECTS = "images/foxfox.pack"; 
	  // Location of image file for level 01  
	 public static String LEVEL_01 = GamePreferences.instance.maps[GamePreferences.instance.levelnum];
	 // GUI Width 
	 public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	 // GUI Height
	 public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	  // Amount of extra lives at level start 
	 public static final int LIVES_START = GamePreferences.instance.foxHealth;
	public static final float ITEM_FEATHER_POWERUP_DURATION = 0;
	// Delay after game over 
	public static final float TIME_DELAY_GAME_OVER = 3;
	
	  public static final String TEXTURE_ATLAS_UI =      "images/menutexturefox.pack"; 
	  public static final String TEXTURE_ATLAS_LIBGDX_UI =      "images/uiskin.atlas";
	  // Location of description file for skins
	  public static final String SKIN_LIBGDX_UI =      "images/uiskin.json";  
	  public static final String SKIN_FOXFOX_UI =      "images/foxfox-ui.json";
	
	  public static final String TEXTURE_ATLAS_UIBUTTONS =      "images/keys.pack"; 
	  public static final String SKIN_FOXFOX_UIBUTTONS =      "images/foxfox-uibuttons.json";
	public static final String PREFERENCES = "save2";
	  
	  
}
