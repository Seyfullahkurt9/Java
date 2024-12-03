package mantıksal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Karnaugh {
    private KarnaughTable karnaughTable;
    private BooleanFunction booleanFunction;

    public Karnaugh(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int numVariables = Integer.parseInt(br.readLine());
        String[] variableNames = br.readLine().split(" ");
        int[][] values = new int[(int) Math.pow(2, numVariables)][numVariables];
        for (int i = 0; i < values.length; i++) {
            String[] line = br.readLine().split(" ");
            for (int j = 0; j < numVariables; j++) {
                values[i][j] = Integer.parseInt(line[j]);
            }
        }
        br.close();

        karnaughTable = new KarnaughTable(numVariables, variableNames, values);
        booleanFunction = new BooleanFunction(karnaughTable);
    }

    public void printTruthTable() {
        karnaughTable.printTruthTable();
    }

    public void printFunction() {
        System.out.println("F = " + booleanFunction.getMintermsSum() + " + " + booleanFunction.getMaxtermsProduct());
    }

    public void printComplementFunction() {
        System.out.println("F' = " + booleanFunction.getMintermsComplementSum() + " + " + booleanFunction.getMaxtermsComplementProduct());
    }
}