package tts.project.igg.activity.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.BaseFragment;
import tts.project.igg.R;
import tts.project.igg.adapter.GoodListAdapter;
import tts.project.igg.bean.GoodsBean;

/**
 *商品详情中的评价
 */
public class GoodsAssessFragment extends BaseFragment {
    private RecyclerViewAutoRefreshUpgraded list;
    private int good_id;
    private List<GoodsBean> goodsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment inflater.inflate(R.layout.fragment_goods_list, container, false)

        return setContentView(R.layout.fragment_goods_list, inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            good_id = ((GoodsListActivity) getActivity()).goods_id;
            startRequestData(getData);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.list);
        adapter();
        good_id = ((GoodsListActivity) getActivity()).goods_id;
        startRequestData(getData);
    }

    private void adapter() {
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        GoodListAdapter goodListAdapter = new GoodListAdapter(getActivity(), goodsList);
        list.setAdapter(goodListAdapter);
    }


    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("goods_id", good_id + "");
                params.put("goods_grade", "");
                getDataWithPost(getData, Host.hostUrl + "goodsInterface.api?getGoodsByGoodsClassId", params);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                List<GoodsBean> temp = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
                }.getType());
                goodsList.clear();
                goodsList.addAll(temp);
                list.notifyDataSetChanged();
                break;
        }
    }
}
