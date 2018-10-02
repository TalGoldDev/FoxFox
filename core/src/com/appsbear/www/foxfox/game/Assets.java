package com.appsbear.www.foxfox.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.appsbear.www.foxfox.util.Constants;
import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont; 
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
public class Assets implements Disposable, AssetErrorListener{

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets(); 
	private AssetManager assetManager;
	 public AssetFonts fonts;
	public AssetFox fox;
	public AssetRock rock;
	public AssetGoldCoin goldCoin;
	public AssetHeart heart;
	public AssetLevelDecoration levelDecoration;
	
	public AssetSounds sounds;
	public AssetMusic music;
	
	public class AssetSounds {
		public final Sound jump;  
		public final Sound hit;
		public final Sound pickupCoin; 
		public final Sound levelup; 
		public final Sound liveLost;
		 public AssetSounds (AssetManager am) { 
			 jump = am.get("sounds/jump.wav", Sound.class); 
			 liveLost = am.get("sounds/hurt.wav",Sound.class); 
			 pickupCoin = am.get("sounds/pickup_coin.wav", Sound.class);   
			 hit = am.get("sounds/hit.wav",Sound.class);
			 levelup = am.get("sounds/levelup.wav", Sound.class); 
			 } 
		 }
	public class AssetMusic {  public final Music song01;
	  public AssetMusic (AssetManager am) {
		  song01 = am.get("music/funky.wav",Music.class); 
		  } 
	  } 
	
	// singleton: prevent instantiation from other classes 
	private Assets () {}
	 
	 public void init (AssetManager assetManager) { 
		 this.assetManager = assetManager;  
		 // set asset manager error handler  
		 assetManager.setErrorListener(this); 
		 // load texture atlas   
		 assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,TextureAtlas.class);
		 // start loading assets and wait until finished 
		 
		  // load sounds
		  assetManager.load("sounds/jump.wav", Sound.class); 
		  assetManager.load("sounds/hurt.wav", Sound.class);
		  assetManager.load("sounds/pickup_coin.wav", Sound.class);  
		  assetManager.load("sounds/hit.wav", Sound.class); 
		  assetManager.load("sounds/levelup.wav", Sound.class);  
		  // load music 
		  assetManager.load("music/funky.wav",Music.class); 
		 
		 assetManager.finishLoading();
		  Gdx.app.debug(TAG, "# of assets loaded: "+ assetManager.getAssetNames().size); 
		  for (String a : assetManager.getAssetNames())
			  	Gdx.app.debug(TAG, "asset: " + a);
		  
		  TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		  // enable texture filtering for pixel smoothing 
		  for (Texture t : atlas.getTextures())   
			  t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		  // create game resource objects  
		  fonts = new AssetFonts(); 
		  fox = new AssetFox(atlas); 
		  rock = new AssetRock(atlas);  
		  goldCoin = new AssetGoldCoin(atlas);
		  heart = new AssetHeart(atlas); 
		  levelDecoration = new AssetLevelDecoration(atlas);
		  sounds = new AssetSounds(assetManager);
		  music = new AssetMusic(assetManager); 
	 }
	
	public void error (String filename, Class type,     Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '"+ filename + "'", (Exception)throwable); 
		} 

	@Override
	public void dispose() {
		 assetManager.dispose();
		  fonts.defaultSmall.dispose(); 
		  fonts.defaultNormal.dispose(); 
		  fonts.defaultBig.dispose(); 
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	//inner classes
	public class AssetFox { 
		public final AtlasRegion head; 
		public AssetFox (TextureAtlas atlas) {   
			head = atlas.findRegion("foxstanding");  
			}
		}
	
	public class AssetHeart { 
		public final AtlasRegion heart; 
		public AssetHeart (TextureAtlas atlas) {   
			heart = atlas.findRegion("heart");  
			}
		}
	
	
	public class AssetRock {  
		public final AtlasRegion edge; 
		public final AtlasRegion middle; 
		public AssetRock (TextureAtlas atlas) { 
			edge = atlas.findRegion("ground1"); 
			middle = atlas.findRegion("ground2");
			} 
		} 
	
	public class AssetGoldCoin { 
		public final AtlasRegion goldCoin; 
		public AssetGoldCoin (TextureAtlas atlas) {
			goldCoin = atlas.findRegion("coin"); 
			} 
		} 
	
	
	public class AssetLevelDecoration {
		public final AtlasRegion mountainLeft;  
		public final AtlasRegion mountainRight;  
		public final AtlasRegion waterOverlay;
	  public AssetLevelDecoration (TextureAtlas atlas) { 
		  mountainLeft = atlas.findRegion("background");
		  mountainRight = atlas.findRegion("background");
		  waterOverlay = atlas.findRegion("waves");
		  } 
	  } 
	
	
	  public class AssetFonts { 
		  public final BitmapFont defaultSmall; 
		  public final BitmapFont defaultNormal; 
		  public final BitmapFont defaultBig;
	  
		  public AssetFonts () {  
			  // create three fonts using Libgdx's 15px bitmap font  
			  defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			  defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true); 
			  defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true); 
			  // set font sizes   
			  defaultSmall.setScale(0.75f);   
			  defaultNormal.setScale(1.0f);     
			  defaultBig.setScale(2.0f);      
			  // enable linear texture filtering for smooth fonts  
			  defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear); 
			  defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);  
			  defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			  } 
		  }
		  
	
}
