package main;

public class Ray {

	float u;
	float v;
	float negd;
	float tmin;
	float tmax;
	Point p;
	Point d;
	int surfaces;
	
	public Ray (Point p, Point d) {
		this.u = d.x;
		this.v = d.y;
		this.negd = d.z;
		this.p = p;
		this.d = d;
		this.surfaces = Raytracer.surfaces;
	}
	
	public float[] groupIntersect () {
		float[] hits = new float[surfaces];
		for (int i = 0 ; i < surfaces ; i++) {
			float[] newhits = new float[3];
			newhits = Raytracer.surfaceList[i].intersectRay(this);
			//if there is a new intersection
			if (newhits[0] != 0) {
				hits = newhits;
				hits[0] = hits[0] + 2*i;
				return hits;
			}
		}
		hits[0] = 0;
		return hits;
	}
	
	public Point evalutePoint (float t) {
		return new Point(t*u,t*v,t*negd);
	}
	
}

