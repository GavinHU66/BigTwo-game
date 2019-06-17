import java.lang.Math;

/**
 * The RegularPolygon class is used to model regular n-sided polygons,
 * it is a subclass of the Shape class.
 *
 * @author Hu Qiyun
 *
 */
public class RegularPolygon extends Shape {

    /**
     * An integer value specifying the number of sides of the regular n-sided polygon.
     */
	private int numOfSides;

    /**
     * A double value specifying the radius of the regular n-sided polygon.
     */
    private double radius;

	/**
	 * Constructor of RegularPolygon class, set the sides to be 3 and radius to be 1.0.
	 */
	public RegularPolygon() {
		this(3, 1.0);
	}

	 /**
	  * Constructor of RegularPolygon class, set the sides to be n(or 3 if n is smaller than 3) 
	  * and radius to be 1.0.
	  *
	  * @param n The number of the sides of the Regular Polygon.
	  * 
	  */
	public RegularPolygon(int n) {
		this(n, 1.0);
	}

	/**
	 * Constructor of RegularPolygon class, set the sides to be n(or 3 if n is smaller than 3) 
	 * and radius to be r(or 0.0 if r is smaller than 0).
	 *
	 * @param n The number of the sides of the Regular Polygon the type is int.
	 * @param r The radius of the Regular Polygon the type is double.
	 */
	public RegularPolygon(int n, double r) {
		this.radius = (r<0)? 0.0 : r;
		this.numOfSides = (n<3)? 3 : n;
		setVertices();
	}



	/**
	 * Getter of NumOfSides, returns the value of numOfSides.
	 *
	 * @return The number of the sides of the Regular Polygon.
	 */
	public int getNumOfSides() {
		return this.numOfSides;
	}

	/**
	 * Setter of NumOfSides, sets a new value of numOfSides and also reset the local coordinates of the vertices.
	 *
	 * @param numOfSides The number of the sides of the Regular Polygon.
	 */
	public void setNumOfSides(int numOfSides) {
		this.numOfSides = (numOfSides<3)? numOfSides : 3;
		setVertices();
	}

	/**
	 * Getter of radius, returns the value of radius.
	 *
	 * @return The radius of the Regular Polygon.
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Getter of radius, sets a new value of radius, and also reset the local coordinates of the vertices.
	 *
	 * @param radius The radius of the Regular Polygon.
	 */
	public void setRadius(double radius) {
		this.radius = (radius<0)? 0 : radius;
		setVertices();
	}




	/**
	 * This method sets the x,y-coordinates of the vertices, in local coordinate system.
	 */
	private void setVertices(){
		int     n = this.numOfSides;
		double  r = this.radius;
		double  PI = Math.PI;
		double  initialAngel = (n%2==0)? PI/n : 0;

		double[] currentXs = new double[n];
		double[] currentYs = new double[n];

		for (int i=0; i<n; i++){
			currentXs[i] = r*Math.cos(initialAngel-i*2*PI/n);
			currentYs[i] = r*Math.sin(initialAngel-i*2*PI/n);
		}

		setXLocal(currentXs);
    	setYLocal(currentYs);
    }



	/**
	 * This method determines whether a point (x,y) in the screen coordinate system is contained by the polygon.
	 *
	 * @param x x-coordinate of the point being tested.
	 * @param y y-coordinate of the point being tested.
	 * @return a Boolean value whether a point (x,y) in the screen coordinate system is contained by the polygon.
	 */
	public boolean contains(double x, double y){	
		System.out.println("!!! contains() called !!!");
		int     n = this.numOfSides;
		double  PI = Math.PI;
		double  leftMostX = (n%2==0)? (-1)*getXLocal()[0] : getXLocal()[n/2]; // local x-coordinate of the left most point.

		// transforming points from screen coordinates into local coordinates.
		// 𝑥(local) = (𝑥(screen) − 𝑥𝑐) cos(−𝜃) − (𝑦(screen) − 𝑦𝑐) sin(−𝜃)
		// 𝑦(local) = (𝑥(screen) − 𝑥𝑐) sin(−𝜃) + (𝑦(screen) − 𝑦𝑐) cos(−𝜃)
		double xl = (x-getXc())*Math.cos((-1)*getTheta()) - (y-getYc())*Math.sin((-1)*getTheta());
		double yl = (x-getXc())*Math.sin((-1)*getTheta()) + (y-getYc())*Math.cos((-1)*getTheta());

		// angle is the initial angle.
		double  distance = Math.pow((Math.pow(xl, 2)+Math.pow(yl, 2)), 0.5);
		double  angle = Math.acos(xl/distance);
		angle = (yl>=0)? angle : -angle;
	   
		// check the position each time we rotate the testing point.
		for (int j=0; j<n; j++){
			xl = distance*Math.cos(angle+j*2*PI/n);
			if (xl<leftMostX){
				System.out.println(xl + "<" + leftMostX);
				return false;
			}
		}
		return true;
	}

}
