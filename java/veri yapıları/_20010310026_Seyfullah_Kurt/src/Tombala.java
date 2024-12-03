import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Tombala {
    public static void main(String[] args) {
        Map<String, List<Integer>> oyunKartlari = new HashMap<>();
        Map<String, List<Integer>> oyuncuKartlari = new LinkedHashMap<>();
        List<String> oyuncular = new ArrayList<>();

        try (BufferedReader okuyucu = new BufferedReader(new InputStreamReader(new FileInputStream("Bilgiler.txt"), "UTF-8"))) {
            String satir;
            boolean kisilerOkunuyor = false;
            boolean kartlarOkunuyor = false;

            while ((satir = okuyucu.readLine()) != null) {
                satir = satir.trim();

                if (satir.startsWith("Kişiler ve Seçtikleri Kart:")) {
                    kisilerOkunuyor = true;
                    kartlarOkunuyor = false;
                    continue;
                }
                if (satir.startsWith("Oyun Kartları:")) {
                    kisilerOkunuyor = false;
                    kartlarOkunuyor = true;
                    continue;
                }
                if (kartlarOkunuyor) {
                    String[] parcalar = satir.split("\\s+");
                    if (parcalar.length < 2) continue;
                    String kartAdi = parcalar[0].trim();
                    List<Integer> sayilar = new ArrayList<>();
                    for (int i = 1; i < parcalar.length; i++) {
                        try {
                            sayilar.add(Integer.parseInt(parcalar[i].trim()));
                        } catch (NumberFormatException e) {
                            System.err.println("Gecersiz sayi: " + parcalar[i]);
                        }
                    }
                    oyunKartlari.put(kartAdi, sayilar);
                } else if (kisilerOkunuyor) {
                    String[] parcalar = satir.split("\\s+");
                    if (parcalar.length != 2) {
                        System.err.println("Hatali oyuncu bilgisi: " + satir);
                        continue;
                    }
                    String oyuncu = parcalar[0].trim();
                    String kartAdi = parcalar[1].trim();

                    oyuncular.add(oyuncu);
                    if (oyunKartlari.containsKey(kartAdi)) {
                        oyuncuKartlari.put(oyuncu, new ArrayList<>(oyunKartlari.get(kartAdi)));
                        System.out.println("Oyuncu ve kart eslestirildi: " + oyuncu + " -> " + kartAdi);
                    } else {
                        System.err.println("Kart bulunamadi: " + kartAdi);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata olustu: " + e.getMessage());
        }

        System.out.println("\nOYUN KARTLARI:");
        for (Map.Entry<String, List<Integer>> giris : oyunKartlari.entrySet()) {
            System.out.println(giris.getKey() + ": " + giris.getValue());
        }

        System.out.println("\nOYUNCU KARTLARI:");
        for (Map.Entry<String, List<Integer>> giris : oyuncuKartlari.entrySet()) {
            System.out.println(giris.getKey() + ": " + giris.getValue());
        }

        if (oyuncuKartlari.isEmpty()) {
            System.err.println("Oyuncular icin kartlar yuklenemedi! Dosyayi kontrol edin.");
        }

        System.out.println("\nOYUNCU VE KARTLARI:");
        for (Map.Entry<String, List<Integer>> giris : oyuncuKartlari.entrySet()) {
            System.out.println(giris.getKey() + ": " + giris.getValue());
        }

        Set<Integer> cekilenSayilar = new HashSet<>();
        Random rastgele = new Random();
        boolean oyunBitti = false;
        int tur = 0;
        while (!oyunBitti) {
            tur++;
            int sayi;
            do {
                sayi = rastgele.nextInt(20) + 1;
            } while (cekilenSayilar.contains(sayi));
            cekilenSayilar.add(sayi);

            System.out.println(tur + ". tur");
            System.out.println("Cekilen sayi: " + sayi);

            for (Map.Entry<String, List<Integer>> giris : oyuncuKartlari.entrySet()) {
                String oyuncu = giris.getKey();
                List<Integer> kartSayilari = giris.getValue();
                boolean tumSayilarEslesti = true;

                System.out.print(oyuncu + ": ");
                for (int i = 0; i < kartSayilari.size(); i++) {
                    int kartSayi = kartSayilari.get(i);
                    if (kartSayi == sayi) {
                        System.out.print(kartSayi + "# ");
                        kartSayilari.set(i, -1);
                    } else if (kartSayi == -1) {
                        System.out.print("-1# ");
                    } else {
                        System.out.print(kartSayi + " ");
                    }
                }
                System.out.println();

                for (int num : kartSayilari) {
                    if (num != -1) {
                        tumSayilarEslesti = false;
                        break;
                    }
                }
                if (tumSayilarEslesti) {
                    System.out.println("\nOyun bitti.");
                    System.out.println("Kazanan: " + oyuncu);
                    oyunBitti = true;
                    break;
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println();
        }
    }
}
