package Hafta6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SeyfullahKurt_20010310026_Iterators {

    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Ali");
        names.add("Veli");
        names.add("Mehmet");
        names.add("Davut");

        Iterator<String> iterator = names.iterator();
        
        // Iterator ile koleksiyon üzerinde döngü oluştur
        while (iterator.hasNext()) {
            // Bir sonraki elemanı al
            String name = iterator.next();
            System.out.println(name);

            if (name.equals("Veli")) {
                iterator.remove();
            }
        }

        System.out.println("Koleksiyondan 'Veli' elemanı kaldırıldı.");

        // Koleksiyonu yazdır
        System.out.println("Koleksiyonun güncel hali:");
        for (String name : names) {
            System.out.println(name);
        }
    }
}
