import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class RegisterPage extends JFrame{


    private JPanel mainPanel;
    private JLabel registerLabel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel usernameLabel;
    private JLabel paswordLabel;
    private JTextField textField3;
    private JTextField textField4;
    private JLabel urlLabel;
    private JLabel commentLabel;
    private JButton registerButton;
    private JButton backButton;
    private JLabel messageLabel;


    public RegisterPage() {
        setContentPane(mainPanel);
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        registerLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage();
                dispose();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = textField2.getText();
                String url = textField3.getText();
                String comment = textField4.getText();

                boolean flag = false;
                if(!username.isEmpty() && !password.isEmpty()){
                    //TODO
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
                                System.out.println("XXXX RG DEC");
                            }else{
                                String a = ap[0];
                                String b = ap[1];
                                String c = ap[2];
                                String d = ap[3];
                                //System.out.println(a+" "+b);
                                decrypter.decryptVardai2(a,b,c,d);
                            }

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    String actual = null;
                    try {
                        actual = Files.readString(Path.of("vardai.txt"));
                        String [] account = actual.split("\\n");

                        for (int i = 0; i<account.length ; i++){
                            String[] ap = account[i].split(",");
                            if (ap[0].isEmpty()){
                                System.out.println("XXXX RG FN");
                            }else {


                                if (username.equals(ap[0])) {
                                    //System.out.println(username);
                                    Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
                                    String encrypted = decrypter.encryptAppend(username, password, url, comment);

                                    messageLabel.setText("New Registration added");
                                    textField1.setText("");
                                    textField2.setText("");
                                    textField3.setText("");
                                    textField4.setText("");
                                    flag = true;
                                    //decrypter.encryptVardai();
                                    break;
                                } else {
                                    //System.out.println("Nera");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (flag == false){
                        try {
                            File file = new File("vardai.txt");
                            //Here true is to append the content to file
                            FileWriter fw = new FileWriter(file,true);
                            //BufferedWriter writer give better performance
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write("\n"+username + "," + password);
                            //Closing BufferedWriter Stream
                            bw.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        try {
                        FileWriter myWriter = new FileWriter(username+".txt");
                        myWriter.close();
                        Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
                        String encrypted = decrypter.encrypt(username,password,url,comment);

                        messageLabel.setText("Register Complete");
                        textField1.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        textField4.setText("");
                        //output.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    }
                    //TODO
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
                                System.out.println("XXXX RG ENC");
                            }else {
                                String a = ap[0];
                                String b = ap[1];
                                //System.out.println(a+" "+b);
                                decrypter.encryptVardai2(a, b);
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else {
                    messageLabel.setText("Invalid input");
                }
            }
        });
    }
}
