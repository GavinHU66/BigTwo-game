
/**
 * This class defines the Square class.
 * 
 * @author Hu Qiyun
 */
public class Square extends Shape{
	
	/**
	 * This method sets the local coordinates of the 4 vertices of the standard square,
	 * the 4 local coordinates are in counter clockwise order,
	 * starting from the lower right corner.
	 * 
	 * @param d the value of half-the-length of a side of the square. 
	 */
	public void setVertices(double d) {
		xLocal = new double[]{d, d, -d, -d};
		yLocal = new double[]{d, -d, -d, d};
	}
}
