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

                    result = doQuery((ArrayList<String>) arrayData);
                    out.writeUTF("DONE   "+result); // отсылаем клиенту обратно ту самую строку текста.
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
        return res;
    }

    public String transaction(String cardFrom, String pin, String sum, String cardTo){
        String res="done";
        double s = Double.parseDouble(sum);
        CardServiceImpl cs = new CardServiceImpl();
        cs.makeTransaction(cardFrom, pin, s ,cardTo );
        return res;
    }

    public String signIn(String cardNum, String pin){
        CardServiceImpl cs = new CardServiceImpl();
        return String.valueOf(cs.isCardExist(cardNum, pin));
    }

    public String getCash(String cardNum, String pin, String sum){
        String res="done";
        double s = Double.parseDouble(sum);
        CardServiceImpl cs = new CardServiceImpl();
        cs.getCash(cardNum, pin, s);
        return res;
    }

    public String checkSum(String cardNum, String pin){
        CardServiceImpl cs = new CardServiceImpl();
        return String.valueOf(cs.isCardExist(cardNum, pin));
    }
}
