package br.com.casadocodigo.meteoros.scenes;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.com.casadocodigo.config.Assets;
/*
 * Import das configurações da tela do dispositivo
 */
import static br.com.casadocodigo.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.config.DeviceSettings.screenWidth;

public class TitleScreen extends CCLayer{

	private ScreenBackground background;
	
	
	public TitleScreen() {
		this.background = new ScreenBackground(Assets.BACKGROUND);
		
		this.background.setPosition(
				screenResolution(
						CGPoint.ccp(screenWidth() / 2.0f, 
								screenHeight() / 2.0f)));
		
		this.addChild(this.background);
		
		// Adicionado depois do bg pois existe uma ordem nas camadas.
		CCSprite title = CCSprite.sprite(Assets.LOGO);
		title.setPosition(screenResolution(
				CGPoint.ccp(screenWidth() / 2,
						screenHeight() - 130)));
		
		this.addChild(title);
		
		MenuButtons menuLayer = new MenuButtons();
		this.addChild(menuLayer);
	}
	
	/*
	 * Retorna a cena do jogo
	 */
	public CCScene scene(){
		CCScene scene = CCScene.node();
		scene.addChild(this);
		return scene;
	}
}
