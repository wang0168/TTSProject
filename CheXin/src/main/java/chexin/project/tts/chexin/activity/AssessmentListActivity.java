package chexin.project.tts.chexin.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.sina.weibo.sdk.api.share.Base;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.carowner.MyCollectionGoodsMemberFragment;
import chexin.project.tts.chexin.activity.carowner.MyCollectionGoodsSourceFragment;
import tts.moudle.api.adapter.FragmentViewPagerAdapter;
import tts.moudle.api.bean.BarBean;

/**
 * Created by sjb on 2016/3/28.
 */
public class AssessmentListActivity extends BaseActivity {
    private List<String> titlelist;
    private List<Fragment> fragments;
    private TabLayout tablayoutOrder;
    private ViewPager viewpagerOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_list_activity);
        setTitle(new BarBean().setMsg("我的评价"));
        findAllView();
        initTable();
    }

    private void initTable() {
        titlelist = new ArrayList<>();
        fragments = new ArrayList<>();
        titlelist.add("对我评价");
        titlelist.add("我的评价");

        fragments.add(new AssessmentMeFragment());
        fragments.add(new AssessmentOthersFragment());
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
