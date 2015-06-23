package br.com.casadocodigo.meteoros.scenes;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.config.Assets;
import static br.com.casadocodigo.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.config.DeviceSettings.screenWidth;

public class GameScene extends CCLayer implements MeteorEngineDelegate{
	
	private ScreenBackground background;
	private MeteorsEngine meteorsEngine;
	private CCLayer meteorLayer;
	private List meteorArray;
	
	public GameScene() {
		this.background = new ScreenBackground(Assets.BACKGROUND);
		this.background.setPosition(
				screenResolution(CGPoint.ccp
						(screenWidth() / 2.0f,
								screenHeight() / 2.0f)));
		
		this.addChild(this.background);
		
		this.meteorLayer = CCLayer.node();
		this.addChild(this.meteorLayer);
		this.addGameObjects();
	}
	
	public void addGameObjects(){
		this.meteorArray = new ArrayList();
		this.meteorsEngine = new MeteorsEngine();
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
		this.startEngines();
	}
	
	private void startEngines(){
		this.addChild(this.meteorsEngine);
		this.meteorsEngine.setDelegate(this);
	}
	
	
	
	public static CCScene createGame() {
		CCScene scene = CCScene.node();
		GameScene layer = new GameScene();
		scene.addChild(layer);
		return scene;
	}

	@Override
	public void createMeteror(Meteor meteor) {
		this.meteorLayer.addChild(meteor);
		meteor.start();
		this.meteorArray.add(meteor);
		
	}
}
