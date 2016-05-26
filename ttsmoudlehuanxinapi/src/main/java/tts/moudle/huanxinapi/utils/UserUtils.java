package tts.moudle.huanxinapi.utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import tts.moudle.huanxinapi.bean.HxUserBean;

public class UserUtils {
	
	/**
	 * 设置用户的聊天头像
	 * @param context
	 * @param name
	 * @param imageView
	 */
	public static void setUserHeadImg(Context context, String name,
			ImageView imageView) {
		/*for (HxUserBean userBean : TTSBaseApplication.getInstance().hxUserBeans) {
			if (userBean.getMyName().equals(name)) {
				byte[] c = FileUtils.readFileData(userBean.getMyImg(),
						context);
				Bitmap bm = ImgUtils.getBitmapFromByte(c);
				imageView.setImageBitmap(bm);
			}
		}*/
	}
}
