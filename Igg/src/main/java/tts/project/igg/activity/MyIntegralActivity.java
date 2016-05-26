package tts.project.igg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.adapter.MyIntegraldapter;
import tts.project.igg.bean.IntegralBean;
import tts.project.igg.common.MyAccountMoudle;

public class MyIntegralActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    private MyIntegraldapter myIntegraldapter;
    private List<IntegralBean> data = new ArrayList<>();
    private int currentPage = 1;
    private final int getCount = 1001;
    private TextView integral_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_integral);
        setTitle(new BarBean().setMsg("我的积分"));
        addMenu(new MenuItemBean().setTitle("积分规则"));
        findAllView();
        adapter();
        startRequestData(getData);
        startRequestData(getCount);
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        startActivity(new Intent(this, AboutActivity.class).putExtra("title", "积分规则").putExtra("url", Host.hostUrl + "html/others/integral_rule.html"));
    }

    private void adapter() {
        list.setLayoutManager(new LinearLayoutManager(this));
        myIntegraldapter = new MyIntegraldapter(this, data);
        list.setAdapter(myIntegraldapter);
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.list);
        View header = LayoutInflater.from(this).inflate(R.layout.item_integral_header, null, false);
        integral_count = (TextView) header.findViewById(R.id.integral_count);
        integral_count.setText("0");
        list.addHeader(header);
        list.setLoadMore(true);
        list.setHeadVisible(true);
        list.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
                startRequestData(loadMore);
            }

            @Override
            public void onRefreshData() {
                currentPage = 1;
                startRequestData(getData);
            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                params.put("page", currentPage + "");
                params.put("member_type", MyAccountMoudle.getInstance().getUserInfo().getMember_type());
                getDataWithPost(getData, Host.hostUrl + "memberInterface.api?getMemberIntegralList", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                params.put("page", currentPage + "");
                params.put("member_type", MyAccountMoudle.getInstance().getUserInfo().getMember_type());
                getDataWithPost(loadMore, Host.hostUrl + "memberInterface.api?getMemberIntegralList", params);
                break;
            case getCount:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                getDataWithPost(getCount, Host.hostUrl + "memberInterface.api?getMemberTotalIntegral", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getCount:
                integral_count.setText(response);
                break;
            case getData:
                List<IntegralBean> temp = new Gson().fromJson(response, new TypeToken<List<IntegralBean>>() {
                }.getType());
                data.clear();
                data.addAll(temp);
                list.notifyDataSetChanged();
                break;
            case loadMore:
                List<IntegralBean> temp1 = new Gson().fromJson(response, new TypeToken<List<IntegralBean>>() {
                }.getType());
                data.addAll(temp1);
                list.notifyDataSetChanged();
                break;
        }
    }
}
