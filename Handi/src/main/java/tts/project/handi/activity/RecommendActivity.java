package tts.project.handi.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.JsonUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;

/**
 * 填写推荐人
 */
public class RecommendActivity extends BaseActivity {

    @Bind(R.id.edit_number)
    EditText editNumber;
//    @Bind(R.id.number_input)
//    TextInputLayout numberInput;
    @Bind(R.id.btn_action)
    Button btnAction;
    String memberid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        ButterKnife.bind(this);
        Logger.d(getIntent().getStringExtra("json"));
        if (!TextUtils.isEmpty(JsonUtils.getJsonFromJson(getIntent().getStringExtra("json"), "member_id")))
            memberid = JsonUtils.getJsonFromJson(getIntent().getStringExtra("json"), "member_id");
        setTitle(new BarBean().setMsg("推荐人"));
        MenuItemBean menuItemBean = new MenuItemBean().setTitle("跳过");
        addMenu(menuItemBean);
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
        if (index == submitData) {

            Map<String, String> params = new ArrayMap<>();
            params.put("member_id", memberid);
            if (TextUtils.isEmpty(editNumber.getText().toString())) {
                return;
            }
            params.put("mobile", editNumber.getText().toString());
            getDataWithPost(submitData, Host.hostUrl + "api.php?m=Api&c=Personal&a=Referees", params);
        }
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
//        super.doMenu(menuItemBean);
        setResult(RESULT_OK);
        finish();
    }
}
