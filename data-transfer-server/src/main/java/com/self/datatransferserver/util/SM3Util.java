package com.self.datatransferserver.util;

import org.bouncycastle.crypto.digests.SM3Digest;

/**
 * SM3工具类
 */
public class SM3Util {

    /**
     * 字节数组转SM3加密字符串
     * @param bytes 字节数组
     * @return SM3字符串
     */
    public static String byteToSM3Str(byte[] bytes){
        byte[] digest = null;

        SM3Digest md = new SM3Digest();
        md.update(bytes, 0, bytes.length);
        digest = new byte[md.getDigestSize()];
        md.doFinal(digest, 0);

        return toHexString(digest);
    }

    /**
     * 字节数组转16进制字符串
     * @param bytes 字节数组
     * @return 16进制字符串
     */
    public static String toHexString(byte[] bytes){
        if(bytes == null || bytes.length < 1){
            throw new IllegalArgumentException("bytes can not be null or empty");
        }

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if((bytes[i] & 0xff) < 0x10){
                builder.append("0");
            }

            builder.append(Integer.toHexString(0xFF & bytes[i]));
        }

        return builder.toString().toLowerCase();
    }

}
