package com.appsbear.www.foxfox.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera; 
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch; 
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable; 
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.appsbear.www.foxfox.game.objects.EnemySpawner;
import com.appsbear.www.foxfox.screens.OptionScreen;
import com.appsbear.www.foxfox.util.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils; 
import com.appsbear.www.foxfox.util.GamePreferences;

public class WorldRenderer implements Disposable{
	 private static final String TAG = WorldRenderer.class.getName();
	
	  private OrthographicCamera camera; 
	  private SpriteBatch batch;  
	  private WorldController worldController;
	  private OrthographicCamera cameraGUI;
	  private OrthographicCamera cameraBUI;
	  
		//building a newbie guide
		private Stage stage2;
		private boolean finishnewbieguide;
		 private Image imgBackground; 
		 Table layerBackground ;
		 
	  Stage UIB;
	  Stack stack = new Stack(); 
	  //textures for keys
	  Texture right= new Texture("images/arrowkeys/right.png");
	  Texture left = new Texture("images/arrowkeys/left.png");
	  Texture jump= new Texture("images/arrowkeys/jump.png");
	  Image a = new Image (right);
	  Image b = new Image (left);
	  Image c = new Image (jump);
	  private Skin skinFox;
	  int leftedge,firstbuttonwidth,secondbuttonwidth;
	  int buttony;
	  
	  //image button skin
	 Skin skinfoxfox = new Skin(Gdx.files.internal(Constants.SKIN_FOXFOX_UIBUTTONS),
			  new TextureAtlas(Constants.TEXTURE_ATLAS_UIBUTTONS)); ;

	 ImageButton l = new ImageButton(skinfoxfox,"leftkey");
	  ImageButton r = new ImageButton(skinfoxfox,"rightkey");
	  ImageButton j = new ImageButton(skinfoxfox,"jumpkey");
	  ImageButton f=new ImageButton(skinfoxfox,"firebutton");
	  
	public WorldRenderer (WorldController worldController) {
		 this.worldController = worldController; 
		 init(); 
	}
	
	private void init () {
	/*	if(GamePreferences.instance.levelnum==0)
		finishnewbieguide=true;
		
		if(finishnewbieguide){
			 skinFox = new Skin(Gdx.files.internal(Constants.SKIN_FOXFOX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
			layerBackground =buildBackGroundLayer();
			stage2= new Stage(new ScreenViewport());
			stage2.clear();
			 Stack stack2 = new Stack(); 
			 stack2.setFillParent(true);
			 stage2.addActor(stack2); 
			 stack2.add(layerBackground);
		} */
		UIB = new Stage(new ScreenViewport());
	    Gdx.input.setInputProcessor(UIB);
		 batch = new SpriteBatch(); 
		 camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
		 camera.position.set(0, 0, 0);   
		 camera.update(); 
		 
		 cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
		 cameraGUI.position.set(0, 0, 0);  
		 cameraGUI.setToOrtho(true);  // flip y-axis  
		 cameraGUI.update();
		 
		 cameraBUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
		 cameraBUI.position.set(0, 0, 0);  
		 cameraBUI.setToOrtho(false);  // flip y-axis  
		 cameraBUI.update();
		 
		 Table layertopground = buildtopLayer(); 
		 Table layerdirectionkeys = buildkeysLayer(); 
		 Table layeractionkeys = buildactionkeysLayer(); 
		 layertopground.setFillParent(true);
		 layerdirectionkeys.setFillParent(true);
		 layeractionkeys.setFillParent(true);
		// layertopground.setFillParent(true);
		 stack.add(layertopground); 
		 stack.add(layerdirectionkeys); 
		 stack.add(layeractionkeys); 
		 stack.setFillParent(true);
	}
	
