import java.util.Vector;
import java.lang.Math;

public class Main {

    public static void main(String[] args)
    {
        Point A = new Point('A', 2, 2);
        Point B = new Point('B', 4,4);
        Point C = new Point('C', 6, 5);
        Point D = new Point('D', 7, 9);

        double distAB = getDistance(A, B);
        double distBC = getDistance(B, C);
        System.out.println(distAB);
        System.out.println(distBC);



    }


    public static double getDistance(Point A, Point B)
    {
        double distance = Math.sqrt(Math.pow((B.getX()-A.getX()), 2)+Math.pow((B.getY()-A.getY()),2));

        return distance;
    }



}
