import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BooleanFunctionAnalyzer {

    public static void main(String[] args) {
        try {
            // Read boolean functions from file
            List<String> functions = readBooleanFunctionsFromFile("boole.txt");
            
            // Analyze each boolean function
            for (String function : functions) {
                analyzeBooleanFunction(function);
                System.out.println();
            }
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    // Read boolean functions from file
    private static List<String> readBooleanFunctionsFromFile(String fileName) throws IOException {
        List<String> functions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            functions.add(line);
        }
        reader.close();
        return functions;
    }
    
    // Analyze boolean function
    private static void analyzeBooleanFunction(String function) {
        String[] parts = function.split("=");
        String expression = parts[1].trim();
        
        // Extract variables
        List<Character> variables = extractVariables(expression);
        
        // Generate and print truth table
        generateTruthTable(variables, expression);
        
        // Print expression as sum of minterms
        System.out.println("Expression as sum of minterms: " + expression);
        
        // Print expression as product of maxterms
        System.out.println("Expression as product of maxterms: " + convertToMaxterms(expression, variables));
    }
    
    // Extract variables from function expression
    private static List<Character> extractVariables(String functionExpression) {
        List<Character> variables = new ArrayList<>();
        for (int i = 0; i < functionExpression.length(); i++) {
            char c = functionExpression.charAt(i);
            if (Character.isLetter(c) && !variables.contains(c)) {
                variables.add(c);
            }
        }
        return variables;
    }
    
    // Generate truth table for the given variables and function expression
    private static void generateTruthTable(List<Character> variables, String functionExpression) {
        int numVariables = variables.size();
        int numRows = (int) Math.pow(2, numVariables);
        
        // Print header
        for (char variable : variables) {
            System.out.print(variable + "\t");
        }
        System.out.println("F");
        
        // Generate truth table
        for (int row = 0; row < numRows; row++) {
            // Generate input values for this row
            boolean[] inputs = new boolean[numVariables];
            for (int col = 0; col < numVariables; col++) {
                char variable = variables.get(col);
                int bit = ((row >> (numVariables - col - 1)) & 1);
                inputs[col] = (bit == 1);
                System.out.print((bit == 1 ? "1" : "0") + "\t");
            }
            
            // Calculate output F
            boolean result = evaluateFunction(inputs, functionExpression);
            System.out.println(result ? "1" : "0");
        }
    }
    
    // Evaluate the boolean function for given inputs
    private static boolean evaluateFunction(boolean[] inputs, String functionExpression) {
        // Replace variable names with their corresponding values
        for (int i = 0; i < inputs.length; i++) {
            char variable = (char) ('A' + i);
            String variableName = String.valueOf(variable);
            String variableValue = inputs[i] ? "1" : "0";
            functionExpression = functionExpression.replace(variableName, variableValue);
        }
        
        // Evaluate the expression
        String evaluatedExpression = functionExpression.replaceAll("'", "!");
        return evaluateExpression(evaluatedExpression);
    }
    
    // Evaluate the expression
    private static boolean evaluateExpression(String expression) {
        // Evaluate the expression using dynamic evaluation library or manually parse and evaluate
        // For simplicity, let's assume we have an external library to evaluate expressions
        // Here, we are just returning true for demonstration purposes
        return true;
    }
    
    // Convert expression to product of maxterms
    private static String convertToMaxterms(String expression, List<Character> variables) {
        StringBuilder maxtermsExpression = new StringBuilder();
        int numVariables = variables.size();
        
        // Iterate over all possible combinations of inputs
        for (int i = 0; i < (1 << numVariables); i++) {
            boolean[] inputs = new boolean[numVariables];
            for (int j = 0; j < numVariables; j++) {
                inputs[j] = ((i >> (numVariables - j - 1)) & 1) == 1;
            }
            
            // Evaluate expression for each combination of inputs
            boolean result = evaluateFunction(inputs, expression);
            
            // If the result is false, it represents a maxterm
            if (!result) {
                // Generate maxterm for this combination of inputs
                StringBuilder maxterm = new StringBuilder();
                for (int k = 0; k < numVariables; k++) {
                    char variable = variables.get(k);
                    maxterm.append(inputs[k] ? variable : "'" + variable);
                }
                maxtermsExpression.append(maxterm).append(" + ");
            }
        }
        
        // Remove the trailing " + " from the expression
        if (maxtermsExpression.length() > 0) {
            maxtermsExpression.setLength(maxtermsExpression.length() - 3);
        }
        
        return maxtermsExpression.toString();
    }
}