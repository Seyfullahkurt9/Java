public class SeyfullahKurt_20010310026_Hafta2_Ornek2 {

    public static void main(String[] args) {

        Point p1 = new Point();
        p1.x = 5;
        p1.y = 12;

        System.out.println("p1: " + p1.x + ", " + p1.y);

        double distance = p1.distanceFromOrigin(p1.x, p1.y);
        System.out.println("Orjine olan uzaklık = " + distance);

        p1.translate(1, 4);
        System.out.println("p1: " + p1.x + ", " + p1.y);

        System.out.println("Yeni noktanin orjine olan uzaklığı = " + p1.distanceFromOrigin(p1.x, p1.y));

        Point p2 = new Point();
        p2.x = 3;
        p2.y = 6;

        System.out.println("p2 Orjine olan uzaklık = " + p2.distanceFromOrigin(p2.x, p2.y));
    }    
}