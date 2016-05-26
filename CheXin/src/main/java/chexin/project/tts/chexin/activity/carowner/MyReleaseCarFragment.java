package chexin.project.tts.chexin.activity.carowner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import chexin.project.tts.chexin.BaseFragment;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.carowner.MyReleaseCarListAdapter;
import chexin.project.tts.chexin.bean.CarBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView.OnItemClickListener;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * Created by sjb on 2016/3/28.
 */
public class MyReleaseCarFragment extends BaseFragment implements OnItemClickListener {
    private RecyclerViewAutoRefreshUpgraded carList;
    private MyReleaseCarListAdapter myReleaseCarListAdapter;
    private List<CarBean> mData;
    private String carId;
    public final int delete = 1002;
    private int currentPage = 1;
    public final int changeStatus = 1003;
    private int carPos;
    private boolean ischecked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.my_release_car_fragment, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findAllView();
        startRequestData(getData);
    }

    private void findAllView() {
        carList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.carList);
    }

    private void adapterCar() {
        carList.setLayoutManager(new LinearLayoutManager(getActivity()));
        myReleaseCarListAdapter = new MyReleaseCarListAdapter(getActivity(), mData, true);
        carList.setAdapter(myReleaseCarListAdapter);
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
        myReleaseCarListAdapter.setOnItemClickListener(this);
        myReleaseCarListAdapter.setBlueActionOnClickListener(new MyReleaseCarListAdapter.BlueActionOnClickListener() {
            @Override
            public void blueAction(int postion) {
                startActivity(new Intent(getActivity(), MyApplyListActivity.class).putExtra("relation_id", mData.get(postion).getID() + ""));
            }
        });
        myReleaseCarListAdapter.setRedActionOnClickListener(new MyReleaseCarListAdapter.RedActionOnClickListener() {
            @Override
            public void redAction(int postion) {
                carId = mData.get(postion).getID() + "";
                mData.remove(postion);
                startRequestData(delete);
            }
        });
        myReleaseCarListAdapter.setCheckChangeListener(new MyReleaseCarListAdapter.CheckListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position) {
                carPos = position;
                ischecked = isChecked;
                startRequestData(changeStatus);
            }
        });
    }

    @Override
    public void onClick(View itemView, int position) {
        startActivityForResult(new Intent(getActivity(), CarDetailsActivity.class).putExtra("data", mData.get(position)), 1002);
    }

    @Override
    public void onLongClick(View itemView, int position) {

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
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("page", "1");
                getDataWithPost(getData, Host.hostUrl + "car.api?getOwnerCar", params);
                break;
            case loadMore:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("page", currentPage + "");
                getDataWithPost(loadMore, Host.hostUrl + "car.api?getOwnerCar", params);
                break;
            case delete:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("ID", carId);
                getDataWithPost(delete, Host.hostUrl + "car.api?deleteCar", params);
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
                params.put("vehicle_id", mData.get(carPos).getVehicle_id());
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
                mData = new Gson().fromJson(response, new TypeToken<List<CarBean>>() {
                }.getType());
                if (mData.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无数据");
                }
                adapterCar();
                break;
            case loadMore:
                List<CarBean> temp = new Gson().fromJson(response, new TypeToken<List<CarBean>>() {
                }.getType());

                if (temp.size() == 0) {
                    CustomUtils.showTipShort(getActivity(), "暂无更多数据");
                    return;
                }
                mData.addAll(temp);
                carList.notifyDataSetChanged();

                break;
            case delete:
                CustomUtils.showTipShort(getActivity(), "删除车源成功");
                carList.notifyDataSetChanged();
                break;
            case changeStatus:
                CustomUtils.showTipShort(getActivity(),"更改状态成功");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1002:
                    startRequestData(getData);
                    break;
            }
        }
    }
}
