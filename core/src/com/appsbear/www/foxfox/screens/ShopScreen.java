package com.appsbear.www.foxfox.screens;

import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.util.Constants;
import com.appsbear.www.foxfox.util.GamePreferences;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class ShopScreen extends AbstractGameScreen{

	
	private Stage stage; 
	  private Skin skinFox;
	  private Skin skinLibgdx;
	  private Image imgBackground; 
	  private TextButton BuyHat;
	  private TextButton BuyPower;
	  private TextButton BuyHealth;
	  private TextButton BuyNowButton;
	  private TextButton ReturnBack;
	  
	  private boolean buyhatopen;
	  private boolean buyHealthopen;
	  private boolean buypoweropen;
	  
	  Table shopbuttons;
	  Table BuyNow;
	  Table foxhats;
	  Table TitleShop;
	  Table foxHealth;
	  Table layerBackground ;
	  Table returnbuttons;
	private Texture hatsheet;
	private TextureRegion[] hats;
	private TextureRegion currenthat;
	private SelectBox selCharHat;
	private Image imgCharHat;
	private int hatselected;
	private boolean buyhealthopen;
	private Label title;
	 //hat splitting
	  private static final int        FRAME_COLS = 4;         // #1
	  private static final int        FRAME_ROWS = 1;         // #2  
	
	
	public ShopScreen(Game game) {
		super(game);
	}

	private void init() {
		 hatsheet = new Texture("images/readyforcutter.png"); // #9
	        TextureRegion[][] tmp = TextureRegion.split(hatsheet, hatsheet.getWidth()/FRAME_COLS, hatsheet.getHeight()/FRAME_ROWS);              // #10
	        hats = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	            for (int j = 0; j < FRAME_COLS; j++) {
	            	hats[index++] = tmp[i][j];
	            }
	        }
		  currenthat=hats[0];
		
		
		
		buyhatopen=false;
		buyHealthopen=false;
		 buypoweropen=false;
		 
		  skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
			 skinFox = new Skin(Gdx.files.internal(Constants.SKIN_FOXFOX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
			 shopbuttons = builshopbuttonsLayer();
			 BuyNow = buildBuyNowButtonsLayer();
			 layerBackground = buildBackGroundLayer();
			 returnbuttons = buildReturnButtonsLayer();
			 foxHealth = buildFoxHealthLayer();
			 foxhats=buildFoxHatsLayer();
			 TitleShop=buildTitleLayer();
			 stage.clear(); 
			 Stack stack = new Stack(); 
			 stack.setFillParent(true);
			 stage.addActor(stack); 
			 stack.add(TitleShop);
			 stack.add(layerBackground);
			 stack.add(shopbuttons);
			 stack.add(returnbuttons);
	}
	
	private Table buildTitleLayer() {
		Table layer = new Table();
		layer.center().top();
		title= new Label("Shop",skinLibgdx);
		title.setFontScale(2);
		  layer.add(title);
		return layer;
	}

	private Table buildFoxHealthLayer() {
		Table layer = new Table();
		layer.center();
		 Label a= new Label("Price : 100 Coins",skinLibgdx);
		  a.setFontScale(2);
		  layer.add(a);
		  layer.row();
		Texture c= new Texture("images/heart.png");
		Image b= new Image(c);
		layer.add(b).size(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/6);
		return layer;
	}

	private Table buildReturnButtonsLayer() {
		Table layer = new Table();
		layer.center().bottom();
		TextButton ReturnBack =new TextButton("Return To Menu", skinLibgdx);
		layer.add(ReturnBack).size(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/6); 
		ReturnBack.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new MenuScreen(game));
			}
			
		});
		return layer;
	}

	private Table buildBackGroundLayer() {
		Table layer = new Table();
		 // + Background   
		imgBackground = new Image(skinFox, "background");
		layer.add(imgBackground).bottom().left(); 
		imgBackground.setFillParent(true);
		return layer; 
		}

	private void updatestage(){
		 stage.clear();   
		 Stack stack = new Stack();
		 stack.setFillParent(true);
		 stack.add(layerBackground);
		 stack.add(TitleShop);
		 if(buyhatopen)
			 stack.add(foxhats);
		 
		 if(buyhealthopen)
			 stack.add(foxHealth);
		 
		 stack.add(BuyNow);
		 stack.add(TitleShop);
		 stage.addActor(stack); 	
	}

	
	
	private Table buildBuyNowButtonsLayer() {
		Table layer = new Table();
		layer.center().bottom();
		TextButton BuyNowButton =new TextButton("Buy", skinLibgdx);
		layer.add(BuyNowButton).size(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/6); 
		BuyNowButton.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GamePreferences.instance.loadfox();
				if(buyhatopen)
				{	GamePreferences.instance.savefoxgold(GamePreferences.instance.foxgold-100);
				GamePreferences.instance.savefoxHat(hatselected);}
				if(buyhealthopen)
				{
					GamePreferences.instance.savefoxHealth(GamePreferences.instance.foxHealth+1);
					GamePreferences.instance.savefoxgold(GamePreferences.instance.foxgold-100);
				}
				game.setScreen(new MenuScreen(game));
			}
			
		});
		return layer;
	}

	
	
	
	
	
	
	private Table builshopbuttonsLayer() {
		Table layer = new Table();
		layer.center();
		TextButton BuyHat =new TextButton("Buy Hat", skinLibgdx);
		 layer.add(BuyHat).size(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/6); 
		 BuyHat.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buyhatopen=true;
				updatestage();
			}
			 
		 });
		 layer.row();
		 TextButton BuyHealth =new TextButton("Buy Health", skinLibgdx);
		 layer.add(BuyHealth).size(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/6); 
		 BuyHealth.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					buyHealthopen=true;
					updatestage();
				}
				 
			 });
		 layer.row();
		 TextButton BuyPower =new TextButton("Buy Power", skinLibgdx);
		 layer.add(BuyPower).size(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/6); 
		 BuyPower.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
				buypoweropen=true;	
				updatestage();
				}
				 
			 });
		return layer;
	}

	@Override
	public void render(float deltaTime) {
		 stage.act(deltaTime);   
		 stage.draw();			
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);		
	}

	@Override
	public void show() {
		GamePreferences.instance.load();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage); 	
		init(); 
	}

	@Override
	public void hide() {
		stage.dispose();
		 skinFox.dispose(); 
		 skinLibgdx.dispose(); 			
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	private Table buildFoxHatsLayer() {
		Table layer = new Table();  
		layer.center();
		 Label a= new Label("Price : 100 Coins",skinLibgdx);
		  a.setFontScale(2);
		  layer.add(a);
		  layer.row();
		layer.pad(10, 10, 0, 10);
		layer.add(new Label("Character Hat", skinLibgdx,  "default-font", Color.ORANGE)).colspan(2);
		layer.row();
		selCharHat = new SelectBox(skinLibgdx);
		selCharHat.setItems(1,2,3,4,5);
		 selCharHat.addListener(new ChangeListener() {  
			 @Override  
			 public void changed (ChangeEvent event, Actor actor) { 
				 onCharHatSelected(((SelectBox)actor).getSelectedIndex());
				 }
			 }); 
		 layer.add(selCharHat).width(Gdx.graphics.getWidth()/6).padRight(20);
		 // + Skin preview image 
		 imgCharHat = new Image(currenthat);
		 layer.add(imgCharHat).width(Gdx.graphics.getWidth()/6).height(Gdx.graphics.getWidth()/5);
		 layer.row();
		 
		return layer;
	}
	
	private void onCharHatSelected(int index) { 
		Gdx.app.debug(null, index + "");
		hatselected=index;
		if(index==4){
			//Image a = null;
			imgCharHat.setVisible(false);
		}else{
			currenthat=hats[index];
		Image b=new Image(hats[index]);
		Gdx.app.debug(null, "new hat selected" + hatselected);
		imgCharHat.setDrawable(b.getDrawable());
		imgCharHat.setVisible(true);
		}
		}
	
}
