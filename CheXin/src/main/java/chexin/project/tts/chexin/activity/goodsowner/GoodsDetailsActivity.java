package chexin.project.tts.chexin.activity.goodsowner;

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
import chexin.project.tts.chexin.bean.GoodsBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.bean.MenuItemBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.SystemUtils;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;

/**
 * 货源详情页
 */
public class GoodsDetailsActivity extends BaseActivity implements View.OnClickListener {
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
    @Bind(R.id.tv_from_place)
    TextView tvFromPlace;
    @Bind(R.id.tv_to_place)
    TextView tvToPlace;
    @Bind(R.id.tv_mileage)
    TextView tvMileage;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_goods_type)
    TextView tvGoodsType;
    @Bind(R.id.tv_goods_name)
    TextView tvGoodsName;
    @Bind(R.id.tv_car_type_require)
    TextView tvCarTypeRequire;
    @Bind(R.id.tv_car_weight_require)
    TextView tvCarWeightRequire;
    @Bind(R.id.tv_car_long_require)
    TextView tvCarLongRequire;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.tv_notes)
    TextView tvNotes;
    @Bind(R.id.btn_request_get_order)
    Button btnRequestGetOrder;
    @Bind(R.id.btn_calling)
    Button btnCalling;
    @Bind(R.id.layout_action)
    LinearLayout layoutAction;
    private GoodsBean goodsBean;
    private final int applyGoods = 1001;
    private final int collectGood = 1002;
    private final int collectUser = 1003;
    private boolean isUserCollect = false;
    private boolean isGoodCollect = false;
    private final int cancleCollectGood = 1004;
    private final int cancleCollectUser = 1005;
    private String userCollectId;
    private String goodCollectId;
    private MenuItemBean menuItemBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("货源详情"));
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("data");
        menuItemBean = new MenuItemBean().setTitle("收藏").setIcon(R.drawable.gradient);
        addMenu(menuItemBean);
        findAllView();
        if ("2".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
            layoutCollect.setVisibility(View.GONE);
            layoutAction.setVisibility(View.GONE);
        }
        if (goodsBean != null)
            bindData();
    }

    @Override
    protected void doMenu(MenuItemBean menuItemBean) {
        super.doMenu(menuItemBean);
        if (isGoodCollect) {
            startRequestData(cancleCollectGood);
        } else {
            startRequestData(collectGood);

        }
    }

    @Override
    public void doIcon() {
        super.doIcon();
        setResult(RESULT_OK);
    }

    private void bindData() {
        userCollectId = goodsBean.getUser_collection_id();
        goodCollectId = goodsBean.getGoods_collection_id();
        tvName.setText(goodsBean.getMemberBean().getName());
        tvType.setText("[货主]");
        tvCollectNumber.setText("");
        tvFromPlace.setText(goodsBean.getFromProvince() + goodsBean.getFromCity() + goodsBean.getFromCountry() + "");
        tvToPlace.setText(goodsBean.getToProvince() + goodsBean.getToCity() + goodsBean.getToCountry() + "");
        tvMileage.setText(goodsBean.getDistance());
        tvTime.setText(goodsBean.getAddDate());
        tvGoodsType.setText(goodsBean.getSKUType());
        tvGoodsName.setText(goodsBean.getSKUName());
        tvCarTypeRequire.setText("");
        if (!TextUtils.isEmpty(goodsBean.getMemberBean().getStar())) {
            ratingbar.setRating(Float.parseFloat(goodsBean.getMemberBean().getStar()));
        }
        tvCarWeightRequire.setText(goodsBean.getSKUWeight());
        tvCarLongRequire.setText(goodsBean.getSKULong());
        tvPhoneNumber.setText(goodsBean.getMobileNo());
        tvAddress.setText(goodsBean.getAddress());
        tvNotes.setText(goodsBean.getNote());
        tvCollectNumber.setText(goodsBean.getMemberBean().getCollection_count());
        if ("1".equals(goodsBean.getMemberBean().getIs_user_collection())) {
            txtCollect.setText("已收藏");
            imgStar.setImageResource(R.drawable.star_full);
            isUserCollect = true;
        } else {
            txtCollect.setText("收藏");
            imgStar.setImageResource(R.drawable.collect_star_null);
            isUserCollect = false;
        }
        //货源收藏
        if ("1".equals(goodsBean.getIs_goods_collection())) {
            menuItemBean.setTitle("取消收藏");
            isGoodCollect = true;
        } else {
            menuItemBean.setTitle("收藏");
            isGoodCollect = false;
        }
        removeMenu();
        addMenu(menuItemBean);
        Glide.with(this).load(goodsBean.getMemberBean().getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(imgFace);
    }

    private void findAllView() {
        imgFace.setOnClickListener(this);
        btnRequestGetOrder.setOnClickListener(this);
        btnCalling.setOnClickListener(this);
        layoutCollect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_calling:
                if (!TextUtils.isEmpty(goodsBean.getMobileNo().toString())) {
                    SystemUtils.callMobile(this, goodsBean.getMobileNo().toString());
                } else {
                    CustomUtils.showTipShort(this, "数据错误");
                }
                break;
            case R.id.img_face:
                startActivity(new Intent(this, GoodsOwnerInfoActivity.class).putExtra("data", goodsBean.getMemberBean()));
                break;
            case R.id.btn_request_get_order:
                startRequestData(applyGoods);
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
            case applyGoods:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken() + "");
                params.put("goods_id", goodsBean.getID() + "");
                params.put("username", MyAccountMoudle.getInstance().getUserInfo().getUsername() + "");
                params.put("pushalias", MyAccountMoudle.getInstance().getUserInfo().getPushalias() + "");
                getDataWithPost(applyGoods, Host.hostUrl + "goods.api?applyGoods", params);
                break;
            case collectUser:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("relation_id", goodsBean.getID() + "");
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    CustomUtils.showTipShort(this, "请先登录后再试");
                    return;
                } else if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                    params.put("collection_type", "3");
                }
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
            case collectGood:
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("relation_id", goodsBean.getID() + "");
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    CustomUtils.showTipShort(this, "请先登录后再试");
                    return;
                } else if ("1".equals(MyAccountMoudle.getInstance().getUserInfo().getUsertype())) {
                    params.put("collection_type", "4");
                }
                getDataWithPost(collectGood, Host.hostUrl + "collection.api?addCollection", params);
                break;
            case cancleCollectGood:
                if (TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getToken())) {
                    CustomUtils.showTipShort(this, "请先登录后再试");
                    return;
                }
                params = new ArrayMap<>();
                params.put("UserId", MyAccountMoudle.getInstance().getUserInfo().getId() + "");
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getToken());
                params.put("collection_id", goodCollectId + "");
                getDataWithPost(cancleCollectGood, Host.hostUrl + "collection.api?cancelCollection", params);
                break;
        }
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case applyGoods:
                CustomUtils.showTipShort(this, "申请成功");
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
                CustomUtils.showTipShort(this, "取消收藏成功");
                isUserCollect = false;
                txtCollect.setText("收藏");
                imgStar.setImageResource(R.drawable.collect_star_null);
                break;
            case collectGood:
                goodCollectId = response;
                CustomUtils.showTipShort(this, "收藏成功");
                menuItemBean.setTitle("取消收藏");
                removeMenu();
                addMenu(menuItemBean);
                isGoodCollect = true;
                break;
            case cancleCollectGood:
                CustomUtils.showTipShort(this, "取消收藏成功");
                menuItemBean.setTitle("收藏");
                removeMenu();
                addMenu(menuItemBean);
                isGoodCollect = false;
                break;
        }
    }
}