	private Table buildBackGroundLayer() {
		Table layer = new Table();
		layer.center();
		 // + Background   
		imgBackground = new Image(skinFox, "background");
		layer.add(imgBackground).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/5); 
		return layer; 
		}
	
	private Table buildactionkeysLayer() {
		 Table layer = new Table();  
		  layer.right().bottom(); 
		  layer.setY(layer.getY()+70);
		  ImageButton s = new ImageButton(skinfoxfox,"jumpkey");
		  layer.add(s).size(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/8);
		  ImageButton f = new ImageButton(skinfoxfox,"firebutton");
		  layer.add(f).size(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/7);
		  s.addListener(new ChangeListener() { 
			  @Override 
			  public void changed (ChangeEvent event, Actor actor) { 
				  onPlayClicked();   
				  }

			private void onPlayClicked() {
				worldController.jumparrow();
			} 
			  });
		  
		  f.addListener(new ChangeListener() { 
			  @Override 
			  public void changed (ChangeEvent event, Actor actor) { 
				  onPlayClicked();   
				  }

			private void onPlayClicked() {
				worldController.level.shotmanager.fireshot(worldController.level.fox.position.x, worldController.level.fox.position.y+(worldController.level.fox.bounds.height/4));
			} 
			  });
		  
		  
		  
		  return layer ;
		  }
	
	
	
	

	private Table buildkeysLayer() {
		 Table layer = new Table();  
		  layer.left().bottom(); 
		  ImageButton s = new ImageButton(skinfoxfox,"leftkey");
		  layer.add(s).size(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/8);
		  ImageButton d = new ImageButton(skinfoxfox,"rightkey");
		  layer.add(d).size(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/8);
		  return layer ;
	}
	
	


	private Table buildtopLayer() {
		  Table layer = new Table();  
		  layer.right().top(); 
		  ImageButton s = new ImageButton(skinfoxfox,"settings");
		  layer.add(s).size(Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/8);
		  s.addListener(new ChangeListener() { 
			  @Override 
			  public void changed (ChangeEvent event, Actor actor) { 
				  onPlayClicked();   
				  }

			private void onPlayClicked() {
				Gdx.app.debug(TAG, "workingbutton");
				worldController.backToMenu();
				} 
			  });
		  layer.row();
		return layer ;
	}

	public void render () { 
		 renderWorld(batch);
		 renderGui(batch); 
		 renderBui(batch);
	} 
	

	private void renderBui(SpriteBatch batch2) {
		 batch.setProjectionMatrix(cameraBUI.combined);   
		  batch.begin();
		  drawkeys(batch2);
		  batch.end();
	}

	private void drawkeys(SpriteBatch batch2) {
		  UIB.act(Gdx.graphics.getDeltaTime());
		    UIB.addActor(stack);
		    UIB.draw();
		    
	}

	
	

	public void resize (int width, int height) {
		//stage2.getViewport().update(width, height, true);
		 camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) *width;  
		 camera.update(); 
		 cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT; 
		 cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT  / (float)height) * (float)width; 
		 cameraGUI.position.set(cameraGUI.viewportWidth / 2,cameraGUI.viewportHeight / 2, 0);  
		 cameraGUI.update();
		 
		 cameraBUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT; 
		 cameraBUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT  / (float)height) * (float)width; 
		 cameraBUI.position.set(cameraGUI.viewportWidth / 2,cameraGUI.viewportHeight / 2, 0);  
		 cameraBUI.update();
		 
		 UIB.getViewport().update(width, height, true);
		 stack.setWidth(width);
		 stack.setHeight(height);
		 
	}
	 
	  private void renderWorld (SpriteBatch batch) {
		  worldController.cameraHelper.applyTo(camera); 
		  batch.setProjectionMatrix(camera.combined); 
		  batch.begin();    
		  worldController.level.render(batch); 
		  batch.end(); 
		  }   
	
	
	  private void renderGuiScore (SpriteBatch batch) {   
		  float x = -15;  
		  float y = -15; 
		  float offsetX = 50; 
		  float offsetY = 50;
		  if (worldController.scoreVisual < worldController.score) 
		  {    
			  long shakeAlpha = System.currentTimeMillis() % 360;
			  float shakeDist = 1.5f; 
			  offsetX += MathUtils.sinDeg(shakeAlpha * 2.2f) * shakeDist;
			  offsetY += MathUtils.sinDeg(shakeAlpha * 2.9f) * shakeDist;
			  } 
		  batch.draw(Assets.instance.goldCoin.goldCoin,x, y,
				  offsetX, offsetY,
				  100, 100, 
				  0.35f, -0.35f,
				  0); 
		  Assets.instance.fonts.defaultBig.draw(batch,"" + (int)worldController.scoreVisual,x + 75, y + 37); 
	  }
	  
	  
	  private void renderGuiExtraLive (SpriteBatch batch) {
		  float x = cameraGUI.viewportWidth -Constants.LIVES_START * 50 -100; 
		  float y = -15; 
		  for (int i = 0; i < Constants.LIVES_START; i++) {   
			  if (worldController.lives <= i)  
				  batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);    
			  batch.draw(Assets.instance.heart.heart,      
					  x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
			  batch.setColor(1, 1, 1, 1);   
			  } 
		  if (worldController.lives >= 0&& worldController.livesVisual > worldController.lives)
		  {  
			  int i = worldController.lives; 
			  float alphaColor = Math.max(0, worldController.livesVisual - worldController.lives - 0.5f);  
			  float alphaScale = 0.35f * (2 + worldController.lives - worldController.livesVisual) * 2;  
			  float alphaRotate = -45 * alphaColor;
			  batch.setColor(1.0f, 0.7f, 0.7f, alphaColor); 
			  batch.draw(Assets.instance.heart.heart,x + i * 50, y,50, 50,120, 100,alphaScale, -alphaScale,alphaRotate); 
			  batch.setColor(1, 1, 1, 1); 
			  }
		  } 
	  
	  
	  
	  private void renderGuiFpsCounter (SpriteBatch batch) {  
		  float x = cameraGUI.viewportWidth - 55; 
		  float y = cameraGUI.viewportHeight - 15;  
		  int fps = Gdx.graphics.getFramesPerSecond(); 
		  BitmapFont fpsFont = Assets.instance.fonts.defaultNormal; 
		  if (fps >= 45) {   
			  // 45 or more FPS show up in green    
			  fpsFont.setColor(0, 1, 0, 1);  
			  } else if (fps >= 30) {
				  // 30 or more FPS show up in yellow 
				  fpsFont.setColor(1, 1, 0, 1);    
				  } else {    
					  // less than 30 FPS show up in red  
					  fpsFont.setColor(1, 0, 0, 1);  
					  }  
		  fpsFont.draw(batch, "FPS: " + fps, x, y);    
		  fpsFont.setColor(1, 1, 1, 1); // white 
		  }
		  
	  
	  private void renderGui (SpriteBatch batch) { 
		  batch.setProjectionMatrix(cameraGUI.combined);   
		  batch.begin();
		  // draw collected gold coins icon + text 
		  // (anchored to top left edge)  
		  renderGuiScore(batch);   
		  // draw extra lives icon + text (anchored to top right edge)  
		  renderGuiExtraLive(batch);  
		  // draw FPS text (anchored to bottom right edge) 
		  if (GamePreferences.instance.showFpsCounter) 
		  renderGuiFpsCounter(batch); 
		  // draw game over text 
		  renderGuiGameOverMessage(batch);
		  batch.end();
	  }
	  
	  
	@Override
	public void dispose() {
		 batch.dispose();
		 UIB.dispose();
	}

	
	private void renderGuiGameOverMessage (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth / 2;  
		float y = cameraGUI.viewportHeight / 2; 
		if (worldController.isGameOver()) { 
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);  
			fontGameOver.drawMultiLine(batch, "GAME OVER",        x, y, 0, BitmapFont.HAlignment.CENTER);  
			fontGameOver.setColor(1, 1, 1, 1); 
			}
		}
	
	
	
}
