import java.util.Arrays;

public class TruthTableConverter {
    
    // Function to evaluate the sum of products expression
    public static boolean evaluateExpression(boolean[] input, boolean[][] terms) {
        boolean result = false;
        for (boolean[] term : terms) {
            boolean termResult = true;
            for (int i = 0; i < input.length; i++) {
                if (term[i] && !input[i]) {
                    termResult = false;
                    break;
                }
            }
            result = result || termResult;
        }
        return result;
    }
    
    // Function to generate truth table
    public static void generateTruthTable(boolean[][] terms) {
        int numVariables = terms[0].length;
        int numRows = (int) Math.pow(2, numVariables);
        
        // Generate column headers
        StringBuilder columnHeader = new StringBuilder();
        for (int i = numVariables - 1; i >= 0; i--) {
            columnHeader.append("x").append(i).append("\t");
        }
        columnHeader.append("Output");
        System.out.println(columnHeader);
        
        // Generate truth table rows
        for (int i = 0; i < numRows; i++) {
            boolean[] input = new boolean[numVariables];
            int temp = i;
            for (int j = 0; j < numVariables; j++) {
                input[j] = (temp % 2) == 1;
                temp /= 2;
            }
            
            // Evaluate expression for current input
            boolean output = evaluateExpression(input, terms);
            
            // Print input values and output
            StringBuilder row = new StringBuilder();
            for (boolean value : input) {
                row.append(value ? "1\t" : "0\t");
            }
            row.append(output ? "1" : "0");
            System.out.println(row);
        }
    }
    
    public static void main(String[] args) {
        // Example sum of products expression
        boolean[][] terms = {
            {true, true, false, false},
            {false, true, false, true},
            {true, false, true, false}
        };
        
        generateTruthTable(terms);
    }
}
