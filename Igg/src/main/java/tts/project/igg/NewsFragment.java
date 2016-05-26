package tts.project.igg;


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

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.activity.NewsDetailActivity;
import tts.project.igg.adapter.NewsAdapter;
import tts.project.igg.bean.NewsBean;
import tts.project.igg.common.MyAccountMoudle;


/**
 * 资讯
 */
public class NewsFragment extends BaseFragment {
    private RecyclerViewAutoRefreshUpgraded news;
    private List<NewsBean> data = new ArrayList<>();
    private int currentPage = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return setContentView(R.layout.fragment_news, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(new BarBean().setMsg("资讯").setIsRemoveBack(true));
        findAllView();
        adapter();
        startRequestData(getData);
    }

    private void findAllView() {
        news = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.news);
        news.setHeadVisible(true);
        news.setLoadMore(true);
        news.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
                startRequestData(loadMore);

            }

            @Override
            public void onRefreshData() {
                currentPage = 1;
                startRequestData(getData);
            }
        });
    }

    private void adapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        news.setLayoutManager(manager);
        NewsAdapter newsAdapter = new NewsAdapter(getActivity(), data);
        news.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivity(new Intent(getActivity(), NewsDetailActivity.class).putExtra("data", data.get(position)));
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token() + "");
                params.put("page", currentPage + "");
                getDataWithPost(getData, Host.hostUrl + "informationInterface.api?getAllInformation", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token() + "");
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "informationInterface.api?getAllInformation", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        List<NewsBean> temp;
        switch (index) {
            case getData:
                data.clear();
                temp = new Gson().fromJson(response, new TypeToken<List<NewsBean>>() {
                }.getType());
                data.addAll(temp);
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无数据,请在个人中心-> 系统设置 -> 广告推送 中设置接收类型");
                }
                break;
            case loadMore:

                temp = new Gson().fromJson(response, new TypeToken<List<NewsBean>>() {
                }.getType());
                data.addAll(temp);
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无更多数据");
                }
                break;
        }
        news.notifyDataSetChanged();
        news.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        news.setOnRefreshFinished(true);
        CustomUtils.showTipShort(getActivity(), response);
    }
}
