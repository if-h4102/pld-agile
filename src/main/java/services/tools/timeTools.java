package services.tools;

public class timeTools {

    /**
     * Return a string which contains the given time with the correct format
     * (separate hours, minutes and seconds).
     * @param time The time to convert into string.
     * @return The string which contains the givent time with the coorect format.
     */
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
