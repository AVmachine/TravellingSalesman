public class Index {
    private char from;
    private char to;
    private double distance;
    private double penalty;
    private int rowNumber;
    private int colNumber;

    public Index(){

    }

    public void setFrom(char from)
    {
        this.from = from;
    }

    public char getFrom() {
        return from;
    }

    public void setTo(char to) {
        this.to = to;
    }

    public char getTo() {
        return to;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void addToPenalty(double penalty)
    {
        this.penalty += penalty;
    }

    public double getPenalty() {
        return penalty;
    }

    public int getColNumber() {
        return colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }
}
