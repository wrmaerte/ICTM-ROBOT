package project;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.TachoMotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class Gripper{
	private int[] coordinates;// theta,z
	private int rotation;
	private int [][] map;
	private int height;
	final int angulardistance=0; //angular distance between two locations in degrees of rotation of motor R UNKNOWN SO FAR
	final int verticaldistance=0;//vertical distance between two locations in degrees of rotation of motor T UNKNOWN SO FAR
	final private EV3LargeRegulatedMotor T= new EV3LargeRegulatedMotor(MotorPort.A);//Translation
	final private EV3LargeRegulatedMotor R=new EV3LargeRegulatedMotor(MotorPort.B);//Rotation
	final private EV3MediumRegulatedMotor G= new EV3MediumRegulatedMotor(MotorPort.C);//Gripping
	final private EV3ColorSensor C= new EV3ColorSensor(SensorPort.S1);// Color sensor
	final private SensorMode red = C.getRedMode();
	final private SensorMode rgb = C.getRGBMode();
	private float[] samplered = new float[red.sampleSize()];
	private float[] samplergb = new float[rgb.sampleSize()];
	
	public Gripper(int[] speeds, int height) {
		this.coordinates= new int[] {0,0};
		this.rotation = 0;
		this.map=new int[4][height];
		this.height=height;
		T.setSpeed(speeds[0]);
		R.setSpeed(speeds[1]);
		G.setSpeed(speeds[2]);
		
		
	}
	public int[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(int[] coordinates) {
		this.coordinates = coordinates;
	}
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public void calibrate() {
		//search bacon 
		this.coordinates[0]=0;
		
	}
	// following are methods to move gripper in discrete steps.
	private void translate(int step) {
		T.rotate(step*verticaldistance);
		this.coordinates[1]+=step;
	}
	private void rotate(int step) {
		R.rotate(step*angulardistance);
		this.coordinates[0]+=step;
	}
	
	// method to move gripper to specific location
	private void navigateTo(int[]newCoordinates) {
		int t=newCoordinates[1]-this.coordinates[1];
		int r=-(newCoordinates[0]-this.coordinates[0])%2;
		this.rotate(r);
		this.translate(t);
		if (newCoordinates[0]==0){
			this.calibrate();
		}
		if (newCoordinates[1]==0) {
			try {
				this.goBottom();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void scanMap() {
		this.calibrate();
		try {
			this.goBottom();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < 3; i++) {
			this.goTop();
			this.rotate(1);
			for(int j =0; j< height; j++) {
				this.translate(-1);
				if(this.checkPresence()) {
					map[coordinates[0]][coordinates[1]]=1;
				}
			}
			
		}
		
		
	}
	// check if block is present on current location
	private boolean checkPresence() {
		red.fetchSample(samplered, 0);
		return(samplered[0]>0.2);	
	}
	// Gripper goes to top and stops
	private void goTop() {
		this.navigateTo(new int[]{(this.height)+1,this.coordinates[0]});
	}
	// Gripper goes to bottom and sets z coordinate to 0
	public void goBottom() throws InterruptedException{
		this.T.backward();
		while(T.isMoving()) {
			Thread.sleep(200);
		}
		T.stop();
		this.coordinates[1]=0;
	}
	
}