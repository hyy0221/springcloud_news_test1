package com.cloud.microservicearticle.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    // 2019-04-27 20:03:00 -> yyyy-MM-dd hh:mm:ss
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static String dateToString(Date date){
        return simpleDateFormat.format(date);
    }
}
