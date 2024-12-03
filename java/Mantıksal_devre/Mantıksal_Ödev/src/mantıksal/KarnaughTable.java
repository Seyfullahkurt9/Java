package mantıksal;
import java.util.ArrayList;

public class KarnaughTable {
    private int numVars;
    private ArrayList<String> variableNames;
    private ArrayList<ArrayList<Integer>> table;
    private int numRows;
    private int numCols;
    private int[][] cells;
    private String[] colLabels;
    private String[] varNames;
    private int[][] karnaughMap;
    private int[] minterms;
    
    private boolean contains(int[] arr, int n) {
        for (int i : arr) {
            if (i == n) {
                return true;
            }
        }
        return false;
    }
    public void printTruthTable() {
        System.out.print('|');
        for (String varName : varNames) {
            System.out.print(' ');
            System.out.print(varName);
            System.out.print(' ');
            System.out.print('|');
        }
        System.out.println();
        for (int i = 0; i < (1 << numVars); i++) {
            System.out.print('|');
            for (int j = 0; j < numVars; j++) {
                System.out.print(' ');
                System.out.print((i & (1 << j)) == 0 ? '0' : '1');
                System.out.print(' ');
                System.out.print('|');
            }
            System.out.print(' ');
            System.out.print((contains(minterms, i) ? '1' : '0'));
            System.out.println();
        }
    }
    public int getNumRows() {
        return numRows;
    }
    public int getNumCols() {
        return numCols;
    }
    public int getCell(int row, int col) {
        if (row >= 0 && row < numRows && col >= 0 && col < numCols) {
            return cells[row][col];
        } else {
            throw new IllegalArgumentException("Invalid row or column index");
        }
    }  
    public KarnaughTable(int numRows, String[] colLabels, int[][] cells) {
        this.numRows = numRows;
        this.colLabels = colLabels;
        this.cells = cells;
    }

    public KarnaughTable(int numVars, ArrayList<String> variableNames, ArrayList<ArrayList<Integer>> table) {
        this.numVars = numVars;
        this.variableNames = variableNames;
        this.table = table;
    }
    public int getMintermValue(int row, int col) {
        int minterm = 0;

        // Satır indeksi, minterm değerinin ikilik sayı sistemindeki en yüksek basamağıdır
        int rowValue = row;
        while (rowValue > 0) {
            minterm++;
            rowValue >>= 1;
        }

        // Sütun indeksi, minterm değerinin ikilik sayı sistemindeki diğer basamaklarıdır
        int colValue = col;
        while (colValue > 0) {
            minterm |= 1 << (minterm - 1);
            colValue >>= 1;
        }

        return minterm;
    }
    public static int[] getMinterms(KarnaughTable karnaughTable) {
        ArrayList<Integer> minterms = new ArrayList<Integer>();

        // Karnaugh tablosunu dolaşarak minterm değerlerini bul
        ArrayList<ArrayList<Integer>> table = karnaughTable.getTable();
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (table.get(i).get(j) == 1) {
                    minterms.add(karnaughTable.getMintermValue(i, j));
                }
            }
        }

        // Minterm değerlerini diziye aktar
        int[] mintermsArray = new int[minterms.size()];
        for (int i = 0; i < minterms.size(); i++) {
            mintermsArray[i] = minterms.get(i);
        }

        return mintermsArray;
    }
    public int getNumVars() {
        return numVars;
    }

    public ArrayList<String> getVariableNames() {
        return variableNames;
    }

    public ArrayList<ArrayList<Integer>> getTable() {
        return table;
    }

    public void printTable() {
        // Tabloyu ekrana yazdır
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                System.out.print(table.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
    
    public BooleanFunction toBooleanFunction() {
        ArrayList<Integer> truthTable = new ArrayList<Integer>();
        for (ArrayList<Integer> row : table) {
            truthTable.addAll(row);
        }
        return new BooleanFunction(truthTable);
    }

    public String getSimplifiedExpression() {
        StringBuilder expression = new StringBuilder();
        int[] minterms = getMinterms();
        for (int i = 0; i < minterms.length; i++) {
            expression.append(minterms.length);
            if (i < minterms.length - 1) {
                expression.append(" + ");
            }
        }
        return expression.toString();
    }
    
    public void printBooleanExpression() {
        // Boole ifadesinin minterm ve maxterm ifadelerini hesapla
        ArrayList<String> minterms = new ArrayList<String>();
        ArrayList<String> maxterms = new ArrayList<String>();

        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (table.get(i).get(j) == 1) {
                    String minterm = "";
                    String maxterm = "";
                    for (int k = 0; k < numVars; k++) {
                        if (i % (int)Math.pow(2, k+1) < Math.pow(2, k)) {
                            minterm += variableNames.get(k) + "'";
                            maxterm += variableNames.get(k);
                        } else {
                            minterm += variableNames.get(k);
                            maxterm += variableNames.get(k) + "'";
                        }
                    }
                    minterms.add(minterm);
                    maxterms.add(maxterm);
                }
            }
        }

        // Minterm ve maxterm ifadelerini ekrana yazdır
        String mintermExpression = String.join(" + ", minterms);
        String maxtermExpression = String.join(" * ", maxterms);
        System.out.println("Minterm expression: " + mintermExpression);
        System.out.println("Maxterm expression: " + maxtermExpression);
    }

    public void printComplementExpression() {
        // Tümleyen ifadesinin minterm ve maxterm ifadelerini hesapla
        ArrayList<String> minterms = new ArrayList<String>();
        ArrayList<String> maxterms = new ArrayList<String>();

        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                if (table.get(i).get(j) == 0) {
                    String minterm = "";
                    String maxterm = "";
                    for (int k = 0; k < numVars; k++) {
                        if (i % (int)Math.pow(2, k+1) < Math.pow(2, k)) {
                            minterm += variableNames.get(k);
                            maxterm += variableNames.get(k) + "'";
                        } else {
                            minterm += variableNames.get(k) + "'";
                            maxterm += variableNames.get(k);
                        }
                    }
                    minterms.add(minterm);
                    maxterms.add(maxterm);
                }
            }
        }

        // Minterm ve maxterm ifadelerini ekrana yazdır
        String mintermExpression = String.join(" + ", minterms);
        String maxtermExpression = String.join(" * ", maxterms);
        System.out.println("Complement minterm expression: " + mintermExpression);
        System.out.println("Complement maxterm expression: " + maxtermExpression);
    }
}