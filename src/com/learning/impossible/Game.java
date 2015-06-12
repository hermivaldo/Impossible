package com.learning.impossible;

import android.app.Activity;
import android.os.Bundle;
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
		view.moveDown(10);
		return true;
	}
}
