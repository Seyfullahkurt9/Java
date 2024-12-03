package Hafta3;

public class Point {

    int x;
    int y;

    public Point(int initialX, int initialY) {

        this.x = initialX;
        this.y = initialY;
    }

    public Point() {

        this(5, 15);
        System.out.println(this.print());
    }

    public Point(int x) {

        this.x = x;
        this.y = 0;
    }

    public double distanceFromOrigin(int x, int y) {
        
        return Math.sqrt(x * x + y * y);
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public String print() {

        return "p-> x:" + x + ",y:" + this.y;
    }

    public String toString() {
        
        return "p->x:" + x + ",y:" + this.y;
    }

    public void translate(int i, int j) {

        throw new UnsupportedOperationException("Unimplemented method 'translate'");
    }
}