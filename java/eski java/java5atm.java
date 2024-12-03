import java.util.Scanner;
public class java5atm {

    public static void main(String[] args) {

        int bakiye=10000;//Bakiyemiz başlangıç olarak "10.000".
        int sifre=316931; //Şifremizi tanımladık.
        int alinansifre; //Şifremizi tanımladık.
        int yapılacakislem; //Yapılacak işlemi tanımladık.
        int cekilecektutar; //çekilecek tutarı tanımladık.
        byte while1 = 1 ;
        byte while2 = 1 ;
        byte hata = 1 ;

        Scanner read = new Scanner(System.in); //Klavyeden girdi almak için..

        while(while1==1) {
            
            System.out.println("Merhaba Kro Bank Ltd.Şti'ye Hoşgeldiniz"); // Kendinize göre kişiselleştirebilirsiniz.

            for (int i = 1; i <4; i++) { //For döngüsünü kullanıcının 3 kez yanlış giriş yaptığında sistemden atılması için kullandık.
                System.out.println("Lütfen şifrenizi giriniz:"); //Şifreyi alıyoruz kullanıcıda her hangi bir veri tabanı kullanmadığımız için şifremiz sabit.
                alinansifre=read.nextInt(); //Şifremizi aldık.

                if (alinansifre==sifre){ //Şifre doğruysa alt işlemleri yapmadan direk yapılacak işlem seçeneğine geçmesini istedik.
                    System.out.println("\nŞİfreniz doğru,başarılı bir şekilde giriiş yaptınız!\n");
                    break;
                                }
                else if (i==3) { //Şifre her yanlış girildiğinde i'nin yani yanlış giriş sayısı artacağından i değerimiz 3'e eşit olduğunda kullanıcı sistemden atılacak.
                    System.out.println("Çok Sayıda Hatalı Şifre Girdiniz Sistemden Çıkılıyor...");
                    System.exit(0);
                }
                else { //Burda şifremizin her yanlış girildiğinde tekrar şifre girdisi almayı sağladık.
                    System.err.println("\nHatalı Şifre Girdiniz!");
                    System.err.println("Lütfen şifrenizi giriniz:");
                    sifre=read.nextInt();
                }
            }   

            while (while2==1) {
                        
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println("Lütfen Yapılacak İşlemi Giriniz:");
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
                System.out.println("1 : Para çekme");
                System.out.println("2 : Para yatırma");
                System.out.println("3 : Şifre değiştirme");
                System.out.println("4 : Bakiye sorgulama");
                System.out.println("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

                yapılacakislem=read.nextInt();

                switch (yapılacakislem) {

                    case 1:
                    for (int i =1; i<23;){

                        System.out.print("\nÇekilecek Tutarı Giriniz:");
                        cekilecektutar= read.nextInt(); //çekilecek tutarı aldık.

                        if (bakiye-cekilecektutar>=0){

                            bakiye=bakiye-cekilecektutar; //burda yeni bakiyemizi eski bakiyemizden çekilecek tutarı çıkartarak bulduk.
                            System.out.println("\nİşlem başarılı!");
                            System.out.println("Yeni bakiyeniz = "+bakiye+"\n"); // yeni bakiyemizi yazdırdık. 

                            break;}

                        else {

                            for (i = 1; i <2;) {
                                System.out.println("\n***bakiye yetersiz***\nmevcut bakiyeniz = "+bakiye);
                                System.out.println("\nyapılacak işlem seçiniz");
                                System.out.println("1 : tekrar dene ");
                                System.out.println("2 : iptal");
                                hata = read.nextByte();
                                    if (hata==1||hata==2){
                                        break;
                                                    }
                                    else {
                                        System.err.println("\n***Hatalı tuşlama yaptınız lütfen tekrar deneyiniz***");
                                    }
                            }
                        }

                        if (hata==2){
                            break;}

                    }    
                    break; //Bu komutu işlem sürekli tekrarlanmasın alt işleme geçebilsin diye kullandık.

                    case 2:
                    System.out.println("\nYatırılacak tutarı giriniz:");  //yatırılacak tutarı istedik.
                    int yatırılacaktutar; // yatırılacak tutarı tanımladık.
                    yatırılacaktutar=read.nextInt(); // yatırlacak tutarı aldık
                    bakiye=bakiye+yatırılacaktutar; // yatırılacak tutarı bakiyemize ekleyip yeni bakiyemizi bulduk.
                    System.out.println("İşlem başarılı!");
                    System.out.println("Yeni bakiyeniz:"+bakiye+"\n"); // yeni bakiyemizi yazdırdık.
                    break;

                    case 3:
                    System.out.println("\nYeni şifrenizi giriniz:"); // kullanıcıdan yeni şifre girmesini istedik.
                    sifre=read.nextInt(); // yeni şifremizi aldık.
                    System.out.println("İşlem başarılı!");
                    System.out.println("Yeni şifreniz:"+sifre+"\n"); // yeni şifremizi yazdırdık.
                    break;
                
                    case 4:
                    System.out.println("\nBakiyeniz:"+bakiye+"\n");
                    break; 

                    default: // kullanıcının yanlış girişinden sonra programı yeniden başlatıyoruz.
                    System.err.println("Hatalı giriş yaptınız,lütfen tekrar deneyin\n");
            
                }
                for (int i = 1; i <2;) {
                    System.out.println("\nyapılacak işlemi seçiniz\n");
                    System.out.println("1 : anamenü ");
                    System.out.println("0 : hesaptan çıkış");
                    while2 = read.nextByte();
                    if (while2==1||while2==0){
                        break;
                                    }
                    else {
                        System.err.println("\n***Hatalı tuşlama yaptınız lütfen tekrar deneyiniz***");
                    }
                }
            }    

            for (int i = 1; i <2;) {
                System.out.println("\nyapılacak işlemi seçiniz\n");
                System.out.println("1 : tekrar giriş ");
                System.out.println("0 : kapat");
                while1 = read.nextByte();
                if (while1==1||while1==0){
                    break;
                                }
                else {
                    System.err.println("\n***Hatalı tuşlama yaptınız lütfen tekrar deneyiniz***");
                }
            }
        }
        read.close();
    }
}