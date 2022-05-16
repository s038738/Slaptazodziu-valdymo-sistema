import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKeyFactory;
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
        //String data = username +","+ password+","+url+","+comment;
        //System.out.println(iv);
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
        System.out.println("Key: "+key);
        System.out.println("Key out: " + out);

        String iv1 = Base64.getEncoder().withoutPadding().encodeToString(iv);
        System.out.println("IV out: "+ iv1);

        System.out.println("Encrypted info info : " + base64EncryptedData1 +","+base64EncryptedData2 +","+base64EncryptedData3 +","+base64EncryptedData4 +","+out+","+iv1);
        writer.write(base64EncryptedData1 +","+base64EncryptedData2 +","+base64EncryptedData3 +","+base64EncryptedData4 +","+out+","+iv1);

        writer.close();
        output.close();

        return base64EncryptedData1;
    }

    public String encryptTest(String username) throws Exception {
        dcipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = dcipher.getParameters();
        iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] utf8EncryptedData1 = dcipher.doFinal(username.getBytes());
        String base64EncryptedData1 = Base64.getEncoder().encodeToString(utf8EncryptedData1);
        return base64EncryptedData1;
    }


    public String decrypt(String base64EncryptedData) throws Exception {
        dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        //byte[] decryptedData = new sun.misc.BASE64Decoder().decodeBuffer(base64EncryptedData);
        byte[] decryptedData = Base64.getDecoder().decode(base64EncryptedData);
        byte[] utf8 = dcipher.doFinal(decryptedData);
        System.out.println(new String(utf8));
        return new String(utf8, "UTF8");
    }

    public String decryptIV(String base64EncryptedData,SecretKey key) throws Exception {
        dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        //byte[] decryptedData = new sun.misc.BASE64Decoder().decodeBuffer(base64EncryptedData);
        byte[] decryptedData = Base64.getDecoder().decode(base64EncryptedData);
        byte[] utf8 = dcipher.doFinal(decryptedData);

        return new String(utf8, "UTF8");
    }

    public String decryptNoText(String fileName) throws Exception {
        System.out.println("-----------D E C R Y P T-----------");
        String actual = Files.readString(Path.of(fileName));
        String [] account = actual.split(",");
        String username = account[0];
        String password = account[1];
        String url = account[2];
        String comment = account[3];
        String keya = account[4];
        byte [] key1 = Base64.getDecoder().decode(keya);
        SecretKey key2 = new SecretKeySpec(key1, "AES");
        System.out.println("Key in: " +keya + ", \nKey origin: "+ key2);

        String iv1 = account[5];
        byte [] iv2 = Base64.getDecoder().decode(iv1);
        System.out.println("IV in: " + iv1);

        dcipher.init(Cipher.DECRYPT_MODE, key2, new IvParameterSpec(iv2));
        //byte[] decryptedData = new sun.misc.BASE64Decoder().decodeBuffer(base64EncryptedData);
        byte[] decryptedData = Base64.getDecoder().decode(username);
        byte[] decryptedData1 = Base64.getDecoder().decode(password);
        byte[] decryptedData2 = Base64.getDecoder().decode(url);
        byte[] decryptedData3 = Base64.getDecoder().decode(comment);

        byte[] utf8 = dcipher.doFinal(decryptedData);
        byte[] utf81 = dcipher.doFinal(decryptedData1);
        byte[] utf82 = dcipher.doFinal(decryptedData2);
        byte[] utf83 = dcipher.doFinal(decryptedData3);

        String name = new String(utf8);
        String pass = new String(utf81);
        String ur = new String(utf82);
        String com = new String(utf83);

        byte[] encoded = key2.getEncoded();
        String out = Base64.getEncoder().withoutPadding().encodeToString(encoded);

        String iv3 = Base64.getEncoder().withoutPadding().encodeToString(iv2);

        Path path = Paths.get(fileName);
        OutputStream output = new BufferedOutputStream(Files.newOutputStream(path));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

        System.out.println("Decrypted info : "+name +","+pass +","+ur +","+com +","+out + ","+iv3);

        writer.write(name +","+pass +","+ur +","+com +","+out + ","+iv3);

        writer.close();
        return password;
    }


    public void writeWW(String username, String password, String url, String comment, String out, String iv1) throws IOException {
        String fileName = username + ".txt";
        Path path = Paths.get(fileName);
        OutputStream output = new BufferedOutputStream(Files.newOutputStream(path));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        System.out.println("INFO: " + username +","+password +","+url +","+comment +","+out+","+iv1);
        writer.write(username +","+password +","+url +","+comment +","+out+","+iv1);

        writer.close();
        output.close();

    }


    public static void main(String args[]) throws Exception {
        Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
        String encrypted = decrypter.encryptTest("viso gero");
        System.out.println(encrypted);
        String decrypted = decrypter.decrypt(encrypted);
        String decrypted1 = decrypter.decryptIV(encrypted, decrypter.getKey());
        System.out.println(decrypted);
        System.out.println(decrypted1);
    }



}