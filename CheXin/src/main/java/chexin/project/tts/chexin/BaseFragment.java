package chexin.project.tts.chexin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.Logger;

import chexin.project.tts.chexin.activity.login.LoginActivity;
import tts.moudle.api.TTSBaseFragment;
import tts.moudle.api.bean.BarBean;


/**
 * Created by chen on 2016/3/9.
 */
public class BaseFragment extends TTSBaseFragment {
    public final int getData = 1;
    public final int loadMore = 2;
    private boolean isTokenFailed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void setTitle(BarBean barBean) {
        barBean.setIconId(R.drawable.gradient);
        barBean.setIconName("".equals(barBean.getIconName()) ? "返回" : barBean.getIconName());
        super.setTitle(barBean);
    }

    @Override
    protected void doPendingFailed(int index, String error) {
        super.doPendingFailed(index, error);
        if (error.equals("token failed")) {
            isTokenFailed = true;
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra("IsCallback", true);
            startActivityForResult(intent, 3001);
        }

    }


    public void loginOk() {
        BaseApplication.getInstance().initMyUser(getActivity().getApplication());
    }

    public void loginFailed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
//       intent.putExtra("isOut","1");
        intent.putExtra("init", "1");
        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private String mobile;
    private String password;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3001:
                Logger.d("1111111111111fragment");
                isTokenFailed = false;
                if (resultCode == getActivity().RESULT_OK) {
                    Logger.d("333333333333333fragment");
                    loginOk();
                } else {
                    loginFailed();
                }
                break;
            case 5011:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("IsCallback", true);
                intent.putExtra("mobile", mobile);
                intent.putExtra("password", password);
                startActivityForResult(intent, 3001);
                break;
        }
    }
}
