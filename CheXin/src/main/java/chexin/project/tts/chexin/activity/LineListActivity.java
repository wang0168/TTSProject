package chexin.project.tts.chexin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.login.LoginActivity;
import chexin.project.tts.chexin.adapter.LineListAdapter;
import chexin.project.tts.chexin.bean.RouteBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

public class LineListActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded line_list;
    private LineListAdapter lineListAdapter;
    private List<RouteBean> mData;
    private String FromProvince = "";
    private String FromCity = "";
    private String FromCountry = "";
    private String ToProvince = "";
    private String ToCity = "";
    private String ToCountry = "";
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_list);
        setTitle(new BarBean().setMsg("专线列表"));
        findAllView();
        getPrarm();
        startRequestData(getData);
    }

    private void findAllView() {
        line_list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.line_list);
    }

    private void adapterMyApply() {
        line_list.setLayoutManager(new LinearLayoutManager(this));
        lineListAdapter = new LineListAdapter(this, mData);
        line_list.setAdapter(lineListAdapter);
        lineListAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivityForResult(new Intent(LineListActivity.this, CompanyDetailActivity.class).putExtra("data", mData.get(position)), 1001);
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        line_list.setLoadMore(true);
        line_list.setHeadVisible(true);
        line_list.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
                startRequestData(loadMore);
            }

            @Override
            public void onRefreshData() {
                startRequestData(getData);
            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == getData) {
            params = new ArrayMap<>();
            if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                startActivityForResult(new Intent(this, LoginActivity.class).putExtra("IsCallback", true), 3001);
                return;
            }

            params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
            params.put("FromProvince", FromProvince);
            params.put("FromCity", FromCity);
            params.put("FromCountry", FromCountry);
            params.put("ToProvince", ToProvince);
            params.put("ToCity", ToCity);
            params.put("ToCountry", ToCountry);
            params.put("page", "1");
            mProgressDialog.show();
            getDataWithPost(getData, Host.hostUrl + "/route.api?getSpecialRouteByCondition", params);
        }
        if (index == loadMore) {
            params = new ArrayMap<>();
            params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
            params.put("FromProvince", FromProvince);
            params.put("FromCity", FromCity);
            params.put("FromCountry", FromCountry);
            params.put("ToProvince", ToProvince);
            params.put("ToCity", ToCity);
            params.put("ToCountry", ToCountry);
            params.put("page", currentPage + "");
            getDataWithPost(loadMore, Host.hostUrl + "/route.api?getSpecialRouteByCondition", params);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == getData) {
            mProgressDialog.dismiss();
            Logger.d(response);
            mData = new Gson().fromJson(response, new TypeToken<List<RouteBean>>() {
            }.getType());
            if (mData.size() == 0) {
                CustomUtils.showTipShort(this, "暂无数据");
                return;
            }
            adapterMyApply();
        }
        if (index == loadMore) {
            List<RouteBean> temp = new Gson().fromJson(response, new TypeToken<List<RouteBean>>() {
            }.getType());

            if (temp.size() == 0) {
                CustomUtils.showTipShort(this, "暂无更多数据");
                return;
            }
            mData.addAll(temp);
            line_list.notifyDataSetChanged();

        }
        line_list.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        line_list.setOnRefreshFinished(true);
    }

    public void getPrarm() {
        FromProvince = getIntent().getStringExtra("FromProvince");
        FromCity = getIntent().getStringExtra("FromCity");
        FromCountry = getIntent().getStringExtra("FromCountry");
        ToProvince = getIntent().getStringExtra("ToProvince");
        ToCity = getIntent().getStringExtra("ToCity");
        ToCountry = getIntent().getStringExtra("ToCountry");
    }
}
