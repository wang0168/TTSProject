package chexin.project.tts.chexin.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.SystemInfoAdapter;
import chexin.project.tts.chexin.adapter.carowner.CarStateListAdapter;
import chexin.project.tts.chexin.bean.SystemInfoBean;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * 系统消息
 */
public class SystemInfoActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded infoList;
    private SystemInfoAdapter systemInfoAdapter;
    private List<SystemInfoBean> systemInfoBeans = new ArrayList<>();
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_info);
        //.setSubTitle("清空").setIconId(R.drawable.gradient)
        setTitle(new BarBean().setMsg("系统消息"));
        startRequestData(getData);
        findAllView();
    }

    private void findAllView() {
        infoList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.infoList);
        infoList.setLoadMore(true);
        infoList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                page++;
                startRequestData(getData);
            }

            @Override
            public void onRefreshData() {

            }
        });
    }

    private void adapterList() {
        infoList.setLayoutManager(new LinearLayoutManager(this));
        systemInfoAdapter = new SystemInfoAdapter(this, systemInfoBeans);
        infoList.setAdapter(systemInfoAdapter);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        if (index == getData) {

            getDataWithGet(getData, Host.hostUrl + "systemfaces?query&page=" + page);
        }
    }


    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == getData) {

            List<SystemInfoBean> temp = new Gson().fromJson(response, new TypeToken<List<SystemInfoBean>>() {
            }.getType());
            if (temp.size() == 0) {
                CustomUtils.showTipShort(this, "没有更多数据啦~");
            }
            systemInfoBeans.addAll(temp);
            adapterList();
            infoList.setOnRefreshFinished(true);
        }
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        infoList.setOnRefreshFinished(true);
    }
}
