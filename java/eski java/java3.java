import java.util.Locale;
import java.util.Scanner;

public class java3 {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        scan.useLocale(Locale.US);
        // scan fonksiyonunun merika dil standartların ayarllanmasını sağladım ve bu sayede bouble float bir değişken tanımlarken . yada , mü kullanacağımı bilecek

        System.out.println("bir sayı gir");

        int ac = scan.nextInt();
        // girilen değeri değişkene atadık int kullanacağım için nexInt seçtim

        System.out.println(ac);

        System.out.println("bir sayı gir");
        double ad = scan.nextDouble();
        System.out.println(ad);
        scan.close();
    }

}
