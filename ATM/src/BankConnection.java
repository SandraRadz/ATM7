import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class BankConnection {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    static int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
    static String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.

    public static String sendTransactionData(ArrayList<String> data){
        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.

//            InputStream sin = socket.getInputStream();
//            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());



           // try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                out.writeObject(data);

                Object resData;

                while (true) {
                    resData = in.readObject();
                    String opRes = (String) resData;
                    //String opRes = in.readUTF();
                    if (!opRes.equals(null)) {
                        return opRes;
                    }
                    else {
                        System.err.println("Data streaming to server error!");
                    }
                }

//            } catch (IOException ex) {
//                System.err.println("Data streaming to server error!");
//            }

        } catch (UnknownHostException ex){

        } catch (IOException ex1){

        } catch (ClassNotFoundException ex2) {

        }
        //return "";
        return "fail: ERROR sending data to server";
    }
}
