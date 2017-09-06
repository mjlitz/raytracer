package main;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.JFrame;

public class Raytracer extends JFrame implements GLEventListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static String name = "Matt";
	
	final int width = 512;
	final int height = 512;
	final float left = -0.1f;
	final float r = 0.1f;
	final float b = -0.1f;
	final float t = 0.1f;
	final static float d = 0.1f;
	final static float d2 = 0.01f;//d^2
	
	static int surfaces = 4;
	static Shape[] surfaceList = new Shape[surfaces];
	
	//ratio (r-l)/n to speed up ray calculations
	final float du = (r-left)/width;
	final float dv = (t-b)/height;
	
	Buffer scene;
	
	public static void main (String [] args) {
		@SuppressWarnings("unused")
		Raytracer raytracer = new Raytracer();
	}	
	
	public Raytracer() {
		super(name + "'s Raytracer");
		this.scene = renderScene();
		
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities caps = new GLCapabilities(profile);
		
		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		
		this.setName(name + "'s Raytracer");
		this.getContentPane().add(canvas);
		
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		canvas.requestFocusInWindow();
	}
	
	public Buffer renderScene() {
		float[] data = new float[width * height * 3];
		surfaceList[0] = new Sphere(-4,0,-7,1,1.0f,0.0f,0.0f,
				0.2f,0.0f,0.0f,1.0f,0.0f,0.0f,0.0f,0.0f,0.0f, 0);
		surfaceList[1] = new Sphere(0,0,-7,2,0.0f,1.0f,0.0f,
				0.0f,0.2f,0.0f,0.0f,0.5f,0.0f,0.5f,0.5f,0.5f, 32);
		surfaceList[2] = new Sphere(4,0,-7,1,0.0f,0.0f,1.0f,
				0.0f,0.0f,0.2f,0.0f,0.0f,1.0f,0.0f,0.0f,0.0f, 0);
		surfaceList[3] = new Plane(0,1,0,-2,1.0f,1.0f,1.0f,
				0.2f,0.2f,0.2f,1.0f,1.0f,1.0f,0.0f,0.0f,0.0f, 0);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int i = (y * height) + x;
				i *= 3;
				float red_avg = 0.0f, green_avg = 0.0f, blue_avg = 0.0f;
				Random r = new Random();
				for (int k = 0 ; k < 64 ; k++) {
					Float f1 = r.nextFloat();
					Float f2 = r.nextFloat();
					float u = left + du*((float)x + f1);
					float v = b + dv*((float)y + f2);
					
					Point e = new Point(0,0,0);
					Point dir = new Point(u,v,-d);
					Ray ray = new Ray(e,dir);
					
					float[] hits = new float[surfaces];
					hits = ray.groupIntersect();
					Point light = new Point(-4,4,-3);
					float final_red = 0.0f, final_green = 0.0f, final_blue = 0.0f;
					
					for (int j = 0 ; j < surfaces ; j++) {
						if (hits[0] <= 2*j+2 && hits[0] > 2*j) {
							Shape currentShape = surfaceList[j];
							float base_red = currentShape.getRed();
							float base_green = currentShape.getGreen();
							float base_blue = currentShape.getBlue();
							float t = hits[1];
							Point p = new Point(t*u,t*v,-t*d);//intersection point
							//n and l are vectors
							//Lambertian Shading
							Point n = currentShape.calculateNormal(p);
							n = n.normalize();
							Point l = new Point (light.x-p.x,light.y-p.y,light.z-p.z);
							l = l.normalize();
							float ndotl = n.dotProduct(l);
							float ldr = currentShape.getkdr()*Math.max(0, ndotl);
							float ldg = currentShape.getkdg()*Math.max(0, ndotl);
							float ldb = currentShape.getkdb()*Math.max(0, ndotl);
							
							//Specular Shading
							Point vray = new Point(-u,-v,d);
							vray = vray.normalize();
							Point h = vray.add(l);
							h = h.normalize();
							float nhp = (float) Math.pow(n.dotProduct(h),currentShape.getPow());
							float lsr = currentShape.getksr()*Math.max(0, nhp);
							float lsg = currentShape.getksg()*Math.max(0, nhp);
							float lsb = currentShape.getksb()*Math.max(0, nhp);
							
							float lar = currentShape.getkar();
							float lag = currentShape.getkag();
							float lab = currentShape.getkab();
							
							
							//Shadows
							//Bring off the shape a little
							//Point c = currentShape.getCenter();
							//n+(r-n)/1000
							//u = left + du*(light.x + 0.5f);
							//v = b + dv*(light.y + 0.5f);
							//float rayx = u-n.x;
							//float rayy = v-n.y;
							//float rayz = light.z-n.z;
							/*Ray shadowRay = new Ray(p,light);
							float[] shadowhits = shadowRay.groupIntersect();*/
							//no intersections
							/*if (shadowhits[0] == 0) {*/
								//gamma correction
								final_red   = base_red   * (ldr+lsr+lar);
								final_green = base_green * (ldg+lsg+lag);
								final_blue  = base_blue  * (ldb+lsb+lab);
							//}
							//intersection
							/*else {
								//only have light from ambience
								final_red   = base_red   * lar;
								final_green = base_green * lag;
								final_blue  = base_blue  * lab;
							}*/
							final_red = (float) Math.pow(final_red,1/2.2);
							final_green = (float) Math.pow(final_green,1/2.2);
							final_blue = (float) Math.pow(final_blue,1/2.2);
							
						}
					}
					if (hits[0] == 0) {
						final_red = 0.0f; // red
						final_green = 0.0f; // green
						final_blue = 0.0f; // blue
					}
					
					red_avg+= final_red;
					green_avg += final_green;
					blue_avg += final_blue;
					
				}
				red_avg /= 64; green_avg /= 64; blue_avg /= 64;
				data[i + 0] = red_avg;
				data[i + 1] = green_avg;
				data[i + 2] = blue_avg;
			}
		}
			
			return FloatBuffer.wrap(data);
	}
		
	public void display(GLAutoDrawable drawable) {
			GL2 gl = drawable.getGL().getGL2();
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

			gl.glDrawPixels(width, height, GL2.GL_RGB, GL2.GL_FLOAT, this.scene);
			
			gl.glFlush();
	}

	public void dispose(GLAutoDrawable arg0) {
		
	}

	@Override
	public void init(GLAutoDrawable arg0) {
			
	}

		@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
			
	}
}
