import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;

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
                    String fileName = username + ".txt";
                    try {
                        Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
                        String pasi = decrypter.decryptNoText(fileName);
                            String actual = Files.readString(Path.of(fileName));
                            String [] account = actual.split(",");
                            String user = account[0];
                            String pass = account[1];

                            System.out.println(username + " "+ user +"\n"
                                    +password+" "+pass);
                            if (user.equals(username) && pass.equals(password)){
                                AccountPage accountPage = new AccountPage(username, pasi );
                                dispose();
                                }else{
                                messageLabel.setText("Invalid Username or Password!");
                            }
                    } catch (Exception ex) {
                        messageLabel.setText("Invalid Username or Password!");
                        //ex.printStackTrace();

                    }
                }else{
                    messageLabel.setText("Invalid input");
                }
            }
        });
    }
}
