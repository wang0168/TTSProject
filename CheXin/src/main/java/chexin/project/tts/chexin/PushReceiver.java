package chexin.project.tts.chexin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import tts.moudle.api.jpush.JpushReceiver;

/**
 * Created by lenove on 2016/4/25.
 */
public class PushReceiver extends JpushReceiver {
    /**
     * 处理自定义消息
     * @param context
     * @param intent
     */
    @Override
    public void doCustom(Context context, Intent intent) {
        super.doCustom(context, intent);

    }

    /**
     * 处理点击通知事件
     * @param context
     * @param intent
     */
    @Override
    public void doNotification(Context context, Intent intent) {
        super.doNotification(context, intent);
        Intent noteIntent=new Intent(context,MainActivity.class);
        noteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(noteIntent);
    }
}
