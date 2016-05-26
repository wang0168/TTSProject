package tts.project.igg.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import tts.moudle.api.adapter.FragmentViewPagerAdapter;
import tts.moudle.api.bean.BarBean;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {
    private List<String> titlelist;
    private List<Fragment> fragments;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private TextView buy_now;
    public static String good_id;
    public static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        setTitle(new BarBean().setMsg("商品信息"));
        good_id = getIntent().getStringExtra("good_id");
        Logger.e("good_id==" + good_id);
        findAllView();
        initTab();
    }

    private void initTab() {
        titlelist = new ArrayList<>();
        fragments = new ArrayList<>();
        titlelist.add("基本信息");
        titlelist.add("商品详情");
        titlelist.add("评价");
        fragments.add(new GoodsBaseInfoFragment());
        fragments.add(new GoodsDetailInfoFragment());
        fragments.add(new GoodsBaseInfoFragment());
        viewpager.setAdapter((new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments,
                titlelist)));
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setupWithViewPager(viewpager);
    }

    private void findAllView() {
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        buy_now = (TextView) findViewById(R.id.buy_now);
        buy_now.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buy_now:
                startActivity(new Intent(this, BuyNowActivity.class));
                break;
        }
    }
}
