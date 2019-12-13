package com.self.datatransferserver.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 */
@Slf4j
public class FileUtil {

    /**
     * 锁
     */
    private static Object lock = new Object();

    /**
     * 移动文件至目标目录
     * @param filePath 文件路径
     * @param targetDir 目标路径
     * @param fileName 文件名
     * @return 移动文件是否成功
     */
    public static boolean moveFileToTarget(Path filePath, Path targetDir, String fileName){
        try {
            //判断移动目录是否存在，如果不存在则创建该目录
            if(Files.notExists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            Path target = Paths.get(targetDir.toString(), fileName);
            Files.move(filePath, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("移动文件： {} 失败", fileName);
            return false;
        }

        return true;
    }

    /**
     * 复制文件至目标路径
     * @param filePath 文件路径
     * @param targetDir 目标路径
     * @param fileName 文件名
     * @return 复制文件是否成功
     */
    public static boolean copyFileToTarget(Path filePath, Path targetDir, String fileName){
        try {
            //判断复制目录是否存在，如果不存在则创建该目录
            if(Files.notExists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            Path target = Paths.get(targetDir.toString(), fileName);
            Files.copy(filePath, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("复制文件： {} 失败", fileName);
            return false;
        }

        return true;
    }

    /**
     * 创建文件夹
     * @param targetDir 目标目录
     */
    public static void mkdirs(Path targetDir){
        File file = targetDir.toFile();

        if(!file.exists() || !file.isDirectory()){
            synchronized (lock){
                if(file.exists() && file.isDirectory()){
                    return;
                }

                boolean result = file.mkdirs();
                if(!result){
                    throw new RuntimeException("创建文件夹：" + targetDir.toString() + " 失败");
                }
            }
        }
    }

    /**
     * 创建空文件，若文件已经存在则替换文件
     * @param filePath 文件路径
     * @return 创建是否成功
     */
    public static boolean createEmptyFile(Path filePath){
        try {
            //如果文件存在则删除旧的文件
            if(Files.exists(filePath)){
                Files.delete(filePath);
            }

            //创建新的文件
            Files.createFile(filePath);
        } catch (IOException e) {
            log.error("创建文件异常");
            return false;
        }

        return true;
    }

    /**
     * 创建新文件并写入数据，若文件存在则覆盖文件
     * @param filePath 文件路径
     * @param data 写入文件数据
     * @return 创建是否成功
     */
    public static boolean writeNewFile(Path filePath, byte[] data){
        try {
            //若文件存在则删除旧文件
            Files.deleteIfExists(filePath);

            //写入文件
            if(data == null){
                Files.write(filePath, new byte[0], StandardOpenOption.CREATE);
            }else {
                Files.write(filePath, data, StandardOpenOption.CREATE);
            }

        } catch (IOException e) {
            log.error("创建文件异常");
            return false;
        }

        return true;
    }

    /**
     * 创建文件或追加文件内容
     * @param filePath 文件路径
     * @param data 写入文件数据
     * @return 创建或追加是否成功
     */
    public static boolean writeOrAppendFile(Path filePath, byte[] data){
        if(data == null){
            data = new byte[0];
        }

        try {
            Files.write(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.SYNC);
        } catch (IOException e) {
            log.error("创建或追加文件异常");
            return false;
        }

        return true;
    }

    /**
     * 读取文件内容为字节数组
     * @param filePath 文件路径
     * @return 字节数组
     */
    public static byte[] readAllBytes(Path filePath){
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("读取文件异常");
            return bytes;
        }

        return bytes;
    }

    /**
     * 按行读取文件所有行
     * @param filePath 文件路径
     * @param charset 字符集
     * @return 文件内容行
     */
    public static List<String> readAllLines(Path filePath, Charset charset){
        List<String> list = new ArrayList<>();

        try {
            list = Files.readAllLines(filePath, charset);
        } catch (IOException e) {
            log.error("读取文件异常");
            return list;
        }

        return list;
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     * @return 删除结果
     */
    public static boolean deleteFile(Path filePath){
        boolean result = false;

        try {
            result = Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("删除文件失败");
        }

        return result;
    }

    /**
     * 删除文件列表
     * @param list 文件列表
     */
    public static void deleteFileList(List<File> list){
        for (File file : list) {
            if(file.exists()){
                file.delete();
            }
        }
    }

    /**
     * 递归删除文件或目录
     * @param fileOrDir 文件或目录
     */
    public static void recursionDeleteFileOrDir(File fileOrDir){
        if(fileOrDir.isDirectory()){
            //如果是目录，先清空内容
            File[] subFiles = fileOrDir.listFiles();
            for (File subFile : subFiles) {
                recursionDeleteFileOrDir(subFile);
            }
        }

        //删除文件或目录
        fileOrDir.delete();
    }

}
