package br.com.casadocodigo.meteoros.scenes;

import static br.com.casadocodigo.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.config.DeviceSettings.screenWidth;

import java.util.Random;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class Meteor extends CCSprite{
	private float x,y;
	private MeteorEngineDelegate delegate;
	
	public Meteor(String image) {
		super(image);
		x = new Random().nextInt(Math.round(screenWidth()));
		y = screenHeight();
	}

	public void setDelegate(MeteorEngineDelegate delegate){
		this.delegate = delegate;
	}
	
	public void start() {
		this.schedule("update");
	}
	
	public void shooted(){
		this.delegate.removeMeteor(this);
		this.unschedule("update");
		float df = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(df, 0.5f);
		CCFadeOut a2 = CCFadeOut.action(df);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		CCCallFunc c1 = CCCallFunc.action(this, "removeMe");
		this.runAction(CCSequence.actions(s1,c1));
	}
	
	public void removeMe(){
		this.removeFromParentAndCleanup(true);
	}
	
	public void update(float dt){
		y -= 1;
		this.setPosition(screenResolution(CGPoint.ccp(x, y)));
	}
}
