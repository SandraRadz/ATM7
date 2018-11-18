import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

class BankConnection {


    protected  String sendTransactionData(ArrayList<String> data, ObjectOutputStream out, ObjectInputStream in){
        try {
                out.writeObject(data);

                Object resData;

                while (true) {
                    resData = in.readUTF();//.readObject();
                    String opRes = (String) resData;
                    //String opRes = in.readUTF();
                    if (!opRes.equals(null)) {
                        return opRes;
                    }
                    else {
                        System.err.println("Data streaming to server error!");
                    }
                }

        } catch (UnknownHostException ex){

        } catch (IOException ex1){

        }
        return "fail: ERROR sending data to server";
    }
}
