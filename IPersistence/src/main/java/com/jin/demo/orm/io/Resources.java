package com.jin.demo.orm.io;

import java.io.InputStream;

/**
 * @author wangjin
 */
public class Resources {
    public static InputStream getResourceAsSteam(String path){
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
