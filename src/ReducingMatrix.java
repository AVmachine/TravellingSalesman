import java.text.DecimalFormat;
import java.util.ArrayList;



public class ReducingMatrix
{
    ArrayList <ArrayList<Index>> rmv;
    Index [][] rmarr;
    DecimalFormat df = new DecimalFormat("#.##");

    public ReducingMatrix()
    {

    }

    public void fillMatrix(ArrayList<Point> points)
    {
        int size = points.size();
        rmarr = new Index[size][size];
        for(int x = 0; x < size; x++)
        {
            for(int y = 0; y < size; y++)
            {
                if(x != y) {
                    double distance = getDistance(points.get(x), points.get(y));
                    rmarr[x][y] = new Index();
                    rmarr[x][y].setDistance(distance);
                    rmarr[x][y].setFrom(points.get(x).getLetter());
                    rmarr[x][y].setTo(points.get(y).getLetter());
                    rmarr[x][y].setColNumber(y);
                    rmarr[x][y].setRowNumber(x);
                }
                else {
                    rmarr[x][y] = new Index();
                    rmarr[x][y].setDistance(Double.NaN);
                }
            }
        }


    }

    public void rowMinimize()
    {
        for(int x = 0; x < rmarr.length; x++)
        {
            Double smallest = null;
            boolean reset = false;
            for(int y = 0; y < rmarr.length ; y++)
            {
                if(smallest == null && !Double.isNaN(rmarr[x][y].getDistance()) && !reset)
                {
                    smallest = rmarr[x][y].getDistance();
                }
                if(smallest != null && rmarr[x][y].getDistance() < smallest && !reset)
                {
                    smallest = rmarr[x][y].getDistance();
                }
                if(y == rmarr.length - 1 && !reset)
                {
                    y = 0;
                    reset = true;
                }
                if(reset == true && rmarr[x][y].getDistance() != Double.NaN)
                {
                    rmarr[x][y].setDistance(rmarr[x][y].getDistance() - smallest);
                }

            }
        }
    }


    public double getDistance(Point A, Point B)
    {
        double distance = Math.sqrt(Math.pow((B.getX()-A.getX()), 2)+Math.pow((B.getY()-A.getY()),2));

        return distance;
    }

    public void displayMatrix()
    {
        for(int x = 0; x < rmarr.length; x++)
        {
            for(int y = 0; y < rmarr.length ; y++)
            {
                System.out.print("["+ df.format(rmarr[x][y].getDistance()) +"] ");
                System.out.print("From: "+ rmarr[x][y].getFrom() +" ");
                System.out.print("To: "+ rmarr[x][y].getTo() +" ");
                System.out.print("Row: "+ rmarr[x][y].getRowNumber() +" ");
                System.out.print("Col: "+ rmarr[x][y].getColNumber() +"     |    ");
            }
            System.out.println("\n");
        }
    }
}

