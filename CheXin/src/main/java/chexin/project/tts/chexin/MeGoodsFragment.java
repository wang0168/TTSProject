package chexin.project.tts.chexin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.activity.AssessmentListActivity;
import chexin.project.tts.chexin.activity.ModifyInfoActivity;
import chexin.project.tts.chexin.activity.SettingActivity;
import chexin.project.tts.chexin.activity.TrueNameActivity;
import chexin.project.tts.chexin.activity.goodsowner.GoodsOrdersListActivity;
import chexin.project.tts.chexin.activity.goodsowner.GoodsTransactionOrderFragment;
import chexin.project.tts.chexin.activity.goodsowner.MyCollectionActivity;
import chexin.project.tts.chexin.adapter.PublicStyleAdapter;
import chexin.project.tts.chexin.bean.PublicMenuBean;
import chexin.project.tts.chexin.bean.UserInfoBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.social.SocialLoginMoudle;
import tts.moudle.api.social.SocialShareMoudle;
import tts.moudle.api.widget.CircleImageView;
import tts.moudle.api.widget.RecyclerViewAutoRefreshUpgraded;

/**
 * 我的（货主）
 * Created by sjb on 2016/3/23.
 */
public class MeGoodsFragment extends BaseFragment {
    private RecyclerViewAutoRefreshUpgraded menuList;
    private PublicStyleAdapter publicStyleAdapter;
    private View headaView;
    private List<PublicMenuBean> publicMenuBeans;
    private CircleImageView headerImg;
    private TextView header_name;
    private TextView header_phone_number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.me_goods_fragment, inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(new BarBean().setMsg("个人中心").setIsRemoveBack(true));
        findAllView();
        adapterMenu();
    }


    private void adapterMenu() {
        publicMenuBeans = new ArrayList<PublicMenuBean>();
        publicMenuBeans.add(new PublicMenuBean().setId("1").setName("我的订单").setIconId(R.drawable.me_order).setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("2").setName("我的货源").setIconId(R.drawable.me_goods));
        publicMenuBeans.add(new PublicMenuBean().setId("3").setName("我的收藏").setIconId(R.drawable.menu_my_collecction).setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("4").setName("我的评价").setIconId(R.drawable.me_assessment));
        publicMenuBeans.add(new PublicMenuBean().setId("5").setName("购买保险").setIconId(R.drawable.me_insurance).setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("6").setName("货运贷款").setIconId(R.drawable.me_loan));
        publicMenuBeans.add(new PublicMenuBean().setId("7").setName("推动消息").setIconId(R.drawable.me_push).setIsMargin(true).setIsLine(true).setIconRightId(R.drawable.push_button));
        publicMenuBeans.add(new PublicMenuBean().setId("8").setName("系统设置").setIconId(R.drawable.me_setting));
        publicMenuBeans.add(new PublicMenuBean().setId("9").setName("实名认证").setIconId(R.drawable.me_certification).setIsMargin(true).setIsLine(true));
        publicMenuBeans.add(new PublicMenuBean().setId("10").setName("分享").setIconId(R.drawable.me_share));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        menuList.setLayoutManager(layoutManager);
        publicStyleAdapter = new PublicStyleAdapter(getActivity(), publicMenuBeans);
        menuList.addHeader(headaView);
        menuList.setAdapter(publicStyleAdapter);
        publicStyleAdapter.setOnItemClickListener(new TTSBaseAdapterRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View itemView, int position) {
                if ("1".equals(publicMenuBeans.get(position).getId())) {
                    startActivityForResult(new Intent(getActivity(), GoodsOrdersListActivity.class), 2001);
                }
                if ("3".equals(publicMenuBeans.get(position).getId())) {
                    startActivityForResult(new Intent(getActivity(), MyCollectionActivity.class), 2003);
                }
                if ("4".equals(publicMenuBeans.get(position).getId())) {
                    startActivityForResult(new Intent(getActivity(), AssessmentListActivity.class), 2001);
                }
                if ("10".equals(publicMenuBeans.get(position).getId())) {
                    SocialShareMoudle.getInstance().showShareUrl(getActivity(), headaView, "https://www.baidu.com/", "车新物流", "真实的货源信息", "", null);
                }
                if ("8".equals(publicMenuBeans.get(position).getId())) {
                    startActivityForResult(new Intent(getActivity(), SettingActivity.class), 2008);
                }
                if ("9".equals(publicMenuBeans.get(position).getId())) {
                    startActivityForResult(new Intent(getActivity(), TrueNameActivity.class), 2009);
                }
            }

            @Override
            public void onLongClick(View itemView, int position) {

            }
        });
        publicStyleAdapter.setOnButtonClickListener(new PublicStyleAdapter.OnButtonClickListener() {
            @Override
            public void onClick(View v, int position) {
                if (publicMenuBeans.get(position).getId().equals("7")) {
                   /* if(v.getTag()==null||v.getTag().toString().equals("1")){
                        v.setTag("2");
                        v.setPressed(true);
                    }else{
                        v.setTag("1");
                        v.setPressed(false);
                    }*/
                }
            }
        });
    }

    private void findAllView() {
        headaView = View.inflate(getActivity(), R.layout.me_header_item, null);
        headerImg = (CircleImageView) headaView.findViewById(R.id.headImg);
        header_name = (TextView) headaView.findViewById(R.id.header_name);
        header_phone_number = (TextView) headaView.findViewById(R.id.header_phone_number);
        bindData();
        headaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ModifyInfoActivity.class).putExtra("from", "2"));
            }
        });
        menuList = (RecyclerViewAutoRefreshUpgraded) rootView.findViewById(R.id.menuList1);
    }

    UserInfoBean userInfoBean;

    private void bindData() {
        userInfoBean = MyAccountMoudle.getInstance().getUserInfo();
        header_name.setText(userInfoBean.getUsername());
        header_phone_number.setText(userInfoBean.getMobileno());
        Glide.with(this).load(userInfoBean.getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(headerImg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (SocialLoginMoudle.getInstance().mTencent != null) {
            SocialLoginMoudle.getInstance().mTencent.onActivityResultData(requestCode, resultCode, data, SocialLoginMoudle.getInstance().baseUiListener);
        }
    }
}
