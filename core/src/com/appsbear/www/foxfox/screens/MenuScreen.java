package com.appsbear.www.foxfox.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color; 
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas; 
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage; 
import com.badlogic.gdx.scenes.scene2d.ui.Button; 
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox; 
import com.badlogic.gdx.scenes.scene2d.ui.Image; 
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle; 
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox; 
import com.badlogic.gdx.scenes.scene2d.ui.Skin; 
import com.badlogic.gdx.scenes.scene2d.ui.Slider; 
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.util.Constants;
import com.badlogic.gdx.InputProcessor;

public class MenuScreen extends AbstractGameScreen{
	 private static final String TAG = MenuScreen.class.getName();
	 
	  private Stage stage;
	  private Skin skinFoxFox;
	  private Skin skinLibgdx;
	  // menu  
	  private Image imgBackground ;
	  private Image imgLogo; 
	  private Image imgInfo; 
	  private Image imgTwitter;
	  private Image imgLeaderboard; 
	  private Button btnMenuPlay;
	  private Button btnMenuOptions;
	  private TextButton btnShop;
	  
	  private void rebuildStage () { 
		  skinFoxFox = new Skin(        Gdx.files.internal(Constants.SKIN_FOXFOX_UI), 
				  new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		  skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
	    // build all layers 
		  Table layerBackground = buildBackgroundLayer(); 
		  layerBackground.setFillParent(true);
		  Table layerObjects = buildObjectsLayer(); 
		  Table layerLogos = buildLogosLayer();  
		  Table layerControls = buildControlsLayer();
		  Table layerOptionsWindow = buildOptionsWindowLayer();
		  
	    // assemble stage for menu screen
		  stage.clear();    
		  Stack stack = new Stack();
		  stage.addActor(stack); 
		  stack.setFillParent(true);  
		  stack.add(layerBackground); 
		  stack.addActor(layerObjects);   
		  stack.addActor(layerLogos);    
		  stack.addActor(layerControls);  
		  stage.addActor(layerOptionsWindow); 
		  }
	  
	 private Table buildOptionsWindowLayer() {
		 Table layer = new Table();    return layer; 
	}
	 
	 

	private Table buildLogosLayer() {
		 Table layer = new Table();  
		 layer.center().top();  
		 // + Game Logo 
		 imgLogo = new Image(skinFoxFox, "logo");
		 layer.add(imgLogo); 
		 layer.row();
		 return layer; 
	}

	private Table buildControlsLayer() {
		 Table layer = new Table();  
		  layer.right().center(); 
		  // + Play Button
		  btnMenuPlay = new Button(skinFoxFox, "play");
		  layer.add(btnMenuPlay).size(Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/5);
		  btnMenuPlay.addListener(new ChangeListener() {
			  @Override   
			  public void changed (ChangeEvent event, Actor actor) { 
				  onPlayClicked(); 
				  }

			private void onPlayClicked() {
				game.setScreen(new GameScreen(game)); 
			}
			  }); 
		  layer.row();
		  
		  // + Options Button  
		  btnMenuOptions = new Button(skinFoxFox, "options"); 
		  layer.add(btnMenuOptions).size(Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/5);
		  btnMenuOptions.addListener(new ChangeListener() { 
			  @Override    
			  public void changed (ChangeEvent event, Actor actor) { 
				  onOptionsClicked();  
				  }

			private void onOptionsClicked() {
				//  ScreenTransition transition = ScreenTransitionSlide.init(0.75f,ScreenTransitionSlide.DOWN, false, Interpolation.bounceOut); 
				  game.setScreen(new OptionScreen(game)); 				
			} 
			  });
		  
		  layer.row();
		  //shop button
		  btnShop = new TextButton("Shop",skinLibgdx);
		  layer.add(btnShop).size(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/6);
		  btnShop.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				  game.setScreen(new ShopScreen(game)); 	
			}
			  
		  });
		  
		 return layer; 
	}

	private Table buildObjectsLayer() {
		 Table layer = new Table();    return layer; 
	}

	private Table buildBackgroundLayer() {
		 Table layer = new Table();  
		// + Background  
		 imgBackground = new Image(skinFoxFox, "background");
		 imgBackground.setPosition(0, 0);
		 imgBackground.setFillParent(true);
		 layer.add(imgBackground).fill(); 
		 return layer; 
	}

	
	
	 public MenuScreen (Game game) {
		 super(game);  
		 }
	 
	@Override
	public void render(float deltaTime) {
		  Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);   
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		  stage.act(deltaTime);
		  stage.draw();    
		  
	}

	@Override
	public void resize(int width, int height) {
		  stage.getViewport().update(width,height, true);
	}

	@Override
	public void show() {
		
		 stage = new Stage(); 
		 Gdx.input.setInputProcessor(stage); 
		 rebuildStage(); 		
	}

	@Override
	public void hide() {
		stage.dispose();  
		skinFoxFox.dispose(); 		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}
