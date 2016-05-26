package tts.project.igg.activity.login;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.common.MyAccountMoudle;

public class InputInviteActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.mobile)
    EditText mobile;
    @Bind(R.id.action)
    Button action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_invite);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("填写邀请人信息"));
        action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                if (mobile.getText() != null) {
                    startRequestData(submitData);
                } else {
                    finish();
                }
                break;

        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case submitData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("fill_invitation_code", mobile.getText().toString());
                getDataWithPost(submitData, Host.hostUrl + "memberInterface.api?memberRegisterInvitation", params);
                break;
        }
    }
}
