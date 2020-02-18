package certificate;

import javax.crypto.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class CertificateUtil {

    public static CertificateDetails getCertificateDetails(String path, String password, String certPath) {

        CertificateDetails certDetails = null;
        try {

            boolean isAliasWithPrivateKey = true;
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            // Localização e password
            keyStore.load(new FileInputStream(path), password.toCharArray());

            String alias = keyStore.getCertificateAlias(keyStore.getCertificate(certPath));

            if (alias != null) {

                KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
                        new KeyStore.PasswordProtection(password.toCharArray()));

                PrivateKey myPrivateKey = pkEntry.getPrivateKey();
                Certificate[] chain = keyStore.getCertificateChain(alias);

                certDetails = new CertificateDetails();
                certDetails.setPrivateKey(myPrivateKey);
                certDetails.setX509Certificate((X509Certificate) chain[0]);
                certDetails.setCertificateChain(keyStore.getCertificateChain(alias));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        return certDetails;
    }

    public static CertificateDetails getCertificateDetailsAlias(String path, String password, String alias) {

        CertificateDetails certDetails = null;
        try {

            boolean isAliasWithPrivateKey = true;
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            // Localização e password
            keyStore.load(new FileInputStream(path), password.toCharArray());

            if (alias != null) {

                KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
                        new KeyStore.PasswordProtection(password.toCharArray()));

                PrivateKey myPrivateKey = pkEntry.getPrivateKey();
                Certificate[] chain = keyStore.getCertificateChain(alias);

                certDetails = new CertificateDetails();
                certDetails.setPrivateKey(myPrivateKey);
                certDetails.setX509Certificate((X509Certificate) chain[0]);
                certDetails.setCertificateChain(keyStore.getCertificateChain(alias));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        return certDetails;
    }

    public static String encrypt(String hash, PublicKey publicKey){
        byte[] cipherHash = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherHash = cipher.doFinal(hash.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }


        return Base64.getEncoder().encodeToString(cipherHash);
    }

    public static String decrypt(String cipherHash, PrivateKey privateKey){
        byte[] bytes = Base64.getDecoder().decode(cipherHash);
        String decrypt = "";
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decrypt = new String(cipher.doFinal(bytes));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return decrypt;
    }

    public static String sign(String hash, PrivateKey privateKey){

        byte[] sign = new byte[0];
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(hash.getBytes());
            sign = signature.sign();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(sign);
    }

    public static boolean verify(String hash, String hashSigned, PublicKey publicKey){

        Boolean verify = false;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(hash.getBytes());

            verify = signature.verify(Base64.getDecoder().decode(hashSigned));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        return verify;
    }
}

