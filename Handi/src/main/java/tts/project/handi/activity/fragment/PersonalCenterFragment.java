package tts.project.handi.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.moudle.SharedPreferencesMoudle;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;
import tts.project.handi.BaseFragment;
import tts.project.handi.R;
import tts.project.handi.activity.InfoCompleteActivity;
import tts.project.handi.activity.MyCommentActivity;
import tts.project.handi.activity.MyIncomeActivity;
import tts.project.handi.activity.MyRecommendActivity;
import tts.project.handi.activity.SettingActivity;
import tts.project.handi.bean.SetTitleBean;
import tts.project.handi.bean.UserInfo;
import tts.project.handi.utils.MyAccountMoudle;


/**
 *
 *
 *
 */
public class PersonalCenterFragment extends BaseFragment {

    @Bind(R.id.img_personal_center_face)
    CircleImageView imgPersonalCenterFace;
    @Bind(R.id.tv_personal_center_name)
    TextView tvPersonalCenterName;
    @Bind(R.id.tv_personal_center_phone)
    TextView tvPersonalCenterPhone;
    @Bind(R.id.tv_personal_center_grab)
    TextView tvPersonalCenterGrab;
    @Bind(R.id.tv_personal_center_orders)
    TextView tvPersonalCenterOrders;
    @Bind(R.id.rl_info_completion)
    RelativeLayout rlInfoCompletion;
    @Bind(R.id.rl_personal_accout)
    RelativeLayout rlPersonalAccout;
    @Bind(R.id.rl_my_comment)
    RelativeLayout rlMyComment;
    @Bind(R.id.rl_my_recommend)
    RelativeLayout rlOffOnline;

    @Bind(R.id.rl_setting)
    RelativeLayout rlSetting;
//    @Bind(R.id.rl_brands_settings)
//    RelativeLayout rlBrandsSettings;
    private UserInfo userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ShareSDK.initSDK(getActivity());
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        startRequestData(getData);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void loginOk() {
        super.loginOk();
//        BaseApplication.getInstance().initUser(getActivity().getApplication());
        startRequestData(getData);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(new BarBean().setMsg("个人中心").setIsRemoveBack(true));
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
        }
    }


    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getData:
                if (!TextUtils.isEmpty(response)) {
                    Logger.d(response);
                    userInfo = JSON.parseObject(response, UserInfo.class);
                    response = response.substring(0, response.length() - 1) + ",\"login\": true}";
                    SharedPreferencesMoudle.getInstance().setJson(getActivity().getApplicationContext(), "user_login", response);
                    bindData(userInfo);
                }
                break;
        }
    }

    private void bindData(UserInfo userInfo) {
        try {
            tvPersonalCenterName.setText(userInfo.getNickname() + "");
            tvPersonalCenterPhone.setText(userInfo.getMobile() + "");
            tvPersonalCenterGrab.setText("抢单" + userInfo.getOrdersnumbers() + "次");
            tvPersonalCenterOrders.setText("接单" + userInfo.getSinglenumbers() + "次");
//            if (!TextUtils.isEmpty(userInfo.getAllevaluate())) {
//                int stars = Integer.parseInt(userInfo.getAllevaluate());
//                for (int i = 0; i < stars; i++) {
//                    imgs.get(i).setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.wjx_3x));
//                }
//            }
            //.placeholder(getActivity().getResources().getDrawable(R.mipmap.tx_2x))
            Glide.with(getActivity()).load(userInfo.getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).
                    into(imgPersonalCenterFace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onEventMainThread(SetTitleBean bean) {
        if (bean.isSet()) {
            setTitle(new BarBean().setMsg("个人中心").setIsRemoveBack(true));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.rl_info_completion, R.id.rl_personal_accout, R.id
            .rl_my_comment, R.id.rl_my_recommend, R.id.rl_setting})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.rl_brands_settings:
//                startActivity(new Intent(getActivity(), BrandsActivity.class));
//                break;
//            case R.id.rl_service_settings:
//                startActivity(new Intent(getActivity(), ServiceSettingActivity.class));
//                break;
            case R.id.rl_info_completion:
                startActivity(new Intent(getActivity(), InfoCompleteActivity.class));
                break;
            case R.id.rl_personal_accout:
                startActivity(new Intent(getActivity(), MyIncomeActivity.class));
                break;
            case R.id.rl_my_comment:
                startActivity(new Intent(getActivity(), MyCommentActivity.class));
                break;
            case R.id.rl_my_recommend:
                startActivity(new Intent(getActivity(), MyRecommendActivity.class));
                break;
//            case R.id.rl_about_us:
//                startActivity(new Intent(getActivity(), AboutActivity.class).putExtra("url", Constant.ABOUT_US).putExtra("title", "关于我们"));
//                break;
//            case R.id.rl_feedback:
//                startActivity(new Intent(getActivity(), FeedBackActivity.class));
//                break;
//            case R.id.rl_share:
//                showShare();
//                startActivity(new Intent(getActivity(), LocationActivity.class));
//                break;
            case R.id.rl_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }


}
