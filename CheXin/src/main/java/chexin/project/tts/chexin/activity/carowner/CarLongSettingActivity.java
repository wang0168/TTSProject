package chexin.project.tts.chexin.activity.carowner;

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
 * 车长设置
 * Created by sjb on 2016/3/28.
 */
public class CarLongSettingActivity extends BaseActivity {
    @Bind(R.id.edit_car_minlong)
    EditText editCarMinlong;
    @Bind(R.id.edit_car_maxlong)
    EditText editCarMaxlong;
    @Bind(R.id.btn_action)
    Button btnAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_long_setting_activity);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("车长设置"));
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
                if (Double.parseDouble(editCarMinlong.getText().toString()) >
                        Double.parseDouble(editCarMaxlong.getText().toString())) {
                    CustomUtils.showTipShort(this, "最小车长不能大于最大车长哟~");
                    return;
                }
                params = new ArrayMap<>();
                params.put("mobileno", MyAccountMoudle.getInstance().getUserInfo().getMobileno() + "");
                params.put("mincarlength", editCarMinlong.getText().toString());
                params.put("maxcarlength", editCarMaxlong.getText().toString());
                getDataWithPost(submitData, Host.hostUrl + "setcarlengthfaces?add", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (submitData == index) {
            CustomUtils.showTipShort(this, "已提交系统");
            setResult(RESULT_OK);
            finish();
        }
    }
}
