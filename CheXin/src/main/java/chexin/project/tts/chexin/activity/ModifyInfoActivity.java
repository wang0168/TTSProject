package chexin.project.tts.chexin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.UserInfoBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;

/**
 * 修改个人信息
 */
public class ModifyInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_face)
    CircleImageView imgFace;
    @Bind(R.id.tv_user_type)
    TextView tvUserType;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_idcard)
    TextView tvIdcard;
    @Bind(R.id.tv_company_name)
    EditText tvCompanyName;
    @Bind(R.id.tv_address)
    EditText tvAddress;
    @Bind(R.id.tv_telephone1)
    EditText tvTelephone1;
    @Bind(R.id.tv_telephone2)
    EditText tvTelephone2;
    @Bind(R.id.btn_action)
    Button btnAction;
    @Bind(R.id.tv_system_code)
    TextView tvSystemCode;
    @Bind(R.id.layout_system_code)
    RelativeLayout layoutSystemCode;
    private UserInfoBean userInfoBean;
    private String from;//1评价车，2评价货
    private String facePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("个人资料"));
        from = getIntent().getStringExtra("from");
        userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        if (userInfoBean != null) {
            bindData();
        }
        btnAction.setOnClickListener(this);
        imgFace.setOnClickListener(this);
    }

    private void bindData() {
        Glide.with(this).load(userInfoBean.getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgFace);
        if ("1".equals(userInfoBean.getUsertype())) {
            tvUserType.setText("车主");
            layoutSystemCode.setVisibility(View.VISIBLE);
            tvSystemCode.setText(userInfoBean.getBoothno());
        } else {
            tvUserType.setText("货主");
        }
        tvName.setText(userInfoBean.getUsername());
        tvAge.setText(userInfoBean.getAge());
        tvSex.setText(userInfoBean.getGender());
        tvIdcard.setText(userInfoBean.getCardid());
        tvCompanyName.setText(userInfoBean.getCompany());
        tvAddress.setText(userInfoBean.getCompanyaddress());
        tvTelephone1.setText(userInfoBean.getTelephoneno());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action:
                startRequestData(submitData);
                break;
            case R.id.img_face:
                Intent intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, 201);
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        if (index == submitData) {
            params = new ArrayMap<>();
            params.put("id", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
            params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
            params.put("company", tvCompanyName.getText().toString());
            params.put("companyaddress", tvAddress.getText().toString());
            if (!TextUtils.isEmpty(tvTelephone1.getText().toString())) {
                params.put("telephoneno", tvTelephone1.getText() + "");
            }
            if (!TextUtils.isEmpty(tvTelephone2.getText().toString())) {
                params.put("telephoneno2", tvTelephone2.getText() + "");
            }
            List<PostFormBuilder.FileInput> files = new ArrayList<>();
            if (!TextUtils.isEmpty(facePath)) {
                files.add(new PostFormBuilder.FileInput("head_path", "face_img.jpg", new File(facePath)));
            }
            mProgressDialog.show();
            uploadFile(submitData, Host.hostUrl + "userfaces?updateUserById", params, files);
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (index == submitData) {

            CustomUtils.showTipShort(this, "保存成功");
            UserInfoBean userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
            userInfoBean.setHead_path(response);

            if (SharedPreferencesMoudle.getInstance().setJson(getApplicationContext(), "user_login", response)) {
                mProgressDialog.dismiss();
                setResult(RESULT_OK, new Intent().putExtra("imgUrl", response));
                //EventBus.getDefault().post(new LoginEvent().setIsLogin(true));
                finish();
            } else {
                CustomUtils.showTipShort(this, "系统异常");
            }


        }
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        System.out.println(response + "123321");
    }

    List<ImgBean> faceImg;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 201:
                    faceImg = (List<ImgBean>) data.getSerializableExtra("imgBeans");
                    facePath = faceImg.get(0).getPath();
                    Glide.with(this).load(facePath).diskCacheStrategy(DiskCacheStrategy.ALL).
                            into(imgFace);
                    break;
            }
        }
    }
}
