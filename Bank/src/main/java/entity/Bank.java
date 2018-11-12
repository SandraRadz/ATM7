package main.java.entity;

public class Bank {
    private static int bankId;
    private static String name;
    private static  String IPadress;

    Bank(int id, String name, String IPadress){
        bankId=id;
        this.name=name;
        this.IPadress = IPadress;
    }

    public static String getIPadress() {
        return IPadress;
    }

    public static void setIPadress(String IPadress) {
        Bank.IPadress = IPadress;
    }

    public static int getBankId() {
        return bankId;
    }

    public static void setBankId(int bankId) {
        Bank.bankId = bankId;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Bank.name = name;
    }
}


