
import java.awt.Color;

public class TriangleTester {

	public static void main(String[] args) {
		
		// create a Triangle object.
		Triangle triangle = new Triangle(); 
		
		// set the configuration of the member variable.
		triangle.color = new Color(250, 0, 0);
		triangle.filled = true;
		triangle.theta = 0;
		triangle.xc = 50;
		triangle.yc = 50;
		
		// print the value to the console.
		System.out.println("Color: " + triangle.color);
		System.out.println("filled: " + triangle.filled);
		System.out.println("theta: " + triangle.theta);
		System.out.println("xc: " + triangle.xc);
		System.out.println("yc: " + triangle.yc);
		System.out.println();
		
		// test the method setVertices().
		// now set the distance from its center to any of its vertices to be 10.0
		triangle.setVertices(10.0);
		System.out.println("content of xLocal[]:");
		for(int i=0; i<triangle.xLocal.length; i++) {
			System.out.print(triangle.xLocal[i] + " ");
		}
		System.out.println();
		System.out.println("content of yLocal[]:");
		for(int i=0; i<triangle.yLocal.length; i++) {
			System.out.print(triangle.yLocal[i] + " ");
		}
		System.out.println();
		System.out.println();
		
		
		// test the getX() and getY().
		int[] xScreen_1 = triangle.getX();
		int[] yScreen_1 = triangle.getY();
		System.out.println("The x-coordinates in screen system:");
		for (int i=0; i<xScreen_1.length; i++) {
			System.out.print(xScreen_1[i] + " ");
		}
		System.out.println();
		System.out.println("The y-coordinates in screen system:");
		for (int i=0; i<yScreen_1.length; i++) {
			System.out.print(yScreen_1[i] + " ");
		}
		System.out.println();
		System.out.println();
		
		
		// test the method translate().
		triangle.translate(85, 85);
		System.out.println("After the 85x and 85y translation of the center, new xc and yc are:");
		System.out.println("xc: " + triangle.xc);
		System.out.println("yc: " + triangle.yc);
		System.out.println();
		
		int[] xScreen_2 = triangle.getX();
		int[] yScreen_2 = triangle.getY();
		System.out.println("The new x-coordinates in screen system:");
		for (int i=0; i<xScreen_2.length; i++) {
			System.out.print(xScreen_2[i] + " ");
		}
		System.out.println();
		System.out.println("The new y-coordinates in screen system:");
		for (int i=0; i<yScreen_2.length; i++) {
			System.out.print(yScreen_2[i] + " ");
		}
		System.out.println();
		System.out.println();
		
		
		// test the method rotate().
		triangle.rotate(Math.PI/3);
		System.out.println("After rotating 60 degree, the new orientation(mearsured by theta) is :");
		System.out.println("theta: " + triangle.theta);
		System.out.println();
		int[] xScreen_3 = triangle.getX();
		int[] yScreen_3 = triangle.getY();
		System.out.println("The x-coordinates in screen system:");
		for (int i=0; i<xScreen_3.length; i++) {
			System.out.print(xScreen_3[i] + " ");
		}
		System.out.println();
		System.out.println("The y-coordinates in screen system:");
		for (int i=0; i<yScreen_3.length; i++) {
			System.out.print(yScreen_3[i] + " ");
		}
		System.out.println();
		System.out.println();
	

	}

}
