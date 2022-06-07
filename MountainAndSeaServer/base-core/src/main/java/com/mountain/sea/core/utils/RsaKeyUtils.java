package com.mountain.sea.core.utils;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-07 13:50
 */
public class RsaKeyUtils {
    public RsaKeyUtils() {
    }

    public static PublicKey getPublicKey(InputStream resourceAsStream) throws Exception {
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        dis.close();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public static PrivateKey getPrivateKey(InputStream resourceAsStream) throws Exception {
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        dis.close();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    public static PublicKey getPublicKey(byte[] publicKey) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public static PrivateKey getPrivateKey(byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKey);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair;
        byte[] publicKeyBytes = (keyPair = keyPairGenerator.genKeyPair()).getPublic().getEncoded();
        FileOutputStream fos;
        (fos = new FileOutputStream(publicKeyFilename)).write(publicKeyBytes);
        fos.close();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        (fos = new FileOutputStream(privateKeyFilename)).write(privateKeyBytes);
        fos.close();
    }

    public static byte[] generatePublicKey(String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        return keyPairGenerator.genKeyPair().getPublic().getEncoded();
    }

    public static byte[] generatePrivateKey(String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        return keyPairGenerator.genKeyPair().getPrivate().getEncoded();
    }

    public static Map<String, byte[]> generateKey(String password) throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair;
        byte[] publicKeyBytes = (keyPair = keyPairGenerator.genKeyPair()).getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        HashMap map;
        (map = new HashMap()).put("pub", publicKeyBytes);
        map.put("pri", privateKeyBytes);
        return map;
    }

    public static String toHexString(byte[] b) {
        return Base64.getEncoder().encodeToString(b);
    }

    public static byte[] toBytes(String s) throws IOException {
        return Base64.getDecoder().decode(s);
    }
}
