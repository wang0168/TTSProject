package tts.project.handi.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.edit_feedback)
    EditText editFeedback;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("反馈"));
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        if (!TextUtils.isEmpty(editFeedback.getText().toString())){
            startRequestData(submitData);
        }else
            CustomUtils.showTipShort(this,"请填写内容");
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == submitData) {
            params = new ArrayMap<>();
            params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
            params.put("content", editFeedback.getText().toString());
            getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=base&a=feedback", params);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        CustomUtils.showTipShort(this, response);
        finish();
    }

}
