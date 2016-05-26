package tts.project.igg;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.CircleImageView;
import tts.moudle.api.widget.TTSRadioButton;
import tts.project.igg.activity.AddressManagerActivity;
import tts.project.igg.activity.BaseInfoActivity;
import tts.project.igg.activity.FeedBackActivity;
import tts.project.igg.activity.MyCollectionActivity;
import tts.project.igg.activity.MyIntegralActivity;
import tts.project.igg.activity.MyNewsActivity;
import tts.project.igg.activity.SystemSettingActivity;
import tts.project.igg.activity.order.OrderListActivity;
import tts.project.igg.bean.UserInfoBean;
import tts.project.igg.common.MyAccountMoudle;
import tts.project.igg.utils.ImageLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout layout_contact_us;
    private RelativeLayout layout_setting;
    private RelativeLayout layout_feedback;
    private RelativeLayout layout_info_header;
    private CircleImageView face;
    private TextView name;
    private TextView phone;
    private TTSRadioButton all_order;
    private TTSRadioButton wait_pay;
    private TTSRadioButton wait_post;
    private TTSRadioButton wait_receive;
    private TTSRadioButton wait_assess;
    private TTSRadioButton my_integral;
    private TTSRadioButton my_collection;
    private TTSRadioButton my_news;
    private TTSRadioButton my_address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return setContentView(R.layout.fragment_me, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        startActivity(new Intent(getActivity(), LoginActivity.class));
        setTitle(new BarBean().setMsg("个人中心").setIsRemoveBack(true));
        findAllView();
        bindData();
    }

    private void bindData() {
        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        name.setText(userInfoBean.getNick_name());
        phone.setText(userInfoBean.getMobile());
        ImageLoader.load(getActivity(), userInfoBean.getHead_path(), face, R.mipmap.touxiang_2x);
    }

    private void findAllView() {
        layout_info_header = (RelativeLayout) rootView.findViewById(R.id.layout_info_header);
        layout_feedback = (RelativeLayout) rootView.findViewById(R.id.layout_feedback);
        layout_contact_us = (RelativeLayout) rootView.findViewById(R.id.layout_contact_us);
        layout_setting = (RelativeLayout) rootView.findViewById(R.id.layout_setting);
        face = (CircleImageView) rootView.findViewById(R.id.face);
        name = (TextView) rootView.findViewById(R.id.name);
        phone = (TextView) rootView.findViewById(R.id.phone);
        all_order = (TTSRadioButton) rootView.findViewById(R.id.all_order);
        wait_pay = (TTSRadioButton) rootView.findViewById(R.id.wait_pay);
        wait_post = (TTSRadioButton) rootView.findViewById(R.id.wait_post);
        wait_receive = (TTSRadioButton) rootView.findViewById(R.id.wait_receive);
        wait_assess = (TTSRadioButton) rootView.findViewById(R.id.wait_assess);
        my_integral = (TTSRadioButton) rootView.findViewById(R.id.my_integral);
        my_collection = (TTSRadioButton) rootView.findViewById(R.id.my_collection);
        my_news = (TTSRadioButton) rootView.findViewById(R.id.my_news);
        my_address = (TTSRadioButton) rootView.findViewById(R.id.my_address);
        layout_info_header.setOnClickListener(this);
        layout_feedback.setOnClickListener(this);
        layout_contact_us.setOnClickListener(this);
        layout_setting.setOnClickListener(this);
        all_order.setOnClickListener(this);
        wait_pay.setOnClickListener(this);
        wait_post.setOnClickListener(this);
        wait_receive.setOnClickListener(this);
        wait_assess.setOnClickListener(this);
        my_integral.setOnClickListener(this);
        my_collection.setOnClickListener(this);
        my_news.setOnClickListener(this);
        my_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_contact_us:

                break;
            case R.id.layout_setting:
                startActivity(new Intent(getActivity(), SystemSettingActivity.class));
                break;
            case R.id.layout_feedback:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.layout_info_header:
                startActivity(new Intent(getActivity(), BaseInfoActivity.class));
                break;
            case R.id.my_news:
                startActivity(new Intent(getActivity(), MyNewsActivity.class));
                break;
            case R.id.my_address:
                startActivity(new Intent(getActivity(), AddressManagerActivity.class));
                break;
            case R.id.my_integral:
                startActivity(new Intent(getActivity(), MyIntegralActivity.class));
                break;
            case R.id.my_collection:
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                break;
            case R.id.all_order:
                startActivity(new Intent(getActivity(), OrderListActivity.class).putExtra("order_state", "-1").putExtra("title", "全部订单"));
                break;
            case R.id.wait_pay:
                startActivity(new Intent(getActivity(), OrderListActivity.class).putExtra("order_state", "wait_pay").putExtra("title", "待付款"));
                break;
            case R.id.wait_post:
                startActivity(new Intent(getActivity(), OrderListActivity.class).putExtra("order_state", "wait_send").putExtra("title", "待发货"));
                break;
            case R.id.wait_assess:
                startActivity(new Intent(getActivity(), OrderListActivity.class).putExtra("order_state", "wait_assessment").putExtra("title", "待评价"));
                break;
            case R.id.wait_receive:
                startActivity(new Intent(getActivity(), OrderListActivity.class).putExtra("order_state", "wait_receive").putExtra("title", "待收货"));
                break;

        }
    }
}
