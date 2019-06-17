import java.awt.Color;
import java.lang.Math;

/**
 * The Shape class is used to model general shapes.
 * 
 * @author Hu Qiyun
 *
 */
public class Shape {

	/**
	 * A Color object specifying the color of the shape.
	 */
	public Color	color;
	
	/**
	 * A boolean type variable showing whether a shape has been colored.
	 */
	public boolean 	filled;
	
	/**
	 * The orientation (in radians) of the shape in the screen coordinate system.
	 */
	public double 	theta;
	
	/**
	 * The x-coordinate of the center of the shape in the screen coordinate system.
	 */
	public double 	xc;
	
	/**
	 * The y-coordinate of the center of the shape in the screen coordinate system.
	 */
	public double 	yc;
	
	/**
	 * An array of the x-coordinates of the vertices (in counter clock-wise order) of the shape in its local coordinate system.
	 */
	public double[] xLocal;
	
	/**
	 * An array of the y-coordinates of the vertices (in counter clock-wise order) of the shape in its local coordinate system.
	 */
	public double[] yLocal;
	
	
	/**
	 * An empty constructor of the Shape class.
	 */
	Shape(){}
	
	/**
	 * This method sets the local coordinates of the vertices of a shape, this 
	 * is a dummy method and is supposed to be overridden in the subclasses.
	 * 
	 * @param d a double value specifying the size of the shape.
	 */
	public void setVertices(double d) {
		// nothing here.
	}
	
	/**
	 * This method translates the center of the shape
	 * along the x and y directions of the screen coordinate system.
	 * 
	 * @param dx distance of center moving along x-axis.
	 * @param dy distance of center moving along y-axis.
	 */
	public void translate(double dx, double dy) {
		this.xc = this.xc + dx;
		this.yc = this.yc + dy;
	}
	
	/**
	 * This method rotates the shape about its center.
	 * 
	 * @param dt the rotated angle in radians.
	 */
	public void rotate(double dt) {
		this.theta = this.theta + dt;
	}
	
	/**
	 * This method retrieves the x-coordinates of the vertices of the shape
	 * in screen coordinate system and rounded to nearest integers.
	 * 
	 * @return An array of the x-coordinates of the vertices in the screen coordinate system.
	 */
	public int[] getX() {
		int[] xScreen = new int[xLocal.length];
		for (int i=0; i<xLocal.length; i++) {
			double toScreen = xLocal[i]*Math.cos(theta) - yLocal[i]*Math.sin(theta) + xc;
			xScreen[i] = (int)Math.round(toScreen);
		}
		return xScreen;
	}
	
	 /**
	  * This method retrieves the y-coordinates of the vertices of the shape 
	  * in screen coordinate system and rounded to nearest integers.
	  *
	  * @return An array of the y-coordinates of the vertices in the screen coordinate system.
      */
	public int[] getY() {
		int[] yScreen = new int[yLocal.length];
		for (int i=0; i<yLocal.length; i++) {
			double toScreen = xLocal[i]*Math.sin(theta) + yLocal[i]*Math.cos(theta) + yc;
			yScreen[i] = (int)Math.round(toScreen);
		}
		return yScreen;
	}

}
