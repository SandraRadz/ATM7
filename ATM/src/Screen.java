import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Screen extends JFrame {


     private String currentMenu = "";
     private String confirmingOp = "";
     private String nextMenu = "";
     private JPasswordField pin;
     private JPasswordField pinTimeout;
     private JTextArea card;
     private JTextArea sum;
     private JTextField userPin = new JTextField();
     private JTextField transferSumField;
     private JTextField transferRecipientNum;
     private JTextField withdrawSumField;
     private JTextArea date;
     private long lastInteractionTime;

    // private int[] operationData;
    private ArrayList<String> operationData;

    private BankConnection bc = new BankConnection();
    private  ObjectOutputStream out;
    private  ObjectInputStream in;

     int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
     String address = "127.0.0.1"; // это IP-адрес компьютера, где исполняется наша серверная программа.

    private  InetAddress ipAddress;
    private  Socket socket;

     private String cardNum = "2222222222222221"; //16 digit; tmp number

    //fillATMwMoney(): max money available in ATM = 508000 == (500*500) + (200 * 600) + (100 * 700) + (50 * 800) + (20 * 900) + (10 * 1000)
    //saved in order: [0] - 500s; [1] - 200s; [2] - 100s; [3] - 50s; [4] - 20s; [5] - 10s.
     private int[] bills = new int[6];

    public Screen () throws Exception {
        this.setTitle("ATM 7");
        //this.setIconImage(new ImageIcon("Logo/1try.png").getImage());
        this.setLayout(null);
        this.setSize(1000, 640);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        //this.getContentPane().setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        try
        {
            setUIFont(new javax.swing.plaf.FontUIResource("Serif",Font.PLAIN,15)); //Tahoma 14 // Palatino Linotype
        }
        catch(Exception e){}
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
        Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.

//            InputStream sin = socket.getInputStream();
//            OutputStream sout = socket.getOutputStream();

        // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());


        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(30,30,500,265);
        p.setBackground(Color.white);
        //p.setBorder(BorderFactory.createTitledBorder("Main Screen"));
        p.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        p.setVisible(false);
        this.add(p);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(30,315,500,265);
        p1.setBackground(Color.white);
        //p1.setBorder(BorderFactory.createTitledBorder("Keyboard"));
        p1.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        p1.setVisible(false);
        this.add(p1);

        JPanel p2 = new JPanel();
        p2.setBounds(560,30,410,550);
        p2.setBackground(Color.white);
//        p2.setBorder(BorderFactory.createTitledBorder("Card reception and money delivery"));
        p2.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        p2.setVisible(false);

        BufferedImage bankLogoPicture = ImageIO.read(new File("Logo/1try.png"));
        JLabel bankLogoLabel = new JLabel(new ImageIcon(bankLogoPicture.getScaledInstance(370, 200, Image.SCALE_SMOOTH)));
        p2.add(bankLogoLabel);

        //p2.add(new JSeparator(SwingConstants.HORIZONTAL));
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        Dimension d = separator.getPreferredSize();
        d.width = p2.getPreferredSize().width;
        separator.setPreferredSize(d);
        separator.setBackground(Color.gray);
        separator.setForeground(Color.gray);
        p2.add(separator);

        JLabel cardAcceptedLabel = new JLabel("We only accept: ", SwingConstants.CENTER);
        cardAcceptedLabel.setBounds(10,420,370, 30);
        p2.add(cardAcceptedLabel);

        BufferedImage cardLogoPicture = ImageIO.read(new File("Logo/card2.png"));
        JLabel cardLogoLabel = new JLabel(new ImageIcon(cardLogoPicture.getScaledInstance(200, 100, Image.SCALE_SMOOTH)), SwingConstants.CENTER);
        p2.add(cardLogoLabel);

        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        Dimension d1 = separator.getPreferredSize();
        d1.width = separator.getPreferredSize().width;
        separator1.setPreferredSize(d1);
        separator1.setBackground(Color.gray);
        separator1.setForeground(Color.gray);
        p2.add(separator1);

        BufferedImage cardEntrancePicture = ImageIO.read(new File("Logo/cardEntrance2.png"));
        JLabel cardEntranceLabelLabel = new JLabel(new ImageIcon(cardEntrancePicture.getScaledInstance(140, 20, Image.SCALE_SMOOTH)), SwingConstants.CENTER);
        p2.add(cardEntranceLabelLabel);

        BufferedImage moneyRetrPicture = ImageIO.read(new File("Logo/moneyRetr5.png"));
        JLabel moneyRetrLabel = new JLabel(new ImageIcon(moneyRetrPicture.getScaledInstance(320, 150, Image.SCALE_SMOOTH)), SwingConstants.CENTER);
        p2.add(moneyRetrLabel);

        this.add(p2);

        JButton b1 = new JButton("1");
        b1.setBounds(30,30,140,33);
        b1.setBackground(new Color(64,128,128));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b1.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        //b1.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        //b1.setBorder(new RoundedBorder(30));
        b1.setFont(new Font("Serif", Font.BOLD, 20));
        b1.setFocusPainted(false);
        p1.add(b1);

        JButton b2 = new JButton("2");
        b2.setBounds(180,30,140,33);
        b2.setBackground(new Color(64,128,128));
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b2.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b2.setFont(new Font("Serif", Font.BOLD, 20));
        b2.setFocusPainted(false);
        p1.add(b2);

        JButton b3 = new JButton("3");
        b3.setBounds(330,30,140,33);
        b3.setBackground(new Color(64,128,128));
        b3.setForeground(Color.WHITE);
        b3.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b3.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b3.setFont(new Font("Serif", Font.BOLD, 20));
        b3.setFocusPainted(false);
        p1.add(b3);

        JButton b4 = new JButton("4");
        b4.setBounds(30,73,140,33);
        b4.setBackground(new Color(64,128,128));
        b4.setForeground(Color.WHITE);
        b4.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b4.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b4.setFont(new Font("Serif", Font.BOLD, 20));
        b4.setFocusPainted(false);
        p1.add(b4);

        JButton b5 = new JButton("5");
        b5.setBounds(180,73,140,33);
        b5.setBackground(new Color(64,128,128));
        b5.setForeground(Color.WHITE);
        b5.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b5.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b5.setFont(new Font("Serif", Font.BOLD, 20));
        b5.setFocusPainted(false);
        p1.add(b5);

        JButton b6 = new JButton("6");
        b6.setBounds(330,73,140,33);
        b6.setBackground(new Color(64,128,128));
        b6.setForeground(Color.WHITE);
        b6.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b6.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b6.setFont(new Font("Serif", Font.BOLD, 20));
        b6.setFocusPainted(false);
        p1.add(b6);

        JButton b7 = new JButton("7");
        b7.setBounds(30,116,140,33);
        b7.setBackground(new Color(64,128,128));
        b7.setForeground(Color.WHITE);
        b7.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b7.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b7.setFont(new Font("Serif", Font.BOLD, 20));
        b7.setFocusPainted(false);
        p1.add(b7);

        JButton b8 = new JButton("8");
        b8.setBounds(180,116,140,33);
        b8.setBackground(new Color(64,128,128));
        b8.setForeground(Color.WHITE);
        b8.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b8.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b8.setFont(new Font("Serif", Font.BOLD, 20));
        b8.setFocusPainted(false);
        p1.add(b8);

        JButton b9 = new JButton("9");
        b9.setBounds(330,116,140,33);
        b9.setBackground(new Color(64,128,128));
        b9.setForeground(Color.WHITE);
        b9.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b9.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b9.setFont(new Font("Serif", Font.BOLD, 20));
        b9.setFocusPainted(false);
        p1.add(b9);

        JButton b_quit = new JButton("QUIT");
        b_quit.setBounds(30,159,140,33);
//        b_quit.setBackground(new Color(64,128,128));
        b_quit.setBackground(new Color(105,115,127));
        b_quit.setForeground(Color.WHITE);
        b_quit.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b_quit.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b_quit.setFont(new Font("Serif", Font.BOLD, 20));
        b_quit.setFocusPainted(false);
        p1.add(b_quit);

        JButton b0 = new JButton("0");
        b0.setBounds(180,159,140,33);
        b0.setBackground(new Color(64,128,128));
        b0.setForeground(Color.WHITE);
        b0.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b0.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b0.setFont(new Font("Serif", Font.BOLD, 20));
        b0.setFocusPainted(false);
        p1.add(b0);


        JButton b_grate = new JButton("#");
        b_grate.setBounds(330,159,140,33);
        //b_grate.setBackground(new Color(64,128,128));
        b_grate.setBackground(new Color(105,115,127));
        b_grate.setForeground(Color.WHITE);
        b_grate.setFont(new Font("Tahoma", Font.PLAIN, 18));
        b_grate.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        b_grate.setFont(new Font("Serif", Font.BOLD, 20));
        b_grate.setFocusPainted(false);
        p1.add(b_grate);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(30,202,140,33);
//        cancel.setBackground(new Color(64,128,128));
        cancel.setBackground(new Color(105,115,127));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        cancel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        cancel.setFont(new Font("Serif", Font.BOLD, 20));
        cancel.setFocusPainted(false);
        p1.add(cancel);

        JButton delete = new JButton("Delete");
        delete.setBounds(180,202,140,33);
        //delete.setBackground(new Color(64,128,128));
        delete.setBackground(new Color(105,115,127));
        delete.setForeground(Color.WHITE);
        delete.setFont(new Font("Tahoma", Font.PLAIN, 18));
        delete.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        delete.setFont(new Font("Serif", Font.BOLD, 20));
        delete.setFocusPainted(false);
        p1.add(delete);

        JButton ok = new JButton("OK");
        ok.setBounds(330,202,140,33);
//        ok.setBackground(new Color(64,128,128));
        ok.setBackground(new Color(105,115,127));
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Tahoma", Font.PLAIN, 18));
        ok.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        ok.setFont(new Font("Serif", Font.BOLD, 20));
        ok.setFocusPainted(false);
        p1.add(ok);

        //keyboard buttons functions logic
        b1.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"1");
                        userPin.setText(userPin.getText()+"1");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"1");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"1");
                    }
                case "successfulLoginMenu":
                    p.removeAll();
                    p.updateUI();
                    nextMenu =  "transferMenu";
                    if (!timeout()) transferMenu(p);
                    else PINTimeout(p);
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"1");
                    }
                    break;
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "1");
                    }
                    break;
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"1");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "1");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("1")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "1-");
                        }else{
                            date.setText(date.getText() + "1");
                        }
                    }
                    break;
            }
        });

        b2.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"2");
                        userPin.setText(userPin.getText()+"2");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"2");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"2");
                    }
                case "successfulLoginMenu":
