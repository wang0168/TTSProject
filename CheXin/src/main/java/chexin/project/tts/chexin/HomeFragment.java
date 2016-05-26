package chexin.project.tts.chexin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.activity.FindLineActivity;
import chexin.project.tts.chexin.activity.HelperActivity;
import chexin.project.tts.chexin.activity.SettingActivity;
import chexin.project.tts.chexin.activity.carowner.CarLongSettingActivity;
import chexin.project.tts.chexin.activity.carowner.CarStateListActivity;
import chexin.project.tts.chexin.activity.carowner.MileageBudgetActivity;
import chexin.project.tts.chexin.activity.carowner.MyCarSourceActivity;
import chexin.project.tts.chexin.activity.carowner.ReleaseCarInfoActivity;
import chexin.project.tts.chexin.activity.carowner.RunningRouteListActivity;
import chexin.project.tts.chexin.activity.goodsowner.MyGoodsSourceActivity;
import chexin.project.tts.chexin.activity.goodsowner.ReleaseGoodsInfoActivity;
import chexin.project.tts.chexin.adapter.HomeMenuAdapter;
import chexin.project.tts.chexin.bean.CarDetailBean;
import chexin.project.tts.chexin.bean.ConfigBean;
import chexin.project.tts.chexin.bean.EffectiveBean;
import chexin.project.tts.chexin.bean.HomeMenuBean;
import chexin.project.tts.chexin.bean.MainBean;
import chexin.project.tts.chexin.common.ConfigMoudle;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.adapter.BannerAdapter;
import tts.moudle.api.bean.BannerBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.BannerView;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.moudle.api.widget.RecyclerViewGridItemDecoration;

