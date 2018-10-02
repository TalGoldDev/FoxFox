package com.appsbear.www.foxfox.screens;

import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.util.AudioManager;
import com.appsbear.www.foxfox.util.CharacterSkin;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class FoxCreationScreen extends AbstractGameScreen{
	private static final String TAG = FoxCreationScreen.class.getName();
	
	 private SelectBox selCharSkin; 
	  private Image imgCharSkin;
	  private SelectBox selCharHat;
	  private Image imgCharHat;
	  private int hatselected;
	  private TextButton btnWinOptSave;
	  private TextField foxnameb;
	  private boolean enteredname;
	  private boolean chosecolor;
	  private boolean chosehat;
	  private String foxnameenter;
	  //hat splitting
	  private static final int        FRAME_COLS = 4;         // #1
	  private static final int        FRAME_ROWS = 1;         // #2
	  
	  Texture                         hatsheet;              // #4
	  TextureRegion[]                 hats;             // #5
	  TextureRegion                   currenthat;           // #7
	 public FoxCreationScreen(Game game) {
		super(game);
	}

	 
	  Table layerBackground ;
	  Table Title;
	  Table username;
	  Table foxcolor;
	  Table foxhats;
	  Table Buttons ;
	 Table current;
	 
	  private void init() {
		  enteredname=false;
		 chosecolor=false;
		  chosehat=false;
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
		  
		  
		  skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
			 skinFox = new Skin(Gdx.files.internal(Constants.SKIN_FOXFOX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
			   layerBackground = buildBackgroundLayer(); 
			   Title = buildTitleLayer();
			   Buttons = buildButtonsLayer();
			   current = buildUserNameLayer();
			  	 stage.clear();   
				 Stack stack = new Stack(); 
				 stage.addActor(stack);  
				 stack.setFillParent(true);
				 stack.add(layerBackground);
				 stack.add(Title);
				 stack.add(current);
				// stack.add(foxhats);
				// stack.add(foxcolor);
				 stack.add(Buttons);
	}
	  
	 

	private Table buildButtonsLayer() {
		Table layer = new Table();
		layer.center().bottom();
		 // + Save Button with event handler  
		 btnWinOptSave = new TextButton("Save", skinLibgdx);
		 layer.add(btnWinOptSave).size(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/5); 
		 btnWinOptSave.addListener(new ChangeListener() {  
			 @Override   
			 public void changed (ChangeEvent event, Actor actor) {   
				 onSaveClicked();    
				 }

			 
			 public void updatestage(){
				 stage.clear();   
				 Stack stack = new Stack(); 
				 stage.addActor(stack);  
				 stack.setFillParent(true);
				 stack.add(layerBackground);
				 layerBackground.setFillParent(true);
				 stack.add(Title);
				 
				   username = buildUserNameLayer();
				   foxcolor= buildFoxColorLayer();
				   foxhats = buildFoxHatsLayer();

				 
				 
				  if(!enteredname)
				  { current.clear();
				  current = buildUserNameLayer();
					  stack.add(current);}
				  if(enteredname&&!chosecolor)
					  {current.clear();
				  	current =buildFoxColorLayer();
					  stack.add(current);}
				  if(enteredname&&chosecolor&&!chosehat)
				  {current.clear();
					  current =buildFoxHatsLayer();
					  stack.add(current);}
				  stack.add(Buttons);
				  
					  
			  }
			 
			private void onSaveClicked() {
				if(!enteredname){
					foxnameenter=foxnameb.getText();
					enteredname=true;
					updatestage();
					return;
				}
				if(enteredname&&!chosecolor){
					chosecolor=true;
					updatestage();
					return;
				}
				if(enteredname&&chosecolor&&!chosehat){
					chosehat=true;
				}
					
				if(enteredname&&chosecolor&&chosehat)
					{saveSettings();
				game.setScreen(new MenuScreen(game)); 	}				
			} 
			 });
		return layer;
	}

	
	private void saveSettings() {  
		GamePreferences prefs = GamePreferences.instance; 
		//prefs.hatNum=hatselected;
		//prefs.foxName =foxnameb.getText(); 
		prefs.savefoxcreation(hatselected,foxnameb.getText(),selCharSkin.getSelectedIndex()); 
		game.setScreen(new MenuScreen(game)); 	
	}
	
	private Table buildFoxHatsLayer() {
		Table layer = new Table();  
		layer.center();
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

	private Table buildFoxColorLayer() {
		Table layer = new Table(); 
		  // + Title: "Character Skin"
		layer.pad(10, 10, 0, 10);
		layer.add(new Label("Character Skin", skinLibgdx,  "default-font", Color.ORANGE)).colspan(2);
		layer.row();
		 // + Drop down box filled with skin items 
		 selCharSkin = new SelectBox(skinLibgdx); 
		 selCharSkin.setItems(CharacterSkin.values());
		 selCharSkin.addListener(new ChangeListener() {  
			 @Override  
			 public void changed (ChangeEvent event, Actor actor) { 
				 onCharSkinSelected(((SelectBox)actor).getSelectedIndex()); 
				 }
			 }); 
		 layer.add(selCharSkin).width(Gdx.graphics.getWidth()/6).padRight(20);
		 // + Skin preview image 
		 imgCharSkin = new Image(Assets.instance.fox.head);
		 layer.add(imgCharSkin).width(Gdx.graphics.getWidth()/6).height(Gdx.graphics.getWidth()/5);
		 layer.row();
		return layer;
	}

	
	private void onCharSkinSelected(int index) { 
		CharacterSkin skin = CharacterSkin.values()[index]; 
		imgCharSkin.setColor(skin.getColor()); 
		 imgCharHat = new Image(currenthat);
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
	
	private Table buildUserNameLayer() {
		Table layer = new Table();  
		Label a= new Label("UserName :",skinLibgdx);
		a.setFontScale(1);
		  layer.add(a);
		  foxnameb= new TextField("UserName",skinLibgdx);
		  layer.add(foxnameb);
		  layer.row().expandY();
		 return layer;		
	}

	private Table buildTitleLayer() {
		Table layer = new Table();  
		layer.center().top();
		  Label a= new Label("Creating Fox",skinLibgdx);
		  a.setFontScale(3);
		  layer.add(a);
		  layer.row().expandY();
		 return layer;
	}

	private Stage stage; 
	  private Skin skinFox;
	  private Skin skinLibgdx;

	  private Image imgBackground; 
	  
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
	
	public Table buildcreationoptions(){
		Table tbl = new Table();  
		
		 return tbl;
	}
	
	private Table buildBackgroundLayer() {
		Table layer = new Table();
		 // + Background   
		imgBackground = new Image(skinFox, "background");
		layer.add(imgBackground).bottom().left(); 
		imgBackground.setFillParent(true);
		return layer; 
	}
	
	
}
