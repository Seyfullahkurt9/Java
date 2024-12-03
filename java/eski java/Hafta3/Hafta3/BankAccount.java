package Hafta3;

public class BankAccount {

    private static int objectCount = 0;
    
    private String name;
    private int id;

    public BankAccount() {

        objectCount++;
        id = objectCount;
    }

    public BankAccount(String a_name) {

        objectCount++;
        id = objectCount;
        name = a_name;
    }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public static int numberOfAccount() {

        return objectCount;
    }

    public int numbers_Accounts() {

        return objectCount;
    }
}
