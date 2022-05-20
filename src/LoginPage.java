import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class LoginPage extends JFrame {

    private JPanel mainPanel;
    private JLabel loginLabel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    private JButton registerButton;
    private JButton loginButton;
    private JLabel messageLabel;



    public LoginPage(){
        setContentPane(mainPanel);
        setSize(400,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        loginLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPage registerPage = new RegisterPage();
                dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                char [] charPassword = passwordField1.getPassword();
                String password = String.valueOf(charPassword);

                if(!username.isEmpty() && !password.isEmpty()){
                    try {
                        Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
                        String fileName = "vardai.txt";
                        String actu = null;
                        actu = Files.readString(Path.of(fileName));
                        String [] account = actu.split("\\n");

                        File file = new File(fileName);
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(" ");
                        bw.close();

                        for (int i = 1; i<account.length ; i++) {
                            String[] ap = account[i].split(",");
                            if (ap[0].isEmpty()){
                                System.out.println("XXXX LG DEC");
                            }else {
                                String a = ap[0];
                                String b = ap[1];
                                String c = ap[2];
                                String d = ap[3];
                                //System.out.println(a+" "+b);
                                decrypter.decryptVardai2(a, b, c, d);
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    String fileName = username + ".txt";
                    String actual = null;
                    //TODO vardai.txt encryption
                    try {
                        actual = Files.readString(Path.of("vardai.txt"));
                        String [] account = actual.split("\\n");

                        for (int i = 0; i<account.length ; i++){
                            String[] ap = account[i].split(",");
                            if (Objects.equals(ap[0], " ")){
                                //System.out.println("XXXX LG");
                            }else {
                                if (username.equals(ap[0]) && password.equals(ap[1])) {
//                                System.out.println(username + " " + ap[0]+
//                                        "\n" + password + " " + ap[1]);
                                    encrypt();
                                    AccountPage accountPage = new AccountPage(username, password);
                                    dispose();
                                    break;
                                } else {
                                    //System.out.println("Nera");
                                    messageLabel.setText("Invalid input");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else{
                    messageLabel.setText("Invalid input");
                }
            }
        });
    }

    public void encrypt(){

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
                    System.out.println("XXXX LG ENC");
                }else {
                    String a = ap[0];
                    String b = ap[1];
//                System.out.println(a+" "+b);
                    decrypter.encryptVardai2(a, b);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
