package tts.project.igg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import tts.moudle.api.TTSBaseActivity;

/**
 * Created by lenove on 2016/4/29.
 */
public class BaseActivity extends TTSBaseActivity {
    public final int getData = 1;
    public final int submitData = 2;
    public final int delete = 3;
    public final int loadMore = 4;
    public boolean isTokenFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void doPendingFailed(int index, String error) {
        super.doPendingFailed(index, error);
        if (error.equals("token failed")) {
            Log.i("", "<<<<<<<<<<<<<<<<22222222222222");
            isTokenFailed = true;
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.putExtra("IsCallback", true);
//            startActivityForResult(intent, 3001);
        }
    }

    public void loginOk() {
//        BaseApplication.getInstance().initMyUser(getApplicationContext());
    }

    public void loginFailed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("init", "1");
        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
}
