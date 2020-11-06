package com.kitty.kitty_base.utils;

import android.annotation.SuppressLint;

import com.kitty.kitty_ads.union_test.toutiao.utils.UIUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 日期相关的工具
 *
 * @author ningkang
 */
public class DateUtil {
    /**
     * 一年的天数
     */
    public static final int YEAR_DAYS = 365;
    public static final long DAY_TIME_IN_MILLIONS = 24 * 60 * 60 * 1000L;
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATE1 = "yyyyMMdd";
    public static final String PATTERN_DATE2 = "yyyyMMddHHmmss";
    public static final String PATTERN_DATE3 = "yyyy.MM.dd";
    public static final String MONTH_DAY = "MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE_CN = "yyyy年MM月dd日";
    public static final String PATTERN_TIME_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String ZERO_TIME = " 00:00:00";
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>();
    private static final Object OBJECT = new Object();
    public static final String YYYY_MM_DD = "yyyy/MM/dd";

    /**
     * 系统时间偏移量
     */
    private static long TIME_OFFSET = 0L;

    /**
     * <b>字符串日期转换为日期类型 formatted as yyyy-MM-dd</b>
     *
     * @return 解析出得日期
     * @throws IllegalArgumentException 参数错误
     */
    public static Date parseDate(String dateValue) throws IllegalArgumentException {
        if (dateValue == null) {
            throw new IllegalArgumentException("date is null");
        }
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        Date date = null;
        try {
            date = dateFormat.parse(dateValue);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
        return date;
    }

    /**
     * 字符串日期转换为日期类型
     *
     * @param dateValue 字符串日期
     * @param pattern   日期模式
     * @return 解析对象
     * @throws IllegalArgumentException 参数错误
     */
    public static Date parseDate(String dateValue, String pattern) throws IllegalArgumentException {
        if (dateValue == null) {
            throw new IllegalArgumentException("date is null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        }
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dateFormat.parse(dateValue);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
        return date;
    }

    /**
     * <b>日期类型转换为字符串 formatted as yyyy-MM-dd</b>
     *
     * @param dateValue 日期类型
     * @return 格式化字符串
     */
    public static String formatDate(Date dateValue) {
        if (dateValue == null) {
            throw new IllegalArgumentException("date is null");
        }
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        String date = dateFormat.format(dateValue);
        return date;
    }

    /**
     * 日期类型转换为字符串
     *
     * @param dateValue 日期类型
     * @param pattern   日期模式
     * @return 格式化字符串
     */
    public static String formatDate(Date dateValue, String pattern) {
        if (dateValue == null) {
            throw new IllegalArgumentException("date is null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        }
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        String date = dateFormat.format(dateValue);
        return date;
    }

    /**
     * <b>获取当前日期：formatted as yyyy-MM-dd</b>
     *
     * @return
     */
    public static String getCurrentDateStr() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        return dateFormat.format(new Date(getCurrentTime()));
    }

    /**
     * <b>获取当前日期：</b>
     *
     * @param pattern 日期模式
     * @return
     */
    public static String getCurrentDate(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("pattern is null");
        }
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }


    /**
     * <b>获取给定日期所在的年份</b>
     *
     * @param date 日期
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


    /**
     * <b>根据给定日期获取其所在的月份 1-12</b>
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * <b>根据给定日期获取其所在月中的日期</b>
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * <b>获取本月最后一天：formatted as yyyy-MM-dd</b>
     *
     * @return
     */
    public static String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取本月最后一天毫秒数：formatted as yyyy-MM-dd</b>
     *
     * @return
     */
    public static long getLastDayTimeOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * <b>获取本月最后一天：</b>
     *
     * @param date    日期
     * @param pattern 日期模式
     * @return
     */
    public static String getLastDayOfMonth(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取本月最后一天毫秒数：</b>
     *
     * @param date 日期
     * @return
     */
    public static long getLastDayTimeOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * <b>获取本月第一天：formatted as yyyy-MM-dd</b>
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        calendar.set(Calendar.DATE, 1);
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取本月第一天毫秒数：formatted as yyyy-MM-dd</b>
     *
     * @return
     */
    public static long getFirstDayTimeOfMonth(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    public static long getFirstDayTimeOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        calendar.set(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * <b>获取给定日期所在月的第一天：</b>
     *
     * @param date    日期
     * @param pattern 日期模式
     * @return
     */
    public static String getFirstDayOfMonth(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * <b>获取给定日期所在月的第一天毫秒数：</b>
     *
     * @param date 日期
     * @return
     */
    public static long getFirstDayTimeOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * <b>获取本周星期一：formatted as yyyy-MM-dd</b>
     *
     * @return
     */
    public static String getMondayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取本周星期一 零时零分零秒
     *
     * @return long
     * @throws Exception Wangshutao 2014年8月14日 上午10:10:43
     */
    public static Long getLongTimeOfMondayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        String date = dateFormat.format(calendar.getTime()) + ZERO_TIME;
        dateFormat = new SimpleDateFormat(PATTERN_DATE_TIME);
        try {
            return dateFormat.parse(date).getTime();
        } catch (ParseException e) {
            throw new IllegalArgumentException("parse fail");
        }
    }

    /**
     * 获取一周当中的某一天
     * dayOfWeek SUNDAY:1,MONDAY:2,TUESDAY:3,WEDNESDAY:4,THURSDAY:5,FRIDAY:6,SATURDAY:7
     *
     * @param dayOfWeek
     * @return
     * @author liujia
     */
    public static Long getLongTimeOfDayOfWeek(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getCurrentTime());
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
        String date = dateFormat.format(calendar.getTime()) + ZERO_TIME;
        dateFormat = new SimpleDateFormat(PATTERN_DATE_TIME);
        try {
            return dateFormat.parse(date).getTime();
        } catch (ParseException e) {
            throw new IllegalArgumentException("parse fail");
        }
    }


    /**
     * @param date    日期
     * @param pattern 日期格式
     * @return 获取上月最后一天
     * @author liujia
     */
    public static String getLastDayOfLastMonth1(Date date, String pattern) {
        int year = getYear(date);
        int month = getMonth(date);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getCurrentTime());

        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 2);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfLastMonth = sdf.format(cal.getTime());

        return lastDayOfLastMonth;
    }

    /**
     * 根据时间增加或者减少天数
     *
     * @param date
     * @param num：天数，增加天数传入正整数，减少天数传入负整数
     * @return
     * @author CH
     * @date 2014年10月18日 下午1:38:00
     */
    public static Date addDay(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }


    /**
     * 增加或者减少月份
     *
     * @param date
     * @param num：天数，增加天数传入正整数，减少天数传入负整数
     * @return
     * @author CH
     * @date 2014年10月18日 下午1:31:20
     */
    public static Date addMonth(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        return calendar.getTime();
    }

    /**
     * 增加或者减少年份
     *
     * @param date：时间
     * @param num：年份，增加年份传入正整数，减少年份传入负整数
     * @return
     * @author CH
     */
    public static Date addYear(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, num);
        return calendar.getTime();
    }

    /**
     * 根据传入的时间，得到传入时间的秒数
     *
     * @param date：时间
     * @author CH
     * @return：返回某一时间秒数
     */
    public static int getTimeSecond(Date date) {
        return Long.valueOf(date.getTime() / 1000L).intValue();
    }

    /**
     * 根据传入的时间，得到传入时间的秒数
     *
     * @param ：时间
     * @author CH
     * @return：返回某一时间毫秒数
     */
    public static long getTime(String dateStr) {

        Date date = null;
        try {
            date = parseDate(dateStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date.getTime();
    }

    /**
     * 以秒数返回当前时间
     *
     * @return
     * @author CH
     */
    public static Integer getCurrentTimeSecond() {
        return Long.valueOf(getCurrentTime() / 1000L).intValue();
    }

    /**
     * 以毫秒数返回当前时间
     *
     * @return
     * @author CH
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis() + TIME_OFFSET;
    }

    /**
     * 根据两个时间，获取两个时间相差的天数，最终以绝对值返回
     *
     * @param date      日期
     * @param otherDate 另一个日期
     * @return 相差天数。如果失败则返回-1
     * @author liujia
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = DateUtil.StringToDate(DateUtil.getDate(date), PATTERN_DATE);
        Date otherDateTmp = DateUtil.StringToDate(DateUtil.getDate(otherDate), PATTERN_DATE);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    public static int getIntervalDays(long startTime, long endTime) {
        int num = -1;
        Date dateTmp = DateUtil.StringToDate(DateUtil.getDate(new Date(startTime)), PATTERN_DATE);
        Date otherDateTmp = DateUtil.StringToDate(DateUtil.getDate(new Date(endTime)), PATTERN_DATE);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    /**
     * 根据两个时间，获取两个时间相差的分钟数，最终以绝对值返回
     *
     * @param startTime : 起始时间
     * @param endTime   : 结束时间
     * @return int, 时间差
     * @author chenchuang
     */
    public static int getIntervalMinutes(long startTime, long endTime) {
        int num = -1;
        Date dateTmp = new Date(startTime);
        Date otherDateTmp = new Date(endTime);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (60 * 1000));
        }
        return num;
    }

    /**
     * 根据所传分钟数，获取时间
     *
     * @param date   : 时间参数
     * @param minute : 分钟参数
     * @return long, 时间值
     * @author chenchuang
     */
    public static long getMinutesInMillis(long date, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date));
        calendar.add(Calendar.MINUTE, minute);

        return calendar.getTimeInMillis();
    }