/**
 * 首页
 * Created by sjb on 2016/3/23.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private BannerView bannerView;
    private final int banner_ok = 101;
    private final int province_ok = 102;
    private final int car_type_ok = 103;
    private final int goods_unit_ok = 104;
    private final int goods_type_ok = 105;
    private final int car_long_ok = 106;
    private final int get_car_list = 107;
    private final int get_car_effective = 108;
    private final int get_good_effective = 109;
    private RecyclerViewAutoRefreshUpgraded menuList;
    private View headView;
    private ArrayList<ConfigBean> provinceBeans;
    private List<ConfigBean> cartypeBeans;
    private List<ConfigBean> goodsWeightBeans;
    private List<ConfigBean> goodsTypeBeans;
    private List<ConfigBean> carLongBeans;
    private List<CarDetailBean> carDetailBeans;
    private List<EffectiveBean> carEffectiveBeans;
    private List<EffectiveBean> goodEffectiveBeans;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.home_fragment, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isLoad) {
            isLoad = true;
            findAllView();
            adapterMenu();
        }
        startRequestData(banner_ok);
        if (ConfigMoudle.getInstance().getProvinceBeans().size() == 0) {
            startRequestData(province_ok);
        }
        if (ConfigMoudle.getInstance().getCarTypeBeans().size() == 0) {
            startRequestData(car_type_ok);
        }
        if (ConfigMoudle.getInstance().getGoodsUnitBeans().size() == 0) {
            startRequestData(goods_unit_ok);
        }
        if (ConfigMoudle.getInstance().getGoodsTypeBeans().size() == 0) {
            startRequestData(goods_type_ok);
        }
        if (ConfigMoudle.getInstance().getCarLongBeans().size() == 0) {
            startRequestData(car_long_ok);
        }
        if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
            startRequestData(get_car_list);
        }
        if (ConfigMoudle.getInstance().getCarEffectiveBeans().size() == 0) {
            startRequestData(get_car_effective);
        }
        if (ConfigMoudle.getInstance().getGoodEffectiveBeans().size() == 0) {
            startRequestData(get_good_effective);
        }
    }

    private void adapterMenu() {
        final List<HomeMenuBean> homeMenuBeans = new ArrayList<HomeMenuBean>();
        homeMenuBeans.add(new HomeMenuBean().setId("1").setIconId(R.drawable.menu_add_car).setName("发布车源"));
        homeMenuBeans.add(new HomeMenuBean().setId("2").setIconId(R.drawable.menu_add_goods).setName("发布货源"));
        homeMenuBeans.add(new HomeMenuBean().setId("3").setIconId(R.drawable.menu_always_route).setName("常跑路线"));
        homeMenuBeans.add(new HomeMenuBean().setId("4").setIconId(R.drawable.menu_my_car).setName("我的车源"));
        homeMenuBeans.add(new HomeMenuBean().setId("5").setIconId(R.drawable.menu_my_goods).setName("我的货源"));
        homeMenuBeans.add(new HomeMenuBean().setId("6").setIconId(R.drawable.menu_my_collecction).setName("我的收藏"));
        homeMenuBeans.add(new HomeMenuBean().setId("7").setIconId(R.drawable.menu_car_long).setName("车长设置"));
        homeMenuBeans.add(new HomeMenuBean().setId("8").setIconId(R.drawable.menu_car_state).setName("车辆状态"));
        homeMenuBeans.add(new HomeMenuBean().setId("9").setIconId(R.drawable.menu_special_line).setName("查找专线"));
        homeMenuBeans.add(new HomeMenuBean().setId("10").setIconId(R.drawable.menu_mileage).setName("里程"));
        homeMenuBeans.add(new HomeMenuBean().setId("11").setIconId(R.drawable.menu_setting).setName("系统设置"));
        homeMenuBeans.add(new HomeMenuBean().setId("12").setIconId(R.drawable.menu_helper).setName("使用帮助"));

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        menuList.setLayoutManager(layoutManager);
        menuList.addItemDecoration(new RecyclerViewGridItemDecoration(getActivity()));
        HomeMenuAdapter homeMenuAdapter = new HomeMenuAdapter(getActivity(), homeMenuBeans);
        menuList.addHeader(headView);
        menuList.setAdapter(homeMenuAdapter);
        homeMenuAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    CustomUtils.showTipShort(getActivity(), "您是游客没有权限,请登录");
//                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 1001);
                    return;
                }
                if (homeMenuBeans.get(position).getId().equals("1")) {
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        startActivityForResult(new Intent(getActivity(), ReleaseCarInfoActivity.class), 1000);
                    } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        CustomUtils.showTipShort(getActivity(), "您是货主主，不能发布车源");
                        return;
                    }
                } else if (homeMenuBeans.get(position).getId().equals("2")) {
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        CustomUtils.showTipShort(getActivity(), "您是车主，不能发布货源");
                        return;
                    } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        startActivityForResult(new Intent(getActivity(), ReleaseGoodsInfoActivity.class), 2000);
                    }
                } else if (homeMenuBeans.get(position).getId().equals("3")) {
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        startActivityForResult(new Intent(getActivity(), RunningRouteListActivity.class), 3000);

                    } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        CustomUtils.showTipShort(getActivity(), "您是货主，不能查看常跑路线");
                        return;
                    }

                } else if (homeMenuBeans.get(position).getId().equals("4")) {
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        startActivityForResult(new Intent(getActivity(), MyCarSourceActivity.class), 4000);
                    } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        CustomUtils.showTipShort(getActivity(), "您是货主，不能发布车源");
                        return;
                    }
                } else if (homeMenuBeans.get(position).getId().equals("5")) {
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        CustomUtils.showTipShort(getActivity(), "您是车主，没有货源");
                        return;
                    } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        startActivityForResult(new Intent(getActivity(), MyGoodsSourceActivity.class), 5000);
                    }
                } else if (homeMenuBeans.get(position).getId().equals("6")) {
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        startActivityForResult(new Intent(getActivity(), chexin.project.tts.chexin.activity.carowner.MyCollectionActivity.class), 5112);
                    } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        startActivityForResult(new Intent(getActivity(), chexin.project.tts.chexin.activity.goodsowner.MyCollectionActivity.class), 5111);
                    }
                } else if (homeMenuBeans.get(position).getId().equals("7")) {
                    startActivityForResult(new Intent(getActivity(), CarLongSettingActivity.class), 6000);
                } else if (homeMenuBeans.get(position).getId().equals("8")) {
                    if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        startActivityForResult(new Intent(getActivity(), CarStateListActivity.class), 7000);
                    } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                        CustomUtils.showTipShort(getActivity(), "您是货主主，不能发布车源");
                        return;
                    }
                } else if (homeMenuBeans.get(position).getId().equals("9")) {
                    startActivityForResult(new Intent(getActivity(), FindLineActivity.class), 8000);
                } else if (homeMenuBeans.get(position).getId().equals("10")) {
                    startActivityForResult(new Intent(getActivity(), MileageBudgetActivity.class), 9000);
                } else if (homeMenuBeans.get(position).getId().equals("11")) {
                    startActivityForResult(new Intent(getActivity(), SettingActivity.class), 10000);
                } else if (homeMenuBeans.get(position).getId().equals("12")) {
                    startActivityForResult(new Intent(getActivity(), HelperActivity.class), 11000);
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        menuList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {

            }

            @Override
            public void onRefreshData() {
                menuList.setOnRefreshFinished(true);
            }
        });
    }

    private RelativeLayout RLGoods, RlCar;

    private void findAllView() {
        headView = View.inflate(getActivity(), R.layout.home_fragment_header, null);
        bannerView = (BannerView) headView.findViewById(R.id.bannerView);
        RLGoods = (RelativeLayout) headView.findViewById(R.id.RLGoods);
        RLGoods.setOnClickListener(this);
        RlCar = (RelativeLayout) headView.findViewById(R.id.RlCar);
        RlCar.setOnClickListener(this);
        menuList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.menuList);
        menuList.setIsHead(true);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        switch (index) {
            case banner_ok:
                getDataWithGet(banner_ok, Host.hostUrl + "/banner.api?getBanners");
                break;
            case province_ok:
                getDataWithGet(province_ok, Host.hostUrl + "/city.api?getCitys");
                break;
            case car_type_ok:
                getDataWithGet(car_type_ok, Host.hostUrl + "/others.api?getCarType");
                break;
            case goods_unit_ok:
                getDataWithGet(goods_unit_ok, Host.hostUrl + "/others.api?getGoodsWeight");
                break;
            case goods_type_ok:
                getDataWithGet(goods_type_ok, Host.hostUrl + "/others.api?getGoodsType");
                break;
            case car_long_ok:
                getDataWithGet(car_long_ok, Host.hostUrl + "/others.api?getCarLongth");
                break;
            case get_car_list:
                Map<String, String> params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                getDataWithPost(get_car_list, Host.hostUrl + "/car.api?getOwnerVehicle", params);
                break;
            case get_car_effective:
                getDataWithGet(get_car_effective, Host.hostUrl + "others.api?getEffective&effective_type=2");
                break;
            case get_good_effective:
                getDataWithGet(get_good_effective, Host.hostUrl + "others.api?getEffective&effective_type=1");
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case banner_ok:
                List<BannerBean> bannerBeans = new Gson().fromJson(response, new TypeToken<List<BannerBean>>() {
                }.getType());
                bannerView.setAdapter(new BannerAdapter(getActivity(), bannerBeans));
                break;
            case province_ok:
                provinceBeans = new Gson().fromJson(response, new TypeToken<ArrayList<ConfigBean>>() {
                }.getType());
                ConfigMoudle.getInstance().setProvinceBeans(provinceBeans);
                break;
            case car_type_ok:
                cartypeBeans = new Gson().fromJson(response, new TypeToken<List<ConfigBean>>() {
                }.getType());
                ConfigMoudle.getInstance().setCarTypeBeans(cartypeBeans);
                break;
            case goods_unit_ok:
                goodsWeightBeans = new Gson().fromJson(response, new TypeToken<List<ConfigBean>>() {
                }.getType());
                ConfigMoudle.getInstance().setGoodsUnitBeans(goodsWeightBeans);
                break;
            case goods_type_ok:
                goodsTypeBeans = new Gson().fromJson(response, new TypeToken<List<ConfigBean>>() {
                }.getType());
                ConfigMoudle.getInstance().setGoodsTypeBeans(goodsTypeBeans);
                break;
            case car_long_ok:
                carLongBeans = new Gson().fromJson(response, new TypeToken<List<ConfigBean>>() {
                }.getType());
                ConfigMoudle.getInstance().setCarLongBeans(carLongBeans);
                break;
            case get_car_list:
                carDetailBeans = new Gson().fromJson(response, new TypeToken<List<CarDetailBean>>() {
                }.getType());
                ConfigMoudle.getInstance().setCarDetailBeans(carDetailBeans);
                break;
            case get_car_effective:
                carEffectiveBeans = new Gson().fromJson(response, new TypeToken<List<EffectiveBean>>() {
                }.getType());
                ConfigMoudle.getInstance().setCarEffectiveBeans(carEffectiveBeans);
                break;
            case get_good_effective:
                goodEffectiveBeans = new Gson().fromJson(response, new TypeToken<List<EffectiveBean>>() {
                }.getType());
                ConfigMoudle.getInstance().setGoodEffectiveBeans(goodEffectiveBeans);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RlCar:
                EventBus.getDefault().post(new MainBean().setType("1"));
                break;
            case R.id.RLGoods:
                EventBus.getDefault().post(new MainBean().setType("2"));
                break;
        }
    }
}
