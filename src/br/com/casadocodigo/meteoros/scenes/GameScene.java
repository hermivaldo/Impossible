package br.com.casadocodigo.meteoros.scenes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.learning.impossible.R;

import br.com.casadocodigo.config.Assets;
import br.com.casadocodigo.config.GameButtons;
import static br.com.casadocodigo.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.config.DeviceSettings.screenWidth;

public class GameScene extends CCLayer implements MeteorEngineDelegate, ShootEngineDelegate{
	
	private ScreenBackground background;
	private MeteorsEngine meteorsEngine;
	private CCLayer meteorLayer;
	private List meteorArray;
	
	private CCLayer playerLayer;
	private Player player;
	
	private CCLayer shootsLayer;
	private ArrayList shootsArray;
	
	private List playerArray;
	
	private CCLayer scoreLayer;
	private Score score;
	
	public GameScene() {
		this.background = new ScreenBackground(Assets.BACKGROUND);
		this.background.setPosition(
				screenResolution(CGPoint.ccp
						(screenWidth() / 2.0f,
								screenHeight() / 2.0f)));
		
		
		this.addChild(this.background);
		
		this.meteorLayer = CCLayer.node();
		this.addChild(this.meteorLayer);
		
		
		this.playerLayer = CCLayer.node();
		this.addChild(this.playerLayer);
		
		GameButtons gmButtons = GameButtons.gameButtons();
		gmButtons.setDelegate(this);
		
		this.addChild(gmButtons);
		
		this.shootsLayer = CCLayer.node();
		this.addChild(this.shootsLayer);
		
		this.setIsTouchEnabled(true);
		
		this.scoreLayer = CCLayer.node();
		this.addChild(scoreLayer);
		
		SoundEngine.sharedEngine().playSound(
				CCDirector.sharedDirector().getActivity(),
				R.raw.music, true);
		/*Deixar este elemento no final*/

		this.addGameObjects();
		startGame();
	}
	
	private void preloadCache(){
		SoundEngine.sharedEngine().preloadEffect(
				CCDirector.sharedDirector().getActivity(),
				R.raw.shoot);
		SoundEngine.sharedEngine().preloadEffect(
				CCDirector.sharedDirector().getActivity(),
				R.raw.bang);
		SoundEngine.sharedEngine().preloadEffect(
				CCDirector.sharedDirector().getActivity(),
				R.raw.over);
	}
	
	public void addGameObjects(){
		this.meteorArray = new ArrayList();
		this.meteorsEngine = new MeteorsEngine();
		
		this.player = new Player();
		this.playerLayer.addChild(this.player);
		this.playerArray = new ArrayList();
		this.playerArray.add(player);
		
		this.shootsArray = new ArrayList();
		this.player.setDelegate(this);
		
		this.score = new Score();
		this.scoreLayer.addChild(score);
	}
	
	
	public void checkHits(float dt){
		
		this.checkRadiusHistsOfArray(
				this.meteorArray, 
				this.shootsArray, this, "meteoroHit");
		
		this.checkRadiusHistsOfArray(meteorArray,
				playerArray, this, "playerHit");
	}
	
	private boolean checkRadiusHistsOfArray(
			List<? extends CCSprite> array1,
			List<? extends CCSprite> array2,
			GameScene gameScene, String hit){
		
		boolean result = false;
		
		for (int i = 0; i < array1.size(); i++) {
			CGRect rect1 = getBorders(array1.get(i));
			
			for (int j = 0; j < array2.size(); j++) {
				
				CGRect rect2 = getBorders(array2.get(j));
				
				if (CGRect.intersects(rect1, rect2)){
					System.out.println("Colision Detected: " + hit);
					result = true;
					Method method;
					try {
						method = GameScene.class.getMethod(hit,
								CCSprite.class, CCSprite.class);
						method.invoke(gameScene, array1.get(i), array2.get(j));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
		return result;
	}
	
	public void startGame(){
		player.catchAccelerometer();
	}
	
	public CGRect getBorders(CCSprite object){
		CGRect rect = object.getBoundingBox();
		CGPoint glPoint = rect.origin;
		CGRect GLrect = CGRect.make(
				glPoint.x, glPoint.y,
				rect.size.width, rect.size.height);
		
		return GLrect;
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
		this.schedule("checkHits");
		this.startEngines();
	}
	
	private void startEngines(){
		this.addChild(this.meteorsEngine);
		this.meteorsEngine.setDelegate(this);
	}
	
	public void moveLeft(){
		player.moveLeft();
	}
	
	public void moveRight(){
		player.moveRight();
	}
	
	
	public static CCScene createGame() {
		CCScene scene = CCScene.node();
		GameScene layer = new GameScene();
		scene.addChild(layer);
		return scene;
	}

	@Override
	public void createMeteror(Meteor meteor) {
		meteor.setDelegate(this);
		this.meteorLayer.addChild(meteor);
		meteor.start();
		this.meteorArray.add(meteor);
		
	}
	
	public boolean shoot(){
		player.shoot();
		return true;
	}

	public void meteoroHit(CCSprite meteor, CCSprite shoot){
		((Meteor) meteor).shooted();
		((Shoot) shoot).explode();
		this.removeMeteor(((Meteor) meteor));
		this.removeShoot(((Shoot) shoot));
		this.score.increse();
	}
	
	@Override
	public void createShoot(Shoot shoot) {
		this.shootsLayer.addChild(shoot);
		shoot.setDelegate(this);
		shoot.start();
		this.shootsArray.add(shoot);
	}

	public void playerHit(CCSprite meteor, CCSprite player){
		((Meteor) meteor).shooted();
		((Player) this.player).explode();
	}
	@Override
	public void removeMeteor(Meteor meteor) {
		this.meteorArray.remove(meteor);
	}

	@Override
	public void removeShoot(Shoot shoot) {
		this.shootsArray.remove(shoot);
	}
}
