public class BooleanFunctionTruthTable {
    public static void main(String[] args) {
        // Boolean fonksiyonu ifade eden string
        String booleanFunction = "A + CD + A'BC'D'";
        
        // Giriş değişkenlerinin sayısı
        int numInputs = 4;
        
        // Giriş değişkenlerinin tüm olası değerlerini içeren 2D boolean dizisi
        boolean[][] inputCombinations = generateInputCombinations(numInputs);
        
        // Boolean fonksiyonun değerlerini hesapla ve doğruluk tablosunu yazdır
        printTruthTable(booleanFunction, inputCombinations);
    }
    
    // Tüm giriş değişkenlerinin olası değerlerini içeren 2D boolean dizisini oluştur
    private static boolean[][] generateInputCombinations(int numInputs) {
        int numCombinations = (int) Math.pow(2, numInputs);
        boolean[][] inputCombinations = new boolean[numCombinations][numInputs];
        for (int i = 0; i < numCombinations; i++) {
            for (int j = 0; j < numInputs; j++) {
                inputCombinations[i][j] = ((i >> (numInputs - 1 - j)) & 1) == 1;
            }
        }
        return inputCombinations;
    }
    
    // Boolean fonksiyonun değerlerini hesapla ve doğruluk tablosunu yazdır
    private static void printTruthTable(String booleanFunction, boolean[][] inputCombinations) {
        // Başlık satırını yazdır
        System.out.print("| ");
        for (int i = 0; i < inputCombinations[0].length; i++) {
            System.out.print("A" + (i+1) + " | ");
        }
        System.out.println("Output |");
        
        // Ayırıcı satırı yazdır
        for (int i = 0; i <= inputCombinations[0].length; i++) {
            System.out.print("|-------");
        }
        System.out.println("|");
        
        // Her bir giriş kombinasyonu için boolean fonksiyonun değerlerini hesapla ve yazdır
        for (boolean[] inputCombination : inputCombinations) {
            // Giriş değerlerini ekrana yazdır
            for (boolean input : inputCombination) {
                System.out.print("| " + (input ? "1" : "0") + "    ");
            }
            
            // Boolean fonksiyonun değerini hesapla ve ekrana yazdır
            boolean output = calculateBooleanFunction(booleanFunction, inputCombination);
            System.out.println("|   " + (output ? "1" : "0") + "    |");
        }
    }
    
    // Boolean fonksiyonun değerini hesapla
    private static boolean calculateBooleanFunction(String booleanFunction, boolean[] inputCombination) {
        // Boolean fonksiyonu temizle (whitespace'leri kaldır)
        String cleanedBooleanFunction = booleanFunction.replaceAll("\\s", "");
        // Boolean fonksiyonunu parçala
        String[] terms = cleanedBooleanFunction.split("\\+");
        
        // Boolean fonksiyonunun değerini hesapla
        boolean result = false;
        for (String term : terms) {
            // Termdeki tüm değişkenlerin olumsuz hallerini tespit et
            boolean allNegations = true;
            for (int i = 0; i < inputCombination.length; i++) {
                char var = (char) ('A' + i);
                if (term.contains(String.valueOf(var)) && !term.contains("'" + var)) {
                    allNegations = false;
                    break;
                }
            }
            
            // Termdeki tüm değişkenlerin negatif hallerini tespit et
            boolean allAffirmatives = true;
            for (int i = 0; i < inputCombination.length; i++) {
                char var = (char) ('A' + i);
                if (term.contains("'" + var) && !term.contains(String.valueOf(var))) {
                    allAffirmatives = false;
                    break;
                }
            }
            
            // Termdeki tüm değişkenlerin doğru değerlerini kontrol et
            boolean termResult = true;
            for (int i = 0; i < inputCombination.length; i++) {
                char var = (char) ('A' + i);
                boolean varValue = inputCombination[i];
                if (term.contains(String.valueOf(var)) && !term.contains("'" + var) && !varValue) {
                    termResult = false;
                    break;
                } else if (term.contains("'" + var) && !term.contains(String.valueOf(var)) && varValue) {
                    termResult = false;
                    break;
                }
            }
            
            // Her bir term için sonucu hesapla ve birleştir
            if (allNegations && !termResult) {
                result = true;
                break;
            } else if (allAffirmatives && termResult) {
                result = true;
                break;
            } else if (termResult) {
                result = true;
            }
        }
        return result;
    }
}