import java.util.HashMap;
import java.util.Map;

class Directory {
    String name; // Dizin adý
    Map<String, Directory> directories = new HashMap<>(); // Alt dizinleri tutan harita
    Map<String, File> files = new HashMap<>(); // Dosyalarý tutan harita

    // Dizin adý ile yapýcý metot
    public Directory(String name) {
        this.name = name;
    }

    // Yeni bir alt dizin ekleme metodu
    public void addDirectory(String name) {
        directories.put(name, new Directory(name)); // Yeni alt dizini haritaya ekler
    }

    // Yeni bir dosya ekleme metodu
    public void addFile(String name) {
        files.put(name, new File(name)); // Yeni dosyayý haritaya ekler
    }

    // Belirtilen adý olan alt dizini getirme metodu
    public Directory getDirectory(String name) {
        return directories.get(name); // Alt dizini haritadan getirir
    }

    // Belirtilen adý olan dosyayý getirme metodu
    public File getFile(String name) {
        return files.get(name); // Dosyayý haritadan getirir
    }

    // Belirtilen adý olan alt dizini silme metodu
    public void removeDirectory(String name) {
        directories.remove(name); // Alt dizini haritadan siler
    }

    // Belirtilen adý olan dosyayý silme metodu
    public void removeFile(String name) {
        files.remove(name); // Dosyayý haritadan siler
    }

    // Dizin içeriðini listeleme metodu
    public void listContents() {
        for (String dirName : directories.keySet()) {
            System.out.println("DÝZÝN: " + dirName); // Alt dizinleri listeler
        }
        for (String fileName : files.keySet()) {
            System.out.println("DOSYA: " + fileName); // Dosyalarý listeler
        }
    }

    // Dizin boyutunu hesaplama metodu
    public int getSize() {
        int size = 0;
        for (File file : files.values()) {
            size += file.getSize(); // Dosyalarýn boyutunu ekler
        }
        for (Directory dir : directories.values()) {
            size += dir.getSize(); // Alt dizinlerin boyutunu ekler
        }
        return size; // Toplam boyutu döner
    }
}