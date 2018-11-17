import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class BankConnection {
    private static DataOutputStream out;
    private static DataInputStream in;

    static int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
    static String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.

    public static String sendTransactionData(ArrayList<String> data){
        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);


            try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                oos.writeObject(data);

                while (true) {
                    String opRes = in.readUTF();
                    if (!opRes.equals(null)) {
                        return opRes;
                    }
                }

            } catch (IOException ex) {
                System.err.println("Data streaming to server error!");
            }

        } catch (UnknownHostException ex){

        } catch (IOException ex1){

        }
        return "";
        //return "ERROR sending data to server";
    }
}
