package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefresh;
import tts.moudle.takecashapi.activity.BankListActivity;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.adapter.BillAdapter;
import tts.project.handi.bean.Income;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 我的收益
 */
public class MyIncomeActivity extends BaseActivity {


    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.btn_take_cash)
    Button btnTakeCash;
    @Bind(R.id.tv_bank_card)
    TextView tvBankCard;
    @Bind(R.id.tv_yesterday_orders)
    TextView tvYesterdayOrders;
    @Bind(R.id.tv_lastweek_orders)
    TextView tvLastweekOrders;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.recycleview_income)
    RecyclerViewAutoRefresh recycleviewIncome;
    private Income income;
    private BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_income);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("我的钱包"));
        mProgressDialog.show();
        recycleviewIncome.setLayoutManager(new LinearLayoutManager(this));
        startRequestData(getData);
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Personal&a=PerAccount", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (!TextUtils.isEmpty(response)) {
            income = JSON.parseObject(response, Income.class);
            bindData(income);
            adapter = new BillAdapter(this, income.getBl());
            recycleviewIncome.setAdapter(adapter);
        }

    }

    private void bindData(Income income) {
        tvMoney.setText(income.getMoney() + "");
        tvBankCard.setText(income.getBanknum() + "");
        tvYesterdayOrders.setText(income.getDaynum() + "");
        tvLastweekOrders.setText(income.getWeeknum() + "");
    }

    @OnClick({R.id.btn_take_cash, R.id.tv_all, R.id.tv_bank_card})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_take_cash:
                intent = new Intent(this, TakeCashActivity.class);
                Logger.d(Double.parseDouble(income.getMoney())+"****");
                intent.putExtra("monkey", Double.parseDouble(income.getMoney()));
                startActivity(intent);
                break;
            case R.id.tv_bank_card:
                intent = new Intent(this, BankListActivity.class);
                intent.putExtra("isClick", true);//不传此参数 则列表不可点击
                startActivityForResult(intent, 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            startRequestData(getData);
        }
    }
}
