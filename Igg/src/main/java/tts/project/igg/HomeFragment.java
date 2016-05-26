package tts.project.igg;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.Host;
import tts.moudle.api.adapter.BannerAdapter;
import tts.moudle.api.bean.BannerBean;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.BannerView;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.activity.SortListActivity;
import tts.project.igg.adapter.HomeBaseAdapter;
import tts.project.igg.bean.GoodsBean;
import tts.project.igg.bean.NewsBean;


/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    private final int banner_ok = 101;
    private final int sort_ok = 102;
    private final int news_ok = 103;
    private final int goods_ok = 104;
    private RecyclerViewAutoRefreshUpgraded home;
    private List<GoodsBean> sorts = new ArrayList<>();
    private List<NewsBean> news = new ArrayList<>();
    private List<GoodsBean> goods = new ArrayList<>();
    private View headerView;
    private BannerView bannerView;
    private HomeBaseAdapter homeBaseAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return setContentView(R.layout.fragment_home, inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(new BarBean().setMsg("首页").setIsRemoveBack(true));
//        removeMenu();
//        addMenu(new MenuItemBean().setType("2").setIcon(R.mipmap.sousuo_2x));
        findAllView();
        adapter();
        startRequestData(banner_ok);
        startRequestData(sort_ok);
        startRequestData(news_ok);
        startRequestData(goods_ok);
    }

    private void adapter() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        home.setLayoutManager(manager);
        homeBaseAdapter = new HomeBaseAdapter(getActivity(), sorts);
        homeBaseAdapter.setNews(news);
        homeBaseAdapter.setGoods(goods);
        home.setAdapter(homeBaseAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (sorts.size() != 0 && position > 1 && position < sorts.size() + 2) {
                    return 1;
                } else
                    return ((GridLayoutManager) home.getLayoutManager()).getSpanCount();
            }
        });
        homeBaseAdapter.setOnSortMenuClickListener(new HomeBaseAdapter.OnSortMenuClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if (position == sorts.size() - 1) {
                    CustomUtils.showTipShort(getActivity(), "sorts.size==" + sorts.size() + "position==" + position);
                    startActivity(new Intent(getActivity(), SortListActivity.class));
                }

            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }

    private void findAllView() {
        headerView = View.inflate(getActivity(), R.layout.item_home_banner, null);
        bannerView = (BannerView) headerView.findViewById(R.id.bannerView);
        home = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.home);
//      home.setIsHead(true);
        home.setHeadVisible(true);
        home.addHeader(headerView);

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        switch (index) {
            case banner_ok:
                getDataWithGet(banner_ok, Host.hostUrl + "/banner.api?getBanners");
                break;
            case sort_ok:
                getDataWithPost(sort_ok, Host.hostUrl + "goodsInterface.api?getRecommendGoodsClass", new ArrayMap<String, String>());
                break;
            case news_ok:
                getDataWithPost(news_ok, Host.hostUrl + "informationInterface.api?getRecommentInformation", new ArrayMap<String, String>());
                break;
            case goods_ok:
                getDataWithPost(goods_ok, Host.hostUrl + "goodsInterface.api?getHotsShopping", new ArrayMap<String, String>());
                break;
        }
    }

    //    int i=0;
    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case banner_ok:
//                i++;
                List<BannerBean> bannerBeans = new Gson().fromJson(response, new TypeToken<List<BannerBean>>() {
                }.getType());
                bannerView.setAdapter(new BannerAdapter(getActivity(), bannerBeans));
                break;
            case sort_ok:
//                i++;
//                List<SortMenuBean> a = new Gson().fromJson(response, new TypeToken<List<SortMenuBean>>() {
//                }.getType());
//                sorts.addAll(a);
//                Logger.d(a.size() + "==aaaa");
                sorts = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
                }.getType());
//                Logger.d(response + "");
                adapter();
//                home.notifyDataSetChanged();
//                homeBaseAdapter.setSortList(sorts);
                break;
            case news_ok:
//                i++;
//                List<NewsBean> b = new Gson().fromJson(response, new TypeToken<List<NewsBean>>() {
//                }.getType());
//                Logger.d(b.size() + "==bbbbb");
//                news.addAll(b);
                news = new Gson().fromJson(response, new TypeToken<List<NewsBean>>() {
                }.getType());
//                home.notifyDataSetChanged();
                adapter();
                break;
            case goods_ok:
//                List<GoodsBean> c = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
//                }.getType());
//                goods.addAll(c);
//                Logger.d(c.size() + "==cccc");
                goods = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
                }.getType());
//                home.notifyDataSetChanged();
                adapter();
                break;
        }

//        if(i==3){
//            adapter();
//        }
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
