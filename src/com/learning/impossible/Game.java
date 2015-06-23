package com.learning.impossible;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Game extends Activity implements OnTouchListener {
	Impossible view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.view = new Impossible(this);
		
		this.view.setOnTouchListener(this);
		
		setContentView(view);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		view.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// restart
		
		if (event.getX() < 100 && event.getY()>100
				&& event.getY() < 250){
			view.init();
		}
		
		// exit
		if (event.getX() < 100 && event.getY()>200
				&& event.getY() < 300){
			System.exit(0);
		}
		
		view.moveDown(10);
		view.addScore(100);
		return true;
	}
}
