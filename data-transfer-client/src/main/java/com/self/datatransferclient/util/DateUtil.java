package com.self.datatransferclient.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
@Slf4j
public class DateUtil {

    /**
     * 日期对象转字符串(如果日期为空，取当前日期)
     * @param date 日期对象
     * @param format 日期格式
     * @return 日期字符串
     */
    public static String formatDate(Date date, String format){
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(date == null){
            result = sdf.format(new Date());
        }else{
            result = sdf.format(date);
        }

        return result;
    }

    /**
     * 日期字符串转日期对象
     * @param date 日期字符串
     * @param format 日期格式
     * @return 日期对象
     */
    public static Date parseDate(String date, String format){
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(StringUtils.isNotBlank(date)){
            try {
                result = sdf.parse(date);
            } catch (ParseException e) {
                log.error("日期转换失败");
            }
        }

        return result;
    }

    /**
     * 计算日期相差小时数
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 相差小时数
     */
    public static long diffHours(Date startDate, Date endDate){
        long result = 0;
        long diffMs = 0;
        if(startDate.before(endDate)){
            diffMs = endDate.getTime() - startDate.getTime();
        }else{
            diffMs = startDate.getTime() - endDate.getTime();
        }

        result = diffMs / (1000 * 60 * 60);

        return result;
    }

    /**
     * 计算日期
     * @param date 基础日期
     * @param amount 相差日期数
     * @param field 相差日期单位(Calendar.DATE、Calendar.MONTH)
     * @return 日期计算结果
     */
    public static Date addTime(Date date, int amount, int field){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * 从时间戳获取日期
     * @param timeStamp 时间戳
     * @return 日期
     */
    public static Date timeStampToDate(long timeStamp){
        Date date = new Date();
        date.setTime(timeStamp);

        return date;
    }

}
