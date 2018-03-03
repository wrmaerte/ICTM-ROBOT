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
import lejos.utility.Delay;

public class gripper{
	private int[] coordinates;
	private int rotation;
	final int angulardistance=0; //angular distance between two locations in degrees of rotation of motor R UNKNOWN SO FAR
	final int verticaldistance=0;//vertical distance between two locations in degrees of rotation of motor T UNKNOWN SO FAR
	final private EV3LargeRegulatedMotor T= new EV3LargeRegulatedMotor(MotorPort.A);//Translation
	final private EV3LargeRegulatedMotor R=new EV3LargeRegulatedMotor(MotorPort.B);//Rotation
	final private EV3MediumRegulatedMotor G= new EV3MediumRegulatedMotor(MotorPort.C);//Gripping
	final private EV3ColorSensor C= new EV3ColorSensor(SensorPort.S1);// Color sensor
	
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
	public void goUp(int step) {
		T.rotate(step*verticaldistance);
	}
	public void goDown(int step) {
		T.rotate(-step*verticaldistance);
	}
	public void rotateRight(int step) {
		R.rotate(step*angulardistance);
	}
	public void rotateLeft(int step) {
		R.rotate(-step*angulardistance);
	}
	public void navigateTo(int[]newCoordinates) {
		
	}
}