package chexin.project.tts.chexin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sina.weibo.sdk.api.share.Base;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.carowner.ReleaseCarInfoActivity;
import chexin.project.tts.chexin.activity.goodsowner.ReleaseGoodsInfoActivity;
import chexin.project.tts.chexin.adapter.HelperAdapter;
import chexin.project.tts.chexin.bean.HomeMenuBean;
import chexin.project.tts.chexin.bean.MainBean;
import chexin.project.tts.chexin.bean.PublicMenuBean;
import de.greenrobot.event.EventBus;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * Created by sjb on 2016/3/28.
 */
public class HelperActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded helperList;
    private HelperAdapter helperAdapter;
    private List<PublicMenuBean> publicMenuBeans;
private View headView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helper_activity);
        setTitle(new BarBean().setMsg("使用帮助"));
        findAllView();
        adapterHelper();
    }

    private void findAllView() {
        helperList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.helperList);
        headView=View.inflate(this,R.layout.helper_header_item,null);
    }

    private void adapterHelper() {
        helperList.setLayoutManager(new LinearLayoutManager(this));

        publicMenuBeans = new ArrayList<PublicMenuBean>();
        publicMenuBeans.add(new PublicMenuBean().setId("1").setIconId(R.drawable.menu_add_car).setName("如果你是车主想要发布车源,请点击"));
        publicMenuBeans.add(new PublicMenuBean().setId("2").setIconId(R.drawable.menu_add_goods).setName("如果你是货主想发布货源,请点击"));
        publicMenuBeans.add(new PublicMenuBean().setId("3").setIconId(R.drawable.menu_my_car).setName("如果你想查找车源，请点击"));
        publicMenuBeans.add(new PublicMenuBean().setId("4").setIconId(R.drawable.menu_my_goods).setName("如果你想要查找货源，请点击"));
        helperAdapter = new HelperAdapter(this, publicMenuBeans);
        helperList.addHeader(headView);
        helperList.setAdapter(helperAdapter);
        helperAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if (publicMenuBeans.get(position).getId().equals("1")) {
                    startActivityForResult(new Intent(HelperActivity.this, ReleaseCarInfoActivity.class), 1000);
                } else if (publicMenuBeans.get(position).getId().equals("2")) {
                    startActivityForResult(new Intent(HelperActivity.this, ReleaseGoodsInfoActivity.class), 2000);
                } else if (publicMenuBeans.get(position).getId().equals("3")) {
                    EventBus.getDefault().post(new MainBean().setType("1"));
                    finish();
                } else if (publicMenuBeans.get(position).getId().equals("4")) {
                    EventBus.getDefault().post(new MainBean().setType("2"));
                    finish();
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
    }
}
