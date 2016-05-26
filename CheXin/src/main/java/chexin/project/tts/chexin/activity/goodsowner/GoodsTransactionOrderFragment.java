package chexin.project.tts.chexin.activity.goodsowner;

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
import chexin.project.tts.chexin.adapter.carowner.CarOrderListAdapter;
import chexin.project.tts.chexin.bean.OrdersBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * Created by sjb on 2016/3/28.
 */
public class GoodsTransactionOrderFragment extends BaseFragment {
    private RecyclerViewAutoRefreshUpgraded carList;
    private CarOrderListAdapter orderListAdapter;
    private List<OrdersBean> mData;
    private String order_id;
    public final int done = 1003;
    private int currentPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.my_release_car_fragment, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findAllView();
        startRequestData(getData);
    }

    private void findAllView() {
        carList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.carList);
    }

    private void adapterCar() {
        carList.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderListAdapter = new CarOrderListAdapter(getActivity(), mData, 1);
        carList.setAdapter(orderListAdapter);
        carList.setLoadMore(true);
        carList.setHeadVisible(true);
        carList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
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
        orderListAdapter.setActionOnClickListener(new CarOrderListAdapter.ActionOnClickListener() {
            @Override
            public void doAction(View v, int postion) {
                order_id = mData.get(postion).getOrder_id() + "";
                startRequestData(done);
            }
        });
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
                params.put("type", "1");
                params.put("page", "1");
                getDataWithPost(getData, Host.hostUrl + "goods.api?getGoodsOrder", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("type", "1");
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "goods.api?getGoodsOrder", params);
                break;
            case done:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("order_id", order_id);
                getDataWithPost(done, Host.hostUrl + "goods.api?finishOrder", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                mData = new Gson().fromJson(response, new TypeToken<List<OrdersBean>>() {
                }.getType());
                if (mData.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无数据");
                }
                adapterCar();
                break;
            case loadMore:
                List<OrdersBean> temp = new Gson().fromJson(response, new TypeToken<List<OrdersBean>>() {
                }.getType());
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无更多数据");
                    return;
                }
                mData.addAll(temp);
                carList.notifyDataSetChanged();
                break;
            case done:
                startRequestData(getData);
                break;
        }
        carList.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        carList.setOnRefreshFinished(true);
    }
}