//              else if(currentMenu.equals("successfulLoginMenu")){
//                  p.removeAll();
//                  p.updateUI();
//                  nextMenu =  "cashBalances";
//                  if (!timeout()) cashBalances(p);
//                  else  PINTimeout(p);
//              }
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"2");
                    }
                    break;
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "2");
                    }
                    break;
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"2");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "2");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("2")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "2-");
                        }else{
                            date.setText(date.getText() + "2");
                        }
                    }
                    break;
            }
        });

        b3.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"3");
                        userPin.setText(userPin.getText()+"3");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"3");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"3");
                    }
                case "successfulLoginMenu":
                    p.removeAll();
                    p.updateUI();
                    nextMenu =  "withdrawMenu";
                    if (!timeout()) withdrawMenu(p);
                    else PINTimeout(p);
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"3");
                    }
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "3");
                    }
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"3");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "3");
                    }
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("3")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "3-");
                        }else{
                            date.setText(date.getText() + "3");
                        }
                    }
                    break;
            }
        });

        b4.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"4");
                        userPin.setText(userPin.getText()+"4");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"4");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"4");
                    }
                case "successfulLoginMenu":
                    p.removeAll();
                    p.updateUI();
                    nextMenu =  "balanceMenu";

                    readBills();

                    if (!timeout()) {
                        operationData = new ArrayList<String>();
                        operationData.add("3");//check balance operation code
                        operationData.add(cardNum);//current client's card number
                        operationData.add(new Password(userPin.getText()).getHash()); //pin hash
                        String balSum = bc.sendTransactionData(operationData, out, in);
                        if (!balSum.contains("fail")) balanceMenu(p, balSum);
                        else displayOpError(this, p, "Failed to transfer data.");
                    }
                    else {
                        PINTimeout(p);
                    }
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"4");
                    }
                    break;
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "4");
                    }
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"4");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "4");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("4")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "4-");
                        }else{
                            date.setText(date.getText() + "4");
                        }
                    }
                    break;
            }
        });

        b5.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"5");
                        userPin.setText(userPin.getText()+"5");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"5");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"5");
                    }
                case "successfulLoginMenu":
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"5");
                    }
                    break;
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "5");
                    }
                    break;
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"5");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "5");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("5")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "5-");
                        }else{
                            date.setText(date.getText() + "5");
                        }
                    }
                    break;
            }
        });

        b6.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"6");
                        userPin.setText(userPin.getText()+"6");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"6");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"6");
                    }
                case "successfulLoginMenu":
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"6");
                    }
                    break;
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "6");
                    }
                    break;
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"6");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "6");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("6")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "6-");
                        }else{
                            date.setText(date.getText() + "6");
                        }
                    }
                    break;
            }
        });

        b7.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"7");
                        userPin.setText(userPin.getText()+"7");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"7");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"7");
                    }
                case "successfulLoginMenu":
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"7");
                    }
                    break;
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "7");
                    }
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"7");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "7");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("7")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "7-");
                        }else{
                            date.setText(date.getText() + "7");
                        }
                    }
                    break;
            }
        });

        b8.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"8");
                        userPin.setText(userPin.getText()+"8");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"8");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"8");
                    }
                    break;
                case "successfulLoginMenu":
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"8");
                    }
                    break;
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "8");
                    }
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"8");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "8");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("8")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "8-");
                        }else{
                            date.setText(date.getText() + "8");
                        }
                    }
                    break;
            }
        });

        b9.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"9");
                        userPin.setText(userPin.getText()+"9");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"9");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"9");
                    }
                case "successfulLoginMenu":
                    break;
                case "cashBalances_sum":
                    if (sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"9");
                    }
                    break;
                case "transferMenu":
                    if(transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "9");
                    }
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"9");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if (withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "9");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("9")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "9-");
                        }else{
                            date.setText(date.getText() + "9");
                        }
                    }
                    break;
            }
        });

        b_quit.addActionListener((ActionEvent e) -> {
            userPin.setText("");
            p.removeAll();
            p.updateUI();
            PIN(p);

        });

        b0.addActionListener((ActionEvent e) -> {
            switch (currentMenu){
                case "PIN":
                    if (pin.getText().length() < 4){
                        pin.setText(pin.getText()+"0");
                        userPin.setText(userPin.getText()+"0");
                    }
                    break;
                case "PINTimeout":
                    if ( pinTimeout.getText().length() < 4){
                        pinTimeout.setText(pinTimeout.getText()+"0");
                    }
                    break;
                case "cashBalances":
                    if ( card.getText().length() < 16) {
                        card.setText(card.getText()+"0");
                    }
                    break;
                case "successfulLoginMenu":
                    break;
                case "cashBalances_sum":
                    if (!sum.getText().equals("") && sum.getText().length() < 6) {
                        sum.setText(sum.getText()+"0");
                    }
                    break;
                case "transferMenu":
                    if(!transferSumField.getText().equals("") && transferSumField.getText().length() < 6) {
                        transferSumField.setText(transferSumField.getText() + "0");
                    }
                    break;
                case "transferMenuSecond" :
                    if (transferRecipientNum.getText().length() < 16){
                        transferRecipientNum.setText(transferRecipientNum.getText()+"0");
                    }
                    break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu":
                    if ( !withdrawSumField.getText().equals("") && withdrawSumField.getText().length() < 6){
                        withdrawSumField.setText(withdrawSumField.getText() + "0");
                    }
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    if(date.getText().length() < 10 && !date.getText().equals("0")){
                        if (date.getText().length() == 3 || date.getText().length() == 6) {
                            date.setText(date.getText() + "0-");
                        }else{
                            date.setText(date.getText() + "0");
                        }
                    }
                    break;
            }
        });

