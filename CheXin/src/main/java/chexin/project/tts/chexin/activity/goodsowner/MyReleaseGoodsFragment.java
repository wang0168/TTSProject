package chexin.project.tts.chexin.activity.goodsowner;

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

import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.BaseFragment;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.goodsowner.MyReleaseGoodListAdapter;
import chexin.project.tts.chexin.bean.GoodsBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView.OnItemClickListener;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * Created by sjb on 2016/3/28.
 */
public class MyReleaseGoodsFragment extends BaseFragment implements OnItemClickListener {
    private RecyclerViewAutoRefreshUpgraded goodsList;
    private MyReleaseGoodListAdapter adapter;
    private List<GoodsBean> mData;
    private final int delete = 1001;
    private String goodsId;
    private int currentPage = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.my_release_goods_fragment, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findAllView();
        startRequestData(getData);
    }

    private void adapterGoods() {
        goodsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyReleaseGoodListAdapter(getActivity(), mData);
        goodsList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        goodsList.setHeadVisible(true);
        goodsList.setLoadMore(true);
        goodsList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
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
        adapter.setBlueActionOnClickListener(new MyReleaseGoodListAdapter.BlueActionOnClickListener() {
            @Override
            public void blueAction(int postion) {
                startActivity(new Intent(getActivity(), AcceptOrdersListActivity.class).putExtra("relation_id", mData.get(postion).getID() + ""));
            }
        });
        adapter.setRedActionOnClickListener(new MyReleaseGoodListAdapter.RedActionOnClickListener() {
            @Override
            public void redAction(int postion) {
                goodsId = mData.get(postion).getID() + "";
                mData.remove(postion);
                startRequestData(delete);
            }
        });
    }

    private void findAllView() {
        goodsList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.goodsList);
    }

    @Override
    public void onClick(View itemView, int position) {
        startActivityForResult(new Intent(getActivity(), GoodsDetailsActivity.class).putExtra("data", mData.get(position)), 2003);
    }

    @Override
    public void onLongClick(View itemView, int position) {

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
                getDataWithPost(getData, Host.hostUrl + "goods.api?getGoodsByUserId", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "goods.api?getGoodsByUserId", params);
                break;
            case delete:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("goods_id", goodsId);
                getDataWithPost(delete, Host.hostUrl + "goods.api?deleteGoods", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                mData = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
                }.getType());
                if (mData.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无数据");
                }
                adapterGoods();
                break;
            case loadMore:
                List<GoodsBean> temp = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
                }.getType());
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无更多数据");
                    return;
                }
                mData.addAll(temp);
                goodsList.notifyDataSetChanged();
                break;
            case delete:
                goodsList.notifyDataSetChanged();
                break;
        }
        goodsList.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        goodsList.setOnRefreshFinished(true);
    }
}
