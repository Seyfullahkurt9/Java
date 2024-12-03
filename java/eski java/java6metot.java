import java.util.Scanner;

public class java6metot{


    public static void yarram() {

        Scanner scan = new Scanner(System.in);

        int a=1;
        int faktöriyel=1;
        boolean x = true;

        while(x==true){
            System.out.println("faktoriyeli alınacak bir sayı giriniz");
            a = scan.nextInt();

            if (a==0){
                System.out.println(a+" faktöriyel = 1");
                break;
            }

            else if (a>0){
                for (int j = 1 ;j <= a;a--){
                    faktöriyel*=a;
                }
                System.out.println(a+"! = "+faktöriyel);
                break;
            }

            else {
                System.out.println("sayının faktoriyeli alınamaz tekrar deneyin");
            }
            scan.close();
        }
    }

    public static void hirrem(int a,int b,int c) {
        int carpim= a*b*c;
        System.out.println("üç sayının çarpımı = "+carpim);
    }
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        byte secim;
        byte while1=1;
        while (while1==1) {
            
            System.out.println("yapmak istediğiniz işlemi seçin");
            System.out.println("1 : faktöriyel alma");
            System.out.println("2 : üç sayının çarpımını alma");
            secim = scan.nextByte();

            if (secim==1){
                yarram();
                break;
            }
            else if (secim==2){
                int x,y,z;
                System.out.println("lütfen 1. sayıyı giriniz");
                x = scan.nextInt();
                System.out.println("lütfen 2. sayıyı giriniz");
                y = scan.nextInt();
                System.out.println("lütfen 3. sayıyı giriniz");
                z = scan.nextInt();
                hirrem(x, y, z);
                break;
                }
            else {
                System.out.println("hatalı tuşladınız tekrar deneyin");
            }    
        }
        scan.close();   
    }
}
