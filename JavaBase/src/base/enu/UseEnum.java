package base.enu;

import java.util.EnumSet;

/*
 * 枚举类型
 * 枚举和普通的类很像,也有域和方法, 也可以继承
 */
public class UseEnum {
    
    EnumSet<SEASON> seaons = EnumSet.allOf(SEASON.class);
    
    interface SeasonInterface{
        void showSeasonName(int key);
    }
    
    public enum SEASON implements SeasonInterface{
        /*
         * 实际上这段声明相当于 Enum<SEASON> SPRING = new Enum<SEASON>(1, "春")
         */
        SPRING(1, "春"),SUMMER(2, "夏"),AUTUM(3, "秋"),WINTER(4, "冬");
        
        SEASON(int value, String name) {
            this.value = value;
            this.name = name;
        }

        private int value;
        private String name;

        @Override
        public void showSeasonName(int key) {
            switch (key) {
            case 1:
                System.out.println(SEASON.SPRING);
                break;
            case 2:
                System.out.println(SEASON.SUMMER);
                break;
            case 3:
                System.out.println(SEASON.AUTUM);
                break;
            case 4:
                System.out.println(SEASON.WINTER);
                break;
            default:
                System.out.println("No such season");
                break;
            }
        }
        
    }
}
