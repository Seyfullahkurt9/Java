package Hafta5;

import java.util.HashSet;
import java.util.Set;

public class SeyfullahKurt_20010310026_PointMain {

    public static void main(String[] args) {
        Point pl = new Point(3, 4);
        System.out.println(pl.toString());
        System.out.println(pl.getX() + " " + pl.getY());
        Set<Point> points = new HashSet<Point>();
        
        points.add(pl);
        points.add(new Point(10,20));
        points.add(new Point(5,6));
        
        System.out.println(points.toString());
        
        for(Point p :points) {
            System.out.println(p.getX() + " " + p.getY());
        }
    }
}