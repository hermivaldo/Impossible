package br.com.casadocodigo.bis;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.app.Activity;
import android.os.Bundle;
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
		
	}

	
}
