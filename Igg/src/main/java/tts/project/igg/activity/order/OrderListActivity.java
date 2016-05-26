package tts.project.igg.activity.order;

import android.os.Bundle;
import android.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapter;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.AutoRefreshListView;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.adapter.OrderListAdapter;
import tts.project.igg.bean.OrderBean;
import tts.project.igg.common.MyAccountMoudle;

public class OrderListActivity extends BaseActivity {
    private String title;
    private String order_state;
    private int currentPage = 1;
    private AutoRefreshListView list;
    private List<OrderBean> data = new ArrayList<>();
    private OrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        title = getIntent().getStringExtra("title");
        order_state = getIntent().getStringExtra("order_state");
        setTitle(new BarBean().setMsg(title));
        findAllView();
        startRequestData(getData);
    }

    private void findAllView() {
        list = (AutoRefreshListView) findViewById(R.id.list);
        adapter = new OrderListAdapter(this, data);
        list.setAdapter(adapter);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token() + "");
                params.put("page", currentPage + "");
                params.put("order_state", order_state);
                getDataWithPost(getData, Host.hostUrl + "orderInterface.api?getOwnerOrder", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                data.clear();
                List<OrderBean> temp = new Gson().fromJson(response, new TypeToken<List<OrderBean>>() {
                }.getType());
                data.addAll(temp);
                adapter.notifyDataSetChanged();
                break;
        }
        Logger.e(response);
    }
}
