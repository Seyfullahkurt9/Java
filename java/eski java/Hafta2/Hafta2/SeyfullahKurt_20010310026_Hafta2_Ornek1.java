public class SeyfullahKurt_20010310026_Hafta2_Ornek1 {

    public static void main(String[] args) {

        Point p1 = new Point();
        p1.x = 3;
        p1.y = 4;

        System.out.println("p1: " + p1.x + ", " + p1.y);

        Point p2 = new Point();
        p2.x = 1;
        p2.y = 10;

        System.out.println("p2: " + p2.x + ", " + p2.y);

        double distance = Math.sqrt(p1.x * p1.x + p1.y * p1.y);
        
        System.out.println("Orjine olan uzaklık = " + distance);

        p1.x = p1.x + 2;
        p1.y += 3;

        System.out.println("p1: " + p1.x + ", " + p1.y);
    }
}
