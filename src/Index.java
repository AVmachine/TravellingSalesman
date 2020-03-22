public class Index {
    private char from;
    private char to;
    double distance;
    double penalty;

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

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public double getPenalty() {
        return penalty;
    }
}
