package chexin.project.tts.chexin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import chexin.project.tts.chexin.activity.login.LoginActivity;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BarBean;


/**
 * Created by chen on 2016/3/9.
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
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("IsCallback", true);
            startActivityForResult(intent, 3001);
        }

    }

    @Override
    protected void setTitle(BarBean barBean) {
        barBean.setIconId(R.drawable.gradient);
        barBean.setIconName(barBean.getIconName() == null ? "返回" : barBean.getIconName());
        super.setTitle(barBean);
    }

    public void loginOk() {
        BaseApplication.getInstance().initMyUser(getApplicationContext());
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

    }

    private String mobile;
    private String password;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3001:
                Logger.d("2222222222activity");
                isTokenFailed = false;
                if (resultCode == this.RESULT_OK) {
                    loginOk();
                } else {
                    loginFailed();
                }
                break;
            case 5011:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("IsCallback", true);
                intent.putExtra("mobile", mobile);
                intent.putExtra("password", password);
                startActivityForResult(intent, 3001);
                break;
        }
    }
}
