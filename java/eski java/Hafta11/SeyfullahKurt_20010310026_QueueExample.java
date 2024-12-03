import java.util.LinkedList;
import java.util.Queue;

public class SeyfullahKurt_20010310026_QueueExample {

    public static void main(String[] args) {
        String[] data = { "Adana", "Bartin", "Kayseri", "Malatya", "Zonguldak" };

        Queue<String> queue = new LinkedList<String>();
        System.out.println(queue.isEmpty());

        queue.add("Ankara");
        System.out.println("queue:" + queue);

        for (String str : data) {
            queue.add(str);
        }

        System.out.println("queue:" + queue);
        System.out.println("size:" + queue.size());
        System.out.println("peek:" + queue.peek());
        System.out.println("queue:" + queue);

        while (!queue.isEmpty()) {
            System.out.print(queue.remove() + " ");
        }
    }
}