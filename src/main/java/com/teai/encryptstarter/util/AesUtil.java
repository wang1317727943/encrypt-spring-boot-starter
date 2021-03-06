package com.teai.encryptstarter.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class AesUtil {

    private final static String KEY_ALGORITHM = "AES";
    //默认的加密算法
    private final static String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private Key _key;


    public AesUtil() {
        byte[] key = {104, 101, 108, 108, 111, 49, 49, -25, -119, -71, -26, -107, -103, -28, -70, -111};
        //生成秘钥:hello11特教云
        this._key = toKey(key);
    }

    private static Key toKey(byte[] key) {
        //生成密钥
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    public byte[] initSecretKey() {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 256
        kg.init(256);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String encrypt(String strSource, byte[] key) throws Exception {
        //byte[] encryptResult = encrypt(strSource.getBytes());
        //String encryptResultStr = parseByte2HexStr(encryptResult);

        return parseByte2HexStr(encrypt(strSource.getBytes(),key));
    }

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }


    private static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    private static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    @SuppressWarnings("unused")
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public static String decrypt(String strSource, byte[] key) throws Exception {
        return new String(decrypt(parseHexStr2Byte(strSource),key), "UTF-8");
    }

    public static byte[] decrypt2Byte(String strSource, byte[] key) throws Exception {
        return decrypt(parseHexStr2Byte(strSource),key);
    }

    public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    private static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    public String showByteArray(byte[] data) {
        if (null == data) {
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for (byte b : data) {
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }
}
