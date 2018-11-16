package controller;

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
        String res="well";
        return res;
    }

}
