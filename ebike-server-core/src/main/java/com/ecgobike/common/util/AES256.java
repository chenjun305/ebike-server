package com.ecgobike.common.util;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * java实现AES256加密解密 依赖说明： bcprov-jdk15-133.jar：PKCS7Padding
 * javabase64-1.3.1.jar：base64 local_policy.jar 和
 * US_export_policy.jar需添加到%JAVE_HOME%\jre\lib\security中（lib中版本适合jdk1.7）
 */
public class AES256 {

    // 密钥算法
    public static final String KEY_ALGORITHM = "AES";

    // 加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 生成密钥
     */
    public static String initkey(String password) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
        kg.init(256);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式
        return Base64.encode(secretKey.getEncoded());
    }

    /**
     * 转换密钥
     */
    public static Key toKey(byte[] key) {
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密数据
     *
     * @param data
     *            待加密数据
     * @param key
     *            密钥
     * @return 加密后的数据
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] keys = Base64.decode(key);
        // 还原密钥
        Key k = toKey(keys);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        // 执行操作
        return Base64.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * 解密数据
     *
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        byte[] datas = Base64.decode(data);
        byte[] keys = Base64.decode(key);
        // 欢迎密钥
        Key k = toKey(keys);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        return new String(cipher.doFinal(datas));
    }

    public static void main(String[] args) throws Exception {
        String source = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
        System.out.println("原文：" + source);

        String orginKey = "bfb2aa6917ad421997da6edf3bd4fabe";
        System.out.println("密钥(原始码)：" + orginKey);

        String key = initkey(orginKey);
        key = "cuhEL5Wk/8V7thvZ3P3mbxTn/5mC+k4cF4QQZF5cQPA=";
        System.out.println("密钥(base64)：" + key);

        String encryptData = encrypt(source, key);
        System.out.println("加密(base64)：" + encryptData);

        String decryptData = decrypt(encryptData, key);
        System.out.println("解密：" + decryptData);
    }
}
