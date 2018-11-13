import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    private static DataOutputStream out;

    public static void main(String[] args) throws Exception {
        Screen c = new Screen();

        int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.
        // Здесь указан адрес того самого компьютера где будет исполняться и клиент.

        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just got hold of the program.");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            out = new DataOutputStream(sout);

            // Создаем поток для чтения с клавиатуры.
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            System.out.println();

            while (true) {
                line = keyboard.readLine(); // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                System.out.println("Sending this line to the server...");
                out.writeUTF(line); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                line = in.readUTF(); // ждем пока сервер отошлет строку текста.
                System.out.println("The server was very polite. It sent me this : " + line);
                System.out.println("Looks like the server is pleased with us. Go ahead and enter more lines.");
                System.out.println();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }

    }
//    public static boolean sendTransactionData(int data[]){
//        //ALGORITHM:
//        //1) sending incoming array length;
//        //2) sending ATM operation's code (1 - ..., 2 -, 3 - withdrawal).
//        //3) the server should process data accordingly to the operation's code
//        try {
//            out.writeInt(data.length);
//            for (int i = 0; i < data.length; i++) {
//                out.writeInt(data[i]);
//            }
//        } catch (IOException ex){
//            System.err.println("Data streaming to server error!");
//        }
//
//        //transaction successful; server responded positively
//        if (true){
//            return true;
//        }
//        //transaction unsuccessful
//        else {
//            return false;
//        }
//    }
    public static String sendTransactionData(ArrayList<String> data){
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(data);

            //out.writeInt(data.size());
//            for (int i = 0; i < data.length; i++) {
//                out.writeInt(data[i]);
//            }
        } catch (IOException ex){
            System.err.println("Data streaming to server error!");
        }
        return "";
    }
}
