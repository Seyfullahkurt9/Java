import java.util.ArrayList;

public class SeyfullahKurt_20010310026_KisiMain {

    public static void main(String[] args) {
        Kisi k1 = new Kisi("Ali", 20);
        System.out.println(k1.getIsim());

        ArrayList<Kisi> kisiler = new ArrayList<Kisi>();
        kisiler.add(k1);

        System.out.println(kisiler.get(0).getYas());
        kisiler.add(new Kisi("Veli", 30));
        kisiler.add(0, new Kisi("Ahmet", 40));

        for (int index = 0; index < kisiler.size(); index++) {
            String isim = kisiler.get(index).getIsim();
            int yas = kisiler.get(index).getYas();
            System.out.println("isim:" + isim + ", yas:" + yas);
        }

        for (Kisi kisi : kisiler) {
            System.out.println("isim:" + kisi.getIsim() + ", yas:" + kisi.getYas());
        }
    }
}