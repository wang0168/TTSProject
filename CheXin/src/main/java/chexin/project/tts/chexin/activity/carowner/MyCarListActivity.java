package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.carowner.CarListAdapter;
import chexin.project.tts.chexin.bean.CarDetailBean;
import chexin.project.tts.chexin.common.ConfigMoudle;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

public class MyCarListActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerViewAutoRefreshUpgraded carList;
    private CarListAdapter carListAdapter;
    private Button addCar;
    private List<CarDetailBean> mData = new ArrayList<>();
    private String vehicle_id;
    private int currentPage = 1;
    public final int changeStatus = 1003;
    private int carPos;
    private boolean ischecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_list);
        setTitle(new BarBean().setMsg("我的车辆"));
        findAllView();
        startRequestData(getData);

    }

    private void findAllView() {
        carList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.carList);
        addCar = (Button) findViewById(R.id.btn_add_car);
        addCar.setOnClickListener(this);
    }

    private void adapterMyApply() {
        carList.setLayoutManager(new LinearLayoutManager(this));
        carListAdapter = new CarListAdapter(this, mData);
        carList.setAdapter(carListAdapter);
        carList.setHeadVisible(true);
        carList.setLoadMore(true);
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
        carListAdapter.setBlueActionOnClickListener(new CarListAdapter.BlueActionOnClickListener() {
            @Override
            public void blueAction(int postion) {
                startActivityForResult(new Intent(MyCarListActivity.this, AddCarActivity.class).
                        putExtra("data", mData.get(postion)), 2001);
            }
        });
        carListAdapter.setRedActionOnClickListener(new CarListAdapter.RedActionOnClickListener() {
            @Override
            public void redAction(int postion) {
                vehicle_id = mData.get(postion).getVehicle_id() + "";
                startRequestData(delete);

            }
        });
        carListAdapter.setCheckChangeListener(new CarListAdapter.CheckListener() {
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
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "car.api?getOwnerVehicle", params);
                break;
            case delete:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("vehicle_id", vehicle_id + "");
                getDataWithPost(delete, Host.hostUrl + "car.api?deleteVehicle", params);
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
                adapterMyApply();
                ConfigMoudle.getInstance().setCarDetailBeans(mData);
                break;
            case loadMore:
                List<CarDetailBean> temp = new Gson().fromJson(response, new TypeToken<List<CarDetailBean>>() {
                }.getType());
                if (temp.size() == 0) {
                    CustomUtils.showTipShort(this, "暂无更多数据");
                }
                mData.addAll(temp);
                carList.notifyDataSetChanged();
                break;
            case delete:
                startRequestData(getData);
                CustomUtils.showTipShort(this, "删除车辆成功");
                carList.notifyDataSetChanged();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_car:
                startActivityForResult(new Intent(this, AddCarActivity.class), 2001);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2001:
                    startRequestData(getData);
                    break;
                case 2002:
                    break;
            }
        }
    }
}
