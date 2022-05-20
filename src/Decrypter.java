import javax.crypto.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;
import java.util.Base64;

public class Decrypter{
    Cipher dcipher;

    byte[] salt = new String("12345678").getBytes();
    int iterationCount = 1024;
    int keyStrength = 256;

    public SecretKey getKey() {
        return key;
    }
    SecretKey key ;
    byte[] iv;

    Decrypter(String passPhrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount, keyStrength);
        SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    public String encrypt(String username, String password, String url, String comment) throws Exception {
        System.out.println("-----------E N C R Y P T-----------");
        String fileName = username + ".txt";
        Path path = Paths.get(fileName);
        OutputStream output = new BufferedOutputStream(Files.newOutputStream(path));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        dcipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = dcipher.getParameters();
        iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] utf8EncryptedData1 = dcipher.doFinal(username.getBytes());
        byte[] utf8EncryptedData2 = dcipher.doFinal(password.getBytes());
        byte[] utf8EncryptedData3 = dcipher.doFinal(url.getBytes());
        byte[] utf8EncryptedData4 = dcipher.doFinal(comment.getBytes());

        String base64EncryptedData1 = Base64.getEncoder().encodeToString(utf8EncryptedData1);
        String base64EncryptedData2 = Base64.getEncoder().encodeToString(utf8EncryptedData2);
        String base64EncryptedData3 = Base64.getEncoder().encodeToString(utf8EncryptedData3);
        String base64EncryptedData4 = Base64.getEncoder().encodeToString(utf8EncryptedData4);

        byte[] encoded = key.getEncoded();
        String out = Base64.getEncoder().withoutPadding().encodeToString(encoded);

        String iv1 = Base64.getEncoder().withoutPadding().encodeToString(iv);

        //System.out.println("Encrypted info info : " + base64EncryptedData1 +","+base64EncryptedData2 +","+base64EncryptedData3 +","+base64EncryptedData4 +","+out+","+iv1);
        writer.write(base64EncryptedData1 +","+base64EncryptedData2 +","+base64EncryptedData3 +","+base64EncryptedData4 +","+out+","+iv1);

        writer.close();
        output.close();

        return base64EncryptedData1;
    }
    public String encryptAppend(String username, String password, String url, String comment) throws Exception {
        System.out.println("-----------ENCRYPT APPEND-----------");

        String fileName = username + ".txt";
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);

        dcipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = dcipher.getParameters();
        iv = params.getParameterSpec(IvParameterSpec.class).getIV();

        byte[] utf8EncryptedData1 = dcipher.doFinal(username.getBytes());
        byte[] utf8EncryptedData2 = dcipher.doFinal(password.getBytes());
        byte[] utf8EncryptedData3 = dcipher.doFinal(url.getBytes());
        byte[] utf8EncryptedData4 = dcipher.doFinal(comment.getBytes());

        String base64EncryptedData1 = Base64.getEncoder().encodeToString(utf8EncryptedData1);
        String base64EncryptedData2 = Base64.getEncoder().encodeToString(utf8EncryptedData2);
        String base64EncryptedData3 = Base64.getEncoder().encodeToString(utf8EncryptedData3);
        String base64EncryptedData4 = Base64.getEncoder().encodeToString(utf8EncryptedData4);

        byte[] encoded = key.getEncoded();
        String out = Base64.getEncoder().withoutPadding().encodeToString(encoded);
        String iv1 = Base64.getEncoder().withoutPadding().encodeToString(iv);
        //System.out.println("Encrypted info info : " + base64EncryptedData1 +","+base64EncryptedData2 +","+base64EncryptedData3 +","+base64EncryptedData4 +","+out+","+iv1);
        bw.write("\n" + base64EncryptedData1 +","+base64EncryptedData2 +","+base64EncryptedData3 +","+base64EncryptedData4 +","+out+","+iv1);
        bw.close();
        return base64EncryptedData1;
    }


    public void encryptVardai2(String name, String pass) throws Exception {
        System.out.println("-----------ENCRYPT VARDAI-----------");

        String fileName = "vardai.txt";
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
                String a = name;
                String b = pass;

                dcipher.init(Cipher.ENCRYPT_MODE, key);
                AlgorithmParameters params = dcipher.getParameters();
                iv = params.getParameterSpec(IvParameterSpec.class).getIV();

                byte[] utf8EncryptedData1 = dcipher.doFinal(a.getBytes());
                byte[] utf8EncryptedData2 = dcipher.doFinal(b.getBytes());

                String base64EncryptedData1 = Base64.getEncoder().encodeToString(utf8EncryptedData1);
                String base64EncryptedData2 = Base64.getEncoder().encodeToString(utf8EncryptedData2);

                byte[] encoded = key.getEncoded();
                String out = Base64.getEncoder().withoutPadding().encodeToString(encoded);
                String iv1 = Base64.getEncoder().withoutPadding().encodeToString(iv);
                bw.write("\n" + base64EncryptedData1 + "," + base64EncryptedData2 + "," + out + "," + iv1);
                bw.close();
    }

    public String aaa(String username, String pass, String url, String com, String ke, String iv) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        System.out.println("___________________________________");


                String user = username;
                String password = pass;
                String urli =url;
                String comm = com;
                String keya = ke;
                byte [] key1 = Base64.getDecoder().decode(keya);
                SecretKey key2 = new SecretKeySpec(key1, "AES");
                String iv1 = iv;
                byte [] iv2 = Base64.getDecoder().decode(iv1);
                dcipher.init(Cipher.DECRYPT_MODE, key2, new IvParameterSpec(iv2));
                byte[] decryptedData = Base64.getDecoder().decode(user);
                byte[] decryptedData1 = Base64.getDecoder().decode(password);
                byte[] decryptedData2 = Base64.getDecoder().decode(urli);
                byte[] decryptedData3 = Base64.getDecoder().decode(comm);
                byte[] utf8 = dcipher.doFinal(decryptedData);
                byte[] utf81 = dcipher.doFinal(decryptedData1);
                byte[] utf82 = dcipher.doFinal(decryptedData2);
                byte[] utf83 = dcipher.doFinal(decryptedData3);
                String name = new String(utf8);
                String passi = new String(utf81);
                String ur = new String(utf82);
                String cm = new String(utf83);
                byte[] encoded = key2.getEncoded();
                String out = Base64.getEncoder().withoutPadding().encodeToString(encoded);
                String iv3 = Base64.getEncoder().withoutPadding().encodeToString(iv2);

                String p = "URL: " + ur +
                        ", " + "Username: " + name +
                        ", " + "Password: " + passi +
                        ", " + "Comment: " + cm;
