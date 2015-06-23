package br.com.casadocodigo.meteoros.scenes;

import java.util.Random;

import org.cocos2d.layers.CCLayer;

import br.com.casadocodigo.config.Assets;

public class MeteorsEngine extends CCLayer{

	private MeteorEngineDelegate delegate;
	
	public MeteorsEngine() {
		this.schedule("meteorsEngine", 1.0f / 10f);
	}
	
	public void meteorsEngine(float dt){
		if (new Random().nextInt(30) == 0){
			System.out.println("Estou aqui");
			this.getDelegate().createMeteror
			(new Meteor(Assets.METETOR));
		}
	}
	
	public void setDelegate(MeteorEngineDelegate delegate){
		this.delegate = delegate;
	}
	
	public MeteorEngineDelegate getDelegate(){
		return delegate;
	}
}
