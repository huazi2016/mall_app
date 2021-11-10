package com.mall.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author : huazi
 * time   : 2021/4/29
 * desc   :
 */
public class TimeUtil {

    public static String getNowDate() {
        Date currentTime = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
