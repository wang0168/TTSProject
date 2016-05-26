package tts.project.handi.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefresh;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.adapter.RecommendAdapter;
import tts.project.handi.bean.MyRecommend;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 我的推荐
 */
public class MyRecommendActivity extends BaseActivity {
    private RecyclerViewAutoRefresh autoRefresh;
    private List<MyRecommend> myRecommend;
    private RecommendAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend);
        autoRefresh = (RecyclerViewAutoRefresh) findViewById(R.id.recycly_recommend);
        autoRefresh.setLayoutManager(new LinearLayoutManager(this));
        setTitle(new BarBean().setMsg("我推荐的人"));
        mProgressDialog.show();
        startRequestData(getData);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                params.put("state", "2");
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Personal&a=MyReferee", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == getData && !TextUtils.isEmpty(response)) {
            myRecommend = JSON.parseArray(response, MyRecommend.class);
            adapter = new RecommendAdapter(this, myRecommend);
            autoRefresh.setAdapter(adapter);
        }
    }
}
