package tts.moudle.api.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义显示
 * 
 * @author sjb
 *
 */
public class CustomUtils {
	/**
	 * 展示提示 1秒
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showTipShort(Context context, String msg) {
		if (context != null) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 展示提示 3秒
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showTipLong(Context context, String msg) {
		if (context != null) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}
}
