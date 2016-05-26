package chexin.project.tts.chexin.common;

import tts.moudle.api.utils.TextUtils;

/**
 * Created by lenove on 2016/4/21.
 */
public class Utils {

    public static String subTime(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) {
            return "时间未知";
        }
        if (timeStr.split(" ").length < 2) {
            return "时间未知";
        }
        String temp = timeStr.split(" ")[1];
        if (temp.length() < 5) {
            return "时间未知";
        }
        return temp.substring(0, 5);
    }

    public static void main(String[] args) {
        System.out.println(Utils.subTime("11:45:34.0"));
    }
}
