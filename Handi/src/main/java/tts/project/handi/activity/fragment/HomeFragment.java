package tts.project.handi.activity.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.adapter.BannerAdapter;
import tts.moudle.api.bean.BannerBean;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.cityapi.CityBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.JsonUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.BannerView;
import tts.moudle.api.widget.RecyclerViewAutoRefresh;
import tts.moudle.loginapi.activity.LoginActivity;
import tts.project.handi.BaseFragment;
import tts.project.handi.R;
import tts.project.handi.activity.OrderDetailsActivity;
import tts.project.handi.activity.ProvinceChooseActivity;
import tts.project.handi.adapter.OrderAdapter;
import tts.project.handi.bean.AreaBean;
import tts.project.handi.bean.Order;
import tts.project.handi.bean.UserInfo;
import tts.project.handi.myInterface.OnItemClickLitener;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * home首页 fragment
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private int currentPage = 1;
    private final int grab_order = 101;
    private final int grab_order_loadMore = 102;
    private final int receive_order = 201;
    private final int receive_order_loadMore = 202;
    private final int getOrder = 301;
    private final int banner_ok = 1003;
    private final int getCity = 4001;
    private List<Order> orders = new ArrayList<>();
    private OrderAdapter orderAdapter;

    //@Bind(R.id.recycle_home)
    RecyclerViewAutoRefresh recycleHome;
    //@Bind(R.id.no_data)
    ImageView noData;
    private BannerView bannerView;
    private Map<String, String> getOrderParam;
    private BarBean barBean;
    private RadioButton rb_garb_orders, rb_task_orders;
    private String province;
    private String city;
    private String country;
//    @Bind(R.id.fab)
//    FloatingActionButton fab;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void loginOk() {
        super.loginOk();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);*/
        return setContentView(R.layout.fragment_home, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findAllView();
        recycleHome.setLoadMore(true);
        recycleHome.setHeadVisible(true);
        startRequestData(grab_order);
        startRequestData(banner_ok);
//        mProgressDialog.show();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleHome.setLayoutManager(linearLayoutManager);

        recycleHome.setOnRefreshListener(new RecyclerViewAutoRefresh.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                if (rb_garb_orders.isChecked())
                    startRequestData(grab_order_loadMore);
                else
                    startRequestData(receive_order_loadMore);
            }

            @Override
            public void onRefreshData() {
                if (rb_garb_orders.isChecked())
                    startRequestData(grab_order);
                else
                    startRequestData(receive_order);

            }
        });
//        recycleHome.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void findAllView() {

        rb_garb_orders = (RadioButton) getActivity().findViewById(R.id.rb_garb_orders);
        rb_task_orders = (RadioButton) getActivity().findViewById(R.id.rb_task_orders);
        rb_garb_orders.setOnClickListener(this);
        rb_task_orders.setOnClickListener(this);
        bannerView = (BannerView) getActivity().findViewById(R.id.bannerView);
        recycleHome = (RecyclerViewAutoRefresh) getActivity().findViewById(R.id.recycle_home);
        noData = (ImageView) getActivity().findViewById(R.id.no_data);
        if (barBean == null) {
            barBean = new BarBean();
            barBean.setMsg("憨弟工匠");
        }
        UserInfo info = MyAccountMoudle.getInstance().getUserInfo();
        if (!TextUtils.isEmpty(info.getLocal_area())) {
            barBean.setSubTitle(info.getLocal_area());
        } else if (!TextUtils.isEmpty(info.getLocal_city())) {
            barBean.setSubTitle(info.getLocal_city());
        } else if (!TextUtils.isEmpty(info.getAddress())) {
            barBean.setSubTitle(info.getAddress());
        } else {
            barBean.setSubTitle("选择城市");
        }

        setTitle(barBean);
    }

    @Override
    public void doIcon() {
//        if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
        startActivity(new Intent(getActivity(), ProvinceChooseActivity.class));
//        } else {
//            startActivityForResult(new Intent(getActivity(), LoginActivity.class).putExtra("param", "2"), 3001);
//        }

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case grab_order:
                params = new ArrayMap<>();
                currentPage = 1;
//                Toast.makeText(getActivity(),"111",Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(province)) {
                    params.put("sheng", province);
                }
                if (!TextUtils.isEmpty(city)) {
                    params.put("shi", city);
                }
                if (!TextUtils.isEmpty(country)) {
                    params.put("qu", country);
                }
                params.put("pagenow", "1");
                getDataWithPost(grab_order, Host.hostUrl + "/api.php?m=Api&c=Login&a=index", params);
                break;
            case banner_ok:
                getDataWithGet(banner_ok, Host.hostUrl + "/api.php?m=Api&c=base&a=banner");
                break;
            case grab_order_loadMore:

                currentPage++;
                Map<String, String> param = new ArrayMap<>();
                param.put("pagenow", currentPage + "");
                getDataWithPost(grab_order_loadMore, Host.hostUrl + "api.php?m=Api&c=Login&a=index", param);
                break;
            case receive_order_loadMore:
                currentPage++;
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                params.put("pagenow", currentPage + "");
                getDataWithPost(receive_order_loadMore, Host.hostUrl + "api.php?m=Api&c=Login&a=index", params);
                break;
            case receive_order:
