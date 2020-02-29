package com.atguigu.gulimall.commons.utils;



public class StringUtils {
    public static String arrayToStringWithSeperator(String[] arr, String sep){
        StringBuffer stringBuffer = new StringBuffer();
        if (arr != null && arr.length > 0){
            for(String s: arr){
                stringBuffer.append(s);
                stringBuffer.append(sep);
            }
        }
        String result = stringBuffer.toString().substring(0, stringBuffer.length() - 1);
        return result;
    }
}
