package com.self.datatransferclient.modules.jna.service;

import com.sun.jna.Library;

/**
 * Jna Service
 */
public interface JnaService extends Library {

    /**
     * 字符转大写
     * @param ch 字符编码值
     * @return 字符编码值
     */
    int toupper(int ch);

    /**
     * 幂计算
     * @param x 底数
     * @param y 指数
     * @return 幂值
     */
    double pow(double x, double y);

}
