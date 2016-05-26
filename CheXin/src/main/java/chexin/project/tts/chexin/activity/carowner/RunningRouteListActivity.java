package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.carowner.RunningRouteListAdapter;
import chexin.project.tts.chexin.bean.RouteBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * Created by sjb on 2016/3/28.
 */
public class RunningRouteListActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded routeList;
    private RunningRouteListAdapter runningRouteListAdapter;
    private List<RouteBean> mData = new ArrayList<>();
    private final int delete = 1001;
    private final int checked = 1002;
    private String routeId;
    private String isCheckStr;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_route_list_activity);
        setTitle(new BarBean().setMsg("常跑路线"));
        findAllView();
        startRequestData(getData);
//        adapterRoute();
    }

    private void adapterRoute() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        routeList.setLayoutManager(layoutManager);
        runningRouteListAdapter = new RunningRouteListAdapter(this, mData);
        routeList.setAdapter(runningRouteListAdapter);
        routeList.setLoadMore(true);
        routeList.setHeadVisible(true);
        routeList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
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
//        runningRouteListAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
//            @Override
//            public void onClick(View itemView, int position) {
//            }
//
//            @Override
//            public void onLongClick(View itemView, int position) {
//
//            }
//        });
        runningRouteListAdapter.setRedActionOnClickListener(new RunningRouteListAdapter.RedActionOnClickListener() {
            @Override
            public void redAction(int postion) {
                routeId = mData.get(postion).getRoute_id() + "";
                startRequestData(delete);
                mData.remove(postion);

            }
        });
        runningRouteListAdapter.setBlueActionOnClickListener(new RunningRouteListAdapter.BlueActionOnClickListener() {
            @Override
            public void blueAction(int postion) {
                startActivityForResult(new Intent(RunningRouteListActivity.this, RunningRouteListenGoodsListActivity.class).putExtra("data", mData.get(postion)), 1000);
            }
        });
        runningRouteListAdapter.setCheckChangeListener(new RunningRouteListAdapter.CheckListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position) {
                routeId = mData.get(position).getRoute_id() + "";
                if (isChecked) {
                    isCheckStr = "1";
                } else {
                    isCheckStr = "0";
                }
                startRequestData(checked);
            }
        });
    }

    private void findAllView() {
        routeList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.routeList);
    }

    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.addRouteBtn:
                startActivityForResult(new Intent(RunningRouteListActivity.this, RunningRouteAddActivity.class), 2000);
                break;
        }
    }

    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                currentPage = 1;
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("page", "1");
                getDataWithPost(getData, Host.hostUrl + "/route.api?getRoutes", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "/route.api?getRoutes", params);
                break;
            case delete:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("route_id", routeId);
                getDataWithPost(getData, Host.hostUrl + "/route.api?deleteRoute", params);
                break;
            case checked:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("route_id", routeId);
                params.put("is_listen", isCheckStr);
                getDataWithPost(getData, Host.hostUrl + "/route.api?updateRouteListen", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                mData = new Gson().fromJson(response, new TypeToken<List<RouteBean>>() {
                }.getType());
                if (mData.size() == 0) {
                    CustomUtils.showTipShort(this, "暂无数据");
                }
                adapterRoute();

                break;
            case loadMore:
                List<RouteBean> temp = new Gson().fromJson(response, new TypeToken<List<RouteBean>>() {
                }.getType());
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(this, "暂无更多数据");
                    return;
                }
                mData.addAll(temp);
                routeList.notifyDataSetChanged();

            case delete:
                routeList.notifyDataSetChanged();
                break;
            case checked:
                startRequestData(getData);
                break;
        }
        routeList.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        routeList.setOnRefreshFinished(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2000:
                    startRequestData(getData);
                    break;
                case 1000:
                    startRequestData(getData);
                    break;
            }
        }
    }
}
