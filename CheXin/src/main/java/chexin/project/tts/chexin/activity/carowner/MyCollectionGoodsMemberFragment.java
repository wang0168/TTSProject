package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
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

import chexin.project.tts.chexin.BaseFragment;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.goodsowner.GoodsDetailsActivity;
import chexin.project.tts.chexin.adapter.carowner.CollectionGoodsMemberAdapter;
import chexin.project.tts.chexin.bean.CollectionBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * 我的收藏（货主收藏）
 * Created by sjb on 2016/3/28.
 */
public class MyCollectionGoodsMemberFragment extends BaseFragment {
    private RecyclerViewAutoRefreshUpgraded collectionList;
    private CollectionGoodsMemberAdapter collectionGoodsMemberAdapter;
    private List<CollectionBean> mData = new ArrayList<>();
    private int currentPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setContentView(R.layout.my_collection_goods_member_fragment, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findAllView();
        startRequestData(getData);
        mProgressDialog.show();
    }

    private void adapterCollection() {
        collectionList.setLayoutManager(new LinearLayoutManager(getActivity()));
        collectionGoodsMemberAdapter = new CollectionGoodsMemberAdapter(getActivity(), mData);
        collectionList.setAdapter(collectionGoodsMemberAdapter);
        collectionList.setLoadMore(true);
        collectionList.setHeadVisible(true);
        collectionList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
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
        collectionGoodsMemberAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivityForResult(new Intent(getActivity(), GoodsDetailsActivity.class).putExtra("data", mData.get(position).getMemberBean()), 1001);
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }

    private void findAllView() {
        collectionList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.collectionList);
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
                params.put("collection_type", "3");
                params.put("page", "1");
                getDataWithPost(getData, Host.hostUrl + "collection.api?getCollection", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("collection_type", "3");
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "collection.api?getCollection", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (getData == index) {
            mData = new Gson().fromJson(response, new TypeToken<List<CollectionBean>>() {
            }.getType());
            if (mData.size() == 0) {
                CustomUtils.showTipShort(getActivity(), "暂无数据");
            }
            adapterCollection();
            mProgressDialog.dismiss();
        }
        if (index == loadMore) {
            List<CollectionBean> temp = new Gson().fromJson(response, new TypeToken<List<CollectionBean>>() {
            }.getType());
            if (temp.size() == 0) {
                CustomUtils.showTipShort(getActivity(), "暂无更多数据");
            }
            mData.addAll(temp);
            collectionList.notifyDataSetChanged();
        }
        collectionList.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        collectionList.setOnRefreshFinished(true);
    }
}
