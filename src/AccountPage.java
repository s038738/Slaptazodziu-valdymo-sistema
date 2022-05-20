import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class AccountPage extends JFrame {


    private JPanel mainPanel;
    private JLabel accountLabel;
    private JLabel nameLabel;
    private JTextField textField1;
    private JButton findPassButton;
    private JTextField textField2;
    private JButton changePassButton;
    private JButton decryptPassButton;
    private JButton generatePassButton;
    private JButton clipboardButton;
    private JButton deleteButton;
    private JButton logOutButton;
    private JLabel infoLabel;
    private JLabel messageLabel;
    private JButton infoButton;
    String pass;

    public AccountPage(String username, String password) throws Exception {
        setContentPane(mainPanel);
        setSize(600,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        accountLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        nameLabel.setText("Hello  " + username);

        Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
        String fileName = username + ".txt";
        String actual = Files.readString(Path.of(fileName));
        String [] account = actual.split(",");
        String user;
        String ur;
        String com;
        user = account[0];
        pass = account[1];
        ur = account[2];
        com = account[3];
        String key2 = account[4];
        String iv2 = account[5];

        Frame[] frames = Frame.getFrames();
        for(Frame f: frames){
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }

            });
        }
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage();
                dispose();
            }
        });
        findPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               textField1.setText(pass);
            }
        });
        decryptPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(password);
            }
        });
        changePassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pass = textField2.getText();
                try {
                    Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
                    String fileName = "vardai.txt";
                    String actua = null;
                    actua = Files.readString(Path.of(fileName));
                    String [] account = actua.split("\\n");

                    File file = new File(fileName);
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(" ");
                    bw.close();

                    for (int i = 1; i<account.length ; i++) {
                        String[] ap = account[i].split(",");
                        if (ap[0].isEmpty()){
                            System.out.println("XXXX AC FIND");
                        }else {
                            String EUS = decrypter.simpleDecrypt(ap[0], ap[2], ap[3]);

                            if(EUS.equals(username)){
                                //System.out.println(username + "   "+EUS);
                                String a = username;
                                String b = pass;
                                decrypter.encryptVardai2(a,b);
                            }else {

                                String a = ap[0];
                                String b = ap[1];
                                String c = ap[2];
                                String d = ap[3];
                                //System.out.println(a+" "+b);
                                decrypter.addUSer(a,b,c,d);
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                messageLabel.setText("Password changed");
            }
        });
        generatePassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int len = 10;
                String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
                        +"jklmnopqrstuvwxyz!@#$%&";
                Random rnd = new Random();
                StringBuilder sb = new StringBuilder(len);
                for (int i = 0; i < len; i++) {
                    sb.append(chars.charAt(rnd.nextInt(chars.length())));
                }
                String apass = sb.toString();
                textField2.setText(apass);
            }
        });
        clipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(textField2.getText());
                Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
                clpbrd.setContents (stringSelection, null);
                messageLabel.setText("Added to Clipboard");
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
                    String fileName = "vardai.txt";
                    String actua = null;
                    actua = Files.readString(Path.of(fileName));
                    String [] account = actua.split("\\n");

                    File file = new File(fileName);
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(" ");
                    bw.close();

                    for (int i = 1; i<account.length ; i++) {
                        String[] ap = account[i].split(",");
                        if (ap[0].isEmpty()){
                            System.out.println("XXXX AC DEL");
                        }else {
                            String EUS = decrypter.simpleDecrypt(ap[0], ap[2], ap[3]);
                            if(EUS.equals(username)){
                                System.out.println(username + "   "+EUS);

                            }else {
                                String a = ap[0];
                                String b = ap[1];
                                String c = ap[2];
                                String d = ap[3];
                                //System.out.println(a+" "+b);
                                decrypter.addUSer(a,b,c,d);
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dispose();
                LoginPage loginPage = new LoginPage();

            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoPage infoPage = new InfoPage(username);
            }
        });
    }

}
