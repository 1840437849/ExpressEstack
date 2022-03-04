package com.express.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//用于格式化日期的
public class DataFormatUtil {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式：年月日 时分秒

    //提供一个供于格式化时间的用于web使用的一个方法
    public static String format(Date date) {    //这样就可以把传入的data换成上面的格式
        return format.format(date);
    }

    public static long toTime(String formatTime) {
        try {
            return format.parse(formatTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