//
//                System.out.println("URL: " + ur +
//                        ", " + "Username: " + name +
//                        ", " + "Password: " + pass +
//                        ", " + "Comment: " + cm);

                return p;

    }

    public void decryptVardai2(String name, String pass, String ke, String II) throws Exception {
        System.out.println("-----------DECRYPT VARDAI-----------");
        String fileName = "vardai.txt";
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);

                String username = name;
                String password = pass;
                String keya = ke;
                byte [] key1 = Base64.getDecoder().decode(keya);
                SecretKey key2 = new SecretKeySpec(key1, "AES");
                String iv1 = II;
                byte [] iv2 = Base64.getDecoder().decode(iv1);
                dcipher.init(Cipher.DECRYPT_MODE, key2, new IvParameterSpec(iv2));
                byte[] decryptedData = Base64.getDecoder().decode(username);
                byte[] decryptedData1 = Base64.getDecoder().decode(password);

                byte[] utf8 = dcipher.doFinal(decryptedData);
                byte[] utf81 = dcipher.doFinal(decryptedData1);

                String name1 = new String(utf8);
                String pass1 = new String(utf81);

                byte[] encoded = key2.getEncoded();
                String out = Base64.getEncoder().withoutPadding().encodeToString(encoded);

                String iv3 = Base64.getEncoder().withoutPadding().encodeToString(iv2);

                //System.out.println("Decrypted info : "+name1 +","+pass1 +","+out + ","+iv3);
                bw.write("\n" + name1 +","+pass1 +","+out + ","+iv3);
                bw.close();
    }

    public void addUSer(String name, String pass, String ke, String II) throws Exception {
        System.out.println("-----------DECRYPT VARDAI-----------");
        String fileName = "vardai.txt";
        File file = new File(fileName);
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("\n" + name +","+pass +","+ke + ","+II);
        bw.close();
    }

    public String simpleDecrypt(String name, String ke, String II) throws Exception {
        String username = name;
        String keya = ke;
        byte [] key1 = Base64.getDecoder().decode(keya);
        SecretKey key2 = new SecretKeySpec(key1, "AES");
        String iv1 = II;
        byte [] iv2 = Base64.getDecoder().decode(iv1);
        dcipher.init(Cipher.DECRYPT_MODE, key2, new IvParameterSpec(iv2));
        byte[] decryptedData = Base64.getDecoder().decode(username);

        byte[] utf8 = dcipher.doFinal(decryptedData);
        String name1 = new String(utf8);

        return name1;
    }

}