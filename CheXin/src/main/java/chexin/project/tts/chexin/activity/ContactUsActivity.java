package chexin.project.tts.chexin.activity;


import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.JsonUtils;

public class ContactUsActivity extends BaseActivity {

    @Bind(R.id.tv_official_phone_number)
    TextView tvOfficialPhoneNumber;
    @Bind(R.id.tv_official_email)
    TextView tvOfficialEmail;
    @Bind(R.id.tv_official_wechat)
    TextView tvOfficialWechat;
    @Bind(R.id.tv_official_qq)
    TextView tvOfficialQq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("联系我们"));
        mProgressDialog.show();
        startRequestData(getData);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        if (index == getData) {
            getDataWithGet(getData, Host.hostUrl + "contactus?query");
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        mProgressDialog.dismiss();
        if (index == getData) {
            tvOfficialPhoneNumber.setText(JsonUtils.getJsonFromJson(response, "officialphone"));
            tvOfficialEmail.setText(JsonUtils.getJsonFromJson(response, "officialemail"));
            tvOfficialWechat.setText(JsonUtils.getJsonFromJson(response, "officialweixin"));
            tvOfficialQq.setText(JsonUtils.getJsonFromJson(response, "officialqq"));
        }
    }
}
