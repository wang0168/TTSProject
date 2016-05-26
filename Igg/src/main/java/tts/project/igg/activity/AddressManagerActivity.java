package tts.project.igg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.adapter.AddressManagerAdapter;
import tts.project.igg.bean.AddressBean;
import tts.project.igg.common.MyAccountMoudle;

public class AddressManagerActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerViewAutoRefreshUpgraded list;
    private Button action;
    private List<AddressBean> data = new ArrayList<>();
    private final int setDefault = 1001;
    private int deletePos;
    private int setDefaultPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);
        setTitle(new BarBean().setMsg("地址管理").setSubTitle("返回"));
        findAllView();
        adapter();
        startRequestData(getData);
    }

    private void adapter() {
        list.setLayoutManager(new LinearLayoutManager(this));
        AddressManagerAdapter addressManagerAdapter = new AddressManagerAdapter(this, data);
        list.setAdapter(addressManagerAdapter);
        addressManagerAdapter.setOnDeleteClickListener(new AddressManagerAdapter.OnDeleteClickListener() {
            @Override
            public void delete(View itemView, int position) {
                deletePos = position;
                startRequestData(delete);
            }
        });
        addressManagerAdapter.setOnSetDefaultClickListener(new AddressManagerAdapter.OnSetDefaultClickListener() {
            @Override
            public void setDefault(View itemView, int position) {
                setDefaultPos = position;
                startRequestData(setDefault);
            }
        });
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.list);
        action = (Button) findViewById(R.id.action);
        action.setOnClickListener(this);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                getDataWithPost(getData, Host.hostUrl + "addressInterface.api?getOwnerAddress", params);
                break;
            case delete:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                params.put("address_id", data.get(deletePos).getAddress_id() + "");
                getDataWithPost(delete, Host.hostUrl + "addressInterface.api?deleteAddress", params);
                break;
            case setDefault:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                params.put("address_id", data.get(setDefaultPos).getAddress_id() + "");
                getDataWithPost(setDefault, Host.hostUrl + "addressInterface.api?setDefaultAddress", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                data.clear();
                List<AddressBean> temp = new Gson().fromJson(response, new TypeToken<List<AddressBean>>() {
                }.getType());
                data.addAll(temp);
                list.notifyDataSetChanged();
                break;
            case delete:
                startRequestData(getData);
                break;
            case setDefault:
                startRequestData(getData);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                startActivityForResult(new Intent(this, EditAddressActivity.class).putExtra("from", 0), 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            startRequestData(getData);
        }
    }
}
