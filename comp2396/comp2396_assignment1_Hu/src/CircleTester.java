
import java.awt.Color;

public class CircleTester {

	public static void main(String[] args) {
		
		// create a Circle object.
		Circle circle = new Circle();
		
		// set the configuration of the member variable.
		circle.color = new Color(250, 0, 0);
		circle.filled = true;
		circle.theta = 0;
		circle.xc = 50;
		circle.yc = 50;
		
		// print the value to the console.
		System.out.println("Color: " + circle.color);
		System.out.println("filled: " + circle.filled);
		System.out.println("theta: " + circle.theta);
		System.out.println("xc: " + circle.xc);
		System.out.println("yc: " + circle.yc);
		System.out.println();
		
		// test the method setVertices().
		// now set the radius of the circle to be 10.0.
		circle.setVertices(10.0);
		System.out.println("content of xLocal[]:");
		for(int i=0; i<circle.xLocal.length; i++) {
			System.out.print(circle.xLocal[i] + " ");
		}
		System.out.println();
		System.out.println("content of yLocal[]:");
		for(int i=0; i<circle.yLocal.length; i++) {
			System.out.print(circle.yLocal[i] + " ");
		}
		System.out.println();
		System.out.println();
		
		// test the getX() and getY().
		int[] xScreen_1 = circle.getX();
		int[] yScreen_1 = circle.getY();
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
		circle.translate(85, 85);
		System.out.println("After the 85x and 85y translation of the center, new xc and yc are:");
		System.out.println("xc: " + circle.xc);
		System.out.println("yc: " + circle.yc);
		System.out.println();
		int[] xScreen_2 = circle.getX();
		int[] yScreen_2 = circle.getY();
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
		circle.rotate(Math.PI/3);
		System.out.println("After rotating 60 degree, the new orientation(mearsured by theta) is :");
		System.out.println("theta: " + circle.theta);
		System.out.println();
		int[] xScreen_3 = circle.getX();
		int[] yScreen_3 = circle.getY();
		System.out.println("The new x-coordinates in screen system:");
		for (int i=0; i<xScreen_3.length; i++) {
			System.out.print(xScreen_3[i] + " ");
		}
		System.out.println();
		System.out.println("The new y-coordinates in screen system:");
		for (int i=0; i<yScreen_3.length; i++) {
			System.out.print(yScreen_3[i] + " ");
		}
		System.out.println();
		System.out.println();
	
	}

}
