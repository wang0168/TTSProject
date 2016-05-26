package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.carowner.CarStateListAdapter;
import chexin.project.tts.chexin.bean.CarDetailBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * 车辆状态
 * Created by sjb on 2016/3/28.
 */
public class CarStateListActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded carList;
    private CarStateListAdapter carStateListAdapter;
    private List<CarDetailBean> mData = new ArrayList<>();
    private final int allDo = 1001;
    private String vehicle_state = "0";
    private int currentPage = 1;
    public final int changeStatus = 1003;
    private int carPos;
    private boolean ischecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_state_list_activity);
        setTitle(new BarBean().setMsg("车辆状态"));
        findAllView();
        addMenu(new MenuItemBean().setTitle("全部空载").setIcon(R.drawable.gradient));
        startRequestData(getData);

    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        startRequestData(allDo);
    }

    private void findAllView() {
        carList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.carList);
    }

    private void adapterCarState() {
        carList.setLayoutManager(new LinearLayoutManager(this));
        carStateListAdapter = new CarStateListAdapter(this, mData);
        carList.setAdapter(carStateListAdapter);
        carList.setLoadMore(true);
        carList.setHeadVisible(true);
        carList.setOnRefreshListener(new RecyclerViewAutoRefreshUpgraded.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                currentPage++;
                startRequestData(loadMore);
            }

            @Override
            public void onRefreshData() {
                startRequestData(getData);
            }
        });
        carStateListAdapter.setCheckChangeListener(new CarStateListAdapter.CheckListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position) {

                carPos = position;
                ischecked = isChecked;
                startRequestData(changeStatus);
            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                currentPage = 1;
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("page", "1");
                getDataWithPost(getData, Host.hostUrl + "car.api?getOwnerVehicle", params);
                break;
            case loadMore:
                currentPage = 1;
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "car.api?getOwnerVehicle", params);
                break;
            case allDo:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("vehicle_state", vehicle_state);
                getDataWithPost(allDo, Host.hostUrl + "car.api?AllVehicleToNullOrFull", params);
                break;
            case changeStatus:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("CarType", mData.get(carPos).getCarType());
                params.put("CarWeight", mData.get(carPos).getCarWeight());
                params.put("CarLength", mData.get(carPos).getCarLength());
                params.put("CarWidth", mData.get(carPos).getCarWidth());
                params.put("CarCode", mData.get(carPos).getCarCode());
                params.put("vehicle_id", mData.get(carPos).getVehicle_id() + "");
                if (ischecked) {
                    params.put("vehicle_state", "1");
                } else {
                    params.put("vehicle_state", "0");
                }
                getDataWithPost(changeStatus, Host.hostUrl + "car.api?addVehicle", params);
                break;

        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                Logger.d(response);
                mData = new Gson().fromJson(response, new TypeToken<List<CarDetailBean>>() {
                }.getType());
                if (mData.size() == 0) {
                    CustomUtils.showTipShort(this, "暂无数据");
                }
                adapterCarState();
                break;
            case loadMore:
                List<CarDetailBean> temp = new Gson().fromJson(response, new TypeToken<List<CarDetailBean>>() {
                }.getType());
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(this, "暂无更多数据");
                    return;
                }
                mData.addAll(temp);
                carList.notifyDataSetChanged();
                break;
            case allDo:
                if ("0".equals(vehicle_state)) {
                    vehicle_state = "1";
                    removeMenu();
                    addMenu(new MenuItemBean().setTitle("全部空载").setIcon(R.drawable.gradient));
                } else {
                    vehicle_state = "0";
                    removeMenu();
                    addMenu(new MenuItemBean().setTitle("全部满载").setIcon(R.drawable.gradient));
                }
                startRequestData(getData);
                break;
            case changeStatus:
                CustomUtils.showTipShort(this, "更改状态成功");
                break;
        }
        carList.setOnRefreshFinished(true);
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        carList.setOnRefreshFinished(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2001:
                    break;
                case 2002:
                    break;
            }
        }
    }
}
