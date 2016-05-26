package tts.project.igg.activity.goods;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.adapter.FragmentViewPagerAdapter;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.bean.GoodsBean;

/**
 * 商品列表页
 */
public class GoodsListActivity extends BaseActivity {
    private List<String> titlelist;
    private List<Fragment> fragments;
    private List<GoodsBean> goodsBeanList;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private String good_id;
    private String title;
    public static int goods_id;
    private FragmentViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);

        good_id = getIntent().getStringExtra("good_id");
        title = getIntent().getStringExtra("title");
        setTitle(new BarBean().setMsg(title).setSubTitle("返回"));
        findAllView();
        startRequestData(getData);

    }

    private void initTable() {
        if (goodsBeanList.size() > 0) {
            goods_id = goodsBeanList.get(0).getGoods_id();
        }
        titlelist = new ArrayList<>();
        fragments = new ArrayList<>();
        for (GoodsBean goodsBean : goodsBeanList) {
            titlelist.add(goodsBean.getGoods_name());
            fragments.add(new GoodsListFragment());
        }
        adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments,
                titlelist);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tablayout.setupWithViewPager(viewpager);

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                goods_id = goodsBeanList.get(tab.getPosition()).getGoods_id();
                CustomUtils.showTipShort(GoodsListActivity.this, goods_id + "");
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tablayout.setTabsFromPagerAdapter(adapter);
        TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(tablayout);
        viewpager.addOnPageChangeListener(listener);
        viewpager.setAdapter(adapter);
    }

    private void findAllView() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("goods_id", good_id);
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
                goodsBeanList = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
                }.getType());
                initTable();
                break;
        }
    }
}
