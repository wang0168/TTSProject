package tts.moudle.huanxinapi.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.huanxinapi.HXBaseApplication;
import tts.moudle.huanxinapi.bean.HxUserBean;

public class HxDbUtils {
	/**
	 * 保存方法
	 */
	public static void savaData(ContentValues values) {
		// 如果数据表中的id为空
		SQLiteDatabase db = HXBaseApplication.getInstance().hxdbHepler
				.getWritableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select name,imgName from userList where name =" + "'"
							+ values.getAsString("name") + "'", null);
			if (cursor.getCount() == 0) {
				db.insert("userList", null, values);
			} else {
				db.update("userList", values, "name=?",
						new String[] { values.getAsString("name") });
			}
			cursor.close();
			db.close();
		}

	}

	/**
	 * 
	 */
	public static List<HxUserBean> selectData() {
		SQLiteDatabase db = HXBaseApplication.getInstance().hxdbHepler
				.getWritableDatabase();
		List<HxUserBean> userBeans = new ArrayList<HxUserBean>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select name,imgName from userList",
					null);
			cursor.getCount();
			while (cursor.moveToNext()) {
				HxUserBean userBean = new HxUserBean();
				String name = cursor.getString(0); // 获取第一列的值,第一列的索引从0开始
				String imgName = cursor.getString(1);// 获取第二列的值
				userBean.setMyName(name);
				userBean.setMyImg(imgName);
				userBeans.add(userBean);
			}
			cursor.close();
			db.close();
		}
		return userBeans;
	}
}
