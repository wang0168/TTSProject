package tts.project.igg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.widget.CircleImageView;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.adapter.BaseInfoItemAdapter;
import tts.project.igg.bean.BaseInfoItemBean;
import tts.project.igg.bean.UserInfoBean;
import tts.project.igg.common.MyAccountMoudle;
import tts.project.igg.utils.ImageLoader;
import tts.project.igg.utils.LoginInfoSave;

public class BaseInfoActivity extends BaseActivity {
    private RecyclerViewAutoRefreshUpgraded list;
    private final int face = 20001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);

        findAllView();
        setTitle(new BarBean().setMsg("基本信息"));
        startRequestData(getData);

    }

    private void adapter() {

        UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_base_info, null, false);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BaseInfoActivity.this, CustomPictureSelectorView.class).putExtra("maxCount", 1), 1000);
            }
        });
        CircleImageView face = (CircleImageView) headerView.findViewById(R.id.face);
        ImageLoader.load(this, userInfoBean.getHead_path(), face, R.mipmap.touxiang_2x);
        list.addHeader(headerView);

        final List<BaseInfoItemBean> baseInfoItemBeans = new ArrayList<>();
        baseInfoItemBeans.add(new BaseInfoItemBean("昵称", userInfoBean.getNick_name(), "", true, true));
        baseInfoItemBeans.add(new BaseInfoItemBean("手机号", userInfoBean.getMobile(), "", true, true));
        baseInfoItemBeans.add(new BaseInfoItemBean("性别", "1".equals(userInfoBean.getSex()) ? "男" : "女", "", true, true));
        baseInfoItemBeans.add(new BaseInfoItemBean("出生日期", userInfoBean.getAge(), "", true, true));
        baseInfoItemBeans.add(new BaseInfoItemBean("工作单位", userInfoBean.getJob_unit(), "", true, true));
        baseInfoItemBeans.add(new BaseInfoItemBean("职位", userInfoBean.getPosition(), "", true, true));
        baseInfoItemBeans.add(new BaseInfoItemBean("爱好", userInfoBean.getHobby(), "", true, true));
        list.setLayoutManager(new LinearLayoutManager(this));
        BaseInfoItemAdapter baseInfoItemAdapter = new BaseInfoItemAdapter(this, baseInfoItemBeans);
        baseInfoItemAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                startActivityForResult(new Intent(BaseInfoActivity.this, EditActivity.class).putExtra("title",
                        baseInfoItemBeans.get(position).getName()).putExtra("oldStr", "" + baseInfoItemBeans.get(position).getContext()), 2000);
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        list.setAdapter(baseInfoItemAdapter);

    }

    private void findAllView() {
        list = (RecyclerViewAutoRefreshUpgraded) findViewById(R.id.list);
        list.setIsHead(true);

    }

    private ImgBean bean;

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                getDataWithPost(getData, Host.hostUrl + "memberInterface.api?getMemberDetail", params);
                break;
            case face:
                params = new ArrayMap<>();
                List<PostFormBuilder.FileInput> files = new ArrayList<>();
                files.add(new PostFormBuilder.FileInput("head_path", "head_path.jpg", new File(bean.getPath())));
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id() + "");
                params.put("user_token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                uploadFile(face, Host.hostUrl + "memberInterface.api?updateMemberDetail", params, files);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                LoginInfoSave.loginOk(this, response);
                adapter();
                break;
            case face:

                break;

        }
//        CustomUtils.showTipShort(this, response);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1000:
                    bean = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0);
                    startRequestData(face);
                    break;
                case 2000:
                    startRequestData(getData);
                    break;
            }
        }
    }
}
