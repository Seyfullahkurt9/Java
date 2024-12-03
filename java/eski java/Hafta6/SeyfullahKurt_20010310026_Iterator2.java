package Hafta6;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SeyfullahKurt_20010310026_Iterator2 {
    public static void main(String[] args) {
        Map<String, Integer> scores = new TreeMap<String, Integer>();
        scores.put("Ali", 38);
        scores.put("Veli", 94);
        scores.put("Mehmet", 87);
        scores.put("Davut", 43);
        
        Iterator<String> itr = scores.keySet().iterator();
        
        while (itr.hasNext()) {
            String name = itr.next();
            int score = scores.get(name);
            System.out.println(name + " " + score);

            if (score < 60) {
                itr.remove();
            }
        }

        System.out.println(scores);
    }
}
