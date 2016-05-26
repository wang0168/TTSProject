package tts.project.igg.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.adapter.CollectionAdapter;
import tts.project.igg.bean.CollectionBean;
import tts.project.igg.common.MyAccountMoudle;

public class MyCollectionActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    private CollectionAdapter adapter;
    private List<CollectionBean> data = new ArrayList<>();
    private int current = 1;
    private int deletePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        setTitle(new BarBean().setMsg("我的收藏").setSubTitle("返回"));
        findAllView();
        adapter();
        startRequestData(getData);
    }

    private void adapter() {
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CollectionAdapter(this, data);
        list.setAdapter(adapter);
        list.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                current++;
                startRequestData(loadMore);
            }

            @Override
            public void onRefreshData() {
                current = 1;
                startRequestData(getData);
            }
        });
        adapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void deleteAddress(int position) {
                deletePos = position;
                startRequestData(delete);
            }
        });
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.list);
        list.setHeadVisible(true);
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
                params.put("collection_type", "2");//1：收藏商家 2：商品
                params.put("page", current + "");
                getDataWithPost(getData, Host.hostUrl + "collectionInterface.api?getCollection", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token() + "");
                params.put("collection_type", "2");//1：收藏商家 2：商品
                params.put("page", current + "");
                getDataWithPost(loadMore, Host.hostUrl + "collectionInterface.api?getCollection", params);
                break;
            case delete:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token() + "");
                params.put("collection_id", data.get(deletePos).getCollection_id() + "");
                getDataWithPost(delete, Host.hostUrl + "collectionInterface.api?cancelCollection", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                List<CollectionBean> temp = new Gson().fromJson(response, new TypeToken<List<CollectionBean>>() {
                }.getType());
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(this, "暂无数据");
                }
                data.clear();
                data.addAll(temp);
                list.notifyDataSetChanged();
                break;
            case loadMore:
                List<CollectionBean> temp1 = new Gson().fromJson(response, new TypeToken<List<CollectionBean>>() {
                }.getType());
                if (temp1.size() == 0) {
                    CustomUtils.showTipShort(this, "暂无数据");
                }
                data.addAll(temp1);
                list.notifyDataSetChanged();
                break;
            case delete:
                startRequestData(getData);
                break;
        }
    }
}