/*        b_grate.addActionListener((ActionEvent e) -> {
            switch (currentMenu) {
                case "PIN":
                    break;
                case "PINTimeout":
                    break;
                case "cashBalances":
                    break;
                case "successfulLoginMenu":
                    break;
                case "cashBalances_sum":
                    break;
                case "transferMenu":
                    break;
                case "transferMenuSecond":
                    break;
                case "balanceMenu":
                    break;
                case "withdrawMenu":
                    break;
                case "confirmMenu":
                    break;
                case "cashBalances_date":
                    break;
            }
        });*/

        cancel.addActionListener((ActionEvent e) -> {
            switch (currentMenu) {
                case "PIN":
                    pin.setText("");
                    userPin.setText("");
                    break;
                case "PINTimeout":
                    pinTimeout.setText("");
                    break;
                case "cashBalances":
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) successfulLoginMenu(p);
                    else PINTimeout(p);
                    break;
                case "successfulLoginMenu":
//                  p.removeAll();
//                  p.updateUI();
//                  nextMenu = "successfulLoginMenu";
//                  if (!timeout()) successfulLoginMenu(p)
//                  else PINTimeout(p);break;
                case "cashBalances_sum":
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) successfulLoginMenu(p);
                    else PINTimeout(p);
                    break;
                case "transferMenu":
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) successfulLoginMenu(p);
                    else PINTimeout(p);
                    break;
                case "transferMenuSecond":
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) successfulLoginMenu(p);
                    else PINTimeout(p);
                    break;
                case "balanceMenu" :
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) successfulLoginMenu(p);
                    else PINTimeout(p);
                    break;
                case "withdrawMenu":
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) successfulLoginMenu(p);
                    else PINTimeout(p);
                    break;
                case "confirmMenu":
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) successfulLoginMenu(p);
                    else PINTimeout(p);
                    break;

                case "cashBalances_date":
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) successfulLoginMenu(p);
                    else PINTimeout(p);
                    break;
            }

        });

        delete.addActionListener((ActionEvent e) -> {
            switch (currentMenu) {
                case "PIN" :
                {String str = pin.getText();
                    if (str != null && str.length() > 0) {
                        pin.setText(str.substring(0, str.length() - 1));
                        userPin.setText(userPin.getText().substring(0,userPin.getText().length()-1));
                    }}
                break;
                case "PINTimeout" :
                {String str = pinTimeout.getText();
                    if (str != null && str.length() > 0) pinTimeout.setText(str.substring(0, str.length() - 1));}
                break;
                case "cashBalances" :
                {String str = card.getText();
                    if (str != null && str.length() > 0) card.setText(str.substring(0, str.length() - 1));}
                break;
                case "cashBalances_sum" :
                {String str = sum.getText();
                    if (str != null && str.length() > 0) sum.setText(str.substring(0, str.length() - 1));}
                break;
                case "transferMenu" :
                {String str = transferSumField.getText();
                    if (str != null && str.length() > 0) transferSumField.setText(str.substring(0, str.length() - 1));}
                break;
                case "transferMenuSecond" :
                {String str = transferRecipientNum.getText();
                    if (str != null && str.length() > 0) transferRecipientNum.setText(str.substring(0, str.length() - 1));}
                break;
                case "balanceMenu" :
                    break;
                case "withdrawMenu" :
                {String str = withdrawSumField.getText();
                    if (str != null && str.length() > 0) withdrawSumField.setText(str.substring(0, str.length() - 1));
                }
                break;
                case "confirmMenu" :
                    break;
                case "cashBalances_date" :
                {String str = date.getText();
                    if (str != null && str.length() > 0) {
                        if (date.getText().length() == 6 || date.getText().length() == 9) date.setText(str.substring(0, str.length() - 2));
                        else date.setText(str.substring(0, str.length() - 1));
                    }
                }
                break;

            }
        });

        ok.addActionListener((ActionEvent e) -> {
            switch (currentMenu)
            {
                case "PIN":
                    if (pin.getText().length() == 4) {
                        p.removeAll();
                        p.updateUI();
                        lastInteractionTime = Instant.now().getEpochSecond();
                        operationData = new ArrayList<String>();
                        operationData.add("0");//check pin
                        operationData.add(cardNum);//current client's card number
                        operationData.add(new Password(userPin.getText()).getHash()); //pin hash
                        String confPIN = bc.sendTransactionData(operationData, out, in);
//                      if (confPIN.equals("true")){
//                      if (confPIN.equals("done")){
                        if (!confPIN.contains("false") && !confPIN.contains("fail")){
                            successfulLoginMenu(p);
                        }
                        else {
                            userPin.setText("");
                            displayOpError(this, p, "Incorrect PIN. Please enter your PIN again.");
                            //displayOpError(this, p, confPIN);
                            p.removeAll();
                            p.updateUI();
                            PIN(p);
                        }
                    }
                    break;

                case "PINTimeout":
                    //TODO validate via db
                    if (pinTimeout.getText().length() == 4) {
                        ArrayList<String> operationDataTimeout = new ArrayList<String>();
                        operationDataTimeout.add("0");//check pin
                        operationDataTimeout.add(cardNum);//current client's card number
                        operationDataTimeout.add(new Password(pinTimeout.getText()).getHash()); //pin hash
                        String confPIN = bc.sendTransactionData(operationDataTimeout, out, in);

                        if (!confPIN.contains("false") && !confPIN.contains("fail")){
                            p.removeAll();
                            p.updateUI();
                            lastInteractionTime = Instant.now().getEpochSecond();
                            String nextMenuTmp = nextMenu;
                            nextMenu = "";
                            switch (nextMenuTmp){
                                case "cashBalances":
                                    currentMenu = nextMenuTmp;
                                    cashBalances(p);
                                    break;
                                case "cashBalances_sum":
                                    currentMenu = nextMenuTmp;
                                    cashBalances_sum(p);
                                    break;
                                case "cashBalances_date":
                                    currentMenu = nextMenuTmp;
                                    cashBalances_date(p);
                                    break;
                                case "successfulLoginMenu":
                                    currentMenu = nextMenuTmp;
                                    successfulLoginMenu(p);
                                    break;
                                case "transferMenu":
                                    currentMenu = nextMenuTmp;
                                    transferMenu(p);
                                    break;
                                case "transferMenuSecond":
                                    currentMenu = nextMenuTmp;
                                    transferMenu(p);
                                    break;
                                case "balanceMenu":
                                    currentMenu = nextMenuTmp;
                                    operationData = new ArrayList<String>();
                                    operationData.add("3");//check balance operation code
                                    operationData.add(cardNum);//current client's card number
                                    operationData.add(new Password(userPin.getText()).getHash()); //pin hash
                                    String balSum = bc.sendTransactionData(operationData, out, in);
                                    if (!balSum.contains("fail")) balanceMenu(p, balSum);
                                    else displayOpError(this, p, "Failed to transfer data.");
                                    balanceMenu(p, balSum);
                                    break;
                                case "withdrawMenu":
                                    currentMenu = nextMenuTmp;
                                    withdrawMenu(p);
                                    break;
                                case "confirmMenu":
                                    currentMenu = nextMenuTmp;
                                    confirmMenu(p, confirmingOp);
                                    break;
                                default:
                                    currentMenu = "successfulLoginMenu";
                                    successfulLoginMenu(p);

                            }
                        }
                    }
                    else {
                        userPin.setText("");
                        //displayOpError(this, p, "Incorrect PIN. Please enter your PIN again.");
                        displayOpError(this, p, "fail: wrong pin_timeout");
                        p.removeAll();
                        p.updateUI();
                        PINTimeout(p);
                    }
                    break;
                case "cashBalances":
                    //TODO validate via db
                    if(card.getText().length() == 16) {
                        p.removeAll();
                        p.updateUI();
                        nextMenu = "cashBalances_sum";
                        if (!timeout()) cashBalances_sum(p);
                        else PINTimeout(p);
                    }
                    break;
                case "successfulLoginMenu":
                    break;
                case "cashBalances_sum":
                    //TODO validate via db
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "cashBalances_date";
                    if (!timeout()) cashBalances_date(p);
                    else PINTimeout(p);
                    break;
                case "transferMenu":
                    if ( transferSumField.getText().length() > 0){
                        p.removeAll();
                        p.updateUI();
                        nextMenu = "transferMenuSecond";

                        operationData = new ArrayList<String>();
                        operationData.add("1");//transfer operation code
                        operationData.add(cardNum);//current client's card number
                        operationData.add(new Password(userPin.getText()).getHash()); //pin hash
                        operationData.add(transferSumField.getText());//transfer sum

                        if (!timeout()) {
                            transferMenuSecond(p);
                        }
                        else {
                            PINTimeout(p);
                        }
                    }
                    break;
                case "transferMenuSecond":
                    if  (transferRecipientNum.getText().length() == 16){
                        p.removeAll();
                        p.updateUI();
                        nextMenu = "confirmMenu";
                        confirmingOp = "confirmTransfer";

                        operationData.add(transferRecipientNum.getText());//recipient card number

                        if (operationData.get(1).equals(operationData.get(4))){
//                            displayOpError(this, p, "Recipient's (beneficiary's) card number is the same as the sender's (benefactor's).");
                            displayOpError(this, p, "Recipient's card number is the same as the sender's one.");
                            p.removeAll();
                            p.updateUI();
                            successfulLoginMenu(p);
                        }
                        else {
                            if (!timeout()) {
                                confirmMenu(p, "confirmTransfer");
                            } else {
                                PINTimeout(p);
                            }
                        }
                    }
                    break;
                case "balanceMenu":
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "successfulLoginMenu";
                    if (!timeout()) {
                        successfulLoginMenu(p);
                    }
                    else {
                        PINTimeout(p);
                    }
                    break;
                case "withdrawMenu":
                    if ( withdrawSumField.getText().length() > 0){
                        p.removeAll();
                        p.updateUI();
                        nextMenu = "confirmMenu";
                        confirmingOp = "confirmWithdrawal";

                        operationData = new ArrayList<String>();
                        operationData.add("2");//withdrawal operation code
                        operationData.add(cardNum);//current client's card number
                        operationData.add(new Password(userPin.getText()).getHash()); //pin hash
                        operationData.add(withdrawSumField.getText());//withdrawal sum

                        if (!timeout()) {
                            confirmMenu(p, "confirmWithdrawal");
                        }
                        else {
                            PINTimeout(p);
                        }
                    }
                    break;
                case "confirmMenu":
                    p.removeAll();
                    p.updateUI();
                    //nextMenu = "successfulLoginMenu";
                    //TODO important notice (!) - in case of timeout during confirmation, the client is returned back to the confirmation (once again) after enetirng timeout_pin successfully.
                    nextMenu = "confirmMenu";
                    if (!timeout()){
                        switch (confirmingOp){
                            case "confirmWithdrawal" :
                                //check whether there are enough needed bills in ATM
                                int[] blsNeeded = calcNeededBills(Integer.valueOf(operationData.get(3)));
                                if (blsNeeded.length == 0) {
                                    displayOpError(this, p, "Not enough bills for giving out such sum.");
                                    p.removeAll();
                                    p.updateUI();
                                    successfulLoginMenu(p);
                                }else {
                                    //send data to server for processing
                                    String opRes = bc.sendTransactionData(operationData, out, in);
                                    if (opRes.contains("fail")) {
                                        displayOpError(this, p, opRes);
                                        p.removeAll();
                                        p.updateUI();
                                        successfulLoginMenu(p);
                                    } else {
                                        //change the values of the bills available => give out money
                                        updateBillsValues(blsNeeded);
                                        //TODO ...not implementing error during changing bills count in the ATM as of right now
                                        boolean rewritingBillsRes = writeBills();
//                                      if (!rewritingBillsRes) {
//                                          displayOpError(this, p);
//                                          p.removeAll();
//                                          p.updateUI();
//                                          successfulLoginMenu(p);
//                                      } else {

                                        //displayOpSuccess(this, p, opRes);
                                        displayOpSuccess(this, p, "receive the money.");
                                        p.removeAll();
                                        p.updateUI();
                                        successfulLoginMenu(p);

//                                      }
                                    }
                                }
                                break;
                            case "confirmTransfer" :
                                //send data to server for processing
                                String opRes = bc.sendTransactionData(operationData, out, in);
                                if (opRes.contains("fail")) {
                                    displayOpError(this, p, opRes);
                                    p.removeAll();
                                    p.updateUI();
                                    successfulLoginMenu(p);
                                } else {
                                    //displayOpSuccess(this, p, opRes);
                                    displayOpSuccess(this, p, "transfer completed.");
                                    p.removeAll();
                                    p.updateUI();
                                    successfulLoginMenu(p);
                                }
                                break;
                            case "confirmBalances":
                                //TODO implement at some point
                                break;
                        }
                        confirmingOp = "";
                    }
                    else {
                        //confirmingOp = "";
                        PINTimeout(p);
                    }
                    break;
                case "cashBalances_date":
                    //TODO validate via db
                    if(date.getText().length() == 10) {
                        p.removeAll();
                        p.updateUI();
                        nextMenu = "confirmMenu";
                        confirmingOp = "confirmBalances";
                        if (!timeout()) {
                            confirmMenu(p, "confirmBalances");
                        }
                        else {
                            PINTimeout(p);
                        }
                    }
                    break;
            }
        });




        PIN(p);
        //p.setVisible(true);
        p1.setVisible(true);
        p2.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        //add max amount of bills to ATM
        //fillATMwMoney();

        //add money to the ATM - just a little of them.
        //fillATMwLittleMoney();

        //Read bills info.
        boolean getBillsInfo = readBills();
        if (!getBillsInfo){
            displayOpError(this, p, "NON_OPERATIONAL_STATE", "CALL_ATM_SERVICE");
            //TODO alt+f4 should exit program fully
        }
    }

    //PIN entering
    private  void PIN(JPanel p){
        currentMenu = "PIN";
        JLabel l = new JLabel("Enter your PIN here: ", SwingConstants.CENTER);
        l.setBounds(150,100,200, 30);
        p.add(l);
        pin = new JPasswordField();
        pin.setEchoChar('•');
        pin.setBounds(150, 140, 200,30);
        pin.setBackground(Color.GRAY);
        pin.setForeground(Color.WHITE);
        //pin.setHighlighter(null);
        pin.setFocusable(false);
        pin.setEditable(false);
        p.add(pin);
        p.setVisible(true);
    }

    private  void PINTimeout(JPanel p){
        currentMenu = "PINTimeout";
        JLabel l = new JLabel("Session timeout. Please, re-enter your PIN: ", SwingConstants.CENTER);
        l.setBounds(100,100,300, 30);
        p.add(l);
        pinTimeout = new JPasswordField();
        pinTimeout.setEchoChar('•');
        pinTimeout.setBounds(150, 140, 200,30);
        pinTimeout.setBackground(Color.GRAY);
        pinTimeout.setForeground(Color.WHITE);
        pinTimeout.setFocusable(false);
        pinTimeout.setEditable(false);
        p.add(pinTimeout);
        p.setVisible(true);

    }


    //Processing of cash balances
    private  void cashBalances (JPanel p){
        currentMenu = "cashBalances";
        JLabel l = new JLabel("Processing of cash balances");
        l.setBounds(120,50,300, 30);
        l.setFont(new Font("Georgia", Font.BOLD, 18));
        p.add(l);

        JLabel l1 = new JLabel("Enter the recipient's card number"); //(16 digits)!
        l1.setBounds(140,100,250, 30);
        l1.setFont(new Font("Georgia", Font.PLAIN, 14));
        p.add(l1);

        card = new JTextArea();
        card.setBounds(140, 130, 210,30);
        card.setBackground(Color.GRAY);
        card.setForeground(Color.WHITE);
        card.setFont(new Font("Times New Roman", Font.BOLD, 18));
        card.setFocusable(false);
        card.setEditable(false);
        p.add(card);


        p.setVisible(true);

    }


    //Processing of cash balances --> choosing sum
    private  void cashBalances_sum (JPanel p) {
        currentMenu = "cashBalances_sum";
        JLabel l = new JLabel("Processing of cash balances");
        l.setBounds(120, 50, 300, 30);
        l.setFont(new Font("Georgia", Font.BOLD, 18));
        p.add(l);

        JLabel l1 = new JLabel("Enter the upper limit of the amount");
        l1.setBounds(140, 100, 220, 30);
        l1.setFont(new Font("Georgia", Font.PLAIN, 14));
        p.add(l1);

        sum = new JTextArea();
        sum.setBounds(140, 130, 220, 30);
        sum.setBackground(Color.GRAY);
        sum.setForeground(Color.WHITE);
        sum.setFont(new Font("Times New Roman", Font.BOLD, 18));
        sum.setFocusable(false);
        sum.setEditable(false);
        p.add(sum);

        p.setVisible(true);

    }

    //Processing of cash balances --> choosing date
    private  void cashBalances_date (JPanel p) {
        currentMenu = "cashBalances_date";

        JLabel l1 = new JLabel("Choose start paying date (format: yyyy-MM-dd): ");
        l1.setBounds(120, 30, 320, 30);
        l1.setFont(new Font("Georgia", Font.PLAIN, 14));
        p.add(l1);

        JLabel l2 = new JLabel("- Leave the field as is to start paying today.");
        l2.setBounds(140, 60, 300, 30);
        l2.setFont(new Font("Georgia", Font.PLAIN, 14));
        p.add(l2);

        JLabel l3 = new JLabel("- Or DELETE and re-enter starting date: ");
        l3.setBounds(140, 90, 300, 30);
        l3.setFont(new Font("Georgia", Font.PLAIN, 14));
        p.add(l3);

        date = new JTextArea();
        date.setBounds(120, 120, 220, 30);
        date.setBackground(Color.GRAY);
        date.setForeground(Color.WHITE);
        date.setFont(new Font("Times New Roman", Font.BOLD, 18));
        date.setFocusable(false);
        date.setEditable(false);
        p.add(date);
        date.setVisible(true);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String today_date = simpleDateFormat.format(new Date());

        date.setText(today_date);

        p.setVisible(true);
    }

    private  void successfulLoginMenu(JPanel p){
        currentMenu = "successfulLoginMenu";
        JLabel l = new JLabel("Welcome. Please, select the operation:");
        l.setBounds(135,60,300, 30);
        p.add(l);

//        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
//        separator.setBackground(Color.gray);
//        separator.setForeground(Color.gray);
//        separator.setBounds(115, 90, 300, 3);
//        p.add(separator);
        JPanel pLabels = new JPanel();
        pLabels.setLayout(null);
        pLabels.setBounds(135,90,230,95);// height = 130
        pLabels.setBackground(Color.white);
        pLabels.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        pLabels.setOpaque(false);
        pLabels.setVisible(true);
        p.add(pLabels);

        JLabel lOptions = new JLabel("1 - Transfer");
        lOptions.setBounds(195,90,200, 30);
        p.add(lOptions);
//        JLabel lOptions2 = new JLabel("2 - Money excesses management"); //TODO Remove for now.
//        lOptions2.setBounds(150,120,300, 30);
//        p.add(lOptions2);
        JLabel lOptions3 = new JLabel("3 - Withdraw");
        lOptions3.setBounds(195,120,200, 30); //y = 150
        p.add(lOptions3);
        JLabel lOptions4 = new JLabel("4 - Check balance");
        lOptions4.setBounds(195,150,200, 30); //y = 180
        p.add(lOptions4);
//        JLabel lOptions5 = new JLabel("Cancel - Quit");
//        lOptions5.setBounds(150,210,200, 30);
//        p.add(lOptions5);

        p.setVisible(true);
    }

    private  void transferMenu(JPanel p){
        currentMenu = "transferMenu";
        JLabel l = new JLabel("Enter amount to transfer: ");
        l.setBounds(150,60,200, 30);
        p.add(l);
        transferSumField = new JTextField();
        transferSumField.setBounds(150, 90, 200,30);
        transferSumField.setFocusable(false);
        transferSumField.setEditable(false);
        transferSumField.setBackground(Color.GRAY);
        transferSumField.setForeground(Color.WHITE);
        p.add(transferSumField);

        p.setVisible(true);
    }

    private  void transferMenuSecond(JPanel p){
        currentMenu = "transferMenuSecond";
        JLabel l = new JLabel("Enter the recipient's card number"); //(16 digits)!
        l.setBounds(140,100,250, 30);
        l.setFont(new Font("Georgia", Font.PLAIN, 14));
        p.add(l);

        transferRecipientNum = new JTextField();
        transferRecipientNum.setBounds(140, 130, 200,30);
        transferRecipientNum.setFocusable(false);
        transferRecipientNum.setEditable(false);
        transferRecipientNum.setBackground(Color.GRAY);
        transferRecipientNum.setForeground(Color.WHITE);
        p.add(transferRecipientNum);

        p.setVisible(true);
    }

    private  void balanceMenu(JPanel p, String balSum){
        currentMenu = "balanceMenu";
        //TODO get balance via DB
        JLabel l = new JLabel("Your balance is: $" + balSum);
        l.setBounds(150,100,200, 30);
        p.add(l);
        p.setVisible(true);
    }

    private  void withdrawMenu(JPanel p){
        currentMenu = "withdrawMenu";



        JLabel l = new JLabel("Available banknotes are: ");
        l.setBounds(150,30,200, 30);
        p.add(l);
        //TODO list banknotes w/ amounts
        JLabel l500 = new JLabel("$500 ", SwingConstants.CENTER);
        l500.setBounds(90,60,35, 30);
        if(bills[0] == 0){
            l500.setOpaque(true);
            l500.setBackground(Color.lightGray);
        }
        p.add(l500);

        JLabel l200 = new JLabel("$200 ", SwingConstants.CENTER);
        l200.setBounds(120,60,35, 30);
        if(bills[1] == 0){
            l200.setOpaque(true);
            l200.setBackground(Color.lightGray);
        }
        p.add(l200);

        JLabel l100 = new JLabel("$100 ", SwingConstants.CENTER);
        l100.setBounds(150,60,35, 30);
        if(bills[2] == 0){
            l100.setOpaque(true);
            l100.setBackground(Color.lightGray);
        }
        p.add(l100);

        JLabel l50 = new JLabel("$50 ", SwingConstants.CENTER);
        l50.setBounds(180,60,30, 30);
        if(bills[3] == 0){
            l50.setOpaque(true);
            l50.setBackground(Color.lightGray);
        }
        p.add(l50);

        JLabel l20 = new JLabel("$20 ", SwingConstants.CENTER);
        l20.setBounds(210,60,30, 30);
        if(bills[4] == 0){
            l20.setOpaque(true);
            l20.setBackground(Color.lightGray);
        }
        p.add(l20);

        JLabel l10 = new JLabel("$10", SwingConstants.CENTER);
        l10.setBounds(240,60,30, 30);
        if(bills[5] == 0){
            l10.setOpaque(true);
            l10.setBackground(Color.lightGray);
        }
        p.add(l10);

        JLabel l1 = new JLabel("Enter amount for withdrawal: ");
        l1.setBounds(150,90,200, 30);
        p.add(l1);
        p.setVisible(true);

        withdrawSumField = new JTextField();
        withdrawSumField.setBounds(150, 120, 200,30);
        withdrawSumField.setBackground(Color.GRAY);
        withdrawSumField.setForeground(Color.WHITE);
        withdrawSumField.setFocusable(false);
        withdrawSumField.setEditable(false);
        p.add(withdrawSumField);
    }

    private void confirmMenu(JPanel p, String s){
        currentMenu = "confirmMenu";

        JLabel l;
        JLabel l1;
        String operation = s;
        //TODO get corresponding data from DB
        switch (operation) {
            case "confirmWithdrawal":
            {String sum = operationData.get(3);
                //l = new JLabel("You are about to withdraw: $" + String.valueOf(sum));
                l = new JLabel("You are about to withdraw: $" + sum);
                l.setBounds(150, 60, 320, 30);
                p.add(l);}
            break;
            case "confirmTransfer":
            {String sum = operationData.get(3);
                String cardn = operationData.get(4);
                l = new JLabel("You are about to transfer: $" + sum);
                l.setBounds(150, 60, 320, 30);
                p.add(l);
                l1 = new JLabel("To a card #" + cardn);
                l1.setBounds(150, 90, 320, 30);
                p.add(l1);}
            break;
            case "confirmBalances":
            {l = new JLabel("You are about to change balances: ");
                l.setBounds(150, 60, 320, 30);
                p.add(l);}
            break;
        }
        p.setVisible(true);
    }
    //Additional methods
    public  void setUIFont(javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
        }
    }

