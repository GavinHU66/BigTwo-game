import java.awt.Color;
import java.lang.Math;

public class RegularPolygonTester {

	// print out the coordinates of vertices in screen system.
	public static void printCoordinScreen(RegularPolygon p) {
		   	int[] xScreen = p.getX();
			int[] yScreen = p.getY();
			System.out.println("xScreen:");
			for (int i=0; i<xScreen.length; i++) {
				System.out.print(xScreen[i] + " ");
			}
			System.out.println();
			System.out.println("yScreen:");
			for (int i=0; i<yScreen.length; i++) {
				System.out.print(yScreen[i] + " ");
			}
			System.out.println();
			System.out.println();
	}
	
	// print out the coordinates of vertices in local system.
	public static void printCoordinLocal(RegularPolygon p) {
	    System.out.println("xLocal:");
	    for (int i=0; i<p.getXLocal().length; i++){
	      System.out.print(p.getXLocal()[i] + " ");
	    }
	    System.out.println();
	    System.out.println("yLocal:");
	    for (int i=0; i<p.getYLocal().length; i++){
	      System.out.print(p.getYLocal()[i] + " ");
	    }
	    System.out.println();
	    System.out.println();
	}
	
	// test the given RegularPolygon p1.
	public static void test(RegularPolygon p1) {
		
		System.out.println("------------------------------------------------------------------------------------------");

	    p1.setColor(new Color(250, 0, 0));
	    p1.setTheta(0.0);
	    p1.setFilled(true);
	    p1.setXc(10.0);
	    p1.setYc(10.0);
	    //p1.setXLocal(p1.getXLocal());
	    //p1.setYLocal(p1.getYLocal());
	    System.out.println("Radius of p1: " + p1.getRadius());
	    System.out.println("Number of sides of p1: " + p1.getNumOfSides());
	    System.out.println("Color of p1 is: " + p1.getColor());
	    System.out.println("theta of p1 is: " + p1.getTheta());
	    System.out.println("xc of p1 is: " + p1.getXc());
	    System.out.println("yc of p1 is: " + p1.getYc());
	    System.out.println("filled of p1 is: " + p1.getFilled());
	    System.out.println();
	    
	    
	    // test getXLocal() and getYLocal().
	    printCoordinLocal(p1);   
	    
		// test the getX() and getY().
	    printCoordinScreen(p1);		

	    // test the method rotate().
	    p1.rotate(Math.PI/4);
	    System.out.println("After rotating 45 degree, the new orientation(mearsured by theta) is :");
	    System.out.println("theta: " + p1.getTheta());
	    
	    printCoordinScreen(p1);
		
	    // test the method rotate().
	    p1.rotate(-Math.PI/4);
	    System.out.println("After rotating -45 degree, the new orientation(mearsured by theta) is :");
	    System.out.println("theta: " + p1.getTheta());
	    
	    printCoordinScreen(p1);
		
		
		// test the method translate().
		p1.translate(85, 85);
		System.out.println("After the 85x and 85y translation of the center, new xc and yc are:");
		System.out.println("xc: " + p1.getXc());
		System.out.println("yc: " + p1.getYc());
		System.out.println();	

		printCoordinScreen(p1);

		// test the contains() method.
		System.out.println("xc: " + p1.getXc());
		System.out.println("yc: " + p1.getYc());
		printCoordinScreen(p1);
		System.out.println("Point (96,95) is contained by the regular polygon or not:" + p1.contains(96,95.1));
		
	}
	
  public static void main(String[] args) {
	  
	    // test the no-args constructor RegularPolygon().
	    // set the default radius to 1.0 and numOfSides to be 3.
	    RegularPolygon p1 = new RegularPolygon(); // call setVerices() automatically.
	    test(p1);



	    // test the constructor RegularPolygon(int n).
	    // set the default radius to 1.0, if n<3, set numOfSides to be 3.
	    RegularPolygon p2_1 = new RegularPolygon(5); // numOfSides = 5
	    RegularPolygon p2_2 = new RegularPolygon(1); // numOfSides = 3
	    test(p2_1);
	    test(p2_2);


	    // test the constructor RegularPolygon(int n, double r).
	    // if n<3. set numOfSides to be 3.
	    // if r<0, set radius to be 0.0.
	    RegularPolygon p3_1 = new RegularPolygon(6 , 1.1); // numOfSides = 6; radius = 1.1.
	    RegularPolygon p3_2 = new RegularPolygon(1 , -1.1); // numOfSides = 3; radius = 0.0.
	    test(p3_1);
	    test(p3_2);


  }

}
