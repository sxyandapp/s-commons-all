/*
 * Copyright (c) 2014, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 描述：SecurityTest 非对称加密解密 使用公钥加密，私钥解密;也可使用私钥加密，公钥解密
 * @author Shixy
 * @since 1.0
 * @date 2014-12-29
 */
public class RSACoder {
    
    /**
     * 生成密钥所用的算法
     * @author ShiXiaoyong
     * @date   2019年12月11日
     */
    static final String KEY_ALGORITHM = "RSA";
    /**
     * 加密所用的算法
     */
    static final String ALGORITHM = "RSA/ECB/PKCS1Padding";
    
    public static String encrypt(String data, String publicKey) {
        try {
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            PublicKey publicKeyFromEncoded = getPublicKeyFromEncoded(Base64.decodeBase64(publicKey));
            byte[] enrypt = encrypt(bytes,publicKeyFromEncoded);
            return Base64.encodeBase64String(enrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String decrypt(String data, String privateKey) {
        try {
            PrivateKey privateKeyFromEncoded = getPrivateKeyFromEncoded(Base64.decodeBase64(privateKey));
            byte[] decryptdecrypt = decrypt(Base64.decodeBase64(data),privateKeyFromEncoded);
            return new String(decryptdecrypt, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密方法
     * @param data 明文数据流
     * @param key 密钥(公钥或私钥)
     * @return 密文数据流
     */
    public static byte[] encrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 用公钥加密
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密方法
     * @param data 密文数据流
     * @param key 密钥(公钥或私钥)
     * @return 明文数据流
     */
    public static byte[] decrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 用私钥去初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 得到解密后的结果
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一对密钥<private,public>
     * @return
     */
    public static Pair<String, String> generateKeyPair() {
        // 实例化Key
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            // 获取一对钥匙
            KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
            return Pair.of(Base64.encodeBase64String(generateKeyPair.getPrivate().getEncoded()), Base64.encodeBase64String(generateKeyPair.getPublic().getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从encodedKey生成publickey
     * @param encodedKey
     * @return
     */
    public static PublicKey getPublicKeyFromEncoded(byte[] encodedKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // 生成publickey方式1：从encoded生成
            return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从encodedKey生成privatekey
     * @param encodedKey
     * @return
     */
    public static PrivateKey getPrivateKeyFromEncoded(byte[] encodedKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
