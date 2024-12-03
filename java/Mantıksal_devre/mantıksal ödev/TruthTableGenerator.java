public class TruthTableGenerator {
    public static void main(String[] args) {
        // Değişkenlerin sayısı
        int numVariables = 4;

        // Tüm olası kombinasyonları hesapla
        for (int i = 0; i < Math.pow(2, numVariables); i++) {
            // Her değişkenin durumunu temsil eden bir dizi oluştur
            int[] variableValues = new int[numVariables];
            for (int j = 0; j < numVariables; j++) {
                variableValues[j] = (i >> j) & 1;
            }

            // Boolean fonksiyonun sonucunu hesapla
            boolean result = evaluateBooleanFunction(variableValues);

            // Kombinasyonu ve sonucu yazdır
            System.out.print("Kombinasyon: ");
            for (int value : variableValues) {
                System.out.print(value + " ");
            }
            System.out.println("Sonuç: " + result);
        }
    }

    // Verilen değişken değerleriyle boolean fonksiyonunu değerlendir
    private static boolean evaluateBooleanFunction(int[] values) {
        // Burada kendi boolean fonksiyonunuzu tanımlayın
        // Örneğin: (A && B) || (!C && D)
        boolean A = (values[0] == 1);
        boolean B = (values[1] == 1);
        boolean C = (values[2] == 1);
        boolean D = (values[3] == 1);

        return (A && B) || (!C && D);
    }
}