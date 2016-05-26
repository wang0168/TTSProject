package tts.project.handi.activity.fragment.orders;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.JsonUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefresh;
import tts.project.handi.BaseFragment;
import tts.project.handi.R;
import tts.project.handi.activity.OrderDetailsActivity;
import tts.project.handi.adapter.OrderAdapter;
import tts.project.handi.bean.Order;
import tts.project.handi.myInterface.OnItemClickLitener;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 代付款
 * A simple {@link Fragment} subclass.
 */
public class WaitPayFragment extends BaseFragment {

    private RecyclerViewAutoRefresh list;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Order> orders = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private final int getList = 1001;
    private int currentPage = 1;
    private ImageView noData;
    private Map<String, String> map;
    public WaitPayFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            startRequestData(getList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return setContentView(R.layout.fragment_order_list, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressDialog.show();
        list = (RecyclerViewAutoRefresh) rootView.findViewById(R.id.recycleview_order_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        //list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        noData = (ImageView) rootView.findViewById(R.id.no_data);
        list.setHeadVisible(true);
        list.setOnRefreshListener(new RecyclerViewAutoRefresh.OnRefreshListener() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshData() {
                startRequestData(getList);
            }
        });
        startRequestData(getList);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> param;
        switch (index) {
            case getList:
                param = new ArrayMap<>();
                param.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                param.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                param.put("state", "1");
                param.put("pagenow", currentPage + "");
                getDataWithPost(getList, Host.hostUrl + "api.php?m=Api&c=Orders&a=ordersCot", param);
                break;
            case 2:
                param = new ArrayMap<>();
                param.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                param.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                param.put("state", "6");
                param.put("ordersnumber", map.get("ordersnumber"));
                getDataWithPost(2, Host.hostUrl + "api.php?m=Api&c=Orders&a=ordersCot", param);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getList:
                orders.clear();
                addDate(response);
//                swipeRefreshLayout.setRefreshing(false);
                break;
            case 2:
                startRequestData(getList);
                CustomUtils.showTipShort(getActivity(), "取消成功");
                break;
        }
        list.setOnRefreshFinished(true);
    }

    private void addDate(String response) {
        if (!TextUtils.isEmpty(response)) {
            orders.addAll(JSON.parseArray(JsonUtils.getJsonFromJson(response, "ords"), Order.class));
            currentPage = Integer.parseInt(JsonUtils.getJsonFromJson(response, "pagenow"));
            if (orders.size() == 0) {
                //noData.setVisibility(View.VISIBLE);
//                list.setVisibility(View.GONE);
            }
        }

        orderAdapter = new OrderAdapter(getActivity(), orders);
//        orderAdapter.notifyDataSetChanged();
        list.setAdapter(orderAdapter);
        list.getParent();
        Logger.i(orders.size() + "*****" + list.isShown() + (list.getVisibility() == View.VISIBLE));
        //设置抢单按钮不可见
        orderAdapter.setAction(false);
        orderAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("order", orders.get(position));
                intent.putExtra("from", "orderList");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        orderAdapter.setOnActionClickListener(new OrderAdapter.OnActionClickListener() {
            @Override
            public void doAction(int index, Map<String, String> param) {
                if (index == 2) {
                    map=param;
                    startRequestData(2);
                }
            }
        });

    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        switch (what) {
            case 1:
                doDataFailed(index, response);
                break;
            case 2:
                doNetFailed(index, response);
                break;
            case 3:
                doErrFailed(index, response);
                break;
            case 4:
                if (response.contains("no more data")) {
                    list.setVisibility(View.GONE);
                    //noData.setVisibility(View.VISIBLE);
                }
                break;
        }
        if (list!=null){
            list.setOnRefreshFinished(true);
        }
    }


}
