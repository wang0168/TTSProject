package chexin.project.tts.chexin.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.UserInfoBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;

public class MyAccountActivity extends BaseActivity {

    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.grade)
    TextView grade;
    @Bind(R.id.balance)
    TextView balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("账户信息"));
        startRequestData(getData);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == getData) {
            params = new ArrayMap<>();
            params.put("mobileno", MyAccountMoudle.getInstance().getUserInfo().getMobileno() + "");
            getDataWithPost(getData, Host.hostUrl + "userfaces?getBalance", params);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == getData) {
            balance.setText(response);
//            userInfoBean = new Gson().fromJson(response, new TypeToken<UserInfoBean>() {
//            }.getType());
//            if (userInfoBean != null) {
//                bindData();
//            }
        }
    }

}
