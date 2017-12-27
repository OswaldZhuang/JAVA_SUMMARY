package base.time;

import java.util.Calendar;
import java.util.Locale;

public class UseCalendar {
    //根据默认的时区和地点创建Calendar实例
    static Calendar calendar = Calendar.getInstance();
    
    public static void method() {
        //如果before里面的参数不是Calendar类型,那么就会返回false
        calendar.before(Calendar.getInstance(Locale.ENGLISH));
        
        /*
         * add(int field, int amount)和roll(int field, int amount)的作用是一样的，不同的是:
         * 用add增加字段超出其范围的时候,会发生进位,上级字段增大,下级字段事实上不会发生变化
         * 比如:2017/11/30 Month上加3个月,那么会变成2018/2/30
         * 而对于roll来说, 增加字段超出范围,上级字段不会发生改变,下级字段同样也不会发生变化
         * 比如:2017/11/30 Month上加3个月,那么会变成2017/2/30
         * 
         * 实际上 ,roll(int field, int amount)底层循环调用的是 roll(int field, boolean up)
         * 该方法表示(前进/后退)  1 (年/月/日) 该方法对上下级的字段都会做出调整
         * 但是roll(int field, int amount)会有问题,暂时不知道为什么
         * 从这个意义上讲,Calendar并不推荐使用
         */
        //下面这个例子 调用roll() year = 2017 month = 2 day = 30
        //调用add() year = 2018 month = 2 day = 30
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 30);
        
        //calendar.set(2017, 11, 30, 0, 0, 0);
        
        calendar.roll(Calendar.MONTH, 3);
        //calendar.roll(Calendar.MONTH, true);
        //calendar.roll(Calendar.MONTH, true);
        //calendar.roll(Calendar.MONTH, true);
        //calendar.add(Calendar.MONTH, 3);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        System.out.println(year+","+month+","+day);
    }
    
    public static void main(String[] args) {
        method();
    }

}
