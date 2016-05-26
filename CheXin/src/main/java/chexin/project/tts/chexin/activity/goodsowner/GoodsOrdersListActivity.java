package chexin.project.tts.chexin.activity.goodsowner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.carowner.CarHistoryOrderFragment;
import chexin.project.tts.chexin.activity.carowner.CarTransactionOrderFragment;
import tts.moudle.api.adapter.FragmentViewPagerAdapter;
import tts.moudle.api.bean.BarBean;

public class GoodsOrdersListActivity extends BaseActivity {
    private List<String> titlelist;
    private List<Fragment> fragments;
    private TabLayout tablayoutOrder;
    private ViewPager viewpagerOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_car_source_activity);
        setTitle(new BarBean().setMsg("我的订单"));
        findAllView();
        initTable();
    }

    private void initTable() {
        titlelist = new ArrayList<>();
        fragments = new ArrayList<>();
        titlelist.add("交易订单");
        titlelist.add("历史订单");

        fragments.add(new GoodsTransactionOrderFragment());
        fragments.add(new GoodsHistoryOrderFragment());
        viewpagerOrder.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments,
                titlelist));
        tablayoutOrder.setTabMode(TabLayout.MODE_FIXED);
        tablayoutOrder.setupWithViewPager(viewpagerOrder);
    }

    private void findAllView() {
        tablayoutOrder = (TabLayout) findViewById(R.id.tablayout_order);
        viewpagerOrder = (ViewPager) findViewById(R.id.viewpager_order);
    }
}
