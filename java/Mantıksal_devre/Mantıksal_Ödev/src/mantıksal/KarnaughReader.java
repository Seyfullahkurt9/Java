package mantıksal;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class KarnaughReader {
    public static KarnaughTable readKarnaughTable(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        // Değişken isimlerini ve tabloyu oku
        ArrayList<String> variableNames = new ArrayList<String>();
        ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("değişkenler:")) {
                String[] parts = line.split(":")[1].trim().split(",");
                for (String part : parts) {
                    variableNames.add(part.trim());
                }
            } else {
                String[] parts = line.trim().split(" ");
                ArrayList<Integer> row = new ArrayList<Integer>();
                for (String part : parts) {
                    row.add(Integer.parseInt(part));
                }
                table.add(row);
            }
        }

        // KarnaughTable nesnesini oluştur
        int numVars = variableNames.size();
        KarnaughTable karnaughTable = new KarnaughTable(numVars, variableNames, table);

        return karnaughTable;
    }
}