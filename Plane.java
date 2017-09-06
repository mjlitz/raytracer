package main;

public class Plane implements Shape {

	private int A;
	private int B;
	private int C;
	private int D;
	
	private float red;
	private float green;
	private float blue;
	
	private float kdr;
	private float kdg;
	private float kdb;
	
	private float ksr;
	private float ksg;
	private float ksb;
	int specpow;
	
	float kar;
	float kag;
	float kab;
	
	final float d = Raytracer.d;
	
	//Plane in the form Ax + By + Cz = D
	public Plane (int A, int B, int C, int D,
			float red, float green, float blue,
			float kar, float kag,   float kab,
			float kdr, float kdg,   float kdb,
			float ksr, float ksg,   float ksb, int pow
			) {
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
		
		this.red = red;
		this.green = green;
		this.blue = blue;
		
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		
		this.ksr = ksr;
		this.ksg = ksg;
		this.ksb = ksb;
		specpow = pow;
		
		this.kar = kar;
		this.kag = kag;
		this.kab = kab;
	}

	public float[] intersectRay(Ray r) {
		float dotPR = A*r.u+B*r.v+C*r.negd;
		float[] hits = new float[3];
		if (dotPR == 0) {
			hits[0] = 0;
			return hits;
		}
		float t = D / (dotPR);
		if (t > 0) {
			hits[0] = 1.0f;
			hits[1] = t;
		} else {
			hits[0] = 0;
		}
		return hits;
	}
	
	public Point calculateNormal (Point p) {
		return new Point(A, B, C);
	}

	public float getRed()   {return red;}
	public float getGreen() {return green;}
	public float getBlue()  {return blue;}
	
	public float getkdr()   {return kdr;}
	public float getkdg()   {return kdg;}
	public float getkdb()   {return kdb;}
	
	public float getksr()   {return ksr;}
	public float getksg()   {return ksg;}
	public float getksb()   {return ksb;}
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
