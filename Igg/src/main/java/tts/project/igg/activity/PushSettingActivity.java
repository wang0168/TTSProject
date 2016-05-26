package tts.project.igg.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.adapter.PushItemAdapter;
import tts.project.igg.bean.PushItemBean;
import tts.project.igg.common.MyAccountMoudle;

public class PushSettingActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    List<PushItemBean> beanList = new ArrayList<>();
    List<String> select = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_setting);
        findAllView();
        addMenu(new MenuItemBean().setTitle("确定"));
        adapter();
        startRequestData(getData);
    }

    private void adapter() {
        list.setLayoutManager(new LinearLayoutManager(this));
        PushItemAdapter pushItemAdapter = new PushItemAdapter(this, beanList);
        list.setAdapter(pushItemAdapter);
        pushItemAdapter.setOnCheckedChangeListener(new PushItemAdapter.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int pos) {
                if (!isChecked) {
                    select.add(beanList.get(pos).getInformation_class_id() + "");

                } else {
                    select.remove(beanList.get(pos).getInformation_class_id() + "");
                }
                CustomUtils.showTipShort(PushSettingActivity.this, select.size() + "");
            }
        });
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        startRequestData(submitData);
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.list);
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
                getDataWithPost(getData, Host.hostUrl + "informationInterface.api?getAllInformationClassByMemberId", params);
                break;
            case submitData:
                String selectStr = "";
                for (int i = 0; i < select.size(); i++) {
                    selectStr += select.get(i) + ",";
                }

                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                if (TextUtils.isEmpty(selectStr)) {
                    params.put("member_information_ids",selectStr);
                } else {
                    params.put("member_information_ids", selectStr.substring(0, selectStr.length() - 1));
                }
                getDataWithPost(submitData, Host.hostUrl + "informationInterface.api?setInformationClass", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                beanList.clear();
                List<PushItemBean> temp = new Gson().fromJson(response, new TypeToken<List<PushItemBean>>() {
                }.getType());
                beanList.addAll(temp);
                list.notifyDataSetChanged();
                break;
            case submitData:
                CustomUtils.showTipShort(this, response);
                startRequestData(getData);
                break;
        }
    }
}
