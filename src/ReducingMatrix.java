import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.*;


public class ReducingMatrix
{
    private Index [][] rmarr;
    private DecimalFormat df = new DecimalFormat("#.##");
    private ArrayList<Index> allZeroesList = new ArrayList<>();
    private LinkedList<Character> finalResult = new LinkedList<>(); //Will be the final list in order from first city and back
    private ArrayList<Index> theWinners = new ArrayList<>(); //Will store the Index Objects that have a correct To and From
    private Index currentIndex; //Holding so we can get values like to and from, row, col, etc
    private ArrayList<Point> listOfPointsClone = new ArrayList<>();
    double shortestDistance = 0;
    private Index [][] rmarrOriginalClone;


    public void theManager(ArrayList<Point> points)
    {
        fillMatrix(points);
        rowMinimize();
        colMinimize();
        getAllZeros();
        getPenalty();
        getHighestPenaltyIndex();
        while(rmarr.length > 1)
        {
            createNewArray();
            resetArray();
            rowMinimize();
            colMinimize();
            getAllZeros();
            getPenalty();
            getHighestPenaltyIndex();
        }
        tieItAllTogether();
        getShortestDistance();
    }

    public void cloneList(ArrayList<Point> points)
    {
        for(int i = 0; i < points.size();i++)
        {
            listOfPointsClone.add(points.get(i));
        }

        int size = points.size();
        rmarrOriginalClone = new Index[size][size];
        for(int x = 0; x < size; x++)
        {
            for(int y = 0; y < size; y++)
            {
                if(x != y) {
                    double distance = getDistance(points.get(x), points.get(y));
                    rmarrOriginalClone[x][y] = new Index();
                    rmarrOriginalClone[x][y].setDistance(distance);
                    rmarrOriginalClone[x][y].setFrom(points.get(x).getLetter());
                    rmarrOriginalClone[x][y].setTo(points.get(y).getLetter());
                    rmarrOriginalClone[x][y].setColNumber(y);
                    rmarrOriginalClone[x][y].setRowNumber(x);
                }
                else {
                    rmarrOriginalClone[x][y] = new Index();
                    rmarrOriginalClone[x][y].setDistance(Double.NaN);
                }
            }
        }


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
        cloneList(points);


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

    public void getHighestPenaltyIndex()
    {
        int highestPenaltyIndex = 0;
        for (int i=1; i < allZeroesList.size(); i++)
        {
            if(allZeroesList.get(highestPenaltyIndex).getPenalty() < allZeroesList.get(i).getPenalty())
            {
                if(theWinners.size() > 0)
                {
                    for (int x = 0; x < theWinners.size(); x++)
                    {
                        if (allZeroesList.get(i).getFrom() != theWinners.get(x).getTo() &&
                                allZeroesList.get(i).getTo() != theWinners.get(x).getFrom() &&
                                rmarr.length > 1)
                        {
                            highestPenaltyIndex = i;
                        }
                    }
                }
            }
        }
        addToFromToList(highestPenaltyIndex);
    }

    public void addToFromToList(int i)
    {
        theWinners.add(allZeroesList.get(i));
        currentIndex = allZeroesList.get(i);
        for (Index item: theWinners) {
            System.out.print("["+item.getFrom()+"]");
        }
        System.out.println();
        for (Index item: theWinners) {
            System.out.print("["+item.getTo()+"]");
        }
        System.out.println();
        allZeroesList.clear();
    }

    public void createNewArray()
    {
        Index tempIndex;
        Index[][] tempRmarr = new Index[rmarr.length][rmarr.length];
        for(int x = 0; x < rmarr.length; x++) //copy into new array
        {
            for(int y = 0; y < rmarr.length; y++)
            {
                tempRmarr[x][y] = rmarr[x][y];
            }
        }
        rmarr = new Index[rmarr.length-1][rmarr.length-1];
        int i = 0;
        int j = 0;
        for(int x = 0; x < tempRmarr.length; x++) //make new array minus the row and col
        {

            if(x != currentIndex.getRowNumber()) {
                for (int y = 0; y < tempRmarr.length; y++) {

                    if(y != currentIndex.getColNumber())
                    {
                        rmarr[i][j] = tempRmarr[x][y];
                        j++;
                    }
                    if(j==tempRmarr.length-1)
                        j=0;
                }
                i++;
            }
            if(i==tempRmarr.length-1)
                i=0;
        }
    }

    public void tieItAllTogether()
    {
        int i = 0; //counter for winner list
        int j = 0; // counter for final list
        while(finalResult.size() != theWinners.size()+1)
        {
           if(theWinners.get(i).getFrom() == 'A')
           {
               finalResult.add(theWinners.get(i).getFrom());
               finalResult.add(theWinners.get(i).getTo());
               j++;
           }
           else if (theWinners.get(i).getFrom() == finalResult.get(j) && theWinners.size() > 1)
           {
               finalResult.add(theWinners.get(i).getTo());
               j++;
           }

           if(i == theWinners.size()-1)
           {
               i = 0;
           }
           i++;
        }

        for(int x = 0; x < finalResult.size();x++)
        {
            System.out.print(finalResult.get(x) + " ");
        }
        System.out.println();
    }

    public void resetArray()
    {
        for(int x = 0; x < rmarr.length; x++)
        {
            for(int y = 0; y < rmarr.length; y++)
            {
                rmarr[x][y].setColNumber(y);
                rmarr[x][y].setRowNumber(x);
                rmarr[x][y].resetPenalty();
                for(int i = 0; i < theWinners.size(); i++)
                {
                    if (rmarr[x][y].getTo() == theWinners.get(i).getFrom() && rmarr[x][y].getFrom() == theWinners.get(i).getTo() )
                        rmarr[x][y].setDistance(Double.NaN);
                }
            }
        }
    }

    public void getShortestDistance()
    {
        for(int i = 0; i < rmarrOriginalClone.length;i++)
        {
            for(int j = 0; j < rmarrOriginalClone.length ;j++)
            {
                for(int z = 0; z < finalResult.size()-1 ;z++)
                {
                    if (rmarrOriginalClone[i][j].getFrom() == finalResult.get(z) && rmarrOriginalClone[i][j].getTo() == finalResult.get(z + 1))
                    {
                        shortestDistance += rmarrOriginalClone[i][j].getDistance();
                    }
                }
            }
        }
        System.out.println("Shortest Distance: " + shortestDistance);
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

