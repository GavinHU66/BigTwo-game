
import java.awt.Color;

public class ShapeTester {

	public static void main(String[] args) {
		
		// create a Shape object.
		Shape shape = new Shape(); 
		
		// set the configuration of the member variable.
		// since a shape does not have a specify appearance, so we do not consider xLocal, yLocal, getX() and getY().
		shape.color = new Color(250, 0, 0);
		shape.filled = true;
		shape.theta = 0;
		shape.xc = 50;
		shape.yc = 50;
		
		shape.xLocal = new double[2];
		shape.yLocal = new double[2];
		shape.xLocal[0] = 1.1;
		shape.xLocal[1] = 2.2;
		shape.yLocal[0] = 1.1;
		shape.yLocal[1] = 2.2;
		
		// print the value to the console.
		System.out.println("Color: " + shape.color);
		System.out.println("filled: " + shape.filled);
		System.out.println("theta: " + shape.theta);
		System.out.println("xc: " + shape.xc);
		System.out.println("yc: " + shape.yc);
		System.out.println("xLocal[0]: " + shape.xLocal[0]);
		System.out.println("xLocal[1]: " + shape.xLocal[1]);
		System.out.println("yLocal[0]: " + shape.yLocal[0]);
		System.out.println("yLocal[1]: " + shape.yLocal[1]);
		System.out.println();
		
		
		// test the getX() and getY().
		int[] xScreen_1 = shape.getX();
		int[] yScreen_1 = shape.getY();
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
		shape.translate(85, 85);
		System.out.println("After the 85x and 85y translation of the center, new xc and yc are:");
		System.out.println("xc: " + shape.xc);
		System.out.println("yc: " + shape.yc);
		System.out.println();
		int[] xScreen_2 = shape.getX();
		int[] yScreen_2 = shape.getY();
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
		shape.rotate(Math.PI/3);
		System.out.println("After rotating 60 degree, the new orientation(mearsured by theta) is :");
		System.out.println("theta: " + shape.theta);
		int[] xScreen_3 = shape.getX();
		int[] yScreen_3 = shape.getY();
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

