public class Point {
    private char letter;
    private float x;
    private float y;



    public Point(char letter, int x, int y)
    {
        this.letter = letter;
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public char getLetter()
    {
        return letter;
    }

}
