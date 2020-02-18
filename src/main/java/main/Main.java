package main;

import certificate.CertificateUtil;
import ldap.Ldap;

import java.io.*;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;
import java.util.Properties;

public class Main {

    public static Properties getProp() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("./properties/ldap.properties");
        properties.load(fileInputStream);
        return properties;

    }

    public static void main(String args[]) throws IOException {

        Properties properties = getProp();

        Ldap ldap = new Ldap(properties.getProperty("ldap.host") + ":"+ properties.getProperty("ldap.port"),
                properties.getProperty("ldap.security"),
                properties.getProperty("ldap.bind"),
                properties.getProperty("ldap.password"));



        switch (args[0]) {
            case "1":
                try {
                    MessageDigest salt = MessageDigest.getInstance("SHA-256");
                    salt.update(UUID.randomUUID().toString().getBytes());

                    String nonce = new String(Base64.getEncoder().encodeToString(salt.digest()));
                    String crypt = CertificateUtil.encrypt(nonce, ldap.getCertificate(properties.getProperty("ldap.identifier"),
                            properties.getProperty("ldap.search"),
                            args[1]).getPublicKey());
                    System.out.println(nonce);
                    System.out.println(crypt);


                } catch (Exception e) {
                    System.err.println("ERRO: " + e);
                }
                break;

            case "3":
                if(CertificateUtil.verify(args[2], args[3], ldap.getCertificate(properties.getProperty("ldap.identifier"),
                        properties.getProperty("ldap.search"),
                        args[1]).getPublicKey())){
                    System.out.println("veryfied");
                }else{
                    System.out.println("erro");
                }
        }


    }

}