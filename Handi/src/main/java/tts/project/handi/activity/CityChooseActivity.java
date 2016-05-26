package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.cityapi.CityBean;
import tts.project.handi.AppManager;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.adapter.CityAdapter;
import tts.project.handi.bean.AreaBean;

import tts.project.handi.bean.UserInfo;
import tts.project.handi.myInterface.OnItemClickLitener;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 城市选择
 */
public class CityChooseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<CityBean> cityBeans;
    private String provinceName;
    private String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_choose);

        provinceName = getIntent().getStringExtra("provinceName");
        cityBeans = (List<CityBean>) getIntent().getSerializableExtra("cityList");
        findView();
        adapterList();
    }

    private void findView() {
        setTitle(new BarBean().setMsg("选择城市"));
        recyclerView = (RecyclerView) findViewById(R.id.recycle_choose_area);
    }

    private void adapterList() {
        CityAdapter cityAdapter = new CityAdapter(this, cityBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                cityName = ((TextView) view).getText().toString();
                if (cityBeans.get(position).getAreaList().size() == 0) {
                    startRequestData(101);
                    return;
                }
                Intent intent = new Intent(CityChooseActivity.this, AreaChooseActivity.class);
                intent.putExtra("areaList", (Serializable) (cityBeans.get(position).getAreaList()));
                intent.putExtra("provinceName", provinceName);
                intent.putExtra("cityName", cityName);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        if (101 == index) {
            Map<String, String> params = new ArrayMap<>();
            params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
            params.put("city", provinceName);
            params.put("county", cityName);
            params.put("address", "");
            getDataWithPost(101, Host.hostUrl + "api.php?m=Api&c=Personal&a=PerPerfect", params);
        }
    }

    @Override
    protected void doSuccess(int index, String respsons) {
        super.doSuccess(index, respsons);
        UserInfo info = MyAccountMoudle.getInstance().getUserInfo();
        info.setLocal_province(provinceName + "");
        info.setLocal_city(cityName + "");
        info.setLocal_area("");
        EventBus.getDefault().post(new AreaBean().setArea("").setProvince(provinceName).setCity(cityName));
        AppManager.getAppManager().finishActivity(AreaChooseActivity.class).
                finishActivity(CityChooseActivity.class).
                finishActivity(ProvinceChooseActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
