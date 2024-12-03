package Hafta3;

public class SeyfullahKurt_20010310026_Hafta3_Ornek1 {

    public static void main(String[] args) {

        Point p1 = new Point(4, 5);

        p1.setX(20);
        p1.setY(30);
        System.out.println("p1->" + p1.getX() + " " + p1.getY());

        Point p3 = new Point(10);

        System.out.println(p3.toString());
        System.out.println(p3.print());

        SeyfullahKurt_20010310026_Hafta3_Ornek1 denemeObje = new SeyfullahKurt_20010310026_Hafta3_Ornek1();
        int toplam = denemeObje.topla(4, 5);
        System.out.println(toplam);
    }

    public int topla(int sayi1, int sayi2) {

        return sayi1 * sayi2;
    }
}