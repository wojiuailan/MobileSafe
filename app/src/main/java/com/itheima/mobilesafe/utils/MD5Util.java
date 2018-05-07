package com.itheima.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/5/7.
 */

public class MD5Util {
    /**
     * 执行MD5加密
     *
     * @param string 需要加密的String
     * @return MD5加密后的String
     */
    public static String string2MD5(String string) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] temp = messageDigest.digest(string.getBytes());

            StringBuffer stringBuffer = new StringBuffer();
            for (byte b: temp) {
                int i = b & 0xff;
                String str = Integer.toHexString(i);

                if (1 == str.length()) {
                    stringBuffer.append("0");
                }

                stringBuffer.append(str);
            }

            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
