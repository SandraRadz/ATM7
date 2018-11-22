package controller;

import service.CardService;
import service.impl.CardServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Controller {
    private ServerSocket ss;
    private ObjectInputStream in;
    private ObjectOutputStream out;


    public Controller(int port){
        try {
            ss = new ServerSocket(port);

        } catch(Exception x) { x.printStackTrace(); }
    }

    public void start(){
            try {
                System.out.println("Waiting for a client...");
                Socket socket = ss.accept();

                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                Object arrayData;
                String result;
                while (true) {

                    arrayData = in.readObject();
                    ArrayList<String> arr = (ArrayList<String>) arrayData;
                    System.out.println("+++++++++++++++++ArrayList+++++++++++++++++++++");
                    for(int i = 0; i < arr.size(); i++) {
                        System.out.println(arr.get(i));
                    }

                    result = doQuery(arr);
                    System.out.println("-----------------resulr----------------"+ result);
                    out.writeUTF(result); // отсылаем клиенту обратно ту самую строку текста.
                    out.flush();
                    out.flush(); // заставляем поток закончить передачу данных.
                    System.out.println("Waiting for the next operation data...");
                    System.out.println();
                }
            }catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public String doQuery(ArrayList<String> data){
        //всі варіанти відповідно до першого елементу масиву
        String res="fail";
        String code = data.get(0);
        if(code.equals("0")) res = signIn(data.get(1), data.get(2));
        if(code.equals("1")) res = transaction(data.get(1), data.get(2), data.get(3), data.get(4));
        if(code.equals("2")) res = getCash(data.get(1), data.get(2), data.get(3));
        if(code.equals("3")) res = checkSum(data.get(1), data.get(2));
        if(code.equals("4")) res = checkUser(data.get(1));
        return res;
    }

    public String transaction(String cardFrom, String pin, String sum, String cardTo){
        String res;
        double s = Double.parseDouble(sum);
        CardServiceImpl cs = new CardServiceImpl();
        try {
            res = cs.makeTransaction(cardFrom, pin, s, cardTo);
        }
        catch (ArithmeticException e){
            res=e.getMessage();
        }
        catch (NullPointerException e){
            res=e.getMessage();
        }
        return res;
    }

    public String signIn(String cardNum, String pin){
        CardServiceImpl cs = new CardServiceImpl();
        return String.valueOf(cs.isCardExist(cardNum, pin));
    }

    public String getCash(String cardNum, String pin, String sum){
        String res="";
        double s = Double.parseDouble(sum);
        CardServiceImpl cs = new CardServiceImpl();
       try {
           res = cs.getCash(cardNum, pin, s);
       }
       catch (ArithmeticException e){
           res=e.getMessage();
           System.out.println(res);
       }
        return res;
    }

    public String checkSum(String cardNum, String pin){
        CardServiceImpl cs = new CardServiceImpl();
        return String.valueOf(cs.getSum(cardNum, pin));
    }

    public String checkUser (String cardNum){
        CardServiceImpl cs = new CardServiceImpl();
        return cs.checkUser(cardNum);
    }
}
