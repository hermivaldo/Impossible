package br.com.casadocodigo.meteoros.scenes;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.config.Assets;
import static br.com.casadocodigo.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.config.DeviceSettings.screenWidth;

public class Shoot extends CCSprite{
	
	float positionX, positionY;
	private ShootEngineDelegate delegate;
	
	public Shoot(float positionX, float positionY) {
		super(Assets.SHOOT);
		this.positionX = positionX;
		this.positionY = positionY;
		
		setPosition(this.positionX,this.positionY);
		this.schedule("update");
	}
	
	public void update(float dt){
		positionY += 2;
		this.setPosition(screenResolution(
				CGPoint.ccp(positionX, positionY)));
	}
	
	public void start(){
		System.out.println("shoot moving");
	}
	
	public void explode(){
		this.unschedule("update");
		
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		
		CCCallFunc c1 = CCCallFunc.action(this, "removeMe");
		this.runAction(CCSequence.actions(s1, c1));
	}
	
	
	public void removeMe(){
		this.removeFromParentAndCleanup(true);
	}
	public void setDelegate(ShootEngineDelegate delegate){
		this.delegate = delegate;
	}

	
}
