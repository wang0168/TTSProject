package tts.moudle.huanxinapi;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tts.moudle.huanxinapi.bean.HxUserBean;
import tts.moudle.huanxinapi.db.HxDbHepler;

/**
 * Created by sjb on 2016/3/2.
 */
public class HXBaseApplication {
    private Context context;
    protected Map<Key, Object> valueCache = new HashMap<Key, Object>();
    public static List<HxUserBean> hxUserBeans;
    public static HxDbHepler hxdbHepler;

    private static class InitDataApp {
        protected final static HXBaseApplication mInstance = new HXBaseApplication();
    }

    public static HXBaseApplication getInstance() {
        return InitDataApp.mInstance;
    }

    /**
     * 环信初始化
     *
     * @param packpageName 包名
     */
    public void initHX(Context context,String packpageName) {
        this.context=context;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process
        // name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(packpageName)) {
            // "com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        // 初始化环信SDK
        Log.d("DemoApplication", "Initialize EMChat SDK");
        EMChat.getInstance().init(context);

        // 获取到EMChatOptions对象
        EMChatOptions options = EMChatManager.getInstance().getChatOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置收到消息是否有新消息通知，默认为true
        options.setNotificationEnable(false);
        // 设置收到消息是否有声音提示，默认为true
        options.setNoticeBySound(false);

        // 设置收到消息是否震动 默认为true
        options.setNoticedByVibrate(false);
        // 设置语音消息播放是否设置为扬声器播放 默认为true
        options.setUseSpeaker(false);
        // 设置从db初始化加载时, 每个conversation需要加载msg的个数
        options.setNumberOfMessagesLoaded(10);
        HXPreferenceUtils.init(context);

        // hxdbHepler = new HxDbHepler(appContext);
        // hxUserBeans = HxDbUtils.selectData();
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm
                            .getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }

    public boolean getSettingMsgSpeaker() {
        Object val = valueCache.get(Key.SpakerOn);
        if (val == null) {
            val = HXPreferenceUtils.getInstance().getSettingMsgSpeaker();
            valueCache.put(Key.SpakerOn, val);
        }
        return (Boolean) (val != null ? val : true);
    }

    enum Key {
        VibrateAndPlayToneOn, VibrateOn, PlayToneOn, SpakerOn, DisabledGroups, DisabledIds
    }
}
