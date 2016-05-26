package chexin.project.tts.chexin.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;



import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.RouteBean;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.TextUtils;

public class CompanyDetailActivity extends BaseActivity {
    @Bind(R.id.img_company)
    ImageView imgCompany;
    @Bind(R.id.company_name)
    TextView companyName;

    @Bind(R.id.layout_license)
    LinearLayout layoutLicense;
    @Bind(R.id.tv_company_name)
    TextView tvCompanyName;
    @Bind(R.id.tv_company_address)
    TextView tvCompanyAddress;
    @Bind(R.id.tv_telephone1)
    TextView tvTelephone1;
    @Bind(R.id.tv_telephone2)
    TextView tvTelephone2;
    @Bind(R.id.tv_citys)
    TextView tvCitys;
    @Bind(R.id.special_citys)
    TextView specialCitys;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.layout_imgs1)
    LinearLayout layoutImgs1;
    @Bind(R.id.img3)
    ImageView img3;
    @Bind(R.id.img4)
    ImageView img4;
    @Bind(R.id.layout_imgs2)
    LinearLayout layoutImgs2;
    private RouteBean routeBean;

    private String TAG = "CompanyDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("公司详情"));
        routeBean = (RouteBean) getIntent().getSerializableExtra("data");

        if (routeBean != null) {
            bindData();
        }
    }

    private void bindData() {

        companyName.setText(routeBean.getCompany_name());
        Glide.with(this).load(routeBean.getCompany_logo()).placeholder(R.drawable.defa_gray).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgCompany);
        tvCompanyName.setText(routeBean.getCompany_name());
        tvCompanyAddress.setText(routeBean.getCompany_address());
        tvTelephone1.setText(routeBean.getFixed_telephone1());
        tvTelephone2.setText(routeBean.getFixed_telephone2());
        tvCitys.setText(routeBean.getCity_name());
        specialCitys.setText(routeBean.getFromProvince() + routeBean.getFromCity() + routeBean.getFromCountry()
                + "——" + routeBean.getToProvince() + routeBean.getToCity() + routeBean.getToCountry());
        if (TextUtils.isEmpty(routeBean.getCompany_img1()) && TextUtils.isEmpty(routeBean.getCompany_img2())) {
            layoutImgs1.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(routeBean.getCompany_img1())) {
                img1.setVisibility(View.VISIBLE);
                Glide.with(this).load(routeBean.getCompany_img1()).placeholder(R.drawable.defa_gray).diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(img1);
            }
            if (!TextUtils.isEmpty(routeBean.getCompany_img2()) && TextUtils.isEmpty(routeBean.getCompany_img1())) {
                img1.setVisibility(View.VISIBLE);
                Glide.with(this).load(routeBean.getCompany_img2()).placeholder(R.drawable.defa_gray).diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(img1);
            } else {
                img2.setVisibility(View.VISIBLE);
                Glide.with(this).load(routeBean.getCompany_img2()).placeholder(R.drawable.defa_gray).diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(img2);
            }

        }
        if (TextUtils.isEmpty(routeBean.getCompany_img3()) && TextUtils.isEmpty(routeBean.getCompany_img4())) {
            layoutImgs2.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(routeBean.getCompany_img3())) {
                img3.setVisibility(View.VISIBLE);
                Glide.with(this).load(routeBean.getCompany_img3()).placeholder(R.drawable.defa_gray).diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(img3);
            }
            if (!TextUtils.isEmpty(routeBean.getCompany_img4()) && TextUtils.isEmpty(routeBean.getCompany_img3())) {
                img3.setVisibility(View.VISIBLE);
                Glide.with(this).load(routeBean.getCompany_img4()).placeholder(R.drawable.defa_gray).diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(img3);
            } else {
                img4.setVisibility(View.VISIBLE);
                Glide.with(this).load(routeBean.getCompany_img4()).placeholder(R.drawable.defa_gray).diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(img4);
            }
        }
//        if (imgStrs.size() > 0) {
////            adapterList();
//        } else {
//            layoutLicense.setVisibility(View.GONE);
//        }
    }


}
