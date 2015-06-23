package br.com.casadocodigo.meteoros.scenes;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.config.Assets;
import br.com.casadocodigo.config.ButtonDelegate;
import static br.com.casadocodigo.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.config.DeviceSettings.screenWidth;


public class MenuButtons extends CCLayer implements ButtonDelegate{

	private Button playButton;
	private Button hightscoreButton;
	private Button helpButton;
	private Button soundButton;
	
	public MenuButtons() {
		this.setIsTouchEnabled(true);
		
		this.playButton = new Button(Assets.PLAY);
		this.hightscoreButton = new Button(Assets.HIGHSCORE);
		this.helpButton = new Button(Assets.HELP);
		this.soundButton = new Button(Assets.SOUND);
		
		setButtonsPosition();
		
		this.playButton.setDelegate(this);
		this.hightscoreButton.setDelegate(this);
		this.helpButton.setDelegate(this);
		this.soundButton.setDelegate(this);
		
		
		
		addChild(playButton);
		addChild(hightscoreButton);
		addChild(helpButton);
		addChild(soundButton);
		
		
	}
	
	private void setButtonsPosition(){
		
		playButton.setPosition(
				screenResolution(
						CGPoint.ccp(
						screenWidth() / 2,
						screenHeight() - 250)));
		hightscoreButton.setPosition(
				screenResolution(
						CGPoint.ccp(
						screenWidth() / 2,
						screenHeight() - 300)));
		helpButton.setPosition(
				screenResolution(
						CGPoint.ccp(
						screenWidth() / 2,
						screenHeight() - 350)));
		soundButton.setPosition(
				screenResolution(
						CGPoint.ccp(
						screenWidth() / 2 - 100,
						screenHeight() - 420)));
	}

	@Override
	public void buttonClicked(Button sender) {
		if(sender.equals(this.playButton)){
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(1.0f,
							GameScene.createGame()));
		}
		
	}
	
}
