public class Point {

    int x;
    int y;

    public double distanceFromOrigin(int x, int y) {

        return Math.sqrt(x * x + y * y);
    }

    public void translate(int dx, int dy) {

        x += dx;
        y += dy;
    }
}