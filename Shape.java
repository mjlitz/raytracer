package main;

public interface Shape {

	
	public float[] intersectRay (Ray r);
	public Point calculateNormal (Point p);
	public float[] shade (Ray ray, Point point, Point normal, Point light);
	public float getRed();
	public float getBlue();
	public float getGreen();
	
	public float getkdr();
	public float getkdg();
	public float getkdb();
	
	public float getksr();
	public float getksg();
	public float getksb();
	public float getPow();
	
	public float getkar();
	public float getkag();
	public float getkab();
}
