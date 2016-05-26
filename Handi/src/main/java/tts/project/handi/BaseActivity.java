package tts.project.handi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import tts.moudle.api.TTSBaseActivity;
import tts.moudle.loginapi.activity.LoginActivity;
import tts.project.handi.activity.RecommendActivity;

/**
 * Created by chen on 2016/3/9.
 */
public class BaseActivity extends TTSBaseActivity {

    public final int getData = 1;
    public final int submitData = 2;

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
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("IsCallback", true);
            intent.putExtra("param", "2");
            startActivityForResult(intent, 3001);
        }

    }


    public void loginOk() {
        BaseApplication.getInstance().initMyUser(this.getApplication());
    }

    public void loginFailed() {
        Intent intent = new Intent(this, MainActivity.class);
//       intent.putExtra("isOut","1");
        intent.putExtra("init", "1");
        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        AppManager.getAppManager().finishActivity(this);
    }

    private String mobile;
    private String password;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3001:
                isTokenFailed = false;
                if (resultCode == this.RESULT_OK) {
                    if (data != null && data.getBooleanExtra("IsCallback", false)) {
                        Intent intent = new Intent(this, RecommendActivity.class);
                        intent.putExtra("json", data.getStringExtra("response"));
                        mobile = data.getStringExtra("mobile");
                        password = data.getStringExtra("password");
                        startActivityForResult(intent, 5011);
                    } else {
                        loginOk();
                    }
                } else {
                    loginFailed();
                }
                break;
            case 5011:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("IsCallback", true);
                intent.putExtra("mobile", mobile);
                intent.putExtra("password", password);
                intent.putExtra("param", "2");
                startActivityForResult(intent, 3001);
                break;
        }
    }
}
