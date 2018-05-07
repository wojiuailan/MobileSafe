package com.itheima.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/6.
 */

public class StreamUtil {
    /**
     * 从输入流里获取里面的字符串
     * @param in 需要获取字符串的输入流
     * @return   输入流里的字符串，返回null代表没有或者获取失败
     * @throws IOException
     */
    public static String readStringFromIn(InputStream in) throws IOException {
        String result = null;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        int len;
        while ((len = in.read(temp)) != -1) {
            bOut.write(temp, 0, len);
        }
        result = bOut.toString();
        in.close();
        bOut.close();
        return result;
    }
}
