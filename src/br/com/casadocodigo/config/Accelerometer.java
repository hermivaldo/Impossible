package br.com.casadocodigo.config;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer implements SensorEventListener{

	private float currentAccelerationX;
	private float currentAccelerationY;
	private SensorManager manager;
	
	private float calibratedAccelerationX;
	private float calibratedAccelerationY;
	
	private int calibrated;
	
	public Accelerometer() {
		this.catchAccelerometer();
	}
	
	public void catchAccelerometer() {
		manager = DeviceSettings.getSensorManager();
		manager.registerListener(this,
				manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
	}

	private AccelerometerDelegate delegate;
	
	
	static Accelerometer sharedAccelerometer = null;
	
	public static Accelerometer sharedAc(){
		if(sharedAccelerometer == null){
			sharedAccelerometer = new Accelerometer();
		}
		return sharedAccelerometer;
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (calibrated < 100){
			this.calibratedAccelerationX += event.values[0];
			this.calibratedAccelerationY += event.values[1];
			
			calibrated++;
			
			if(calibrated == 100){
				this.calibratedAccelerationX /= 100;
				this.calibratedAccelerationY /= 100;
			}
			return;
			
		}
		this.currentAccelerationX = event.values[0] - calibratedAccelerationX;
		this.currentAccelerationY = event.values[1] - calibratedAccelerationY;
		if (this.delegate != null){
			this.delegate.accelerometerDidAccelerate(currentAccelerationX,
					currentAccelerationY);
		}
		
	}
	
	public AccelerometerDelegate getDelegate() {
		return delegate;
	}
	
	public void setDelegate(AccelerometerDelegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
