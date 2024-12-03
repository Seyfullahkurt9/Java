import java.util.ArrayList;
import java.util.Collections;

public class SeyfullahKurt_20010310026_Fruits {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<String>();

        System.out.println(list.size());
        list.add("Elma");
        System.out.println(list.toString());
        list.add("Muz");
        System.out.println(list.toString());
        list.add("Portakal");
        System.out.println(list.toString());

        System.out.println(list.contains("Elma"));
        System.out.println(list.get(0));
        System.out.println(list.get(list.size() - 1));

        list.set(0, "PORTAKAL");
        System.out.println(list.toString());
        System.out.println(list.isEmpty());

        for (int index = 0; index <= list.size() - 1; index++) {

            String str = list.get(index);
            System.out.println(str);
        }

        for (String fruit : list) {

             System.out.println(fruit);
        }

         Collections.sort(list);
         System.out.println(list.toString());

         list.clear();
         System.out.println(list.toString());
    }
}