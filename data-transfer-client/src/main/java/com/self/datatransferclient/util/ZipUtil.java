package com.self.datatransferclient.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Zip压缩工具类
 */
@Slf4j
public class ZipUtil {

    /**
     * 字节单元大小
     */
    private static final Integer BUFFER_SIZE = 1024 * 2;

    /**
     * Zip压缩
     * @param compressList 压缩文件列表
     * @param compressFilePath 生成压缩文件路径
     */
    public static void toZip(List<File> compressList, String compressFilePath){
        ZipOutputStream zos = null;

        try {
            zos = new ZipOutputStream(new FileOutputStream(new File(compressFilePath)));
            for (File file : compressList) {
                byte[] buffer = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(file.getName()));
                int len;
                FileInputStream fis = new FileInputStream(file);
                while ((len = fis.read(buffer)) != -1){
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
        } catch (IOException e) {
            log.error("压缩文件失败: {}", e);
        }finally {
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    log.error("Zip输出流关闭失败: {}", e);
                }
            }
        }
    }

    /**
     * 解压缩zip文件到指定目录
     * @param zipPath zip文件路径
     * @param targetDir 目标目录
     */
    public static void unZip(String zipPath, String targetDir){
        //判断待解压缩文件是否存在
        File zipFile = new File(zipPath);
        if(!zipFile.exists()){
            throw new RuntimeException("待解压文件不存在");
        }

        //初始化解压目标目录
        File targetFile = new File(targetDir);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        try {
            ZipFile zip = new ZipFile(zipFile, Charset.forName("gb2312"));
            Enumeration entries = zip.entries();
            while (entries.hasMoreElements()){
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream is = zip.getInputStream(entry);
                String outPath = (targetDir + File.separator + zipEntryName).replaceAll("\\*", "/");

                //判断解压路径是否存在，不存在则创建路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }

                //判断全路径是否是文件夹
                if(new File(outPath).isDirectory()){
                    continue;
                }

                OutputStream os = new FileOutputStream(outPath);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) != -1){
                    os.write(bytes, 0, len);
                }
                os.close();
                is.close();
            }
        } catch (IOException e) {
            log.error("解压缩文件异常");
        }
    }

}
