package mantıksal;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    	try {
    	    // Dosyadan Karnaugh tablosunu oku
    	    KarnaughTable karnaughTable = KarnaughReader.readKarnaughTable("karnaugh.txt");

    	    // Karnaugh tablosundan Boolean fonksiyonunu oluştur
    	    BooleanFunction booleanFunction = karnaughTable.toBooleanFunction();

    	    // Başlıkları yazdır
    	    System.out.println("karnaugh.txt dosyası okundu.");
    	    System.out.print("doğruluk tablosu: ");
    	    for (String variableName : karnaughTable.getVariableNames()) {
    	        System.out.print(variableName + " ");
    	    }
    	    System.out.println("F");

    	    // Boolean fonksiyonun gerçeklik tablosunu yazdır
    	    ArrayList<ArrayList<Integer>> table = karnaughTable.getTable();
    	    for (ArrayList<Integer> row : table) {
    	        for (int val : row) {
    	            System.out.print(val + " ");
    	        }
    	        System.out.println();
    	    }

    	    // Boolean fonksiyonun sadeleştirilmiş ifadesini yazdır
    	    String simplifiedExpression = karnaughTable.getSimplifiedExpression();
    	    System.out.println("fonksiyon ifadeleri:");
    	    System.out.println("F = " + simplifiedExpression);
    	    System.out.println("F' = " + simplifiedExpression.replaceAll("([A-Z])", "$1'"));

    	} catch (FileNotFoundException e) {
    	    System.out.println("Dosya bulunamadı.");
    	} catch (Exception e) {
    	    System.out.println("Bir hata oluştu: " + e.getMessage());
    	}
    }
}