package tts.project.igg.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

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
import tts.project.igg.adapter.ALLSortAdapter;
import tts.project.igg.bean.GoodsBean;

public class SortListActivity extends BaseActivity {


    private RecyclerViewAutoRefreshUpgraded sortOne;
    private RecyclerViewAutoRefreshUpgraded sortTwo;
    private List<GoodsBean> sortList = new ArrayList<>();
    private List<GoodsBean> secondClassList = new ArrayList<>();
    private final int allSort = 1001;
    private final int secondClass = 1003;
    private int secondClassID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);
        setTitle(new BarBean().setMsg("分类").setSubTitle("返回"));
        findAllView();
        startRequestData(allSort);
//        adapterSortOne();
//        adapterSecondClass();
    }

    private void findAllView() {
        sortOne = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.sort_one);
        sortTwo = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.sort_two);
    }

    private void adapterSortOne() {
        sortOne.setLayoutManager(new LinearLayoutManager(this));
        ALLSortAdapter allSortAdapter = new ALLSortAdapter(this, sortList);
        sortOne.setAdapter(allSortAdapter);
        allSortAdapter.OnItemClickListener(new ALLSortAdapter.OnItemClickLiseren() {
            @Override
            public void onClick(View v, int pos) {
                sortOne.notifyDataSetChanged();
            }
        });
    }

    private void adapterSecondClass() {
////        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
//        sortTwo.setLayoutManager(new LinearLayoutManager(this));
//        SecondClassAdapter secondClassAdapter = new SecondClassAdapter(this, secondClassList);
//        sortTwo.setAdapter(secondClassAdapter);
////        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
////                                                @Override
////                                                public int getSpanSize(int position) {
////                                                    if (position > 1 || position == 0 || (position - 2) % 2 != 0) {
////                                                        return 1;
////                                                    } else {
////                                                        return gridLayoutManager.getSpanCount();
////                                                    }
////
////                                                }
////                                            }
////
////        );
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case allSort:
                params = new ArrayMap<>();
                getDataWithPost(allSort, Host.hostUrl + "goodsInterface.api?getAllGoodsClass", params);
                break;

            case secondClass:
                params = new ArrayMap<>();
                params.put("goods_id", secondClassID + "");
                params.put("goods_grade", "2");
                getDataWithPost(secondClass, Host.hostUrl + "goodsInterface.api?getGoodsByGoodsClassId", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case allSort:
                sortList = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
                }.getType());
                secondClassID = sortList.get(0).getGoods_id();
//                startRequestData(secondClass);
                adapterSortOne();
                adapterSecondClass();
                break;

            case secondClass:
                secondClassList = new Gson().fromJson(response, new TypeToken<List<GoodsBean>>() {
                }.getType());
                sortTwo.notifyDataSetChanged();
                break;
        }
    }
}
