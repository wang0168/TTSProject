package chexin.project.tts.chexin.activity.goodsowner;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.sina.weibo.sdk.api.share.Base;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.carowner.MyAcceptOrderFragment;
import chexin.project.tts.chexin.activity.carowner.MyReleaseCarFragment;
import tts.moudle.api.adapter.FragmentViewPagerAdapter;
import tts.moudle.api.bean.BarBean;

/**
 * Created by sjb on 2016/3/28.
 */
public class MyGoodsSourceActivity extends BaseActivity {
    private List<String> titlelist;
    private List<Fragment> fragments;
    private TabLayout tablayoutOrder;
    private ViewPager viewpagerOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_goods_source_activity);
        setTitle(new BarBean().setMsg("我的货源"));
        findAllView();
        initTable();
    }

    private void initTable() {
        titlelist = new ArrayList<>();
        fragments = new ArrayList<>();
        titlelist.add("我发的货");
        titlelist.add("我订的车");

        fragments.add(new MyReleaseGoodsFragment());
        fragments.add(new MyApplyCarFragment());
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
