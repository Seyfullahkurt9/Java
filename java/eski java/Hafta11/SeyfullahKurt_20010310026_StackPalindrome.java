import java.util.Scanner;
import java.util.Stack;

public class SeyfullahKurt_20010310026_StackPalindrome {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Lutfen bir kelime giriniz:");
        String inputString = input.nextLine();

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < inputString.length(); i++) {
            stack.push(inputString.charAt(i));
        }
        
        System.out.println("stack:" + stack);
        String reverseString = "";

        while (!stack.isEmpty()) {
            reverseString += stack.pop();
        }

        if (inputString.equals(reverseString)) {
            System.out.println("Girilen kelime palindromdur.");
        } else {
            System.out.println("Girilen kelime palindrom degildir.");
        }
    }
}
