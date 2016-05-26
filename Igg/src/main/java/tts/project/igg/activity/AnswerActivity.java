package tts.project.igg.activity;

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
import tts.project.igg.adapter.AnswerAdapter;
import tts.project.igg.bean.QuestionBean;
import tts.project.igg.common.MyAccountMoudle;

public class AnswerActivity extends BaseActivity implements View.OnClickListener {
    private String information_id;
    private RecyclerViewAutoRefreshUpgraded list;
    private Button action;
    private List<QuestionBean> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        information_id = getIntent().getStringExtra("id");

        findAllView();
        setTitle(new BarBean().setMsg("答题").setSubTitle("返回"));
        adapter();
        if (information_id != null) {
            startRequestData(getData);
        }
    }

    private void adapter() {

        list.setLayoutManager(new LinearLayoutManager(this));
        AnswerAdapter answerAdapter = new AnswerAdapter(this, data);
        list.setAdapter(answerAdapter);
    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.list);
        action = (Button) findViewById(R.id.action);
        action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:

                break;
        }
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
                params.put("information_id", information_id);
                getDataWithPost(getData, Host.hostUrl + "informationInterface.api?getInformationQuestions", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                data.clear();
                List<QuestionBean> temp = new Gson().fromJson(response, new TypeToken<List<QuestionBean>>() {
                }.getType());
                data.addAll(temp);
                list.notifyDataSetChanged();
                break;
        }
    }
}
