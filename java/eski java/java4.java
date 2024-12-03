import java.util.Scanner;

public class java4 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("bir sayı gir");
        int ac = scan.nextInt();
        System.out.println("başka bir sayı gir");
        int ad = scan.nextInt();
        System.out.println("ilk sayının ikinciye bölümü = "+((double)ac/ad));
        scan.close();
    }
}
