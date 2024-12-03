import java.util.HashMap;
import java.util.Map;

class Directory {
    String name; // Dizin ad�
    Map<String, Directory> directories = new HashMap<>(); // Alt dizinleri tutan harita
    Map<String, File> files = new HashMap<>(); // Dosyalar� tutan harita

    // Dizin ad� ile yap�c� metot
    public Directory(String name) {
        this.name = name;
    }

    // Yeni bir alt dizin ekleme metodu
    public void addDirectory(String name) {
        directories.put(name, new Directory(name)); // Yeni alt dizini haritaya ekler
    }

    // Yeni bir dosya ekleme metodu
    public void addFile(String name) {
        files.put(name, new File(name)); // Yeni dosyay� haritaya ekler
    }

    // Belirtilen ad� olan alt dizini getirme metodu
    public Directory getDirectory(String name) {
        return directories.get(name); // Alt dizini haritadan getirir
    }

    // Belirtilen ad� olan dosyay� getirme metodu
    public File getFile(String name) {
        return files.get(name); // Dosyay� haritadan getirir
    }

    // Belirtilen ad� olan alt dizini silme metodu
    public void removeDirectory(String name) {
        directories.remove(name); // Alt dizini haritadan siler
    }

    // Belirtilen ad� olan dosyay� silme metodu
    public void removeFile(String name) {
        files.remove(name); // Dosyay� haritadan siler
    }

    // Dizin i�eri�ini listeleme metodu
    public void listContents() {
        for (String dirName : directories.keySet()) {
            System.out.println("D�Z�N: " + dirName); // Alt dizinleri listeler
        }
        for (String fileName : files.keySet()) {
            System.out.println("DOSYA: " + fileName); // Dosyalar� listeler
        }
    }

    // Dizin boyutunu hesaplama metodu
    public int getSize() {
        int size = 0;
        for (File file : files.values()) {
            size += file.getSize(); // Dosyalar�n boyutunu ekler
        }
        for (Directory dir : directories.values()) {
            size += dir.getSize(); // Alt dizinlerin boyutunu ekler
        }
        return size; // Toplam boyutu d�ner
    }
}