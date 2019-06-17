
/**
 * This class defines the Circle class.
 * 
 * @author Hu Qiyun
 */
public class Circle extends Shape {
	
	/**
	 * This method sets the local coordinates of a standard circle, 
	 * the coordinates are the upper left and lower right vertices of an axis-aligned bounding box.
	 * 
	 * @param d the radius of the circle.
	 */
	public void setVertices(double d) {
		xLocal = new double[]{-d, d};
		yLocal = new double[]{-d, d};
	}

	/**
	 * This method retrieves the x-coordinates of the vertices on circle,
	 * the x-coordinates are the upper left and lower right vertices of an axis-aligned bounding box,
	 * in screen coordinate system and rounded to nearest integers.
	 * 
	 * @return An array containing the x-coordinates
	 */
	public int[] getX() {
		int[] xScreen = new int[xLocal.length];
		for (int i=0; i<xLocal.length; i++) {
			xScreen[i] = (int)(xLocal[i] + xc);
		}
		return xScreen;
	}
	
	/**
	 * This method retrieves the y-coordinates of the vertices on circle,
	 * the y-coordinates are the upper left and lower right vertices of an axis-aligned bounding box,
	 * in screen coordinate system and rounded to nearest integers.
	 * 
	 * @return An array containing the y-coordinates.
	 */
	public int[] getY() {
		int[] yScreen = new int[yLocal.length];
		for (int i=0; i<yLocal.length; i++) {
			yScreen[i] = (int)(yLocal[i] + yc);
		}
		return yScreen;
	}
	
	
}
