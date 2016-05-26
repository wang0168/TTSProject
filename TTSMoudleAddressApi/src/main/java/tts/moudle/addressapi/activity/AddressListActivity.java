package tts.moudle.addressapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moudle.project.tts.tts_moudle_address_api.R;
import tts.moudle.addressapi.adapter.AddressAdapter;
import tts.moudle.addressapi.bean.AddressBean;
import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseActivity;
import tts.moudle.api.moudle.AccountMoudle;
import tts.moudle.api.widget.RecyclerViewAutoRefresh;

/**
 * Created by sjb on 2016/3/3.
 */
public class AddressListActivity extends TTSBaseActivity{
    private RecyclerViewAutoRefresh mListaddress;
    private final int init_ok = 1001;
    private final int delete_ok = 1002;
    private final int default_ok = 1003;
    private List<AddressBean> addressBeans;
    private AddressAdapter adapter;
    public int position;//记录选择的地址序列号
    private boolean IsClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_list_activity);
        IsClick = getIntent().getBooleanExtra("IsClick", false);
        findAllView();
    }

    private void findAllView(){
        setTitle("我的地址");
        mListaddress= (RecyclerViewAutoRefresh) findViewById(R.id.addressList);
    }


    public void doClick(View v) {
        int i = v.getId();
        if (i == R.id.addBtn) {
            startActivityForResult(new Intent(this, AddressNewActivity.class), 1000);
        }
    }

    private void adapterAddress() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置是否反向显示
        // layoutManager.setReverseLayout(false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListaddress.setLayoutManager(layoutManager);
        adapter = new AddressAdapter(this, addressBeans);
        mListaddress.setAdapter(adapter);
        mListaddress.setOnRefreshListener(new RecyclerViewAutoRefresh.OnRefreshListener() {
            @Override
            public void onLoadMore() {
                mListaddress.onRefreshFinished(true);
            }

            @Override
            public void onRefreshData() {
                mListaddress.onRefreshFinished(true);
            }
        });
        // mListaddress.addItemDecoration(new RecyclerViewItemSpace(getResources().getDimensionPixelSize(R.dimen.y10), getResources().getColor(R.color.RGB240_240_240)));
        adapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if (IsClick) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addressBean", addressBeans.get(position));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void deleteAddress(int position) {
                AddressListActivity.this.position = position;
                startRequestData(delete_ok);
            }

            @Override
            public void editAddress(int position) {
                /*Intent intent = new Intent(AddressListActivity.this, AddressNewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("addressBean", addressBean);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2000);*/
            }

            @Override
            public void defaultAddress(int position) {
                AddressListActivity.this.position = position;
                startRequestData(default_ok);
            }
        });
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        switch (index) {
            case init_ok:
                showTipMsg("初始化......");
                getDataWithGet(init_ok, Host.hostUrl + "/Api.php?m=api&c=memeber&a=address" + "&member_id=" + AccountMoudle.getInstance().getUserBean().getMember_id());
                break;
            case delete_ok:
                showTipMsg("删除中......");
                getDataWithGet(delete_ok, Host.hostUrl + "/Api.php?m=api&c=memeber&a=del_address" + "&id=" + addressBeans.get(position).getId());
                break;
            case default_ok:
                showTipMsg("修改中......");
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", addressBeans.get(position).getName());
                params.put("address", addressBeans.get(position).getAddress());
                params.put("county", addressBeans.get(position).getCounty());
                params.put("mobile", addressBeans.get(position).getMobile());
                params.put("is_default", "on");
                params.put("member_id", AccountMoudle.getInstance().getUserBean().getMember_id());
                params.put("id", addressBeans.get(position).getId());
                getDataWithPost(default_ok,Host.hostUrl + "/Api.php?m=api&c=memeber&a=addaddress", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case init_ok:
                addressBeans = new Gson().fromJson(response, new TypeToken<List<AddressBean>>() {
                }.getType());
                adapterAddress();
                break;
            case delete_ok:
                addressBeans.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                break;
            case default_ok:
                startRequestData(init_ok);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2000:
                if (RESULT_OK == resultCode) {
                    if (data != null) {
                        AddressBean addressBean = (AddressBean) data.getSerializableExtra("addressBean");
                        for (int i = 0; i < addressBeans.size(); i++) {
                            if (addressBeans.get(i).getId().equals(addressBean.getId())) {
                                addressBeans.set(i, addressBean);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            case 3000:
                if (resultCode == RESULT_OK) {
                    startRequestData(init_ok);
                } else {
                    finish();
                }
                break;
        }
    }
}
