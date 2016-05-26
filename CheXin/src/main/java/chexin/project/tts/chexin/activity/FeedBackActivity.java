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
import tts.moudle.api.utils.TextUtils;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.edit_feedback)
    EditText editFeedback;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("意见反馈"));
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                startRequestData(submitData);
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == submitData) {
            params = new ArrayMap<>();
            params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
            if (TextUtils.isEmpty(editFeedback.getText().toString())) {
                CustomUtils.showTipShort(this, "请输入意见");
                return;
            }
            params.put("advice_desc", editFeedback.getText().toString());
            getDataWithPost(submitData, Host.hostUrl + "/others.api?addAdvice", params);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == submitData) {
            CustomUtils.showTipShort(this, "提交成功");
            finish();
        }
    }
}
