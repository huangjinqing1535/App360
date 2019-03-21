package com.huang.app360.util;

import android.renderscript.Long2;
import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by huang on 2018/3/20.
 */

public class MathUtil {

    private static   DecimalFormat df = new DecimalFormat("#.00");
    public static String getDownloadTimes(String times){
        if (TextUtils.isEmpty(times)){
            return "0人在用";
        }
        double size = Double.parseDouble(times);
        if (size>=100000000.0){
            size = size/100000000.0;
            return (int)size+"亿人在用";
        }else if (size>10000.0){
            size = size/10000.0;
            return (int)size+"万人在用";
        }
        return times+"人在用";
    }


    public static String formatFileSize(String size) {
        if (TextUtils.isEmpty(size))return "";
       long fileS =  Long.parseLong(size);
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }
}
