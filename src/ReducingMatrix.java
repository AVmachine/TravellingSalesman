import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;



public class ReducingMatrix
{
    Index [][] rmarr;
    DecimalFormat df = new DecimalFormat("#.##");
    ArrayList<Index> allZeroesList = new ArrayList<>();



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

    public void colMinimize()
    {
        for(int x = 0; x < rmarr.length; x++)
        {
            Double smallest = null;
            boolean reset = false;
            for(int y = 0; y < rmarr.length ; y++)
            {
                if(smallest == null && !Double.isNaN(rmarr[y][x].getDistance()) && !reset)
                {
                    smallest = rmarr[y][x].getDistance();
                }
                if(smallest != null && rmarr[y][x].getDistance() < smallest && !reset)
                {
                    smallest = rmarr[y][x].getDistance();
                }
                if(y == rmarr.length - 1 && !reset)
                {
                    y = 0;
                    reset = true;
                }
                if(reset == true && rmarr[y][x].getDistance() != Double.NaN)
                {
                    rmarr[y][x].setDistance(rmarr[y][x].getDistance() - smallest);
                }

            }
        }
    }


    public double getDistance(Point A, Point B)
    {
        double distance = Math.sqrt(Math.pow((B.getX()-A.getX()), 2)+Math.pow((B.getY()-A.getY()),2));

        return distance;
    }

    public void getAllZeros() {
        for (int x = 0; x < rmarr.length ; x++)
        {
            for (int y = 0; y < rmarr.length ; y++)
            {
                if (rmarr[x][y].getDistance() == 0)
                {
                    allZeroesList.add(rmarr[x][y]);
                }
            }
        }
    }

    public void getPenalty()
    {
        for (int x = 0; x < allZeroesList.size(); x++)
        {
            addRowPenalty(allZeroesList.get(x).getRowNumber(), x);
            addColPenalty(allZeroesList.get(x).getColNumber(), x);
        }
    }

    public void addRowPenalty(int row, int allZeroesIndex)
    {
        Double smallest = Double.NaN;

       for (int y = 0; y < rmarr.length ; y++)
        {
            if(!Double.isNaN(rmarr[row][y].getDistance()) && Double.isNaN(smallest) && y != allZeroesList.get(allZeroesIndex).getColNumber())
            {
                smallest = rmarr[row][y].getDistance();
            }
            else if (rmarr[row][y].getDistance() < smallest && !Double.isNaN(smallest) && y != allZeroesList.get(allZeroesIndex).getColNumber())
            {
                smallest = rmarr[row][y].getDistance();
            }
        }
        allZeroesList.get(allZeroesIndex).addToPenalty(smallest);

    }

    public void addColPenalty(int col, int allZeroesIndex)
    {
        Double smallest = Double.NaN;

        for (int x = 0; x < rmarr.length ; x++)
        {
            if(!Double.isNaN(rmarr[x][col].getDistance()) && Double.isNaN(smallest) && x != allZeroesList.get(allZeroesIndex).getRowNumber())
            {
                smallest = rmarr[x][col].getDistance();
            }
            else if (rmarr[x][col].getDistance() < smallest && !Double.isNaN(smallest) && x != allZeroesList.get(allZeroesIndex).getRowNumber())
            {
                smallest = rmarr[x][col].getDistance();
            }
        }
        allZeroesList.get(allZeroesIndex).addToPenalty(smallest);
        smallest = Double.NaN;

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
                System.out.print("Col: "+ rmarr[x][y].getColNumber() +" ");
                System.out.print("Penalty: " + rmarr[x][y].getPenalty()+"       |       ");
            }
            System.out.println("\n");
        }
    }
}

