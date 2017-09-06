package main;

public class Point {

	float x;
	float y;
	float z;
	
	public Point (float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float dotProduct (Point p) {
		return x*p.x+y*p.y+z*p.z;
	}
	
	public Point normalize () {
		float mag = magnitude();
		return new Point(x/mag,y/mag,z/mag);
	}
	
	public float magnitude () {
		return (float) Math.sqrt(dotProduct(this));
	}
	
	public Point add (Point p) {
		return new Point (p.x+x,p.y+y,p.z+z);
	}
	
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
}
