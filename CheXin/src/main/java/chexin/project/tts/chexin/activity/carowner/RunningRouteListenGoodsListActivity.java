package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.goodsowner.GoodsDetailsActivity;
import chexin.project.tts.chexin.adapter.GoodsAdapter;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * Created by sjb on 2016/3/28.
 */
public class RunningRouteListenGoodsListActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded goodsList;
    private GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running_route_listen_goods_list_activity);
        setTitle(new BarBean().setMsg("新的货源"));
        findAllView();
        adapterGoods();
    }

    private void findAllView() {
        goodsList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.goodsList);
    }

    private void adapterGoods() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        goodsList.setLayoutManager(layoutManager);
        goodsAdapter = new GoodsAdapter(this, null);
        goodsList.setAdapter(goodsAdapter);
        goodsAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivity(new Intent(RunningRouteListenGoodsListActivity.this, GoodsDetailsActivity.class));
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }
}
