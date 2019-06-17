
import java.awt.Color;
import java.lang.Math;

public class SquareTester {

	public static void main(String[] args) {
		
		// create a Square object.
		Square square = new Square(); 
		
		// set the configuration of the member variable.
		square.color = new Color(250, 0, 0);
		square.filled = true;
		square.theta = 0;
		square.xc = 50;
		square.yc = 50;
		
		// print the value to the console.
		System.out.println("Color: " + square.color);
		System.out.println("filled: " + square.filled);
		System.out.println("theta: " + square.theta);
		System.out.println("xc: " + square.xc);
		System.out.println("yc: " + square.yc);
		System.out.println();
		
		// test the method setVertices().
		// now set the half-the-length of a side of the square to be 10.0
		square.setVertices(10.0);
		System.out.println("content of xLocal[]:");
		for(int i=0; i<square.xLocal.length; i++) {
			System.out.print(square.xLocal[i] + " ");
		}
		System.out.println();
		System.out.println("content of yLocal[]:");
		for(int i=0; i<square.yLocal.length; i++) {
			System.out.print(square.yLocal[i] + " ");
		}
		System.out.println();
		System.out.println();
		
		
		// test the getX() and getY().
		int[] xScreen_1 = square.getX();
		int[] yScreen_1 = square.getY();
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
		square.translate(85, 85);
		System.out.println("After the 85x and 85y translation of the center, new xc and yc are:");
		System.out.println("xc: " + square.xc);
		System.out.println("yc: " + square.yc);
		System.out.println();	
		int[] xScreen_2 = square.getX();
		int[] yScreen_2 = square.getY();
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
		square.rotate(Math.PI/3);
		System.out.println("After rotating 60 degree, the new orientation(mearsured by theta) is :");
		System.out.println("theta: " + square.theta);
		System.out.println();
		int[] xScreen_3 = square.getX();
		int[] yScreen_3 = square.getY();
		System.out.println("The new x-coordinates in screen system:");
		for (int i=0; i<xScreen_3.length; i++) {
			System.out.print(xScreen_3[i] + " ");
		}
		System.out.println();
		System.out.println("The new y-coordinates in screen system:");
		for (int i=0; i<yScreen_1.length; i++) {
			System.out.print(yScreen_3[i] + " ");
		}
		System.out.println();
		System.out.println();

	}

}
