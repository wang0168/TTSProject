package chexin.project.tts.chexin.activity.carowner;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import chexin.project.tts.chexin.BaseActivity;
import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.MemberBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;

/**
 * 车主信息
 */
public class CarsOwnerInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_face)
    CircleImageView imgFace;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.ratingbar)
    RatingBar ratingbar;
    @Bind(R.id.tv_collect_number)
    TextView tvCollectNumber;
    @Bind(R.id.txt_collect)
    TextView txtCollect;
    @Bind(R.id.img_star)
    ImageView imgStar;
    @Bind(R.id.layout_collect)
    LinearLayout layoutCollect;
    //    @Bind(R.id.tv_browse_number)
//    TextView tvBrowseNumber;
//    @Bind(R.id.layout_browse)
//    LinearLayout layoutBrowse;
    @Bind(R.id.tv_cheyuan_number)
    TextView tvCheyuanNumber;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_car_code)
    TextView tvCarCode;
    @Bind(R.id.tv_route)
    TextView tvRoute;
    @Bind(R.id.tv_car_count)
    TextView tvCarCount;
    @Bind(R.id.tv_car_type)
    TextView tvCarType;
    @Bind(R.id.tv_car_long)
    TextView tvCarLong;
    @Bind(R.id.tv_car_width)
    TextView tvCarWidth;
    @Bind(R.id.tv_car_weight)
    TextView tvCarWeight;
    private MemberBean carOwnerBean;
    private final int collect = 1002;
    private final int cancleCollect = 1003;
    private boolean isCollect = false;
    private String collectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_owner_info);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("车主信息"));
        carOwnerBean = (MemberBean) getIntent().getSerializableExtra("data");
        if (carOwnerBean != null)
            bindData();
        if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
            layoutCollect.setVisibility(View.GONE);
//            layoutAction.setVisibility(View.GONE);
        } else {
            layoutCollect.setVisibility(View.VISIBLE);
        }
        findAllView();
    }

    private void findAllView() {
//        btnRequestGetOrder.setOnClickListener(this);
//        btnCalling.setOnClickListener(this);
        layoutCollect.setOnClickListener(this);
    }

    private void bindData() {
        layoutCollect.setVisibility(View.GONE);
        Glide.with(this).load(carOwnerBean.getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgFace);
        tvName.setText(carOwnerBean.getUserName());
//        tvType.setText("[车主]");
        tvCheyuanNumber.setText("");
        tvCarCode.setText(carOwnerBean.getCarNo());
        tvPhoneNumber.setText(carOwnerBean.getMobileNo());
        tvRoute.setText("");
        tvCarCount.setText("");
        tvCarType.setText(carOwnerBean.getCardType());
        tvCarLong.setText("");
        tvCarWidth.setText("");
        tvCarWeight.setText("");
        if (!TextUtils.isEmpty(carOwnerBean.getStar())) {
            ratingbar.setRating(Float.parseFloat(carOwnerBean.getStar()));
        }
        if ("1".equals(carOwnerBean.getIs_user_collection())) {
            txtCollect.setText("已收藏");
            imgStar.setImageResource(R.drawable.star_full);
            isCollect = true;
        } else {
            txtCollect.setText("收藏");
            imgStar.setImageResource(R.drawable.collect_star_null);
            isCollect = false;
        }
        tvCollectNumber.setText(carOwnerBean.getCollection_count());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_collect:
                if (isCollect) {
                    startRequestData(cancleCollect);
                } else {
                    startRequestData(collect);
                }
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {

            case collect:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("relation_id", carOwnerBean.getID() + "");
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    CustomUtils.showTipShort(this, "请先登录后再试");
                    return;
                } else if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                    params.put("collection_type", "1");
                }
                getDataWithPost(collect, Host.hostUrl + "collection.api?addCollection", params);
                break;
            case cancleCollect:
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    CustomUtils.showTipShort(this, "请先登录后再试");
                    return;
                }
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("collection_id", collectId + "");
                getDataWithPost(cancleCollect, Host.hostUrl + "collection.api?cancelCollection", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {

            case collect:
                collectId = response;
                CustomUtils.showTipShort(this, "收藏成功");
                txtCollect.setText("已收藏");
                imgStar.setImageResource(R.drawable.star_full);
                isCollect = true;
                break;
            case cancleCollect:
                CustomUtils.showTipShort(this, "取消收藏成功");
                isCollect = false;
                txtCollect.setText("收藏");
                imgStar.setImageResource(R.drawable.collect_star_null);
                break;
        }
    }
}
