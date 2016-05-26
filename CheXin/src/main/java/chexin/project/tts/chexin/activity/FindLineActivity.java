package chexin.project.tts.chexin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.common.ConfigMoudle;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import chexin.project.tts.chexin.widget.selectPopupWindow.SelectPopupWindow;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;

/**
 * 查找专线
 */
public class FindLineActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.edit_from)
    EditText editFrom;
    @Bind(R.id.btn_from)
    Button btnFrom;
    @Bind(R.id.edit_to)
    EditText editTo;
    @Bind(R.id.btn_to)
    Button btnTo;

    private LinearLayout hiddenLayout;
    private Button btnAction;
    private SelectPopupWindow selectPopupWindow;
    private String FromProvince = "";
    private String FromCity = "";
    private String FromCountry = "";
    private String ToProvince = "";
    private String ToCity = "";
    private String ToCountry = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mileage_budget_activity);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("查找专线"));
        addMenu(new MenuItemBean().setTitle("我要入驻").setIcon(R.drawable.gradient));
        findAllView();
    }

    private void findAllView() {
        hiddenLayout = (LinearLayout) findViewById(R.id.layout_gongliyunjia);
        hiddenLayout.setVisibility(View.GONE);
        btnAction = (Button) findViewById(R.id.btn_action);
        btnAction.setText("查找");
        btnAction.setOnClickListener(this);
        btnFrom.setOnClickListener(this);
        btnTo.setOnClickListener(this);
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
            startActivity(new Intent(this, StationActivity.class));
        }else {
            CustomUtils.showTipShort(this,"您是货主，没有入住权限");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_from:
                selectPopupWindow = new SelectPopupWindow(this, ConfigMoudle.getInstance().getProvinceBeans(), 1, "始发地");
                selectPopupWindow.showAtLocation(btnTo, Gravity.CENTER, 0, 0);
                selectPopupWindow.setOnClickListener(new SelectPopupWindow.OnClickListener() {
                    @Override
                    public void doClick(String province, String city, String area, String other, int pos) {
                        FromProvince = province;
                        FromCity = city;
                        FromCountry = area;
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
                    public void doClick(String province, String city, String area, String other, int pos) {
                        ToProvince = province;
                        ToCity = city;
                        ToCountry = area;
                        String strTemp = province + city + area;
                        editTo.setText(strTemp);
                        editTo.setSelection(strTemp.length());
                    }
                });
                break;
            case R.id.btn_action:
                if (TextUtils.isEmpty(FromProvince)) {
                    CustomUtils.showTipShort(this, "请选择始发地");
                    return;
                }
                if (TextUtils.isEmpty(ToProvince)) {
                    CustomUtils.showTipShort(this, "请选择目的地");
                    return;
                }
                Intent intent = new Intent(this, LineListActivity.class);
                intent.putExtra("FromProvince", FromProvince);
                intent.putExtra("FromCity", FromCity);
                intent.putExtra("FromCountry", FromCountry);
                intent.putExtra("ToProvince", ToProvince);
                intent.putExtra("ToCity", ToCity);
                intent.putExtra("ToCountry", ToCountry);
                startActivity(intent);
                break;
        }
    }


}