//    private  class RoundedBorder implements Border {
//
//        private int radius;
//
//
//        RoundedBorder(int radius) {
//            this.radius = radius;
//        }
//
//
//        public Insets getBorderInsets(Component c) {
//            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
//        }
//
//
//        public boolean isBorderOpaque() {
//            return true;
//        }
//
//
//        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
//        }
//    }

    private  boolean readBills(){
        try (FileInputStream fis = new FileInputStream("bills.ser")){
            try (ObjectInputStream ois = new ObjectInputStream(fis)){
                bills = ((int[]) ois.readObject());
            } catch (IOException ex){
                return false;
            } catch (ClassNotFoundException ex1){
                return false;
            }
       } catch (FileNotFoundException ex){
           //throw Error = new Error("Error: Can't read bills");
            return false;
       } catch (IOException ex1){
            return false;
       }
        return true;
    }

    private  boolean writeBills(){
        try (FileOutputStream fos = new FileOutputStream("bills.ser")){
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)){
                oos.writeObject(bills);
            } catch (IOException ex) {
                return false;
            }
        } catch (FileNotFoundException ex){
            //throw Error = new Error("Error: Can't read bills");
            return false;
        } catch (IOException ex1){
            return false;
        }
        return true;
    }

    private  int[] calcNeededBills(int sumReq){
//TODO implement bills counting
        int[] blsNeeded = new int[6];
        int sumPossible = sumReq;

        //available bills
        int a500 = bills[0];
        int a200 = bills[1];
        int a100 = bills[2];
        int a50 = bills[3];
        int a20 = bills[4];
        int a10 = bills[5];

        //required bills
        int w500 = sumPossible / 500;

        for (int i = w500; i >= 0; i--){
            if (a500 - i >= 0){
                w500 = i;
                break;
            }
        }
        sumPossible = sumPossible - (500 * w500);

        int w200 = sumPossible / 200;

        for (int i = w200; i >= 0; i--){
            if (a200 - i >= 0){
                w200 = i;
                break;
            }
        }
        sumPossible = sumPossible - (200 * w200);

        int w100 = sumPossible / 100;

        for (int i = w100; i >= 0; i--){
            if (a100 - i >= 0){
                w100 = i;
                break;
            }
        }
        sumPossible = sumPossible - (100 * w100);

        int w50 = sumPossible / 50;

        for (int i = w50; i >= 0; i--){
            if (a50 - i >= 0){
                w50 = i;
                break;
            }
        }
        sumPossible = sumPossible - (50 * w50);

        int w20 = sumPossible / 20;

        for (int i = w20; i >= 0; i--){
            if (a20 - i >= 0){
                w20 = i;
                break;
            }
        }
        sumPossible = sumPossible - (20 * w20);

        int w10 = sumPossible / 10;

        for (int i = w10; i >= 0; i--){
            if (a10 - i >= 0){
                w10 = i;
                break;
            }
        }
        sumPossible = sumPossible - (10 * w10);

        blsNeeded[0] = w500;
        blsNeeded[1] = w200;
        blsNeeded[2] = w100;
        blsNeeded[3] = w50;
        blsNeeded[4] = w20;
        blsNeeded[5] = w10;

        if(sumPossible == 0){

            return blsNeeded;
        }
        return new int[0];
    }

    private  void updateBillsValues(int[] blsNeeded){
        for (int i = 0; i < blsNeeded.length; i++){
            bills[i] = bills[i] - blsNeeded[i];
        }
        return;
    }

    private  void displayOpSuccess(Screen sc, JPanel p, String opRes) {
        //JOptionPane.showMessageDialog(this, "Operation successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        final JDialog dialog = new JDialog(sc, "Success", true);
        //dialog.setSize(200,200);
        dialog.setSize(p.getWidth(), p.getHeight());
        dialog.setLocationRelativeTo(p);
        JLabel succ = new JLabel("Operation successful: " + opRes, SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.lightGray, 2, true);
        succ.setBorder(border);
        succ.setOpaque(true);
        succ.setBackground(Color.WHITE);
        succ.setBackground(new Color (255,255,255));
        succ.setForeground(new Color(0,153,0));
        dialog.setUndecorated(true);
        dialog.add(succ);

        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
        s.schedule(new Runnable() {
            public void run() {
                dialog.setVisible(false); //should be invoked on the EDT
                dialog.dispose();
                //successfulLoginMenu(p);
            }
        }, 2, TimeUnit.SECONDS);
        dialog.setVisible(true);
    }

    private  void displayOpError(Screen sc, JPanel p, String opRes){
        operationData = new ArrayList<String>();
        //JOptionPane.showMessageDialog(this, "Operation error!","Error", JOptionPane.ERROR_MESSAGE);
        final JDialog dialog = new JDialog(sc, "Error", true);
        dialog.setSize(p.getWidth(), p.getHeight());
        dialog.setLocationRelativeTo(p);
        JLabel err = new JLabel("Operation error: " + opRes, SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.lightGray, 2, true);
        err.setBorder(border);
        err.setOpaque(true);
        err.setBackground(Color.WHITE);
        err.setForeground(new Color(204,0,0));
        dialog.setUndecorated(true);
        dialog.add(err);
        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
        s.schedule(new Runnable() {
            public void run() {
                dialog.setVisible(false); //should be invoked on the EDT
                dialog.dispose();
            }
        }, 2, TimeUnit.SECONDS);
        dialog.setVisible(true);
    }

    private  void displayOpError(Screen sc, JPanel p, String state, String diagn){
        operationData = new ArrayList<String>();
        //JOptionPane.showMessageDialog(this, "Operation error!","Error", JOptionPane.ERROR_MESSAGE);
        final JDialog dialog = new JDialog(sc, "Error", true);
        dialog.setSize(p.getWidth(), p.getHeight());
        dialog.setLocationRelativeTo(p);
        JLabel err = new JLabel("Bills reading error: " + state, SwingConstants.CENTER);
        Border border = BorderFactory.createLineBorder(Color.lightGray, 2, true);
        err.setBorder(border);
        err.setOpaque(true);
        err.setBackground(Color.WHITE);
        err.setForeground(new Color(204,0,0));
        dialog.setUndecorated(true);
        dialog.add(err);
        dialog.setVisible(true);
    }

    private  boolean timeout(){
        long currTime = Instant.now().getEpochSecond();
        if ((currTime - lastInteractionTime) > 300) {
            lastInteractionTime = Instant.now().getEpochSecond();
            return true;
        }
        return false;
    }

    private  void fillATMwMoney(){
        for (int i = 5; i < 11; i++){
            bills[(i-5)] = i*100;
        }
        writeBills();
    }
    private  void fillATMwLittleMoney(){
        for (int i = 5; i < 11; i++){
            bills[(i-5)] = i;
        }
        writeBills();
    }

}