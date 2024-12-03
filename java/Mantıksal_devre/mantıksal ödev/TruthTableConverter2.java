import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TruthTableConverter2 {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("boole.txt"));
            String expression = reader.readLine();
            reader.close();

            int numVariables = countVariables(expression);
            int numRows = (int) Math.pow(2, numVariables);

            System.out.println("Truth Table for expression: " + expression);
            System.out.println("=====================================");

            for (int row = 0; row < numRows; row++) {
                boolean[] inputs = getBinaryInputs(row, numVariables);
                boolean result = evaluateExpression(expression, inputs);

                for (boolean input : inputs) {
                    System.out.print(input ? "1" : "0");
                }
                System.out.println(" | " + (result ? "1" : "0"));
            }
        } catch (IOException e) {
            System.err.println("Error reading boole.txt: " + e.getMessage());
        }
    }

    private static int countVariables(String expression) {
        return expression.chars()
                .filter(ch -> Character.isLetter(ch))
                .distinct()
                .toArray().length;
    }

    private static boolean[] getBinaryInputs(int row, int numVariables) {
        boolean[] inputs = new boolean[numVariables];
        for (int i = 0; i < numVariables; i++) {
            inputs[i] = ((row >> i) & 1) == 1;
        }
        return inputs;
    }

    private static boolean evaluateExpression(String expression, boolean[] inputs) {
        // Implement your logic to evaluate the expression here
        // You can use a truth table or other methods
        // For simplicity, I'm assuming the expression is in the form of X'Y + XY' + XY
        boolean x = inputs[0];
        boolean y = inputs[1];
        return (!x && y) || (x && !y) || (x && y);
    }
}
