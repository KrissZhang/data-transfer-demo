package com.self.datatransferserver.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Base64处理工具类
 */
@Slf4j
public class Base64Util {

    /**
     * base64字符串转文件
     * @param base64Str base64字符串
     * @param fileDir 文件目录
     * @param fileName 文件名
     * @return 保存路径
     */
    public static String base64ToFile(String base64Str, String fileDir, String fileName){
        //校验base64字符串是否为空
        if(StringUtils.isBlank(base64Str)){
            return "";
        }

        //转储文件路径
        String filePath = fileDir + File.separator + fileName;
        Base64 base64 = new Base64();

        try{
            byte[] bytes = base64.decode(base64Str);
            for (int i = 0; i < bytes.length; i++) {
                if(bytes[i] < 0){
                    bytes[i] += 256;
                }
            }

            File file = new File(fileDir);
            if(!file.exists()){
                file.mkdirs();
            }

            OutputStream out = new FileOutputStream(filePath);
            out.write(bytes);
            out.flush();
            out.close();
        }catch (Exception e){
            log.error("文件保存错误");
            return "文件保存错误";
        }

        return filePath;
    }

    /**
     * 文件转base64字符串
     * @param filePath 文件路径
     * @return base64字符串
     */
    public static String fileToBase64(String filePath){
        FileInputStream fis = null;
        String base64Str = "";

        try {
            File file = new File(filePath);
            if(file.exists()){
                fis = new FileInputStream(file);
                byte[] bytes = new byte[(int)file.length()];
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length && (numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0) {
                    offset += numRead;
                }

                if(offset != bytes.length){
                    log.error("文件未读取完毕");
                }
                fis.close();
                BASE64Encoder encoder = new BASE64Encoder();
                base64Str = encoder.encode(bytes);
            }
        }catch (Exception e){
            log.error("文件转换异常");
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

        return base64Str;
    }

}
