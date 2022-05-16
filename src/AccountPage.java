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

        infoLabel.setText("Info: " + username +", " + ur+", "+ com);


        Frame[] frames = Frame.getFrames();
        for(Frame f: frames){
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                    try {
                        String a = decrypter.encrypt(user, pass, ur, com);
                        System.out.println(a + " File encrypted");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });
        }

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage();
                dispose();
                try {
                    String a = decrypter.encrypt(user, pass, ur, com);
                    System.out.println(a + " File encrypted");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        findPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(password);
            }
        });
        decryptPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(pass);
            }
        });
        changePassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pass = textField2.getText();
                try {
                    decrypter.writeWW(username, pass,ur,com,key2,iv2);
                } catch (IOException ex) {
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

                try
                {
                    File f= new File(fileName);           //file to be delete
                    if(f.delete())                      //returns Boolean value
                    {
                        System.out.println(f.getName() + " deleted");   //getting and printing the file name
                    }
                    else
                    {
                        System.out.println("failed");
                    }
                }
                catch(Exception exe)
                {
                    exe.printStackTrace();
                }
                dispose();
                LoginPage loginPage = new LoginPage();

            }
        });
    }

}
