import java.util.*;

class File {
    String name; // Dosya ad�
    String content; // Dosya i�eri�i
    int size; // Dosya boyutu

    // Dosya ad� ile yap�c� metot
    public File(String name) {
        this.name = name;
        this.content = "";
        this.size = 0;
    }

    // Dosyaya veri yazma metodu
    public void write(String data) {
        this.content += data;
        this.size = content.length(); // ��eri�in uzunlu�unu boyut olarak g�ncelleriz
    }

    // Dosya i�eri�ini okuma metodu
    public String read() {
        return this.content;
    }

    // Dosya boyutunu alma metodu
    public int getSize() {
        return size;
    }
}