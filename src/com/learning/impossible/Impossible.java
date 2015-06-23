package com.learning.impossible;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	
	// dolis�o
	private double distance;
	private boolean gameover;
	
	
	// score
	private int score;
	
	public Impossible(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
	}

	/*
	 * Thread Principal onde acontece todo o processo
	 * de desenho e movimenta��o dos objetos na tela
	 * do dispositivo
	 * 
	 */
	@Override
	public void run() {
		while (running){
			
			if(!holder.getSurface().isValid())
				continue;
			
			Canvas canvas = holder.lockCanvas();
			canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ceu),0,0,null);
			
			drawPlayer(canvas);
			drawEnemy(canvas);
			
			checkCollision(canvas);
			drawButtons(canvas);
			if (gameover){
				stopGame(canvas);
				/* Destravamento do canvas para poder exibir a mensagem
				 * de fim de jogo na tela. */
				
				holder.unlockCanvasAndPost(canvas);
				break;
			}
			
			drawScore(canvas);
			
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	// Desenhar o player na tela do dispositivo
	private void drawPlayer(Canvas canvas){
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nave), playerX - 50, playerY - 50,null);
		
	}
	

	public void resume(){
		this.running = true;
		this.renderThread = new Thread(this);
		this.renderThread.start();
	}

	// m�todo para mover o objeto para baixo
	public void moveDown(int pixels){
		playerY += pixels;
	}
	
	// Desenhar o enimigo na tela
	private void drawEnemy(Canvas canvas){
		paint.setColor(Color.GRAY);
		enemyRadius++; // faz com que o enimigo aumente seu tamanho gradativamente
		canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
	}
	
	
	// Adicionar score
	public void addScore(int points){
		score += points;
	}
	private void checkCollision(Canvas canvas){
		
		distance = Math.pow(playerY - enemyY, 2)
				+ Math.pow(playerX - enemyY, 2);
		
		distance = Math.sqrt(distance);
	
		if (distance <= playerRadius + enemyRadius){
			gameover = true;
		}
	}
	
	/*
	 * Bot�es de sair e reiniciar o jogo
	 */
	
	private void drawButtons(Canvas canvas){
		// Restart
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("Restart", 50, 200, paint);
		
		// Exit
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("Exit", 50, 300, paint);
	}
	
	/*
	 * Reiniciar jogo
	 */
	public void init(){
		enemyX = enemyY = enemyRadius = 0;
		playerX = playerY = 30;
		playerRadius = 10;
		gameover = false;
		// Zerar o placar do jogo
		score = 0;
		// Reiniciar o jogo caso o usu�rio queira.
		this.renderThread.run();
	}
	
	
	/*
	 * Exibi��o do score na tela do dispositivo
	 */
	
	private void drawScore(Canvas canvas){
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		canvas.drawText(String.valueOf(score), 50, 200, paint);
	}
	
	/*
	 * M�todo para exibir a mensagem de fim do jogo
	 */
	private void stopGame(Canvas canvas){
		paint.setStyle(Style.FILL);
		paint.setColor(Color.LTGRAY);
		paint.setTextSize(100);
		canvas.drawText("GAME OVER!", 50, 150, paint);
	}
}
