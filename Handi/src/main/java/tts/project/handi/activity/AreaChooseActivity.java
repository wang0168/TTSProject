package tts.project.handi.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.TextUtils;
import tts.project.handi.AppManager;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.adapter.AreaAdapter;
import tts.project.handi.bean.AreaBean;
import tts.project.handi.bean.UserInfo;
import tts.project.handi.myInterface.OnItemClickLitener;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 地区选择
 */
public class AreaChooseActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<String> areaBeans;
    private String provinceName;
    private String cityName;
    private String areaName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_choose);
        areaBeans = (List<String>) getIntent().getSerializableExtra("areaList");
        provinceName = getIntent().getStringExtra("provinceName");
        cityName = getIntent().getStringExtra("cityName");
        findView();
        adapterList();
    }

    private void findView() {
        setTitle(new BarBean().setMsg("选择县区"));
        recyclerView = (RecyclerView) findViewById(R.id.recycle_choose_area);


    }

    private void adapterList() {
        AreaAdapter cityAdapter = new AreaAdapter(this, areaBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                areaName = areaBeans.get(position);
                EventBus.getDefault().post(new AreaBean().setArea(areaName).setProvince(provinceName).setCity(cityName));
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
                    startRequestData(101);
                } else {
                    AppManager.getAppManager().finishActivity(AreaChooseActivity.class).
                            finishActivity(CityChooseActivity.class).
                            finishActivity(ProvinceChooseActivity.class);
                }
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
            params.put("address", areaName);
            getDataWithPost(101, Host.hostUrl + "api.php?m=Api&c=Personal&a=PerPerfect", params);
        }
    }

    @Override
    protected void doSuccess(int index, String respsons) {
        super.doSuccess(index, respsons);
        UserInfo info = MyAccountMoudle.getInstance().getUserInfo();
        info.setLocal_province(provinceName + "");
        info.setLocal_city(cityName + "");
        info.setLocal_area(areaName + "");
        AppManager.getAppManager().finishActivity(AreaChooseActivity.class).
                finishActivity(CityChooseActivity.class).
                finishActivity(ProvinceChooseActivity.class);
    }
}
