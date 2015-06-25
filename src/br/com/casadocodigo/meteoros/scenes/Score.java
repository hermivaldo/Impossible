package br.com.casadocodigo.meteoros.scenes;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import static br.com.casadocodigo.config.DeviceSettings.screenResolution;
import static br.com.casadocodigo.config.DeviceSettings.screenHeight;
import static br.com.casadocodigo.config.DeviceSettings.screenWidth;


public class Score extends CCLayer {

	private int score;
	private CCBitmapFontAtlas text;
	
	public Score() {
		this.score = 0;
		this.text = CCBitmapFontAtlas.bitmapFontAtlas(String.valueOf(score),
				"UniSansSemiBold_Numbers_240.fnt");
		
		this.text.setScale((float) 240 / 240);
		this.setPosition(screenWidth() - 50, screenHeight() - 50);
		this.addChild(text);
	}
	
	public void increse(){
		score++;
		this.text.setString(String.valueOf(this.score));
	}
}
