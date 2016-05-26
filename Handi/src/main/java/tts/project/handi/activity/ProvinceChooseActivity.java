package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import tts.moudle.api.bean.BarBean;

import tts.moudle.api.cityapi.CityBean;
import tts.moudle.api.cityapi.CityMoudle;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;

import tts.project.handi.adapter.ProvinceAdapter;
import tts.project.handi.myInterface.OnItemClickLitener;


/**
 * 省份选择
 */
public class ProvinceChooseActivity extends BaseActivity implements OnItemClickLitener {
    private RecyclerView recyclerView;
    //    private List<ProvinceBean> provinceBeans;
    private List<CityBean> provinceBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_choose);
        provinceBeans = CityMoudle.getInstance().getCityBeans(this);
        findView();
//        mProgressDialog.show();
        adapterList();
//        startRequestData(getData);
    }

    private void findView() {
        setTitle(new BarBean().setMsg("选择省份"));
        recyclerView = (RecyclerView) findViewById(R.id.recycle_choose_area);
    }

//    @Override
//    protected void startRequestData(int index) {
//        super.startRequestData(index);
//        if (index == getData) {
//            getDataWithGet(getData, "http://aiwu.tstweiguanjia.com/api.php?m=Api&c=Conf&a=cityConfig");
//        }
//    }
//
//    @Override
//    protected void doSuccess(int index, String response) {
//        super.doSuccess(index, response);
//        if (index == getData) {
//            provinceBeans = JSON.parseArray(response, ProvinceBean.class);
//            adapterList();
//        }
//    }

    private void adapterList() {
        ProvinceAdapter cityAdapter = new ProvinceAdapter(this, provinceBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnItemClickLitener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, CityChooseActivity.class);
        intent.putExtra("cityList", (Serializable) (provinceBeans.get(position).getCityList()));
        String pName=provinceBeans.get(position).getName();

        intent.putExtra("provinceName",  pName.replace("市",""));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
