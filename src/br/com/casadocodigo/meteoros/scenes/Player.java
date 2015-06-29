package br.com.casadocodigo.meteoros.scenes;

import static br.com.casadocodigo.config.DeviceSettings.screenWidth;

import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.config.Accelerometer;
import br.com.casadocodigo.config.AccelerometerDelegate;
import br.com.casadocodigo.config.Assets;

import com.learning.impossible.R;


public class Player extends CCSprite implements AccelerometerDelegate{

	private ShootEngineDelegate delegate;
	private static final double NOISE = 1;
	
	float positionX = screenWidth() / 2;
	float positionY = 150;
	
	float currentAccelX;
	float currentAccelY;
	
	private Accelerometer accelerometer;
	
	public Player(){
		super(Assets.NAVE);
		setPosition(positionX, positionY);
		this.schedule("update");
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
	
	public void update(float dt){
		if (this.currentAccelX < -NOISE){
			this.positionX++;
		}
		if (this.currentAccelX > NOISE){
			this.positionX--;
		}
		if (this.currentAccelY < -NOISE){
			this.positionY++;
		}
		if (this.currentAccelY > NOISE){
			this.positionY--;
		}
		
		this.setPosition(CGPoint.ccp(positionX, positionY));
	}
	
	public void explode(){
		this.unschedule("update");
		
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		
		this.runAction(CCSequence.actions(s1));
		SoundEngine.sharedEngine().playEffect
		(CCDirector.sharedDirector().getActivity(), R.raw.over);
		
	}
	
	public void catchAccelerometer(){
		Accelerometer.sharedAc().catchAccelerometer();
		this.accelerometer = Accelerometer.sharedAc();
		this.accelerometer.setDelegate(this);
	}
	
	public void moveRight(){
		if (positionX < screenWidth() - 30){
			positionX +=10;
		}
		setPosition(positionX, positionY);
	}

	@Override
	public void accelerometerDidAccelerate(float x, float y) {
		this.currentAccelX = x;
		this.currentAccelY = y;
	}
}
