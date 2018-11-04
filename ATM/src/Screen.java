import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

//TODO add more safe validation (check for sum == 0 etc.)
//TODO improve UI
//TODO implement DB integration

public class Screen extends JFrame {

    static private String currentMenu = "";
    static private String confirmingOp = "";
    static private String nextMenu = "";
    static private JPasswordField pin;
    static private JPasswordField pinTimeout;
    static private JTextArea card;
    static private JTextArea sum;
    static private JTextField transferSumField;
    static private JTextField transferRecipientNum;
    static private JTextField withdrawSumField;
    static private JTextArea date;
    static private long lastInteractionTime;

    public Screen () throws Exception {
        this.setTitle("ATM 7");
        this.setLayout(null);
        this.setSize(1000, 640);
        this.setResizable(false);
        try
        {
            setUIFont(new javax.swing.plaf.FontUIResource("Tahoma",Font.PLAIN,14));
        }
        catch(Exception e){}
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(30,30,500,265);
        //p.setBackground(Color.gray);
        p.setBorder(BorderFactory.createTitledBorder("Main Screen"));
        p.setVisible(false);
        this.add(p);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(30,315,500,265);
        //p1.setBackground(Color.gray);
        p1.setBorder(BorderFactory.createTitledBorder("Keyboard"));
        p1.setVisible(false);
        this.add(p1);

        JPanel p2 = new JPanel();
        p2.setBounds(560,30,410,550);
        //p1.setBackground(Color.gray);
        p2.setBorder(BorderFactory.createTitledBorder("Card reception and money delivery"));
        p2.setVisible(false);

        BufferedImage bankLogoPicture = ImageIO.read(new File("Logo/1try.png"));
        JLabel bankLogoLabel = new JLabel(new ImageIcon(bankLogoPicture.getScaledInstance(370, 200, Image.SCALE_FAST)));
        p2.add(bankLogoLabel);

        JLabel cardAcceptedLabel = new JLabel("We only accept: ", SwingConstants.CENTER);
        cardAcceptedLabel.setBounds(10,420,370, 30);
        p2.add(cardAcceptedLabel);

        BufferedImage cardLogoPicture = ImageIO.read(new File("Logo/card2.png"));
        JLabel cardLogoLabel = new JLabel(new ImageIcon(cardLogoPicture.getScaledInstance(200, 100, Image.SCALE_FAST)), SwingConstants.CENTER);
        p2.add(cardLogoLabel);

        BufferedImage cardEntrancePicture = ImageIO.read(new File("Logo/cardEntrance2.png"));
        JLabel cardEntranceLabelLabel = new JLabel(new ImageIcon(cardEntrancePicture.getScaledInstance(140, 20, Image.SCALE_FAST)), SwingConstants.CENTER);
        p2.add(cardEntranceLabelLabel);

        BufferedImage moneyRetrPicture = ImageIO.read(new File("Logo/moneyRetr2.png"));
        JLabel moneyRetrLabel = new JLabel(new ImageIcon(moneyRetrPicture.getScaledInstance(320, 150, Image.SCALE_FAST)), SwingConstants.CENTER);
        p2.add(moneyRetrLabel);

        this.add(p2);

        JButton b1 = new JButton("1");
        b1.setBounds(30,30,140,33);
        b1.setBackground(Color.GRAY);
        b1.setForeground(Color.WHITE);
        p1.add(b1);

        JButton b2 = new JButton("2");
        b2.setBounds(180,30,140,33);
        b2.setBackground(Color.GRAY);
        b2.setForeground(Color.WHITE);
        p1.add(b2);

        JButton b3 = new JButton("3");
        b3.setBounds(330,30,140,33);
        b3.setBackground(Color.GRAY);
        b3.setForeground(Color.WHITE);
        p1.add(b3);

        JButton b4 = new JButton("4");
        b4.setBounds(30,73,140,33);
        b4.setBackground(Color.GRAY);
        b4.setForeground(Color.WHITE);
        p1.add(b4);

        JButton b5 = new JButton("5");
        b5.setBounds(180,73,140,33);
        b5.setBackground(Color.GRAY);
        b5.setForeground(Color.WHITE);
        p1.add(b5);

        JButton b6 = new JButton("6");
        b6.setBounds(330,73,140,33);
        b6.setBackground(Color.GRAY);
        b6.setForeground(Color.WHITE);
        p1.add(b6);

        JButton b7 = new JButton("7");
        b7.setBounds(30,116,140,33);
        b7.setBackground(Color.GRAY);
        b7.setForeground(Color.WHITE);
        p1.add(b7);

        JButton b8 = new JButton("8");
        b8.setBounds(180,116,140,33);
        b8.setBackground(Color.GRAY);
        b8.setForeground(Color.WHITE);
        p1.add(b8);

        JButton b9 = new JButton("9");
        b9.setBounds(330,116,140,33);
        b9.setBackground(Color.GRAY);
        b9.setForeground(Color.WHITE);
        p1.add(b9);

        JButton b_quit = new JButton("QUIT");
        b_quit.setBounds(30,159,140,33);
        b_quit.setBackground(Color.GRAY);
        b_quit.setForeground(Color.WHITE);
        p1.add(b_quit);

        JButton b0 = new JButton("0");
        b0.setBounds(180,159,140,33);
        b0.setBackground(Color.GRAY);
        b0.setForeground(Color.WHITE);
        p1.add(b0);


        JButton b_grate = new JButton("#");
        b_grate.setBounds(330,159,140,33);
        b_grate.setBackground(Color.GRAY);
        b_grate.setForeground(Color.WHITE);
        p1.add(b_grate);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(30,202,140,33);
        cancel.setBackground(Color.GRAY);
        cancel.setForeground(Color.WHITE);
        p1.add(cancel);

        JButton delete = new JButton("Delete");
        delete.setBounds(180,202,140,33);
        delete.setBackground(Color.GRAY);
        delete.setForeground(Color.WHITE);
        p1.add(delete);

        JButton ok = new JButton("OK");
        ok.setBounds(330,202,140,33);
        ok.setBackground(Color.GRAY);
        ok.setForeground(Color.WHITE);
        p1.add(ok);

        //keyboard buttons functions logic
        b1.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"1");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"1");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"1");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu =  "transferMenu";
                if (!timeout()) {
                    transferMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"1");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"1");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"1");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"1");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "1-");
                    } else{
                        date.setText(date.getText() + "1");
                    }
                }
            }
        });

        b2.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"2");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"2");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"2");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu =  "cashBalances";
                if (!timeout()) {
                    cashBalances(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"2");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"2");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"2");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"2");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "2-");
                    } else{
                        date.setText(date.getText() + "2");
                    }
                }
            }
        });

        b3.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"3");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"3");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"3");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu =  "withdrawMenu";
                if (!timeout()) {
                    withdrawMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"3");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"3");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"3");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"3");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "3-");
                    } else{
                        date.setText(date.getText() + "3");
                    }
                }
            }
        });

        b4.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"4");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"4");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"4");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu =  "balanceMenu";
                if (!timeout()) {
                    balanceMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"4");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"4");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"4");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"4");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "4-");
                    } else{
                        date.setText(date.getText() + "4");
                    }
                }
            }
        });

        b5.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"5");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"5");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"5");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"5");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"5");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"5");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"5");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "5-");
                    } else{
                        date.setText(date.getText() + "5");
                    }
                }
            }
        });

        b6.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"6");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"6");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"6");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"6");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"6");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"6");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"6");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "6-");
                    } else{
                        date.setText(date.getText() + "6");
                    }
                }
            }
        });

        b7.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"7");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"7");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"7");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"7");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"7");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"7");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"7");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "7-");
                    } else{
                        date.setText(date.getText() + "7");
                    }
                }
            }
        });

        b8.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"8");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"8");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"8");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"8");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"8");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"8");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"8");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "8-");
                    } else{
                        date.setText(date.getText() + "8");
                    }
                }
            }
        });

        b9.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"9");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"9");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"9");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if (currentMenu.equals("cashBalances_sum")){
                sum.setText(sum.getText()+"9");
            }
            else if (currentMenu.equals("transferMenu")){
                transferSumField.setText(transferSumField.getText()+"9");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"9");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                withdrawSumField.setText(withdrawSumField.getText()+"9");
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "9-");
                    } else{
                        date.setText(date.getText() + "9");
                    }
                }
            }
        });

        b_quit.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if (currentMenu.equals("PINTimeout")) {
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if (currentMenu.equals("cashBalances")) {
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if(currentMenu.equals("successfulLoginMenu")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if(currentMenu.equals("cashBalances_sum")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if (currentMenu.equals("transferMenu")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if (currentMenu.equals("transferMenuSecond")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if (currentMenu.equals("balanceMenu")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if (currentMenu.equals("withdrawMenu")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if(currentMenu.equals("confirmMenu")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
            else if(currentMenu.equals("cashBalances_date")){
                p.removeAll();
                p.updateUI();
                PIN(p);
            }
        });

        b0.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN") && pin.getText().length() < 4){
                pin.setText(pin.getText()+"0");
            }
            else if (currentMenu.equals("PINTimeout") && pinTimeout.getText().length() < 4){
                pinTimeout.setText(pinTimeout.getText()+"0");
            }
            else if (currentMenu.equals("cashBalances") && card.getText().length() < 16) {
                card.setText(card.getText()+"0");
            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if (currentMenu.equals("cashBalances_sum") && !sum.getText().equals("") && sum.getText()!= null) {
                sum.setText(sum.getText()+"0");
            }
            else if(currentMenu.equals("transferMenu") && !transferSumField.getText().equals("")) {
                transferSumField.setText(transferSumField.getText() + "0");
            }
            else if (currentMenu.equals("transferMenuSecond") && transferRecipientNum.getText().length() < 16){
                transferRecipientNum.setText(transferRecipientNum.getText()+"0");
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                if(!withdrawSumField.getText().equals("")) {
                    withdrawSumField.setText(withdrawSumField.getText() + "0");
                }
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                if(date.getText().length() < 10 && !date.getText().equals("0")){
                    if (date.getText().length() == 3 || date.getText().length() == 6) {
                        date.setText(date.getText() + "0-");
                    } else{
                        date.setText(date.getText() + "0");
                    }
                }
            }
        });

        b_grate.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN")){

            }
            else if (currentMenu.equals("PINTimeout")){
            }
            else if (currentMenu.equals("cashBalances")) {

            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if(currentMenu.equals("cashBalances_sum")){
            }
            else if (currentMenu.equals("transferMenu")){
            }
            else if (currentMenu.equals("transferMenuSecond")){
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
            }
        });

        cancel.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN")){
                pin.setText("");
            }
            if (currentMenu.equals("PINTimeout")) {
                pinTimeout.setText("");
            }
            else if (currentMenu.equals("cashBalances")) {
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if(currentMenu.equals("successfulLoginMenu")){
//                p.removeAll();
//                p.updateUI();
//                nextMenu = "successfulLoginMenu";
//                if (!timeout()) {
//                    successfulLoginMenu(p);
//                }
//                else {
//                    PINTimeout(p);
//                }
            }
            else if(currentMenu.equals("cashBalances_sum")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("transferMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("transferMenuSecond")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("balanceMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("withdrawMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if(currentMenu.equals("confirmMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if(currentMenu.equals("cashBalances_date")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
        });

        delete.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN")) {
                String str = pin.getText();
                if (str != null && str.length() > 0) {
                    pin.setText(str.substring(0, str.length() - 1));
                }
            }
            else if (currentMenu.equals("PINTimeout")) {
                String str = pinTimeout.getText();
                if (str != null && str.length() > 0) {
                    pinTimeout.setText(str.substring(0, str.length() - 1));
                }
            }
            else if (currentMenu.equals("cashBalances")) {
                String str = card.getText();
                if (str != null && str.length() > 0) {
                    card.setText(str.substring(0, str.length() - 1));
                }
            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if(currentMenu.equals("cashBalances_sum")){
                String str = sum.getText();
                if (str != null && str.length() > 0) {
                    sum.setText(str.substring(0, str.length() - 1));
                }
            }
            else if (currentMenu.equals("transferMenu")){
                String str = transferSumField.getText();
                if (str != null && str.length() > 0) {
                    transferSumField.setText(str.substring(0, str.length() - 1));
                }
            }
            else if (currentMenu.equals("transferMenuSecond")){
                String str = transferRecipientNum.getText();
                if (str != null && str.length() > 0) {
                    transferRecipientNum.setText(str.substring(0, str.length() - 1));
                }
            }
            else if (currentMenu.equals("balanceMenu")){
            }
            else if (currentMenu.equals("withdrawMenu")){
                String str = withdrawSumField.getText();
                if (str != null && str.length() > 0) {
                    withdrawSumField.setText(str.substring(0, str.length() - 1));
                }
            }
            else if(currentMenu.equals("confirmMenu")){
            }
            else if(currentMenu.equals("cashBalances_date")){
                String str = date.getText();
                if (str != null && str.length() > 0) {
                    if (date.getText().length() == 6 || date.getText().length() == 9){
                        date.setText(str.substring(0, str.length() - 2));
                    }else {
                        date.setText(str.substring(0, str.length() - 1));
                    }
                }
            }
        });

        ok.addActionListener((ActionEvent e) -> {
            if (currentMenu.equals("PIN")) {
                if (pin.getText().length() == 4) {
                    p.removeAll();
                    p.updateUI();
                    lastInteractionTime = Instant.now().getEpochSecond();
                    successfulLoginMenu(p);
                }
            }
            else if (currentMenu.equals("PINTimeout")) {
                //TODO validate via db
                if (pinTimeout.getText().length() == 4) {
                    p.removeAll();
                    p.updateUI();
                    lastInteractionTime = Instant.now().getEpochSecond();
                    String nextMenuTmp = nextMenu;
                    nextMenu = "";
                    if (nextMenuTmp.equals("cashBalances")){
                        currentMenu = nextMenuTmp;
                        cashBalances(p);
                    }
                    else if (nextMenuTmp.equals("cashBalances_sum")){
                        currentMenu = nextMenuTmp;
                        cashBalances_sum(p);
                    }
                    else if (nextMenuTmp.equals("cashBalances_date")){
                        currentMenu = nextMenuTmp;
                        cashBalances_date(p);
                    }
                    else if (nextMenuTmp.equals("successfulLoginMenu")){
                        currentMenu = nextMenuTmp;
                        successfulLoginMenu(p);
                    }
                    else if (nextMenuTmp.equals("transferMenu")){
                        currentMenu = nextMenuTmp;
                        transferMenu(p);
                    }
                    else if (nextMenuTmp.equals("transferMenuSecond")){
                        currentMenu = nextMenuTmp;
                        transferMenu(p);
                    }
                    else if (nextMenuTmp.equals("balanceMenu")){
                        currentMenu = nextMenuTmp;
                        balanceMenu(p);
                    }
                    else if (nextMenuTmp.equals("withdrawMenu")){
                        currentMenu = nextMenuTmp;
                        withdrawMenu(p);
                    }
                    else if (nextMenuTmp.equals("confirmMenu")){
                        currentMenu = nextMenuTmp;
                        confirmMenu(p, confirmingOp);
                    }
                    //just in case
                    else {
                        currentMenu = "successfulLoginMenu";
                        successfulLoginMenu(p);
                    }
                }
            }
            else if (currentMenu.equals("cashBalances")) {
                //TODO validate via db
                if(card.getText().length() == 16) {
                    p.removeAll();
                    p.updateUI();
                    nextMenu = "cashBalances_sum";
                    if (!timeout()) {
                        cashBalances_sum(p);
                    }
                    else {
                        PINTimeout(p);
                    }
                }
            }
            else if(currentMenu.equals("successfulLoginMenu")){
            }
            else if(currentMenu.equals("cashBalances_sum")){
                //TODO validate via db
                //if(sum.getText().length() == 16) {
                p.removeAll();
                p.updateUI();
                nextMenu = "cashBalances_date";
                if (!timeout()) {
                    cashBalances_date(p);
                }
                else {
                    PINTimeout(p);
                }
                //}
            }
            else if (currentMenu.equals("transferMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu = "transferMenuSecond";
                if (!timeout()) {
                    if (true) { //TODO validate and check via DB
                        transferMenuSecond(p);
                    }
                    else {
                        //failed to transfer
                        successfulLoginMenu(p);
                    }
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("transferMenuSecond")){
                //TODO check via DB
                p.removeAll();
                p.updateUI();
                nextMenu = "confirmMenu";
                confirmingOp = "confirmTransfer";
                if (!timeout()) {
                    if (transferRecipientNum.getText().length() == 16) {
                        confirmMenu(p, "confirmTransfer");
                    } else {
                        //failed to transfer
                        successfulLoginMenu(p);
                    }
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("balanceMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                if (!timeout()) {
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if (currentMenu.equals("withdrawMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu = "confirmMenu";
                confirmingOp = "confirmWithdrawal";
                if (!timeout()) {
                    if (true) { //TODO validate and check via DB
                        confirmMenu(p, "confirmWithdrawal");
                    } else {
                        //failed to withdraw
                        successfulLoginMenu(p);
                    }
                }
                else {
                    PINTimeout(p);
                }

            }
            else if(currentMenu.equals("confirmMenu")){
                p.removeAll();
                p.updateUI();
                nextMenu = "successfulLoginMenu";
                confirmingOp = "";
                if (!timeout()){
                    successfulLoginMenu(p);
                }
                else {
                    PINTimeout(p);
                }
            }
            else if(currentMenu.equals("cashBalances_date")){
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
            }
        });




        PIN(p);

        //p.setVisible(true);
        p1.setVisible(true);
        p2.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    //PIN entering
    private static void PIN(JPanel p){
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
        //pin.getCaret().deinstall( pin );
        p.add(pin);
        p.setVisible(true);
    }

    private static void PINTimeout(JPanel p){
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
    private static void cashBalances (JPanel p){
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
    private static void cashBalances_sum (JPanel p) {
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
    private static void cashBalances_date (JPanel p) {
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

    private static void successfulLoginMenu(JPanel p){
        currentMenu = "successfulLoginMenu";
        JLabel l = new JLabel("Welcome, ");
        l.setBounds(150,60,200, 30);
        p.add(l);
        JLabel lOptions = new JLabel("1 - Transfer");
        lOptions.setBounds(150,90,200, 30);
        p.add(lOptions);
        JLabel lOptions2 = new JLabel("2 - Money excesses management");
        lOptions2.setBounds(150,120,300, 30);
        p.add(lOptions2);
        JLabel lOptions3 = new JLabel("3 - Withdraw");
        lOptions3.setBounds(150,150,200, 30);
        p.add(lOptions3);
        JLabel lOptions4 = new JLabel("4 - Check balance");
        lOptions4.setBounds(150,180,200, 30);
        p.add(lOptions4);
//        JLabel lOptions5 = new JLabel("Cancel - Quit");
//        lOptions5.setBounds(150,210,200, 30);
//        p.add(lOptions5);

        p.setVisible(true);
    }

    private static void transferMenu(JPanel p){
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

    private static void transferMenuSecond(JPanel p){
        currentMenu = "transferMenuSecond";
//        JLabel l = new JLabel("Enter recipient number: ");
//        l.setBounds(150,60,200, 30);
//        p.add(l);
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

    private static void balanceMenu(JPanel p){
        currentMenu = "balanceMenu";
        //TODO get balance via DB
        JLabel l = new JLabel("Your balance is: $");
        l.setBounds(150,100,200, 30);
        p.add(l);
        p.setVisible(true);
    }

    private static void withdrawMenu(JPanel p){
        currentMenu = "withdrawMenu";

        JLabel l = new JLabel("Available banknotes are: ");
        l.setBounds(150,60,200, 30);
        p.add(l);
        //TODO list banknotes w/ amounts
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

    private static void confirmMenu(JPanel p, String s){
        currentMenu = "confirmMenu";

        JLabel l;
        String operation = s;
        //TODO get corresponding data from DB
        if (operation.equals("confirmWithdrawal")) {
            l = new JLabel("You are about to withdraw: $");
            l.setBounds(150, 60, 320, 30);
            p.add(l);
        }
        else if (operation.equals("confirmTransfer")) {
            l = new JLabel("You are about to transfer: $");
            l.setBounds(150, 60, 320, 30);
            p.add(l);
        }
        else if (operation.equals("confirmBalances")) {
            l = new JLabel("You are about to change balances: ");
            l.setBounds(150, 60, 320, 30);
            p.add(l);
        }
        p.setVisible(true);
    }
    //Additional methods
    public static void setUIFont(javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
        }
    }

    private static boolean timeout(){
        long currTime = Instant.now().getEpochSecond();
        if ((currTime - lastInteractionTime) > 300) {
            lastInteractionTime = Instant.now().getEpochSecond();
            return true;
        }
        return false;
    }
}