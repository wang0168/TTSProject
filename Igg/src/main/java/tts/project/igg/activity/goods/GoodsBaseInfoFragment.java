package tts.project.igg.activity.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.adapter.BannerAdapter;
import tts.moudle.api.bean.BannerBean;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.BannerView;
import tts.project.igg.BaseFragment;
import tts.project.igg.R;
import tts.project.igg.bean.GoodsBean;
import tts.project.igg.bean.GoodsImgBean;
import tts.project.igg.utils.ImageLoader;

/**
 * 商品详情中的基本信息
 */
public class GoodsBaseInfoFragment extends BaseFragment {
    private GoodsBean goodsBean;
    private TextView title;
    private TextView price;
    private TextView post;
    private TextView monthly_sales;
    private TextView place_of_origin;
    private RelativeLayout layout_package;
    private TextView shop_name;
    private TextView tv_description;
    private TextView tv_service;
    private TextView tv_send_speed;
    private ImageView shop_img;
    private BannerView bannerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment inflater.inflate(R.layout.fragment_goods_list, container, false)

        return setContentView(R.layout.fragment_goods_base_info, inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            startRequestData(getData);
        }

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findAllView();
        startRequestData(getData);
    }

    private void findAllView() {
        title = (TextView) rootView.findViewById(R.id.title);
        price = (TextView) rootView.findViewById(R.id.price);
        post = (TextView) rootView.findViewById(R.id.post);
        monthly_sales = (TextView) rootView.findViewById(R.id.monthly_sales);
        place_of_origin = (TextView) rootView.findViewById(R.id.place_of_origin);
        shop_name = (TextView) rootView.findViewById(R.id.shop_name);
        tv_description = (TextView) rootView.findViewById(R.id.tv_description);
        tv_service = (TextView) rootView.findViewById(R.id.tv_service);
        tv_send_speed = (TextView) rootView.findViewById(R.id.tv_send_speed);
        layout_package = (RelativeLayout) rootView.findViewById(R.id.layout_package);
        shop_img = (ImageView) rootView.findViewById(R.id.shop_img);
        bannerView = (BannerView) rootView.findViewById(R.id.bannerView);
    }


    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("goods_id", ((GoodsDetailActivity) getActivity()).good_id + "");
                getDataWithPost(getData, Host.hostUrl + "goodsInterface.api?getGoodsDetail", params);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                Logger.e(response);
                goodsBean = new Gson().fromJson(response, new TypeToken<GoodsBean>() {
                }.getType());
                List<BannerBean> bannerBeans = new ArrayList<>();
                if (goodsBean.getGoodsImgBeens() != null) {
                    for (GoodsImgBean goodsImgBean : goodsBean.getGoodsImgBeens()) {
                        BannerBean bannerBean = new BannerBean();
                        bannerBean.setImg(goodsImgBean.getGoods_img());
                        bannerBeans.add(bannerBean);
                    }
                }
                bannerView.setAdapter(new BannerAdapter(getActivity(), bannerBeans));
                bindData();
                break;
        }
    }

    private void bindData() {
        if (!TextUtils.isEmpty(goodsBean.getGoods_name())) {
            title.setVisibility(View.VISIBLE);
            title.setText(goodsBean.getGoods_name());
        } else {
            title.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(goodsBean.getGoods_price())) {
            price.setVisibility(View.VISIBLE);
            price.setText(goodsBean.getGoods_price());
        } else {
            price.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(goodsBean.getGoods_price())) {
            post.setVisibility(View.VISIBLE);
            post.setText("快递:" + goodsBean.getGoods_price());
        } else {
            post.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(goodsBean.getGoods_price())) {
            monthly_sales.setVisibility(View.VISIBLE);
            monthly_sales.setText("月销量" + goodsBean.getGoods_price() + "笔");
        } else {
            monthly_sales.setText("月销量0笔");
        }
        if (!TextUtils.isEmpty(goodsBean.getGoods_price())) {
            place_of_origin.setVisibility(View.VISIBLE);
            place_of_origin.setText(goodsBean.getGoods_address());
        } else {
            place_of_origin.setVisibility(View.INVISIBLE);
        }
        if (goodsBean.getMerchantsBean() != null) {
            shop_name.setText(goodsBean.getMerchantsBean().getMerchants_name());
            ImageLoader.load(getActivity(), goodsBean.getMerchantsBean().getMerchants_img(), shop_img);
            tv_description.setText("描述相符" + goodsBean.getMerchantsBean().getMerchants_star1());
            tv_service.setText("服务态度" + goodsBean.getMerchantsBean().getMerchants_star2());
            tv_send_speed.setText("发货速度" + goodsBean.getMerchantsBean().getMerchants_star3());
        }
    }
}
