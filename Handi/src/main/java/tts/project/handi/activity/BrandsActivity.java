package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

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
import tts.project.handi.adapter.RecommendAdapter;
import tts.project.handi.bean.BrandsBean;
import tts.project.handi.bean.SetTitleBean;
import tts.project.handi.utils.MyAccountMoudle;

public class BrandsActivity extends BaseActivity implements OnClickListener {

    @Bind(R.id.layout_choose_brands)
    LinearLayout layoutChooseBrands;
    @Bind(R.id.tv_brands_name)
    TextView tvBrandsName;
    @Bind(R.id.tv_brands_address)
    TextView tvBrandsAddress;
    @Bind(R.id.recycleview_members)
    RecyclerViewAutoRefresh recycleviewMembers;
    private String pinkId;
    private BrandsBean brandsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("我的品牌"));
        pinkId = MyAccountMoudle.getInstance().getUserInfo().getPink_id();
//        Logger
        if ("0".equals(pinkId) || TextUtils.isEmpty(pinkId)) {
            layoutChooseBrands.setVisibility(View.VISIBLE);
            layoutChooseBrands.setOnClickListener(this);
        } else {
            layoutChooseBrands.setVisibility(View.GONE);
            startRequestData(getData);
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        if (index == getData) {
            Map<String, String> params = new ArrayMap<>();
            params.put("pink_id", pinkId);
            getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Owner&a=Pinks", params);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_choose_brands:
                startActivityForResult(new Intent(this, ChooseBrandsActivity.class), 101);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            layoutChooseBrands.setVisibility(View.GONE);
            startRequestData(getData);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == getData) {
            Logger.d(response);
            brandsBean = JSON.parseObject(response, BrandsBean.class);
            bindData();
        }
    }

    private void bindData() {
        tvBrandsName.setText(brandsBean.getPinkname());
        tvBrandsAddress.setText(brandsBean.getProvince() + brandsBean.getCity() + brandsBean.getArea() + "");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleviewMembers.setLayoutManager(layoutManager);
        RecommendAdapter adapter = new RecommendAdapter(this, brandsBean.getMeb());
        recycleviewMembers.setAdapter(adapter);
    }

    @Override
    public void doIcon() {
        super.doIcon();
        initTab();

    }

    private void initTab() {
        EventBus.getDefault().post(new SetTitleBean().setIsSet(true));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            initTab();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
