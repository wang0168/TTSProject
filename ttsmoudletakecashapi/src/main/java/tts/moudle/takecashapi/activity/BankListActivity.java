package tts.moudle.takecashapi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moudle.project.tts.ttsmoudletakecashapi.R;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.bean.BankBean;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.takecashapi.adapter.BankAdapter;

/**
 * Created by sjb on 2016/2/22.
 */
public class BankListActivity extends TTSBaseActivity {
    private final int init_ok = 1001;
    private final int delete_ok = 1002;

    private List<BankBean> bankBeans;
    private BankAdapter bankAdapter;
    private RecyclerView bankList;

    private BankBean bankBean;//银行卡对象
    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_list_activity);
        isClick = getIntent().getBooleanExtra("isClick", false);
        findAllView();
        onCreteMenu();
        startRequestData(init_ok);
    }

    private void findAllView() {
        setTitle(new BarBean().setMsg("银行卡").setSubTitle("返回"));
        bankList = (RecyclerView) findViewById(R.id.bankList);
    }

    private void onCreteMenu() {
        addMenu(new MenuItemBean().setId(2).setIcon("2", R.drawable.jia));
    }

    private void adapterBank() {
        bankAdapter = new BankAdapter(this, bankBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bankList.setLayoutManager(layoutManager);
        bankList.setAdapter(bankAdapter);
        bankAdapter.setOnItemClickListener(new BankAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (isClick) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bankBean", bankBeans.get(position));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void deleteAddress(int position) {
                bankBean = bankBeans.get(position);
                startRequestData(delete_ok);
            }
        });
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        startActivityForResult(new Intent(this, BankAddActivity.class), 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        BankBean bankBean = (BankBean) data.getSerializableExtra("bankBean");
                        if (bankBeans == null) {
                            bankBeans = new ArrayList<BankBean>();
                        }
                        bankBeans.add(bankBean);
                        bankAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case init_ok:
                showTipMsg("初始化...");
                params = new HashMap<String, String>();
                params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserBean().getUser_token());
                getDataWithPost(init_ok, Host.hostUrl + "api.php?m=Api&c=bank&a=banklist", params);
                break;
            case delete_ok:
                showTipMsg("删除中...");
                params = new HashMap<String, String>();
                params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
                params.put("token", AccountMoudle.getInstance().getUserBean().getUser_token());
                params.put("bank_id", bankBean.getBank_id());
                getDataWithPost(delete_ok, Host.hostUrl + "api.php?m=Api&c=bank&a=delbank", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case init_ok:
                bankBeans = new Gson().fromJson(response, new TypeToken<List<BankBean>>() {
                }.getType());
                adapterBank();
                break;
            case delete_ok:
                bankBeans.remove(bankBean);
                bankBean = null;
                bankAdapter.notifyDataSetChanged();
                break;
        }
    }
}
