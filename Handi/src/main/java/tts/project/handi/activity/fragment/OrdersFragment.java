package tts.project.handi.activity.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import tts.moudle.api.adapter.FragmentViewPagerAdapter;
import tts.moudle.api.bean.BarBean;
import tts.project.handi.BaseFragment;
import tts.project.handi.R;
import tts.project.handi.activity.fragment.orders.OrderDoneFragment;
import tts.project.handi.activity.fragment.orders.WaitConfirmFragment;
import tts.project.handi.activity.fragment.orders.WaitPayFragment;
import tts.project.handi.activity.fragment.orders.WaitServiceFragment;

/**
 * 订单fragment
 */
public class OrdersFragment extends BaseFragment {

    private List<String> titlelist;
    private List<Fragment> fragments;
    private TabLayout tablayoutOrder;
    private ViewPager viewpagerOrder;
    private BarBean barBean;

    public OrdersFragment() {
        // Required empty public constructor
    }

    private static boolean isLoad=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_orders, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //if(!isLoad){
            tablayoutOrder= (TabLayout) rootView.findViewById(R.id.tablayout_order);
            viewpagerOrder= (ViewPager) rootView.findViewById(R.id.viewpager_order);
            isLoad=true;
            viewpagerOrder.setOffscreenPageLimit(3);
            barBean = new BarBean();
            barBean.setMsg("我的订单");
            barBean.setIsRemoveBack(true);
//        barBean.setSubTitle("选择城市");
            setTitle(barBean);
            titlelist = new ArrayList<String>();
            fragments = new ArrayList<Fragment>();
            titlelist.add("待付款");
            titlelist.add("待服务");
            titlelist.add("待确认");
            titlelist.add("已完成");

            fragments.add(new WaitPayFragment());
            fragments.add(new WaitServiceFragment());
            fragments.add(new WaitConfirmFragment());
            fragments.add(new OrderDoneFragment());
            viewpagerOrder.setAdapter(new FragmentViewPagerAdapter(getChildFragmentManager(), fragments,
                    titlelist));
            tablayoutOrder.setTabMode(TabLayout.MODE_FIXED);
            tablayoutOrder.setupWithViewPager(viewpagerOrder);


//        tablayoutOrder.setTabGravity(TabLayout.GRAVITY_CENTER);
//        tablayoutOrder.setTabTextColors();
       // }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
