package com.example.myitime;

   public class TimeUtils {

    public static int getDayback() {
        return dayback;
    }

    public static int getMinuteback() {
        return minuteback;
    }

    public static int getSecondback() {
        return secondback;
    }

    public static int getHourback() {
        return hourback;
    }

    public static void setHourback(int hourback) {
        TimeUtils.hourback = hourback;
    }

    private static int dayback;
    private static int hourback;
    private static int minuteback;
    private static int secondback;
        /**
         * 毫秒换成00:00:00
         */
        public static String getCountTimeByLong(long finishTime) {
            int totalTime =(int)  (finishTime / 1000);//秒
            int month=0,day=0,hour = 0, minute = 0, second = 0;
            if ((3600*24)<=totalTime){
                day=totalTime/(3600*24);
                totalTime=totalTime-3600*24*day;
            }
            if (3600 <= totalTime) {
                hour = totalTime / 3600;
                totalTime = totalTime - 3600 * hour;
            }
            if (60 <= totalTime) {
                minute = totalTime / 60;
                totalTime = totalTime - 60 * minute;
            }
            if (0 <= totalTime) {
                second = totalTime;
            }
            StringBuilder sb = new StringBuilder();
            if (day < 10&day>0) {
                sb.append("0").append(day).append("日");
                dayback=day;
            } else if (day>=10){
                sb.append(day).append("日");
                dayback=day;
            }else{
                dayback=0;
            }
            if (hour < 10&hour>0) {
                sb.append("0").append(hour).append("小时");
                hourback=hour;
            }
            else if (hour>=10){
                sb.append(hour).append("小时");
                hourback=hour;
            }
            else{
                hourback=0;
            }
            if (minute < 10&minute>0) {
                sb.append("0").append(minute).append("分钟");
                minuteback=minute;
            } else if (minute>=10){
                sb.append(minute).append("分钟");
                minuteback=minute;
            }else{
                minuteback=0;
            }
            if (second < 10&second>0) {
                sb.append("0").append(second).append("秒");
                secondback=second;
            } else if (second>=10){
                sb.append(second).append("秒");
                secondback=second;
            }else{
                secondback=0;
                sb.append("0").append("秒");

            }
            return sb.toString();
        }

        /**
         * 将LONG类型的时间戳转换为xx:xx:xx时分秒
         */
       public static String formatLongToTimeStr(Long l) {
            int hour = 0;
            int minute = 0;
            int second = 0;
            second = l.intValue();
            if (second > 60) {
                minute = second / 60;         //取整
                second = second % 60;         //取余
            }
            if (minute > 60) {
                hour = minute / 60;
                minute = minute % 60;
            }
            String strtime = hour + "小时" + minute + "分钟" + second+"秒";
            return strtime;
        }
}

