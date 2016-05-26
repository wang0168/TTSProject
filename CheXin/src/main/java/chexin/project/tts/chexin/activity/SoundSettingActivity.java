package chexin.project.tts.chexin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.adapter.PublicStyleAdapter;
import chexin.project.tts.chexin.bean.PublicMenuBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

public class SoundSettingActivity extends BaseActivity implements TTSBaseAdapterRecyclerView.OnItemClickListener {
    private RecyclerViewAutoRefreshUpgraded settingList;
    PublicStyleAdapter publicStyleAdapter;
    List<PublicMenuBean> publicMenuBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_setting);
        setTitle(new BarBean().setMsg("声音设置"));
        findAllView();
        adapterSetting();
    }

    private void findAllView() {
        settingList = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.settingList);
    }

    private void adapterSetting() {
        publicMenuBeans = new ArrayList<PublicMenuBean>();
        publicMenuBeans.add(new PublicMenuBean().setId("1").setName("系统声音").setIconRightId(R.drawable.push_button).setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("2").setName("推送声音").setIconRightId(R.drawable.push_button));
        publicMenuBeans.add(new PublicMenuBean().setId("3").setName("申请提示").setIconRightId(R.drawable.push_button).setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("4").setName("评价提示").setIconRightId(R.drawable.push_button));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        settingList.setLayoutManager(layoutManager);
        publicStyleAdapter = new PublicStyleAdapter(this, publicMenuBeans);
        settingList.setAdapter(publicStyleAdapter);
        publicStyleAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View itemView, int position) {

    }

    @Override
    public void onLongClick(View itemView, int position) {

    }
}
