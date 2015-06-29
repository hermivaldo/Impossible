package br.com.casadocodigo.config;

import static br.com.casadocodigo.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.meteoros.scenes.Button;
import br.com.casadocodigo.meteoros.scenes.GameScene;

public class GameButtons extends CCLayer implements ButtonDelegate{

	private Button leftControl;
	private Button rightControl;
	private Button shootButton;
	private GameScene delegate;
	
	public GameButtons() {
		this.setIsTouchEnabled(true);
		
		this.leftControl 	= new Button(Assets.LEFTCONTROL);
		this.rightControl 	= new Button(Assets.RIGHTCONTROL);
		this.shootButton	= new Button(Assets.SHOOTBUTTON);
		
		this.leftControl.setDelegate(this);
		this.rightControl.setDelegate(this);
		this.shootButton.setDelegate(this);
		
		setButtonsPosition();
		
//		addChild(leftControl);
//		addChild(rightControl);
		addChild(shootButton);
		
	}
	
	private void setButtonsPosition() {
		leftControl.setPosition(screenResolution(CGPoint.ccp(40, 40)));
		
		rightControl.setPosition(screenResolution(CGPoint.ccp(100, 40)));
		
		shootButton.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() -  40, 40)));
	}

	public static GameButtons gameButtons() {
		return new GameButtons();
	}
	
	public void setDelegate(GameScene gameScene){
		this.delegate = gameScene;
	}
	
	
	@Override
	public void buttonClicked(Button sender) {
		if(sender.equals(this.leftControl)){
			this.delegate.moveLeft();
		}
		if(sender.equals(this.rightControl)){
			this.delegate.moveRight();
		}
		if(sender.equals(this.shootButton)){
			this.delegate.shoot();
		}
		
	}
}
