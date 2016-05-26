package chexin.project.tts.chexin.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;

/**
 * 实名认证页面
 */
public class TrueNameActivity extends BaseActivity {

    @Bind(R.id.edit_true_name)
    EditText editTrueName;
    @Bind(R.id.edit_card_code)
    EditText editCardCode;
    @Bind(R.id.btn_action)
    Button btnAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_name);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("实名认证"));
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRequestData(submitData);
            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case submitData:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("certification_name", editTrueName.getText().toString());
                params.put("certification_card", editCardCode.getText().toString());
                getDataWithPost(submitData, Host.hostUrl + "userfaces?userCertification", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (submitData == index) {
            CustomUtils.showTipShort(this, "已提交系统审核");
            setResult(RESULT_OK);
            finish();
        }
    }
}
