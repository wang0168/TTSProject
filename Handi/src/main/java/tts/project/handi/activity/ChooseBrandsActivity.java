package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefresh;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.adapter.BrandsAdapter;
import tts.project.handi.bean.AreaBean;
import tts.project.handi.bean.BrandsBean;
import tts.project.handi.bean.UserInfo;
import tts.project.handi.myInterface.OnItemClickLitener;
import tts.project.handi.utils.MyAccountMoudle;

public class ChooseBrandsActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.btn_action)
    Button btnAction;
    @Bind(R.id.recycleview_brands)
    RecyclerViewAutoRefresh recycleviewBrands;
    private List<BrandsBean> list;
    private String pinkId;
    private String province;
    private String city;
    private String area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_brands);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setTitle(new BarBean().setMsg("选择品牌"));
        UserInfo info = MyAccountMoudle.getInstance().getUserInfo();
        if (!TextUtils.isEmpty(info.getLocal_province())) {
            province = info.getLocal_province();
            city = info.getLocal_city();
            area = info.getLocal_area();
        } else {
            province = info.getCity();
            city = info.getCounty();
            area = info.getAddress();
        }
        if (!TextUtils.isEmpty(area)) {
            tvAddress.setText(province + city + area + "");
            startRequestData(getData);
        } else {
            tvAddress.setText("");
        }
        btnAction.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleviewBrands.setLayoutManager(manager);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_action) {
            Intent intent = new Intent(this, ProvinceChooseActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        UserInfo info = MyAccountMoudle.getInstance().getUserInfo();
        if (index == getData) {
            params = new ArrayMap<>();
            params.put("token", info.getUser_token() + "");
            params.put("member_id", info.getMember_id() + "");
            params.put("area", area + "");
            params.put("city", city + "");
            params.put("province", province + "");
            getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Personal&a=pink", params);
        }
        if (index == submitData) {
            params = new ArrayMap<>();
            params.put("token", info.getUser_token());
            params.put("member_id", info.getMember_id());
            params.put("pink_id", pinkId);
            getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Personal&a=pink", params);
        }

    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == getData) {
            Logger.d(response);
            list = JSON.parseArray(response, BrandsBean.class);
            adapterBrands();
        }
        if (index == submitData) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private void adapterBrands() {
        BrandsAdapter adapter = new BrandsAdapter(this, list);
        recycleviewBrands.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                pinkId = list.get(position).getPink_id();
                startRequestData(submitData);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    public void onEventMainThread(AreaBean areaBean) {
        province = areaBean.getProvince();
        city = areaBean.getCity();
        area = areaBean.getArea();
        tvAddress.setText(province + city + area + "");
        startRequestData(getData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
