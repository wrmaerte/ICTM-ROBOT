package project;

public class Map {
// objects of this class are maps containing all information about the environment collected by the robot
	private int [][][] map;
	
	//method to fill map locations
	public void setLocation(int[]location, int value) {
		this.map[location[0]][location[1]][location[2]]=value;
	}
	//method to read map location
	public int readLocation(int[]location){
		return this.map[location[0]][location[1]][location[2]];
	}
	
	//constructor
	public Map(int[] size) {
		super();
		this.map =new int[size[0]][size[1]][];
	}
	
}