    /**
     * 获取两个时间之间相差的月份
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return
     * @author liujia
     */
    public static int getIntervalMonths(long date1, long date2) {
        int result = 0;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(new Date(date1));
        c2.setTime(new Date(date2));

        int y1 = c1.get(Calendar.YEAR);
        int y2 = c2.get(Calendar.YEAR);

        int m1 = c1.get(Calendar.MONTH);
        int m2 = c2.get(Calendar.MONTH);

        result = m2 - m1 + (y2 - y1) * 12;

        return Math.abs(result);
    }

    /**
     * 获取两个时间之间相差的周数
     *
     * @param startTime
     * @param endTime
     * @return
     * @author liujia
     */
    public static int getIntervalWeeks(long startTime, long endTime) {
        int num = 0;

        long startTimeMonday = getFirstDayOfWeek(new Date(startTime));
        long endTimeSuday = getLastDayOfWeek(new Date(endTime));

        long time = Math.abs(endTimeSuday - startTimeMonday);
        num = (int) (time / (7 * 24 * 60 * 60 * 1000));

        return num;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static Date StringToDate(String date) {
        return StringToDate(date, PATTERN_DATE);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date    日期字符串
     * @param pattern 日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 获取SimpleDateFormat
     *
     * @param pattern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern) {
        SimpleDateFormat dateFormat = THREAD_LOCAL.get();
        if (dateFormat == null) {
            synchronized (OBJECT) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    THREAD_LOCAL.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return DateToString(date, PATTERN_DATE);
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将以秒数记录的时间转换为字符串类型的时间格式
     *
     * @param timestamp：以秒记录的时间
     * @param format：转换后的时间格式   　eg:　yyyy-MM-dd HH:mm:ss
     * @return
     * @author CH
     */
    public static String TimeStampDateSecond(long timestamp, String format) {
        long time = (long) timestamp * 1000;
        String date = new SimpleDateFormat(format).format(new Date(time));
        return date;
    }

    /**
     * 将以毫秒数记录的时间转换为字符串类型的时间格式
     *
     * @param timestamp：以毫秒记录的时间
     * @param format：转换后的时间格式    　eg:　yyyy-MM-dd HH:mm:ss
     * @return
     * @author CH
     */
    public static String TimeStampDate(long timestamp, String format) {
        String date = new SimpleDateFormat(format).format(new Date(timestamp));
        return date;
    }


    /**
     * 根据上一次还款日，及设置的每月付息日，计算下个月的付息日
     *
     * @param lastRepaymentDate
     * @param accountDay
     * @return
     * @author liujia
     * @Return Date
     */
    public static Date getRepaymentDate(long lastRepaymentDate, int accountDay, int multiNum) {
        Date returnDate = null;
        //上一次还款日
        Date valueDate = new Date(Long.parseLong(lastRepaymentDate + ""));
        //还款月+1
        Date nextMonth = DateUtil.addMonth(valueDate, 1 * multiNum);
        //获取还款日所在的日
        int day = DateUtil.getDay(nextMonth);
        //日<定制还息日
        if (day < accountDay) {
            int num = accountDay - day;
            //本次还息日
            returnDate = DateUtil.addDay(nextMonth, num);
        } else if (day > accountDay) {
            int num = day - accountDay;
            returnDate = DateUtil.addDay(nextMonth, -num);
        } else {
            returnDate = nextMonth;
        }
        return getIntervalDays(returnDate, valueDate) > 30 ? nextMonth : returnDate;
    }

    /**
     * 获取当前时间的小时数
     *
     * @return
     * @author CH
     */
    public static int getHour() {
        long currentTime = getCurrentTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(currentTime));
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前日期对象
     *
     * @return
     * @author: z-feng
     */
    public static Calendar getCurrentCalendar() {
        long currentTime = getCurrentTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(currentTime));
        return calendar;
    }

    /**
     * 设置系统的时间偏移量
     *
     * @param timeOffset
     * @author: z-feng
     */
    public static void setTimeOffset(long timeOffset) {
        TIME_OFFSET = timeOffset;
    }

    /**
     * 获取下一日零点的毫秒值
     *
     * @param time
     * @return
     * @author: liujia
     */
    public static long getNextDayZeroHourInMills(long time) {
        Date endTime = DateUtil.addDay(new Date(time), 1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * 获取某一日零点的毫秒值
     *
     * @param time
     * @param day
     * @return
     * @author: liujia
     */
    public static long getDayZeroHourInMills(long time, int day) {
        Date returnTime = DateUtil.addDay(new Date(time), day);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(returnTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * 获取下一日零点的毫秒值
     * 获取离给定时间最接近的一个9点时间，
     *
     * @param time
     * @return 如果给定时间是当天0-9时，则返回当天的8时
     * 若给定时间为9-24时，则返回第二天的8时
     * @author: z-feng
     */
    public static long getRecentlyNighHourInMillis(long time) {
        long offset = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if (calendar.get(Calendar.HOUR_OF_DAY) > 9) {
            offset = DAY_TIME_IN_MILLIONS;
        }
        calendar.set(Calendar.HOUR_OF_DAY, 9);

        return calendar.getTimeInMillis() + offset;
    }

    /**
     * 获取某一日23:59:59的毫秒值
     *
     * @param time 指定时间
     * @param day  递增天数
     * @return 指定时间的最后时间
     * @author chenchuang
     */
    public static long getDayLastHourInMills(long time, int day) {
        Date returnTime = DateUtil.addDay(new Date(time), day);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(returnTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * 传入固定格式日期返回该日期的时间戳
     *
     * @param dateFormatStr eg:yyyy-MM-dd
     * @return
     * @author: JX
     */
    public static long getTimestampByDate(String dateFormatStr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(dateFormatStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }

    public static boolean betweenHour(long time, int minHour, int maxHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return hour >= minHour && hour <= maxHour;
    }

    /**
     * 获得延迟短信的推送时间
     *
     * @return
     */
    public static long getNoticeTime() {
        long currentTime = getCurrentTime();
        long noticeTime = getRecentlyNighHourInMillis(currentTime);
        if (DateUtil.betweenHour(currentTime, 9, 21)) {
            noticeTime = currentTime;
        }
        return noticeTime;
    }

    public static int dayOfMonth(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthDays(long timeMillis) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(timeMillis);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 判断两个日期是否为同一天
     *
     * @param date1 日期一
     * @param date2 日期二
     * @return 同一天true，不是同一天false
     */
    public static boolean isSameDate(Date date1, Date date2) {
        boolean result = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            result = true;
        }
        return result;
    }

    public static boolean isDate(String value) {
        //String value = "2007-02-2a";
        String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]? ((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(value);
        boolean flag = m.matches();
        return flag;
    }

    public static int getDayOfMonth(long longTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(longTime);

        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得当前日期所在周的第一天
     * 默认周一为每周第一天
     *
     * @param date
     * @return
     */
    public static long getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                // Sunday
                calendar.getFirstDayOfWeek());
        return calendar.getTime().getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     * 默认周一为每周第一天
     *
     * @param date
     * @return
     */
    public static long getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime().getTime();
    }

    public static long getCurrentDayZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某一日是周的第几天
     * 默认周一为每周第一天
     *
     * @param date
     * @return
     * @author liujia
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int num = calendar.get(Calendar.DAY_OF_WEEK);

        if (calendar.getFirstDayOfWeek() == Calendar.MONDAY) {
            num = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        return num == 0 ? Calendar.SATURDAY : num;
    }

//    /**
//     * 获取保存的时间和当前时间之间的时间差是否达到某个设定的时间；
//     *
//     * @param key             用来获取保存的时间值
//     * @param intervalMin     设定的时间差
//     * @param needLoginStatus 是否需要登录状态的判断条件
//     */
//    public static boolean getIntervalTime(String key, long intervalMin, boolean needLoginStatus) {
//        Long offKeyBack = (Long) SPUtils.getLong(Utils.getContext(), key, 0L);
//        long offTime = ((System.currentTimeMillis() - offKeyBack) / 60000);
//        boolean notIntervalTime = needLoginStatus ? !LoginUtils.isUserLogin() || offTime < intervalMin || offKeyBack == 0L : offTime < intervalMin || offKeyBack == 0L;
//        if (notIntervalTime) {
//            return false;
//        }
//        return true;
//    }

    public static String getPatternDate3(long time) {
        return getDateFormat(PATTERN_DATE3).format(new java.sql.Date(time));
    }

    public static String getPatternData4(long time) {
        return getDateFormat(YYYY_MM_DD).format(new java.sql.Date(time));
    }

    public static String getPatternData5(long time) {
        return getDateFormat(PATTERN_DATE_CN).format(new java.sql.Date(time));
    }

    public static String getPatternDataTime(long time) {
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE_TIME);
        return dateFormat.format(new Date(time));
    }

    public static String getPatternDate(long time) {
        return getDateFormat(PATTERN_DATE).format(new java.sql.Date(time));
    }

    public static boolean isSameDay(long millis1, long millis2, TimeZone timeZone) {
        long interval = millis1 - millis2;
        return interval < 86400000 && interval > -86400000 && millis2Days(millis1, timeZone) == millis2Days(millis2, timeZone);
    }

    private static long millis2Days(long millis, TimeZone timeZone) {
        return (((long) timeZone.getOffset(millis)) + millis) / 86400000;
    }

    public static boolean isSomeDay(long mill1, long mill2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(mill1);
        calendar2.setTimeInMillis(mill2);
        int month1 = calendar1.get(Calendar.MONTH);
        int month2 = calendar2.get(Calendar.MONTH);
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
       return month1 == month2&& day1==day2;
    }

}
