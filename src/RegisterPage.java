import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

                if(!username.isEmpty() && !password.isEmpty()){

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

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }else {
                    messageLabel.setText("Invalid input");
                }

            }
        });
    }


}
