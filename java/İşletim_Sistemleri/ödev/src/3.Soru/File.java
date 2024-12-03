import java.util.*;

class File {
    String name; // Dosya adý
    String content; // Dosya içeriði
    int size; // Dosya boyutu

    // Dosya adý ile yapýcý metot
    public File(String name) {
        this.name = name;
        this.content = "";
        this.size = 0;
    }

    // Dosyaya veri yazma metodu
    public void write(String data) {
        this.content += data;
        this.size = content.length(); // Ýçeriðin uzunluðunu boyut olarak güncelleriz
    }

    // Dosya içeriðini okuma metodu
    public String read() {
        return this.content;
    }

    // Dosya boyutunu alma metodu
    public int getSize() {
        return size;
    }
}