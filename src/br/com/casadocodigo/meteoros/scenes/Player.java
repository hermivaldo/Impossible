package br.com.casadocodigo.meteoros.scenes;

import static br.com.casadocodigo.config.DeviceSettings.screenWidth;

import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;

import br.com.casadocodigo.config.Assets;


public class Player extends CCSprite{

	private ShootEngineDelegate delegate;
	
	float positionX = screenWidth() / 2;
	float positionY = 150;
	
	public Player(){
		super(Assets.NAVE);
		setPosition(positionX, positionY);
	}
	
	public void shoot(){
		delegate.createShoot(new Shoot(positionX, positionY));
	}
	
	public void setDelegate(ShootEngineDelegate delegate){
		this.delegate = delegate;
	}
	
	public void moveLeft(){
		if(positionX > 30){
			positionX -=10;
		}
		setPosition(positionX, positionY);
	}
	
	public void explode(){
		this.unschedule("update");
		
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		
		this.runAction(CCSequence.actions(s1));
	}
	
	public void moveRight(){
		if (positionX < screenWidth() - 30){
			positionX +=10;
		}
		setPosition(positionX, positionY);
	}
}
