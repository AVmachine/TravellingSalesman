import java.util.ArrayList;
import java.lang.Math;

public class Main {

    public static void main(String[] args)
    {
        Point A = new Point('A', 2, 2);
        Point B = new Point('B', 4,4);
        Point C = new Point('C', 6, 5);
        Point D = new Point('D', 7, 9);

       ArrayList<Point> listOfPoints = new ArrayList<>();
       listOfPoints.add(A);
       listOfPoints.add(B);
       listOfPoints.add(C);
       listOfPoints.add(D);

       ReducingMatrix redmax = new ReducingMatrix();
       redmax.fillMatrix(listOfPoints);
       redmax.displayMatrix();
       redmax.rowMinimize();
       redmax.displayMatrix();





    }

}
