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
	private Color color;

	/**
	 * A boolean type variable showing whether a shape has been colored.
	 */
	private boolean filled;

	/**
	 * The orientation (in radians) of the shape in the screen coordinate system.
	 */
	private double theta;

	/**
	 * The x-coordinate of the center of the shape in the screen coordinate system.
	 */
	private double xc;

	/**
	 * The y-coordinate of the center of the shape in the screen coordinate system.
	 */
	private double yc;

	/**
	 * An array of the x-coordinates of the vertices (in counter clock-wise order) of the shape in its local coordinate system.
	 */
	private double[] xLocal;

	/**
	 * An array of the y-coordinates of the vertices (in counter clock-wise order) of the shape in its local coordinate system.
	 */
	private double[] yLocal;


	/**
	 * Getter of Color, returns the value of color.
	 *
	 * @return The color of the shape, the type is Color.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Setter of Color, sets new value of color.
	 *
	 * @param color The color of the shape, the type is Color.
	 */
	public void setColor(Color color) {
		this.color = color;
		//this.filled = true;
	}

	/**
	 * Getter of filled,, returns the value of filled.
	 *
	 * @return filled The Boolean value of true or false if the shape is filled or not.
	 */
	public boolean getFilled() {
		return this.filled;
	}

	/**
	 * Setter of filled, sets a new value to filled.
	 *
	 * @param filled The Boolean value of true or false if the shape is filled or not.
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	/**
	 * Getter of theta, returns the value of theta.
	 *
	 * @return The orientation of the shape.
	 */
	public double getTheta() {
		return this.theta;
	}

	/**
	 * Setter of theta, sets a new value to theta.
	 *
	 * @param theta The orientation of the shape.
	 */
	public void setTheta(double theta) {
		this.theta = theta;
	}

	/**
	 * Getter of xs, returns the value of x-coordinate of the center in system coordinate.
	 *
	 * @return The x-coordinate of the center in system coordinate.
	 */
	public double getXc() {
		return this.xc;
	}

	/**
	 * Setter of xc, sets a new value of x-coordinate of the center in system coordinate.
	 *
	 * @param xc The x-coordinate of the center in system coordinate.
	 */
	public void setXc(double xc) {
		this.xc = xc;
	}

	/**
	 * Getter of yc, returns the value of y-coordinate of the center in system coordinate.
	 *
	 * @return The y-coordinate of the center in system coordinate.
	 */
	public double getYc() {
		return this.yc;
	}

	/**
	 * Setter of yc, sets a new value of y-coordinate of the center in system coordinate.
	 *
	 * @param yc The y-coordinate of the center in system coordinate.
	 */
	public void setYc(double yc) {
		this.yc = yc;
	}

	/**
	 * Getter of of xLocal, returns the value of x-coordinates of the vertices in local coordinate system.
	 *
	 * @return The x-coordinates of the vertices.
	 */
	public double[] getXLocal() {
		return this.xLocal;
	}

	/**
	 * Setter of xLocal, sets a new value of x-coordinates of the vertices.
	 *
	 * @param xLocal An array of x-coordinates of the vertices.
	 */
	public void setXLocal(double[] xLocal) {
		this.xLocal = xLocal;
	}

  /**
	* Getter of yLocal, returns the value of y-coordinates of the vertices in local coordinate system.
	*
	* @return The y-coordinates of the vertices.
	*/
	public double[] getYLocal() {
		return this.yLocal;
	}

  /**
	* Setter of yLocal, sets a new value of y-coordinates of the vertices.
	*
	* @param yLocal An array of y-coordinates of the vertices.
	*/
	public void setYLocal(double[] yLocal) {
		this.yLocal = yLocal;
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
