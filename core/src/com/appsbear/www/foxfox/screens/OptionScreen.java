package com.appsbear.www.foxfox.screens;

import com.appsbear.www.foxfox.game.Assets;
import com.appsbear.www.foxfox.util.AudioManager;
import com.appsbear.www.foxfox.util.CharacterSkin;
import com.appsbear.www.foxfox.util.Constants;
import com.appsbear.www.foxfox.util.GamePreferences;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class OptionScreen extends AbstractGameScreen{
	 private static final String TAG = MenuScreen.class.getName();
	 
	  private Stage stage; 
	  private Skin skinFox;
	  private Skin skinLibgdx;
	  // menu 
	  private Image imgBackground; 
	  private Image imgLogo; 
	  private Image imgInfo;  
	  private Image imgCoins; 
	  private Image imgBunny; 
	  private Button btnMenuPlay; 
	  private Button btnMenuOptions;
	  
	  // options  
	  private Window winOptions;
	  private TextButton btnWinOptSave;
	  private TextButton btnWinOptCancel;
	  private CheckBox chkSound;
	 
	  private Slider sldSound; 
	  private CheckBox chkMusic; 
	  private Slider sldMusic;  
	  private SelectBox selCharSkin; 
	  private Image imgCharSkin;
	  private CheckBox chkShowFpsCounter;
	 
	 
	public OptionScreen(Game game) {
		super(game);
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
		rebuildStage(); 		
	}

	private void rebuildStage() {
		  skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
		 skinFox = new Skin(Gdx.files.internal(Constants.SKIN_FOXFOX_UI),new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		 Table layerOptionsWindow = buildOptionsWindowLayer();
		  Table layerBackground = buildBackgroundLayer(); 
		  Table layerLogos = buildLogosLayer();
		  Table buildOptWinButtons= buildOptWinButtons();
		   // assemble stage for menu screen  
		 stage.clear();   
		 Stack stack = new Stack(); 
		 stage.addActor(stack);  
		 stack.setFillParent(true);
		 stack.add(layerBackground);
		 stack.add(layerLogos);
		 stack.add(layerOptionsWindow);

		 
	}

	private Table buildOptWinButtons() {
		 Table tbl = new Table();  
		
	
		 return tbl;
	}

	private Table buildLogosLayer() {
		  Table layer = new Table(); 
		  layer.center().top();
		  // + Game Logo 
		  imgLogo = new Image(skinFox, "logo");
		  layer.add(imgLogo); 
		  layer.row().expandY();  
		  // + Info Logos  
		  return layer; 
	}

	private Table buildBackgroundLayer() {
		Table layer = new Table();
		 // + Background   
		imgBackground = new Image(skinFox, "background");
		layer.add(imgBackground); 
		return layer; 
	}

	private Table buildOptionsWindowLayer() {
		 Table tbl = new Table();
		 // + Title: "Audio"
		 tbl.pad(10, 10, 0, 10); 
		 tbl.add(new Label("Audio", skinLibgdx, "default-font",Color.ORANGE)).colspan(3);  
		 tbl.row(); 
		 tbl.columnDefaults(0).padRight(10); 
		 tbl.columnDefaults(1).padRight(10);  
		 // + Checkbox, "Sound" label, sound volume slider
		 chkSound = new CheckBox("", skinLibgdx);
		 tbl.add(chkSound);
		 tbl.add(new Label("Sound", skinLibgdx));  
		 sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx); 
		 tbl.add(sldSound);  tbl.row(); 
		 // + Checkbox, "Music" label, music volume slider
		 chkMusic = new CheckBox("", skinLibgdx);
		 tbl.add(chkMusic);
		 tbl.add(new Label("Music", skinLibgdx));
		 sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx); 
		 tbl.add(sldMusic); 
		 tbl.row();
		 
		  // + Title: "Character Skin"
		 tbl.pad(10, 10, 0, 10);
		 tbl.add(new Label("Character Skin", skinLibgdx,  "default-font", Color.ORANGE)).colspan(2);
		 tbl.row();
		 // + Drop down box filled with skin items 
		 selCharSkin = new SelectBox(skinLibgdx); 
		 selCharSkin.setItems(CharacterSkin.values());
		 selCharSkin.addListener(new ChangeListener() {  
			 @Override  
			 public void changed (ChangeEvent event, Actor actor) { 
				 onCharSkinSelected(((SelectBox)actor).getSelectedIndex()); 
				 }
			 }); 
		 tbl.add(selCharSkin).width(120).padRight(20);
		 // + Skin preview image 
		 imgCharSkin = new Image(Assets.instance.fox.head);
		 tbl.add(imgCharSkin).width(50).height(50);
		 tbl.row();
		 // + Title: "Debug" 
		 tbl.pad(10, 10, 0, 10); 
		 tbl.add(new Label("Debug", skinLibgdx, "default-font",     Color.RED)).colspan(3); 
		 tbl.row(); 
		 tbl.columnDefaults(0).padRight(10); 
		 tbl.columnDefaults(1).padRight(10); 
		 // + Checkbox, "Show FPS Counter" label 
		 chkShowFpsCounter = new CheckBox("", skinLibgdx);
		 tbl.add(new Label("Show FPS Counter", skinLibgdx));
		 tbl.add(chkShowFpsCounter);  
		 tbl.row();
		 
		 
		 // + Separator 
		 Label lbl = null; 
		 lbl = new Label("", skinLibgdx);
		 lbl.setColor(0.75f, 0.75f, 0.75f, 1);  
		 lbl.setStyle(new LabelStyle(lbl.getStyle()));
		 lbl.getStyle().background = skinLibgdx.newDrawable("white"); 
		 tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 0, 0, 1); 
		 tbl.row();  lbl = new Label("", skinLibgdx); 
		 lbl.setColor(0.5f, 0.5f, 0.5f, 1); 
		 lbl.setStyle(new LabelStyle(lbl.getStyle()));
		 lbl.getStyle().background = skinLibgdx.newDrawable("white");
		 tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 1, 5, 0); 
		 tbl.row(); 
		 // + Save Button with event handler  
		 btnWinOptSave = new TextButton("Save", skinLibgdx);
		 tbl.add(btnWinOptSave).padRight(30); 
		 btnWinOptSave.addListener(new ChangeListener() {  
			 @Override   
			 public void changed (ChangeEvent event, Actor actor) {   
				 onSaveClicked();    
				 } 
			 });
		 return tbl;
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
	
	private void loadSettings() { 
		GamePreferences prefs = GamePreferences.instance;  
		prefs.load();  chkSound.setChecked(prefs.sound); 
		sldSound.setValue(prefs.volSound); 
		chkMusic.setChecked(prefs.music); 
		sldMusic.setValue(prefs.volMusic);  
		selCharSkin.setSelectedIndex(prefs.charSkin);
		onCharSkinSelected(prefs.charSkin);
		chkShowFpsCounter.setChecked(prefs.showFpsCounter);
		}

	private void saveSettings() {  
		GamePreferences prefs = GamePreferences.instance; 
		prefs.sound = chkSound.isChecked(); 
		prefs.volSound = sldSound.getValue();
		prefs.music = chkMusic.isChecked();
		prefs.volMusic = sldMusic.getValue(); 
		prefs.charSkin = selCharSkin.getSelectedIndex(); 
		prefs.showFpsCounter = chkShowFpsCounter.isChecked();
		prefs.save(); 
	}
	
	private void onCharSkinSelected(int index) { 
		CharacterSkin skin = CharacterSkin.values()[index]; 
		imgCharSkin.setColor(skin.getColor()); 
		}
	private void onSaveClicked() { 
		saveSettings();
		AudioManager.instance.onSettingsUpdated();
		game.setScreen(new MenuScreen(game)); 		
		
		}


	
}
