package chexin.project.tts.chexin.activity.carowner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.BaseFragment;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.carowner.MyAcceptOrderListAdapter;
import chexin.project.tts.chexin.bean.ApplyBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * 我接的单
 * Created by sjb on 2016/3/28.
 */
public class MyAcceptOrderFragment extends BaseFragment {
    private RecyclerViewAutoRefreshUpgraded orderList;
    private List<ApplyBean> mData;
    private int currentPage = 1;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            findAllView();
            startRequestData(getData);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.my_accept_order_fragment, inflater, container, savedInstanceState);
    }

    private void adapterOrder() {
        orderList.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderList.setAdapter(new MyAcceptOrderListAdapter(getActivity(), mData, false));
        orderList.setLoadMore(true);
        orderList.setHeadVisible(true);
        orderList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
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

    private void findAllView() {
        orderList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.orderList);
    }

    @Override
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
                getDataWithPost(getData, Host.hostUrl + "car.api?getOwnerApplyGoods", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "car.api?getOwnerApplyGoods", params);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                mData = new Gson().fromJson(response, new TypeToken<List<ApplyBean>>() {
                }.getType());
                if (mData.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无数据");
                }
                adapterOrder();
                break;
            case loadMore:
                List<ApplyBean> temp = new Gson().fromJson(response, new TypeToken<List<ApplyBean>>() {
                }.getType());
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无更多数据");
                    return;
                }
                mData.addAll(temp);
                orderList.notifyDataSetChanged();
                break;
        }
        orderList.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        orderList.setOnRefreshFinished(true);
    }
}
