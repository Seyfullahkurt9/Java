import java.util.Stack;

public class SeyfullahKurt_20010310026_StackExample {

    public static void main(String[] args) {

        String[] data = { "Adana", "Bartin", "Kayseri", "Malatya", "Zonguldak"};
        Stack<String> stack = new Stack<String>();
        System.out.println(stack.isEmpty());
        stack.push("Ankara");
        System.out.println("stack: " + stack);
        for(String str: data) {
            stack.push(str);
        }

        System.out.println("stack: " + stack);
        System.out.println("size: " + stack.size());
        System.out.println("peek: " + stack.peek());
        System.out.println("stack: " + stack);
        System.out.println("pop:" +stack.pop());
        System.out.println("stack: "+ stack);

        while(!stack.isEmpty()) {
            System.out.print(stack.pop()+" ");
        }
    }
}