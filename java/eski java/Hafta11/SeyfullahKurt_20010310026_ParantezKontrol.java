import java.util.Stack;

public class SeyfullahKurt_20010310026_ParantezKontrol {

    public static void main(String[] args) {
        String expr = " ";
        if (duzenliMi(expr)) {
            System.out.println("Duzenli ifade");
        } else {
            System.out.println("Duzenli ifade degil");
        }
    }

    public static boolean duzenliMi(String expr) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < expr.length(); i++) {
            char x = expr.charAt(i);
            if (x == '(' || x == '[' || x == '{') {
                stack.push(x);
                continue;
            }

            if (stack.isEmpty()) {
                return false;
            }

            char check;
            switch (x) {
                case ')':
                    check = stack.pop();
                    if (check == '{' || check == '[')
                        return false;
                    break;

                case '}':
                    check = stack.pop();
                    if (check == '(' || check == '[')
                        return false;
                    break;

                case ']':
                    check = stack.pop();
                    if (check == '(' || check == '{')
                        return false;
                    break;
            }
        }
        
        return stack.isEmpty();
    }
}