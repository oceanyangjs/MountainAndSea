package com.mountain.sea.core.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-07 13:52
 */
public class AECUtil {
    private static final String sKey = "abcdef0123456789";
    private static final String ivParameter = "0123456789abcdef";

    public AECUtil() {
    }

    public static String encrypt(String sSrc) {
        String result = "";

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = "abcdef0123456789".getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec("0123456789abcdef".getBytes());
            cipher.init(1, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            result = (new BASE64Encoder()).encode(encrypted);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return result;
    }

    public static String decrypt(String sSrc) {
        try {
            byte[] raw = "abcdef0123456789".getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0123456789abcdef".getBytes());
            cipher.init(2, skeySpec, iv);
            byte[] encrypted1 = (new BASE64Decoder()).decodeBuffer(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }
}
