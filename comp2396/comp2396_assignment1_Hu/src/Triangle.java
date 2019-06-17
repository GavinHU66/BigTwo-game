import java.lang.Math;

/**
 * This class defines the Triangle class.
 * 
 * @author Hu Qiyun
 */
public class Triangle extends Shape{
	
	/**
	 * This method sets the local coordinates of the 3 vertices of a standard triangle,
	 * the 3 local coordinates are in counter clockwise order,
	 * from the one on the positive x-axis.
	 * 
	 * @param d the value of the distance from the center of the triangle to any of its vertices.
	 */
	public void setVertices(double d) {
		xLocal = new double[]{d, -d*Math.cos(Math.PI/3), -d*Math.cos(Math.PI/3) };
		yLocal = new double[]{0, -d*Math.sin(Math.PI/3), d*Math.sin(Math.PI/3) };
	}
}
