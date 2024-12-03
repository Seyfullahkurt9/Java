import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class SeyfullahKurt_20010310026_QueuePalindrome {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Lütfen bir kelime giriniz:");
        String inputString = input.nextLine();
        Queue<Character> queue = new LinkedList<>();

        for (int i = inputString.length() - 1; i >= 0; i--) {
            queue.add(inputString.charAt(i));
        }

        String reverseString = "";
        while (!queue.isEmpty()) {
            reverseString = reverseString + queue.remove();
        }

        if (inputString.equals(reverseString)) {
            System.out.println("Girilen kelime palindromdur.");
        } else {
            System.out.println("Girilen kelime palindrom değildir.");
        }
    }
}
