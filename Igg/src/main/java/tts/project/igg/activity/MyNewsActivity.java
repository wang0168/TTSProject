package tts.project.igg.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.adapter.MyNewsAdapter;
import tts.project.igg.bean.MyNewsBean;

public class MyNewsActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    private List<MyNewsBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);
        setTitle(new BarBean().setMsg("我的消息").setSubTitle("返回"));
        findAllView();
        adapter();
    }

    private void adapter() {
        list.setLayoutManager(new LinearLayoutManager(this));
        MyNewsAdapter myNewsAdapter = new MyNewsAdapter(this, data);
        list.setAdapter(myNewsAdapter);
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.list);

    }
}
