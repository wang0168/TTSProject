package chexin.project.tts.chexin.activity.carowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.Button;
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
import chexin.project.tts.chexin.bean.CarBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;

/**
 * 车辆详情
 */
public class CarDetailsActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.img_face)
    CircleImageView imgFace;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_type)
    TextView tvType;
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
    @Bind(R.id.ratingbar)
    RatingBar ratingbar;
    @Bind(R.id.tv_origin)
    TextView tvOrigin;
    @Bind(R.id.tv_destination)
    TextView tvDestination;
    @Bind(R.id.tv_mileage)
    TextView tvMileage;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.btn_request_go)
    Button btnRequestGo;
    @Bind(R.id.btn_calling)
    Button btnCalling;
    @Bind(R.id.tv_car_number)
    TextView tvCarNumber;
    @Bind(R.id.tv_car_type)
    TextView tvCarType;
    @Bind(R.id.tv_car_long)
    TextView tvCarLong;
    @Bind(R.id.tv_car_width)
    TextView tvCarWidth;
    @Bind(R.id.tv_car_weight)
    TextView tvCarWeight;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_notes)
    TextView tvNotes;
    @Bind(R.id.layout_btns)
    LinearLayout layoutBtns;

    private CarBean carBean;
    private final int go = 1001;
    private final int collectCar = 1002;
    private final int collectUser = 1003;
    private boolean isUserCollect = false;
    private boolean isCarCollect = false;
    private final int cancleCollectCar = 1004;
    private final int cancleCollectUser = 1005;
    private String userCollectId;
    private String carCollectId;
    private MenuItemBean menuItemBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_details);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("车源详情"));
        carBean = (CarBean) getIntent().getSerializableExtra("data");
        menuItemBean = new MenuItemBean().setTitle("收藏").setIcon(R.drawable.gradient);
        addMenu(menuItemBean);
        findAllView();
        if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
            layoutCollect.setVisibility(View.GONE);
            layoutBtns.setVisibility(View.GONE);
        }
        bindData();
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        if (isCarCollect) {
            startRequestData(cancleCollectCar);
        } else {
            startRequestData(collectCar);
        }
    }

    @Override
    public void doIcon() {
        super.doIcon();
        setResult(RESULT_OK);
    }

    private void bindData() {
        userCollectId = carBean.getUser_collection_id();
        carCollectId = carBean.getCar_collection_id();
        tvName.setText(carBean.getMemberBean().getName());
        tvType.setText("[车主]");
        tvOrigin.setText(carBean.getFromProvince() + carBean.getFromCity() + carBean.getFromCountry() + "");
        tvDestination.setText(carBean.getToProvince() + carBean.getToCity() + carBean.getToCountry() + "");
        tvMileage.setText(carBean.getDistance());
        tvTime.setText(carBean.getAddDate());
        tvCollectNumber.setText(carBean.getMemberBean().getCollection_count());
        tvCarNumber.setText(carBean.getCarCode());
        tvCarType.setText(carBean.getCarType());
        tvCarLong.setText(carBean.getCarLength());
        tvCarWidth.setText(carBean.getCarWidth());
        tvCarWeight.setText(carBean.getCarWeight());
        tvPhoneNumber.setText(carBean.getMobileNo());
        tvAddress.setText(carBean.getAddress());
        tvNotes.setText(carBean.getNote());
        if (!TextUtils.isEmpty(carBean.getMemberBean().getStar())) {
            ratingbar.setRating(Float.parseFloat(carBean.getMemberBean().getStar()));
        }

        //车主收藏
        if ("1".equals(carBean.getIs_user_collection())) {
            txtCollect.setText("取消收藏");
            imgStar.setImageResource(R.drawable.star_full);
            isUserCollect = true;
        } else {
            txtCollect.setText("收藏");
            imgStar.setImageResource(R.drawable.collect_star_null);
            isUserCollect = false;
        }
        //车源收藏
        if ("1".equals(carBean.getIs_car_collection())) {
            menuItemBean.setTitle("取消收藏");
            isCarCollect = true;
        } else {
            menuItemBean.setTitle("收藏");
            isCarCollect = false;
        }

        Glide.with(this).load(carBean.getMemberBean().getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgFace);
    }

    private void findAllView() {
        btnRequestGo.setOnClickListener(this);
        btnCalling.setOnClickListener(this);
        imgFace.setOnClickListener(this);
        layoutCollect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_face:
                startActivity(new Intent(this, CarsOwnerInfoActivity.class).putExtra("data", carBean.getMemberBean()));
                break;
            case R.id.btn_request_go:
                startRequestData(go);
                break;
            case R.id.btn_calling:
                startActivity(new Intent(this, CarsOwnerInfoActivity.class).putExtra("data", carBean.getMemberBean()));
                break;
            case R.id.layout_collect:
                if (isUserCollect) {
                    startRequestData(cancleCollectUser);
                } else {
                    startRequestData(collectUser);
                }
                break;
        }
    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case go:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("relation_id", carBean.getID() + "");
                params.put("username", MyAccountMoudle.getInstance().getUserInfo().getUsername() + "");
                params.put("pushalias", MyAccountMoudle.getInstance().getUserInfo().getPushalias()+ "");
                getDataWithPost(go, Host.hostUrl + "car.api?applyCar", params);
                break;
            case collectCar:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("relation_id", carBean.getID() + "");
                params.put("collection_type", "2");
                getDataWithPost(collectCar, Host.hostUrl + "collection.api?addCollection", params);
                break;
            case collectUser:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("relation_id", carBean.getID() + "");
                params.put("collection_type", "1");
                getDataWithPost(collectUser, Host.hostUrl + "collection.api?addCollection", params);
                break;
            case cancleCollectUser:
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    CustomUtils.showTipShort(this, "请先登录后再试");
                    return;
                }
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("collection_id", userCollectId + "");
                getDataWithPost(cancleCollectUser, Host.hostUrl + "collection.api?cancelCollection", params);
                break;
            case cancleCollectCar:
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    CustomUtils.showTipShort(this, "请先登录后再试");
                    return;
                }
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("collection_id", carCollectId + "");
                getDataWithPost(cancleCollectCar, Host.hostUrl + "collection.api?cancelCollection", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case go:
                CustomUtils.showTipShort(this, "申请成功");
                setResult(RESULT_OK);
                finish();
                break;
            case collectUser:
                userCollectId = response;
                CustomUtils.showTipShort(this, "收藏成功");
                txtCollect.setText("已收藏");
                imgStar.setImageResource(R.drawable.star_full);
                isUserCollect = true;
                break;
            case cancleCollectUser:
                txtCollect.setText("收藏");
                imgStar.setImageResource(R.drawable.collect_star_null);
                CustomUtils.showTipShort(this, "取消收藏成功");
                isUserCollect = false;
                break;
            case collectCar:
                carCollectId = response;
                CustomUtils.showTipShort(this, "收藏成功");
                menuItemBean.setTitle("取消收藏");
                isCarCollect = true;
                break;
            case cancleCollectCar:
                CustomUtils.showTipShort(this, "取消收藏成功");
                menuItemBean.setTitle("收藏");
                isCarCollect = false;
                break;
        }
    }
}
