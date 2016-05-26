package tts.project.handi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.ImgBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.bean.AuthBean;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 我的认证
 */
public class MyAuthenticateActivity extends BaseActivity {
    String state;
    @Bind(R.id.tv_authenticate_status)
    TextView tvAuthenticateStatus;
    @Bind(R.id.tv_real_name)
    EditText tvRealName;
    @Bind(R.id.tv_id_number)
    EditText tvIdNumber;
    @Bind(R.id.img_IDcard_facade)
    ImageView imgIDcardFacade;
    @Bind(R.id.rl_IDcard_facade)
    RelativeLayout rlIDcardFacade;
    @Bind(R.id.img_IDcard_back)
    ImageView imgIDcardBack;
    @Bind(R.id.rl_IDcard_back)
    RelativeLayout rlIDcardBack;
    @Bind(R.id.img_other)
    ImageView imgOther;
    @Bind(R.id.rl_other)
    RelativeLayout rlOther;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    private final int front = 2001;
    private final int back = 2002;
    private final int other = 2003;

    ImgBean fronts;
    ImgBean backs;
    ImgBean others;
    AuthBean authBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_authenticate);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("我的认证"));
        state = getIntent().getStringExtra("state");
        mProgressDialog.show();
        if (!TextUtils.isEmpty(state)) {
            switch (state) {
                case "0":
                    break;
                case "1":
                    startRequestData(getData);
                    tvRealName.setEnabled(false);
                    tvIdNumber.setEnabled(false);
                    rlIDcardFacade.setClickable(false);
                    rlIDcardBack.setClickable(false);
                    rlOther.setClickable(false);
                    btnSubmit.setClickable(false);
                    btnSubmit.setBackgroundColor(getResources().getColor(R.color.grayFontsColor));
                    break;
                case "2":

                    break;
                case "3":
                    startRequestData(getData);
                    btnSubmit.setVisibility(View.GONE);
                    break;
            }
        }
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
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Personal&a=Cr", params);
                break;
            case submitData:
                params = new ArrayMap<>();
                List<PostFormBuilder.FileInput> files = new ArrayList<>();
                files.add(new PostFormBuilder.FileInput("positive", "positive.jpg", new File(fronts.getPath())));
                files.add(new PostFormBuilder.FileInput("back", "back.jpg", new File(backs.getPath())));

                if (fronts == null || backs == null) {
                    CustomUtils.showTipShort(this, "请选择图片");
                    return;
                }
                if (others != null) {
                    files.add(new PostFormBuilder.FileInput("morecard", "morecard.jpg", new File(others.getPath())));
                }
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                if (TextUtils.isEmpty(tvRealName.getText().toString()) || TextUtils.isEmpty(tvIdNumber.getText().toString())
                        || tvIdNumber.getText().length() != 18) {
                    CustomUtils.showTipShort(this, "请填写正确的资料");
                    return;
                }
                params.put("name", tvRealName.getText().toString());
                params.put("idcard", tvIdNumber.getText().toString());
                Logger.d(files.size() + "");
                uploadFile(submitData, Host.hostUrl + "api.php?m=Api&c=Personal&a=Cards", params, files);

                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                if (!TextUtils.isEmpty(response)) {
                    authBean = JSON.parseObject(response, AuthBean.class);
                    bindData(authBean);
                }
                Logger.d(response);
                break;
            case submitData:
                CustomUtils.showTipShort(this, response);
                Intent intent = new Intent(this, InfoCompleteActivity.class);
//                intent.putExtra("select", 3);
                startActivity(intent);
                finish();
//                Logger.d(response);
                break;
        }
        mProgressDialog.dismiss();
    }

    private void bindData(AuthBean authBean) {
        tvRealName.setText(authBean.getName());
        tvIdNumber.setText(authBean.getIdcard());
        Glide.with(this).load(authBean.getPositive()).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgIDcardFacade);
        Glide.with(this).load(authBean.getBack()).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgIDcardBack);
        if (authBean.getMorecard() != null) {
            Glide.with(this).load(authBean.getMorecard().toString()).diskCacheStrategy(DiskCacheStrategy.ALL).
                    into(imgOther);
        }
    }


    @OnClick({R.id.rl_IDcard_facade, R.id.rl_IDcard_back, R.id.rl_other, R.id.btn_submit})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_IDcard_facade:
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, front);
                break;
            case R.id.rl_IDcard_back:
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, back);
                break;
            case R.id.rl_other:
                intent = new Intent(this, CustomPictureSelectorView.class);
                intent.putExtra("maxCount", 1);
                startActivityForResult(intent, other);
                break;
            case R.id.btn_submit:
                startRequestData(submitData);
                break;
        }
    }

    @Override
    public void doIcon() {
//        super.doIcon();
        startActivity(new Intent(this, InfoCompleteActivity.class));
        finish();
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        mProgressDialog.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            startActivity(new Intent(this, InfoCompleteActivity.class));
        finish();
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                switch (requestCode) {
                    case front:
                        fronts = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0);
                        Glide.with(this).load(new File(fronts.getPath())).diskCacheStrategy(DiskCacheStrategy.ALL).
                                into(imgIDcardFacade);
                        break;
                    case back:
                        backs = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0);
                        Glide.with(this).load(new File(backs.getPath())).diskCacheStrategy(DiskCacheStrategy.ALL).
                                into(imgIDcardBack);
                        break;
                    case other:
                        others = ((List<ImgBean>) data.getSerializableExtra("imgBeans")).get(0);
                        Glide.with(this).load(new File(others.getPath())).diskCacheStrategy(DiskCacheStrategy.ALL).
                                into(imgOther);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
