package com.learning.impossible;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Impossible extends SurfaceView implements Runnable{

	boolean running = false;
	Thread renderThread = null;
	SurfaceHolder holder;
	Paint paint;
	
	private int playerY = 30 , playerX = 30, playerRadius = 10;
	
	
	// inimigo
	private float enemyRadius = 10, enemyX, enemyY;
	
	// dolisão
	private double distance;
	private boolean gameover;
	
	public Impossible(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
	}

	@Override
	public void run() {
		while (running){
			
			if(!holder.getSurface().isValid())
				continue;
			
			Canvas canvas = holder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			
			drawPlayer(canvas);
			drawEnemy(canvas);
			
			checkCollision(canvas);
			
			if (gameover){
				stopGame(canvas);
				break;
			}
			
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	private void drawPlayer(Canvas canvas){
		paint.setColor(Color.GREEN);
		canvas.drawCircle(playerX, playerY, 50, paint);
	}
	
	public void resume(){
		this.running = true;
		this.renderThread = new Thread(this);
		this.renderThread.start();
	}

	public void moveDown(int pixels){
		playerY += pixels;
	}
	
	private void drawEnemy(Canvas canvas){
		paint.setColor(Color.GRAY);
		enemyRadius++;
		canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
	}
	
	private void checkCollision(Canvas canvas){
		
		distance = Math.pow(playerY - enemyY, 2)
				+ Math.pow(playerX - enemyY, 2);
		
		distance = Math.sqrt(distance);
	
		if (distance <= playerRadius + enemyRadius){
			gameover = true;
		}
	}
	
	private void stopGame(Canvas canvas){
		paint.setStyle(Style.FILL);
		paint.setColor(Color.LTGRAY);
		paint.setTextSize(100);
		canvas.drawText("GAME OVER!", 50, 150, paint);
	}
}
