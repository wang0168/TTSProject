package chexin.project.tts.chexin.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.CircleImageView;

/**
 * 评价页面
 */
public class AssessmentActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_face)
    CircleImageView imgFace;
    @Bind(R.id.tv_name)
    TextView tvName;
    //    @Bind(R.id.on_time)
//    TextView onTime;
//    @Bind(R.id.service)
//    TextView service;
//    @Bind(R.id.intact)
//    TextView intact;
    @Bind(R.id.bar_on_time)
    RatingBar barOnTime;
    @Bind(R.id.bar_service)
    RatingBar barService;
    @Bind(R.id.bar_intact)
    RatingBar barIntact;
    @Bind(R.id.edit_context)
    EditText editContext;
    @Bind(R.id.btn_action)
    Button btnAction;
    private String from;//1评价车，2评价货
    private String order_id;
    private String urlStr;
    private String faceUrlStr;
    private String nameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("进行评价"));
        from = getIntent().getStringExtra("from");
        order_id = getIntent().getStringExtra("order_id");
        faceUrlStr = getIntent().getStringExtra("face");
        nameStr = getIntent().getStringExtra("name");
        btnAction.setOnClickListener(this);
        tvName.setText(nameStr);
        Glide.with(this).load(faceUrlStr).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgFace);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action:
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
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
            params.put("username", nameStr);
            params.put("order_id", order_id);
            params.put("remark", editContext.getText().toString());
            params.put("timely_star", barOnTime.getRating() + "");
            params.put("service_star", barService.getRating() + "");
            params.put("username", MyAccountMoudle.getInstance().getUserInfo().getUsername() + "");
            params.put("pay_timely_star", barIntact.getRating() + "");
            params.put("pushalias", MyAccountMoudle.getInstance().getUserInfo().getPushalias() + "");
            if ("1".equals(from)) {
                urlStr = "goods.api?assessmentOrder";
            } else if ("2".equals(from)) {
                urlStr = "car.api?assessmentOrder";
            }
            getDataWithPost(submitData, Host.hostUrl + urlStr, params);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        finish();
    }
}
