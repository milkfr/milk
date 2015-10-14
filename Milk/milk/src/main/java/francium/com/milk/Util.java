package francium.com.milk;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Francium on 2015/8/3.
 */
public class Util {

    private static long getLiveDay() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        long liveDay = 0;
        try {
            liveDay = (calendar.getTime().getTime() - timeFormat.parse(Person.birthday).getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liveDay;
    }

    private static long getDeathDay() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        long deathDay = 0;
        try {
            deathDay = (timeFormat.parse(Person.deathDay).getTime() - calendar.getTime().getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deathDay;
    }

    public static String refreshLiveTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return (timeFormat.format(calendar.getTime()) + "  小活钫出生的第" + getLiveDay() + "天");
    }

    public static String refreshDeathTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return (timeFormat.format(calendar.getTime()) + "  老活钫离嗝屁剩" + getDeathDay() + "天");
    }

    public static String refreshLiveMilk() {
        long milk = (long)(0.25 * Person.milkLikeLevel * getLiveDay());
        return "喝了" + milk + "L牛奶";
    }

    public static String refreshDeathMilk() {
        long milk = (long)(0.25 * Person.milkLikeLevel * getDeathDay());
        return "还能喝" + milk + "L牛奶";
    }

    public static String refreshLiveEgg() {
        long egg = (long)(1.5 * Person.eggLikeLevel * getLiveDay());
        return "吃了" + egg + "个鸡蛋";
    }

    public static String refreshDeathEgg() {
        long egg = (long)(1.5 * Person.eggLikeLevel * getDeathDay());
        return "还能吃" + egg + "个鸡蛋";
    }

    public static String refreshLiveMango() {
        long mango = Person.mangoLikeLevel * getLiveDay();
        return "咬了" + mango + "个芒果";
    }

    public static String refreshDeathMango() {
        long mango = Person.mangoLikeLevel * getDeathDay();
        return "还能咬" + mango + "个芒果";
    }

    public static String refreshLiveMeat() {
        long meat = (long)(0.5 * Person.milkLikeLevel * getLiveDay());
        return "吞了" + meat + "斤肉";
    }

    public static String refreshDeathMeat() {
        long meat = (long)(0.5 * Person.meatLikeLevel * getDeathDay());
        return "还能吞" + meat + "斤肉";
    }
}
