package com.android.libs.ext.utils;

/**
 * Created by E on 2017/12/28.
 */
public class DateUtil extends DateHelper{

    /**
     * 配速转换。
     * @param seconds 一KM需要多少秒
     * @return 相应格式的字符串
     */
    public static String seconds2RunningPace(int seconds){
        if (seconds < 60){
            return "00" + "\'" + formatDigs(seconds) + "\"" ;
        }else {
            int minutes = seconds/60;
            int secondsL = seconds%60;

            return formatDigs(minutes) +  "\'" + formatDigs(secondsL) + "\"";
        }
    }


    /**
     * 将秒转化为小时显示。
     * @param seconds 总秒数
     */
    public static String secondsFormatHours(int seconds){
        if (seconds < 0){
            return "00";
        }
        if (seconds < 60){
            return formatDigs(seconds);
        }else if (seconds >= 60 && seconds < 3600){
            int minutes = seconds/60;
            int secondsL = seconds%60;
            return formatDigs(minutes) + ":" + formatDigs(secondsL);
        }else {
            int minutes = seconds/60;
            int hours = minutes/60;
            int minutesL = minutes%60;
            int secondsL = seconds%60;
            return formatDigs(hours) + ":" + formatDigs(minutesL) + ":" + formatDigs(secondsL);
        }
    }

    /**
     * 将秒转化为小时显示。
     * @param seconds 总秒数
     */
    public static String secondsFormatHours1(int seconds){
        if (seconds < 0){
            return "00:00";
        }
        if (seconds < 60){
            return "00:" + formatDigs(seconds);
        }else if (seconds >= 60 && seconds < 3600){
            int minutes = seconds/60;
            int secondsL = seconds%60;
            return formatDigs(minutes) + ":" + formatDigs(secondsL);
        }else {
            int minutes = seconds/60;
            int hours = minutes/60;
            int minutesL = minutes%60;
            int secondsL = seconds%60;
            return formatDigs(hours) + ":" + formatDigs(minutesL) + ":" + formatDigs(secondsL);
        }
    }

    /**
     * 将秒转化为小时显示(00:00:00)。
     * @param seconds 总秒数
     */
    public static String secondsFormatHours3(int seconds){
        if (seconds < 0){
            return "00:00:00";
        }
        if (seconds < 60){
            return "00:00:" + formatDigs(seconds);
        }else if (seconds >= 60 && seconds < 3600){
            int minutes = seconds/60;
            int secondsL = seconds%60;
            return "00:" + formatDigs(minutes) + ":" + formatDigs(secondsL);
        }else {
            int minutes = seconds/60;
            int hours = minutes/60;
            int minutesL = minutes%60;
            int secondsL = seconds%60;
            return formatDigs(hours) + ":" + formatDigs(minutesL) + ":" + formatDigs(secondsL);
        }
    }

    /**
     * 将秒转化为小时显示。
     * @param seconds 总秒数
     */
    public static String secondsFormatHours2(int seconds){
        if (seconds < 0){
            return "00";
        }
        if (seconds < 60){
            return formatDigs(seconds) + "秒";
        }else if (seconds >= 60 && seconds < 3600){
            int minutes = seconds/60;
            int secondsL = seconds%60;
            return formatDigs(minutes) + "分钟" + formatDigs(secondsL) + "秒";
        }else {
            int minutes = seconds/60;
            int hours = minutes/60;
            int minutesL = minutes%60;
            int secondsL = seconds%60;
            return formatDigs(hours) + "小时" + formatDigs(minutesL) + "分钟" + formatDigs(secondsL) + "秒";
        }
    }

    /**
     * 将秒转化为小时显示。
     * @param seconds 总秒数
     */
    public static String secondsFormatHours4(int seconds){
        if (seconds < 0){
            return "00";
        }
        if (seconds < 60){
            return formatDigs(seconds) + "秒";
        }else if (seconds >= 60 && seconds < 3600){
            int minutes = seconds/60;
            int secondsL = seconds%60;
            if (0 == secondsL){
                return formatDigs(minutes) + "分钟";
            }
            return formatDigs(minutes) + "分钟" + formatDigs(secondsL) + "秒";
        }else {
            int minutes = seconds/60;
            int hours = minutes/60;
            int minutesL = minutes%60;
            int secondsL = seconds%60;
            if (0 == minutesL && 0 == secondsL){
                return formatDigs(hours) + "小时";
            }
            if (0 == secondsL){
                return formatDigs(hours) + "小时" + formatDigs(minutesL) + "分钟";
            }
            return formatDigs(hours) + "小时" + formatDigs(minutesL) + "分钟" + formatDigs(secondsL) + "秒";
        }
    }

    private static String formatDigs(int digs){
        return digs < 10 ? ("0" + digs) : String.valueOf(digs);
    }

}
