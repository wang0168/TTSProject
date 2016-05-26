package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.common.ConfigMoudle;
import chexin.project.tts.chexin.widget.selectPopupWindow.SelectPopupWindow;
import tts.moudle.api.Host;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * 里程预算
 * Created by sjb on 2016/3/28.
 */
public class MileageBudgetActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.edit_from)
    EditText editFrom;
    @Bind(R.id.btn_from)
    Button btnFrom;
    @Bind(R.id.edit_to)
    EditText editTo;
    @Bind(R.id.btn_to)
    Button btnTo;
    @Bind(R.id.edit_price)
    EditText editPrice;
    @Bind(R.id.btn_action)
    Button btnAction;
    private SelectPopupWindow selectPopupWindow;
//    private String FromProvince = "";
//    private String FromCity = "";
//    private String FromCountry = "";
//    private String ToProvince = "";
//    private String ToCity = "";
//    private String ToCountry = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mileage_budget_activity);
        ButterKnife.bind(this);
        setTitle("里程预算");
        btnAction.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnTo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action:
                if (TextUtils.isEmpty(editPrice.getText().toString())) {
                    CustomUtils.showTipShort(this, "请输入价格");
                    return;
                }
                if (TextUtils.isEmpty(editFrom.getText().toString())) {
                    CustomUtils.showTipShort(this, "请选择始发地");
                    return;
                }
                if (TextUtils.isEmpty(editTo.getText().toString())) {
                    CustomUtils.showTipShort(this, "请选择目的地");
                    return;
                }
                startActivity(new Intent(this, AboutActivity.class).putExtra("title", "里程预算").
                        putExtra("url", Host.hostUrl + "MileageBudget.html?from=" + editFrom.getText()
                                + "&to=" + editTo.getText() + "&price=" + editPrice.getText()));
                break;
            case R.id.btn_from:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getProvinceBeans(), 1, "始发地");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other,int pos) {
//                        FromProvince = province;
//                        FromCity = city;
//                        FromCountry = area;
                        String strTemp = province + city + area;
                        editFrom.setText(strTemp);
                        editFrom.setSelection(strTemp.length());
                    }
                });
                break;
            case R.id.btn_to:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getProvinceBeans(), 1, "目的地");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other,int pos) {
//                        ToProvince = province;
//                        ToCity = city;
//                        ToCountry = area;
                        String strTemp = province + city + area;
                        editTo.setText(strTemp);
                        editTo.setSelection(strTemp.length());
                    }
                });
                break;
        }
    }
}
