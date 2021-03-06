package tts.moudle.api.utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.util.Log;

public class TimeUtils {

	public static long dateToLong(String in, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date;
		try {
			date = format.parse(in);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.getTimeInMillis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 从毫秒数 转时间格式
	 * 
	 * @return
	 */
	public static String getStringFromInt(long ssTime, String pattern) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
		return sDateFormat.format(new Date(ssTime * 1000 + 0));
	}

	/**
	 * 获得当前日
	 * 
	 * @return
	 */
	public static int getDay() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 获得当前月
	 * 
	 * @return
	 */
	public static int getMonth() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int month = c.get(Calendar.MONTH);
		return month;
	}

	/**
	 * 获得当前年
	 * 
	 * @return
	 */
	public static int getYear() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 获得当前小时
	 * 
	 * @return
	 */
	public static int getHour() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/**
	 * 获得当前分钟
	 * 
	 * @return
	 */
	public static int getMinute() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int minute = c.get(Calendar.MINUTE);
		return minute;
	}

	/**
	 * 返回星期几
	 * 
	 * @return 1代表 星期天 以此类推
	 */
	public static String getWeekDay(int mWay) {
		String weekDay = "";
		switch (mWay) {
		case 1:
			weekDay = "星期天";
			break;
		case 2:
			weekDay = "星期一";
			break;
		case 3:
			weekDay = "星期二";
			break;
		case 4:
			weekDay = "星期三";
			break;
		case 5:
			weekDay = "星期四";
			break;
		case 6:
			weekDay = "星期五";
			break;
		case 7:
			weekDay = "星期六";
			break;
		default:
			break;
		}
		return weekDay;
	}

	/**
	 * 返回星期几
	 * 
	 * @return 1代表 星期天 以此类推
	 */
	public static int getWeekDay() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int mWay = c.get(Calendar.DAY_OF_WEEK);
		return mWay;
	}

	/**
	 * 毫秒数转时间字符串
	 * 
	 * @param pattern
	 *            时间格式
	 * @param dateTime
	 *            毫秒数
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getTimeFromMinmis(String Format, long dateTime) {
		try {
			Log.i("", dateTime + ":Minmis");
			if (dateTime == 0) {
				return "";
			}
			SimpleDateFormat sDateFormat = new SimpleDateFormat(Format);
			return sDateFormat.format(new Date(dateTime + 0));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将秒数除以1000的数 转化为时间
	 * 
	 * @param Format
	 * @param dateTime
	 * @return
	 */
	public static String getTimeFromMinmis(String Format, String dateTime) {
		try {
			if (TextUtils.isEmpty(dateTime)) {
				return "";
			}
			if (!TextUtils.isNumeric(dateTime)) {
				return "";
			}
			return getTimeFromMinmis(Format, Long.valueOf(dateTime) * 1000);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获得当前时间
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTimeString(String Format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(Format);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获得一个时间N天之后的时间
	 * 
	 * @return
	 */
	public static String getTimeAfter(Date date, int dayNum, String dateFormat) {
		String dateString = "";
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			now.set(Calendar.DATE, now.get(Calendar.DATE) + dayNum);
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			dateString = formatter.format(now.getTime());
		} catch (Exception e) {
			dateString = "";
		}
		return dateString;
	}

	/**
	 * 字符串转date
	 * 
	 * @param strTime
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date getDateFromString(String strTime, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = formatter.parse(strTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 时间差--单位天
	 */

	public static long getDayCut(String startTime, String endTime, String format) {
		DateFormat df = new SimpleDateFormat(format);
		long days = 0;
		try {

			Date d1 = df.parse(startTime);
			Date d2 = df.parse(endTime);
			long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			days = diff / (1000 * 60 * 60 * 24);
			// System.out.println(""+days+"天"+hours+"小时"+minutes+"分");

		} catch (Exception e) {
		}
		return days;
	}

	/**
	 * 到期时间
	 * 
	 * @param startTime
	 * @param endTime
	 * @param pattern
	 * @return
	 */
	public static String getDifferenceTime(long startTime, long endTime, String pattern) {

		String result;

		// 时间差
		long difference = endTime - startTime;

		if (difference <= 0) {
			result = "已过期";
		} else {

			result = getStringFromInt(difference, pattern);
		}
		return result;
	}
}
