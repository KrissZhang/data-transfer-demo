package com.self.datatransferclient.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 水印工具类
 */
@Slf4j
public class WaterMarkUtil {

    /**
     * 为图片添加水印
     * @param imagePath 图片文件路径
     * @param waterMarkContent 水印内容
     */
    public static void addWaterMark(String imagePath, String waterMarkContent){
        //配置水印字体和颜色
        Font font = new Font("微软雅黑", Font.PLAIN,24);
        Color color = new Color(255,255,255,255);

        //获取图片文件
        File imageFile = new File(imagePath);
        if(!imageFile.exists()){
            return;
        }

        try {
            //文件转换为图片
            Image image = ImageIO.read(imageFile);

            //获取图片的宽和高
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);

            //创建画板
            BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_BGR);

            //创建2D图像
            Graphics2D graphics2D = bufferedImage.createGraphics();

            //画出图片
            graphics2D.drawImage(image, 0, 0, imageWidth, imageHeight, null);

            //设置水印的字体和颜色
            graphics2D.setFont(font);
            graphics2D.setColor(color);

            //设置水印的坐标
            int x = 20;
            int y = 10;

            //处理水印内容换行符
            List<String> contentLineList = Arrays.asList(waterMarkContent.split("\n"));

            //根据坐标在相应的位置画出水印
            for (int i = 0; i < contentLineList.size(); i++) {
                graphics2D.drawString(contentLineList.get(i), x, y + (i+1) * 26);
            }

            //清除画板
            graphics2D.dispose();

            //输出新的图片
            FileOutputStream fos = new FileOutputStream(imageFile);
            ImageIO.write(bufferedImage, "jpg", fos);

            //关闭资源
            fos.flush();
            fos.close();
        } catch (IOException e) {
            log.error("添加水印失败: {}", e);
        }
    }

}