//                Toast.makeText(getActivity(),"222",Toast.LENGTH_SHORT).show();
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                params.put("pagenow", "1");
                getDataWithPost(receive_order, Host.hostUrl + "api.php?m=Api&c=Login&a=index", params);
                currentPage = 1;
                break;
            case getOrder:
                getDataWithPost(getOrder, Host.hostUrl + "api.php?m=Api&c=Orders&a=jieqD", getOrderParam);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case grab_order:
                orders.clear();
                addDate(response);
                break;
            case grab_order_loadMore:
                addDate(response);
                break;
            case receive_order:
                orders.clear();
                addDate(response);
                break;
            case receive_order_loadMore:
                addDate(response);
                break;
            case banner_ok:
                List<BannerBean> bannerBeans = new Gson().fromJson(response, new TypeToken<List<BannerBean>>() {
                }.getType());
                BannerAdapter bannerAdapter = new BannerAdapter(getActivity(), bannerBeans);
                bannerView.setAdapter(bannerAdapter);
                bannerAdapter.setOnItemClickListener(new BannerAdapter.OnItemClickListener() {
                    @Override
                    public void doClick(int position) {

                    }
                });
                break;
            case getOrder:
                if (rb_garb_orders.isChecked()) {
                    startRequestData(grab_order);
                } else {
                    startRequestData(receive_order);
                }
                break;
        }

        recycleHome.onRefreshFinished(true);
    }

    @Override
    protected void doErrFailed(int index, String response) {
        super.doErrFailed(index, response);
        Logger.i(response + "");
        if (recycleHome != null) {
            recycleHome.onRefreshFinished(true);
        }
    }

    @Override
    protected void doPendingFailed(int index, String error) {
        super.doPendingFailed(index, error);
        CustomUtils.showTipLong(getActivity(), error);
        orders.clear();
        addDate(null);
        if (recycleHome != null) {
            recycleHome.onRefreshFinished(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void addDate(String response) {
        if (!TextUtils.isEmpty(response)) {
            orders.addAll(JSON.parseArray(JsonUtils.getJsonFromJson(response, "ords"), Order.class));
            currentPage = Integer.parseInt(JsonUtils.getJsonFromJson(response, "pagenow"));
            if (orders.size() == 0) {
                noData.setVisibility(View.VISIBLE);
            } else {
                noData.setVisibility(View.GONE);
                recycleHome.setVisibility(View.VISIBLE);
            }
        }else {
            noData.setVisibility(View.VISIBLE);
        }
        orderAdapter = new OrderAdapter(getActivity(), orders);

        orderAdapter.notifyDataSetChanged();
        orderAdapter.setAction(true);
        recycleHome.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("order", orders.get(position));
                intent.putExtra("from", "home");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        orderAdapter.setOnActionClickListener(new OrderAdapter.OnActionClickListener() {
            @Override
            public void doAction(int index, final Map<String, String> param) {
                if (index == 1) {
                    String strStatus = MyAccountMoudle.getInstance().getUserInfo().getCardyn();
                    if ("4".equals(strStatus)) {
                        new android.app.AlertDialog.Builder(getActivity()).setTitle("您还没有通过师傅验证")
                                .setMessage("点击\n我的->完善资料->身份认证")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    } else {
                        new AlertDialog.Builder(getActivity()).setMessage("确认抢单？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
                                    getOrderParam = param;
                                    getOrderParam.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                                    getOrderParam.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                                    startRequestData(getOrder);
                                    dialog.dismiss();
                                } else {
                                    startActivityForResult(new Intent(getActivity(), LoginActivity.class).putExtra("param", "2"), 1001);
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                } else {
                    CustomUtils.showTipLong(getActivity(), "系统异常");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_garb_orders:
//                if (rb_garb_orders.isChecked()) {
                startRequestData(grab_order);
//                }
                break;
            case R.id.rb_task_orders:
                if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getUser_token())) {
                    startRequestData(receive_order);
                } else {
//                    rb_task_orders.setChecked(false);
//                    rb_garb_orders.setChecked(false);
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class).putExtra("param", "2"), 3001);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == getCity && resultCode == Activity.RESULT_OK) {
            CityBean cityBean = (CityBean) data.getSerializableExtra("cityBean");
            barBean.setSubTitle(cityBean.getName());
            setTitle(barBean);
            Logger.d(cityBean.getName());
        }
    }

    public void onEventMainThread(AreaBean areaBean) {
        barBean = new BarBean();
        barBean.setMsg("憨弟e家");
        String area = "选择城市";

        if (!TextUtils.isEmpty(areaBean.getArea())) {
            area = areaBean.getArea();
        } else if (!TextUtils.isEmpty(areaBean.getCity())) {
            area = areaBean.getCity();
        }
        barBean.setSubTitle(area);
        setTitle(barBean);
        province = areaBean.getProvince();
        city = areaBean.getCity();
        country = areaBean.getArea();
        startRequestData(grab_order);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
