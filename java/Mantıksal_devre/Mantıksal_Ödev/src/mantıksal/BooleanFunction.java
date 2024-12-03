package mantıksal;
import java.util.ArrayList;

public class BooleanFunction {
    private ArrayList<Integer> truthTable;
    private KarnaughTable karnaughTable;
    private int[] minterms;
    private int numVars;
    private boolean contains(int[] arr, int n) {
        for (int i : arr) {
            if (i == n) {
                return true;
            }
        }
        return false;
    }
    private String productOfTerms(int[] terms) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < terms.length; i++) {
            int term = terms[i];
            sb.append('(');
            for (int j = 0; j < numVars; j++) {
                if ((term & (1 << j)) == 0) {
                    sb.append((char) ('A' + j));
                } else {
                    sb.append((char) ('A' + j));
                    sb.append('\'');
                }
            }
            sb.append(')');
            if (i < terms.length - 1) {
                sb.append('+');
            }
        }
        return sb.toString();
    }

    public int getMintermsComplementSum() {
        int maxMinterm = (1 << numVars) - 1;
        int complementSum = 0;
        for (int minterm : minterms) {
            complementSum += maxMinterm - minterm;
        }
        return complementSum;
    }
    public int getMintermsSum() {
        int sum = 0;
        for (int minterm : minterms) {
            sum += minterm;
        }
        return sum;
    }
    public String getMaxtermsProduct() {
        int[] maxterms = new int[getNumVars() - minterms.length];
        int k = 0;
        for (int i = 0; i < (1 << getNumVars()); i++) {
            if (!contains(minterms, i)) {
                maxterms[k++] = i;
            }
        }
        return productOfTerms(maxterms);
    }
    public String getMaxtermsComplementProduct() {
        StringBuilder sb = new StringBuilder();

        // Tablodaki tüm hücreleri gez ve maksterm tamamlayıcı çarpımını hesapla
        for (int row = 0; row < karnaughTable.getNumRows(); row++) {
            for (int col = 0; col < karnaughTable.getNumCols(); col++) {
                // Maksterm değeri 0 olan hücrelerin tamamlayıcısının çarpımını hesapla
                if (karnaughTable.getCell(row, col) == 0) {
                    sb.append(getCellMaxtermComplement(row, col)).append(" & ");
                }
            }
        }

        // Son '&' karakterini sil
        sb.delete(sb.length() - 3, sb.length());

        return sb.toString();
    }

    // Bir hücrenin maksterm tamamlayıcısının çarpımını hesaplayan getCellMaxtermComplement() metodu
    private String getCellMaxtermComplement(int row, int col) {
        int numVars = karnaughTable.getNumVars();
        StringBuilder sb = new StringBuilder();

        // Hücrenin konumuna göre, maksterm tamamlayıcısının çarpımını hesapla
        for (int i = 0; i < numVars; i++) {
            if ((row & (1 << (numVars - i - 1))) == 0) {
                sb.append((char) ('A' + i));
            } else {
                sb.append((char) ('A' + i) + "'");
            }
        }

        for (int i = 0; i < numVars; i++) {
            if ((col & (1 << (numVars - i - 1))) == 0) {
                sb.append((char) ('a' + i));
            } else {
                sb.append((char) ('a' + i) + "'");
            }
        }

        return sb.toString();
    }
    public BooleanFunction(KarnaughTable karnaughTable) {
        this.karnaughTable = karnaughTable;
    }
    public BooleanFunction(ArrayList<Integer> truthTable) {
        this.truthTable = truthTable;
    }

    public ArrayList<Integer> getTruthTable() {
        return truthTable;
    }

    public int getNumVars() {
        return (int) (Math.log(truthTable.size()) / Math.log(2));
    }

    public boolean evaluate(ArrayList<Boolean> inputs) {
        int index = 0;
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i)) {
                index += Math.pow(2, i);
            }
        }
        return (truthTable.get(index) == 1);
    }

    public void printTruthTable() {
        int numVars = getNumVars();
        for (int i = 0; i < truthTable.size(); i++) {
            String binaryRep = Integer.toBinaryString(i);
            while (binaryRep.length() < numVars) {
                binaryRep = "0" + binaryRep;
            }
            System.out.println(binaryRep + " " + truthTable.get(i));
        }
    }
}