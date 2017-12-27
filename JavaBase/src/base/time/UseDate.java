package base.time;

import java.util.Date;

//Date类型不推荐使用
public class UseDate {
    
    //年从1990开始
    //月:0-11
    //日:1-31
    //小时:0-23
    //分钟:0-59
    //秒钟:0-59
    Date date = new Date();
    
    public void method() {
        //该时间是date与1990/1/1 00:00:00的差值，单位是毫秒
        long time = date.getTime();
    }
    /*
     * 时间类型简介:
     * UTC:世界协调时间
     * GMT:Greenwich Mean Time,格林尼治平均时
     * UTC和GMT时完全相同的
     * Gregorian calendar:公历
     */
}
