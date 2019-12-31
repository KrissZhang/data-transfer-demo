package com.self.datatransferclient.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5工具类
 */
@Slf4j
public class Md5Util {

    /**
     * 字节数组转16进制字符串
     * @param array 字节数组
     * @return 16进制字符串
     */
    public static String byteArrayToHex(byte[] array){
        StringBuilder builder = new StringBuilder();
        for (byte b : array) {
            builder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }

        return builder.toString();
    }

    /**
     * md5编码
     * @param bytes 字节数组
     * @return md5编码字符串
     */
    public static String encodeByMd5(byte[] bytes){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(bytes);
            return byteArrayToHex(array);
        } catch (NoSuchAlgorithmException e) {
            log.error("md5编码异常: {}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * md5编码
     * @param str 字符串
     * @return md5编码字符串
     */
    public static String encodeByMd5(String str){
        return encodeByMd5(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 校验md5
     * @param encodeBytes encodeBytes
     * @param md5Str md5Str
     * @return 校验结果
     */
    public static boolean checkMd5(byte[] encodeBytes, String md5Str){
        return encodeByMd5(encodeBytes).equalsIgnoreCase(md5Str);
    }

    /**
     * 校验md5
     * @param encodeStr encodeStr
     * @param md5Str md5Str
     * @return 校验结果
     */
    public static boolean checkMd5(String encodeStr, String md5Str){
        return encodeByMd5(encodeStr).equalsIgnoreCase(md5Str);
    }

}
