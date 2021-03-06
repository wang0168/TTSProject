package tts.moudle.huanxinapi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HxDbHepler extends SQLiteOpenHelper {

	// 数据库名称
	private static final String DB_NAME = "hxChat.db";
	// 数据表名称
	private static final String TABLE_NAME = "userList";
	// 数据库版本
	private static final int DB_VERSION = 1;
	// 创建数据表SQL语句
	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ "(" + "name VARCHAR(50) NOT NULL PRIMARY KEY," + "imgName" + ")";
	// 删除数据表SQL语句
	private static final String DROP_TABLE = "DROP IF TABLE EXISTS "
			+ TABLE_NAME;

	public HxDbHepler(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		System.out.println("--------------------数据库创建成功!!");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建数据表
		db.execSQL(CREATE_TABLE);
		System.out.println("------------------------数据表创建成功!!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE);
		System.out.println("-------------------------数据表更新成功!!");
		onCreate(db);
	}

}
