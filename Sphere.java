package main;

public class Sphere implements Shape{

	private Point c;
	private int r;
	final float d = Raytracer.d;
	
	private float red;
	private float green;
	private float blue;
	
	float kdr;
	float kdg;
	float kdb;
	
	float ksr;
	float ksg;
	float ksb;
	int specpow;
	
	float kar;
	float kag;
	float kab;
	
	public Sphere (int x, int y, int z, int r,
			float red, float green, float blue,
			float kar, float kag,   float kab,
			float kdr, float kdg,   float kdb,
			float ksr, float ksg,   float ksb, int pow
			){
		this.c = new Point(x,y,z);
		this.r = r;
		this.red = red;
		this.green = green;
		this.blue = blue;
		
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		
		this.ksr = ksr;
		this.ksg = ksg;
		this.ksb = ksb;
		this.specpow = pow;
		
		this.kar = kar;
		this.kag = kag;
		this.kab = kab;
	}
	
	public float[] intersectRay (Ray ray) {
		//[num_intersec, t1, t2]
		float u = ray.u;
		float v = ray.v;
		float negd = ray.negd;
		float[] hits = new float[3];
		float cd = u*c.x + v*c.y + negd*c.z;
		float dd = u*u + v*v + negd*negd;
		float discr3 = c.x*c.x + c.y*c.y + c.z*c.z - r;
		float discr = (float) Math.pow(cd,2) - dd*discr3;
		float rad = (float) Math.sqrt(discr);
		
		if (discr > 0) {
			hits[0] = 2;
			hits[1] = (cd - rad)/dd;
			hits[2] = (cd + rad)/dd;
		} else if (discr == 0) {
			hits[0] = 1;
			hits[1] = cd/dd;
		} else {
			hits[0] = 0;
		}
		return hits;
		//System.out.println("Ray at (" + u + ", " + v + ", " + d + ")" );
	}

	
	//Given intersection point, calculate normal
	public Point calculateNormal (Point p) {
		
		return new Point (p.x-c.x,p.y-c.y,p.z-c.z);
	}
	
	public int getRadius() {return r;}
	public Point getCenter() {return c;}
	
	public float getRed()  {return red;}
	public float getGreen(){return green;}
	public float getBlue() {return blue;}
	
	public float getkdr()  {return kdr;}
	public float getkdg()  {return kdg;}
	public float getkdb()  {return kdb;}
	
	public float getksr()  {return ksr;}
	public float getksg()  {return ksg;}
	public float getksb()  {return ksb;}
	public float getPow()  {return specpow;}
	
	public float getkar()  {return kar;}
	public float getkag()  {return kag;}
	public float getkab()  {return kab;}

	@Override
	public float[] shade(Ray ray, Point point, Point normal, Point light) {
		// TODO Auto-generated method stub
		return null;
	}
}
