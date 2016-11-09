package services.tools;

/**
 * Created by nicolas on 08/11/16.
 */
public class timeTools {

    public static String printTime(int time){
        int hour = time/3600;
        int minute = (time%3600)/60;
        String ret = "";
        if(hour<10)
            ret += "0";
        ret += hour+"h";
        if(minute<10)
            ret += "0";
        ret += minute;
        return ret;
    }
}
