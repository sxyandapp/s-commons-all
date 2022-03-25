/*
 * Copyright (c) 2014, ShiXiaoyong. All rights reserved.
 */
package com.simon.commonsall.utils;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * AESCoder
 * @author Shixy
 * @date 2019年12月11日
 * @since 1.0
 */
public class AESCoder {
    static final String KEY_ALGORITHM = "AES";
    static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static String encrypt(String plaintext, String key) {
        try {
            byte[] databytes = plaintext.getBytes(StandardCharsets.UTF_8);
            byte[] keybytes = key.getBytes(StandardCharsets.UTF_8);
            return Base64.encodeBase64String(encrypt(databytes, keybytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String ciphertext, String key) {
        try {
            byte[] keybytes = key.getBytes(StandardCharsets.UTF_8);
            return new String(decrypt(Base64.decodeBase64(ciphertext), keybytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对称加密
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
            Pair<SecretKeySpec, IvParameterSpec> secretKey = getSecretKey(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey.getLeft(),secretKey.getRight());// 初始化
            return cipher.doFinal(data); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对称解密
     * 
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
            Pair<SecretKeySpec, IvParameterSpec> secretKey = getSecretKey(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey.getLeft(),secretKey.getRight());// 初始化
            return cipher.doFinal(data); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Pair<SecretKeySpec, IvParameterSpec> getSecretKey(final byte[] key) throws NoSuchAlgorithmException {
        byte[] digest = DigestUtils.md5(key);
        SecretKeySpec keya = new SecretKeySpec(digest, KEY_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(digest);
        return Pair.of(keya, iv);
    }

}
