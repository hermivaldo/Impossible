package br.com.casadocodigo.bis;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import com.learning.impossible.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import br.com.casadocodigo.config.DeviceSettings;
import br.com.casadocodigo.meteoros.scenes.TitleScreen;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CCGLSurfaceView glSurfaceView = new CCGLSurfaceView(this);
		glSurfaceView.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
		setContentView(glSurfaceView);
		CCDirector.sharedDirector().attachInView(glSurfaceView);
		
		CCDirector.sharedDirector().setScreenSize(320, 480);
		
		CCScene scene = new TitleScreen().scene();
		CCDirector.sharedDirector().runWithScene(scene);
		
		configSensorManager();
	}
	
	private void configSensorManager(){
		SensorManager sensorManager = 
				(SensorManager) getSystemService(Context.SENSOR_SERVICE);
		DeviceSettings.setSensorManager(sensorManager);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity()
				, R.raw.over);
		SoundEngine.sharedEngine().pauseSound();
	}

	
}
