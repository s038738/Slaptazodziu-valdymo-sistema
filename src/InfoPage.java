import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class InfoPage extends JFrame{
    private JPanel mainPanel;
    private JButton backButton;
    private JLabel lb;
    private JButton bttt;
    DefaultListModel listModel = new DefaultListModel();
    List<String> list = new ArrayList<>();

    InfoPage(String username){
        setContentPane(mainPanel);
        setSize(600,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            String fileName = username + ".txt";
            String actual = null;
            actual = Files.readString(Path.of(fileName));
            String [] account = actual.split("\\n");
            System.out.println("___________________________________");
            for (int i = 0; i<account.length ; i++){
                String[] ap = account[i].split(",");
                String user = ap[0];

                String password = ap[1];
                String url = ap[2];
                String com = ap[3];

                System.out.println( user+
                        password+
                        url+
                        com);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        lb.setText("Check Terminal");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        bttt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
                    decrypter.aaa(username);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
