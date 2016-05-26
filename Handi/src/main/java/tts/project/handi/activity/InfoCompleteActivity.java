package tts.project.handi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tts.moudle.api.Host;
import tts.moudle.api.activity.CustomPictureSelectorView;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;
import tts.project.handi.BaseActivity;
import tts.project.handi.MainActivity;
import tts.project.handi.R;
import tts.project.handi.bean.UserInfo;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 信息完善
 */
public class InfoCompleteActivity extends BaseActivity {

    @Bind(R.id.rl_face)
    RelativeLayout rlFace;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.rl_name)
    RelativeLayout rlName;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @Bind(R.id.rl_phone_number)
    RelativeLayout rlPhoneNumber;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.rl_address)
    RelativeLayout rlAddress;
    @Bind(R.id.tv_contacts)
    TextView tvContacts;
    @Bind(R.id.rl_contacts)
    RelativeLayout rlContacts;
    @Bind(R.id.tv_contacts_number)
    TextView tvContactsNumber;
    @Bind(R.id.rl_contact_number)
    RelativeLayout rlContactNumber;
    @Bind(R.id.tv_auth_status)
    TextView tvAuthStatus;
    @Bind(R.id.rl_id_info)
    RelativeLayout rlIdInfo;
    @Bind(R.id.img_face)
    CircleImageView imgFace;
    @Bind(R.id.rl_brands_settings)
    RelativeLayout rlBrandsSettings;
    @Bind(R.id.rl_service_settings)
    RelativeLayout rlServiceSettings;
    private final int name = 1001;
    private final int phone = 1002;
    private final int address = 1003;
    private final int contants = 1004;
    private final int contantsNumber = 1005;
    private final int face = 20001;
    private UserInfo userInfo;
    private String state;
    private ImgBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_complete);
        ButterKnife.bind(this);
        setTitle("返回", "完善资料");
        mProgressDialog.show();
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
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Personal&a=Index", params);
                break;
            case face:
                List<PostFormBuilder.FileInput> files = new ArrayList<>();
                files.add(new PostFormBuilder.FileInput("img", "img.jpg", new File(bean.getPath())));
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                uploadFile(face, Host.hostUrl + "api.php?m=Api&c=Personal&a=Uploadimg", params, files);
                break;
        }
    }

    @Override
    public void doIcon() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("init", "3");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                if (!TextUtils.isEmpty(response)) {
                    Logger.d(response);
                    userInfo = JSON.parseObject(response, UserInfo.class);
                    MyAccountMoudle.getInstance().setUserInfo(userInfo);
                    bindData(userInfo);
                }
                break;
            case face:
                Logger.d(response);
                CustomUtils.showTipShort(this, "头像更改成功");
                break;
        }
    }

    @Override
    protected void doErrFailed(int index, String response) {
        super.doErrFailed(index, response);
        CustomUtils.showTipShort(this, response);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("init", 3);
            startActivity(intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void bindData(UserInfo userInfo) {
        tvName.setText(userInfo.getNickname());
        tvPhoneNumber.setText(userInfo.getMobile());
        tvAddress.setText(userInfo.getCity() + userInfo.getCounty() + userInfo.getAddress() + "");
        tvContacts.setText(userInfo.getUrgentname());
        tvContactsNumber.setText(userInfo.getUrgentphone());
        if (!TextUtils.isEmpty(userInfo.getCardyn())) {
            state = userInfo.getCardyn();
            if ("0".equals(userInfo.getCardyn())) {
                tvAuthStatus.setText("去认证");
                tvAuthStatus.setTextColor(getResources().getColor(R.color.grayFontsColor));
            }
            if ("1".equals(userInfo.getCardyn())) {
                tvAuthStatus.setText("处理中");
                tvAuthStatus.setTextColor(getResources().getColor(R.color.themeFontsColor));
            }
            if ("2".equals(userInfo.getCardyn())) {
                tvAuthStatus.setText("验证失败");
                tvAuthStatus.setTextColor(getResources().getColor(R.color.grayFontsColor));
            }
            if ("3".equals(userInfo.getCardyn())) {
                tvAuthStatus.setText("已认证");
                tvAuthStatus.setTextColor(getResources().getColor(R.color.themeFontsColor));
            }
            if ("4".equals(userInfo.getCardyn())) {
                tvAuthStatus.setText("已被下架");
                tvAuthStatus.setTextColor(getResources().getColor(R.color.redFontsColor));
            }
        }
        Glide.with(this).load(userInfo.getImg()).placeholder(R.mipmap.tx_2x).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgFace);
    }

    @OnClick({R.id.rl_service_settings, R.id.rl_brands_settings, R.id.img_face, R.id.rl_name, R.id.rl_phone_number, R.id.rl_address, R.id.rl_contacts, R.id.rl_contact_number, R.id.rl_id_info})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_face:
//                goEdit(tv);
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, face);
                break;
            case R.id.rl_name:
                goEdit(tvName.getText().toString(), name, "nickname");
                break;
            case R.id.rl_phone_number:
//                goEdit(tvPhoneNumber.getText().toString(), phone, "mobile");
                break;
            case R.id.rl_address:
                goEdit(tvAddress.getText().toString(), address, "address");
                break;
            case R.id.rl_contacts:
                goEdit(tvContacts.getText().toString(), contants, "urgentname");
                break;
            case R.id.rl_contact_number:
                goEdit(tvContactsNumber.getText().toString(), contantsNumber, "urgentphone");
                break;

            case R.id.rl_brands_settings:
                startActivity(new Intent(this, BrandsActivity.class));
                break;
            case R.id.rl_service_settings:
                startActivity(new Intent(this, ServiceSettingActivity.class));
                break;
            case R.id.rl_id_info:
                if ("4".equals(userInfo.getCardyn())) {
//                    Utils.showDialog(this,"您的账户已被下架",);
                    new AlertDialog.Builder(this)
                            .setMessage("您的账户已被下架，请联系客服")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();

                } else {
                    intent = new Intent(this, MyAuthenticateActivity.class);
                    intent.putExtra("state", state);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    void goEdit(String oldStr, int requestcode, String key) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("oldStr", oldStr);
        intent.putExtra("key", key);
        startActivityForResult(intent, requestcode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case name:
                    tvName.setText(data.getStringExtra("newStr"));
                    break;
                case phone:
                    tvPhoneNumber.setText(data.getStringExtra("newStr"));
                    break;
                case address:
                    tvAddress.setText(data.getStringExtra("newStr"));
                    break;
                case contants:
                    tvContacts.setText(data.getStringExtra("newStr"));
                    break;
                case contantsNumber:
                    tvContactsNumber.setText(data.getStringExtra("newStr"));
                    break;
                case face:
                    bean = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0);
                    Glide.with(this).load(new File(bean.getPath())).placeholder(R.mipmap.tx_2x).diskCacheStrategy(DiskCacheStrategy.ALL).
                            into(imgFace);
                    startRequestData(face);
                    break;
            }
        }
    }


}