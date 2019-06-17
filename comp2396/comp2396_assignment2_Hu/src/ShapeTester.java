import java.awt.Color;
import java.lang.Math;

public class ShapeTester {

  public static void main(String[] args) {


    // create a Shape object.
    Shape s1 = new Shape();

    // test the setter methods, set the configuration of the member variable.
    // since a shape does not have a specify appearance, so we set a xLocal and yLocal just for testing.
    s1.setColor(new Color(250, 0, 0));
    s1.setTheta(0.0);
    s1.setFilled(true);
    s1.setXc(10.0);
    s1.setYc(10.0);
    double[] tempXLocal = {1.0, 1.0, -1.0, -1.0};
    double[] tempYLocal = {1.0, -1.0, -1.0, 1.0};
    // test setXlocal() and setYLocal().
    s1.setXLocal(tempXLocal);
    s1.setYLocal(tempYLocal);

    // test the getter methods, and print the value to the console.
    System.out.println("Color: " + s1.getColor());
    System.out.println("filled: " + s1.getFilled());
    System.out.println("theta: " + s1.getTheta());
    System.out.println("xc: " + s1.getXc());
    System.out.println("yc: " + s1.getYc());

    
    // test the getXLocal() and getYLocal().
    System.out.println("xLocal:");
    for (int i=0; i<s1.getXLocal().length; i++){
      System.out.print(s1.getXLocal()[i] + " ");
    }
    System.out.println();
    System.out.println("yLocal:");
    for (int i=0; i<s1.getYLocal().length; i++){
      System.out.print(s1.getYLocal()[i] + " ");
    }
    System.out.println();
    System.out.println();

    // test the getX() and getY().
    System.out.println("xScreen:");
    for (int i=0; i<s1.getX().length; i++){
      System.out.print(s1.getX()[i] + " ");
    }
    System.out.println();
    System.out.println("yScreen:");
    for (int i=0; i<s1.getY().length; i++){
      System.out.print(s1.getY()[i] + " ");
    }
    System.out.println();
    System.out.println();


    // test the method translate().
    s1.translate(85, 85);
    System.out.println("After the 85x and 85y translation of the center, new xc and yc are:");
    System.out.println("xc: " + s1.getXc());
    System.out.println("yc: " + s1.getYc());
    System.out.println("xSreen:");
    for (int i=0; i<s1.getX().length; i++){
      System.out.print(s1.getX()[i] + " ");
    }
    System.out.println();
    System.out.println("ySreen:");
    for (int i=0; i<s1.getY().length; i++){
      System.out.print(s1.getY()[i] + " ");
    }
    System.out.println();
    System.out.println();

    // test the method rotate().
    s1.rotate(Math.PI/4);
    System.out.println("After rotating 45 degree, the new orientation(mearsured by theta) is :");
    System.out.println("theta: " + s1.getTheta());
    System.out.println("xSreen:");
    for (int i=0; i<s1.getX().length; i++){
      System.out.print(s1.getX()[i] + " ");
    }
    System.out.println();
    System.out.println("ySreen:");
    for (int i=0; i<s1.getY().length; i++){
      System.out.print(s1.getY()[i] + " ");
    }
    System.out.println();



  }

}
