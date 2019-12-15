package com.self.datatransferclient.modules.jna.impl;

import com.self.datatransferclient.modules.jna.service.JnaService;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Jna Service Impl
 */
@Service
@Scope("singleton")
public class JnaServiceImpl {

    /**
     * msvcrt 测试
     */
    public void msvcrt(){
        JnaService instance = Native.loadLibrary(Platform.isWindows()?"msvcrt":"c", JnaService.class);

        //toupper
        char lowerChar = 'a';
        char upperChar = (char)instance.toupper(lowerChar);
        System.out.println(lowerChar + " toupper is: " + upperChar);

        //pow
        System.out.println("pow(2d,3d) is: " + instance.pow(2d,3d));
    }

}
