package tts.project.handi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.utils.SystemUtils;
import tts.moudle.api.utils.TextUtils;
import tts.project.handi.BaseActivity;
import tts.project.handi.MainActivity;
import tts.project.handi.R;
import tts.project.handi.bean.Order;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.tv_order_number)
    TextView tvOrderNumber;
    @Bind(R.id.tv_order_status)
    TextView tvOrderStatus;
    @Bind(R.id.tv_order_type)
    TextView tvOrderType;
    @Bind(R.id.tv_order_description)
    TextView tvOrderDescription;
    @Bind(R.id.tv_service_time)
    TextView tvServiceTime;
    @Bind(R.id.tv_service_address)
    TextView tvServiceAddress;
    @Bind(R.id.item_rl_address)
    RelativeLayout itemRlAddress;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.tv_owner_name)
    TextView tvOwnerName;
    @Bind(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @Bind(R.id.layout_owner_info)
    RelativeLayout layoutOwnerInfo;
    @Bind(R.id.tv_shop_name)
    TextView tvShopName;
    @Bind(R.id.tv_shop_phone_number)
    TextView tvShopPhoneNumber;
    @Bind(R.id.tv_shop_address)
    TextView tvShopAddress;
    @Bind(R.id.tv_shop_remark)
    TextView tvShopRemark;
    @Bind(R.id.layout_store_info)
    RelativeLayout layoutStoreInfo;
    @Bind(R.id.tv_worker_name)
    TextView tvWorkerName;
    @Bind(R.id.tv_worker_number)
    TextView tvWorkerNumber;
    TextView tvArtisanPhoneNumber;
    @Bind(R.id.layout_worker_info)
    RelativeLayout layoutArtisanInfo;
    @Bind(R.id.btn_comment_tag)
    TextView btnCommentTag;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.layout_comment)
    RelativeLayout layoutComment;
//    @Bind(R.id.btn_closing)
//    Button btnClosing;

    @Bind(R.id.bottom_bar)
    RelativeLayout bottomBar;
    @Bind(R.id.btn_action)
    Button btnAction;
    @Bind(R.id.tv_navigation)
    TextView tvNavigation;
    @Bind(R.id.call_owner)
    ImageButton callOwner;
    @Bind(R.id.call_store)
    ImageButton callStore;
    @Bind(R.id.call_worker)
    ImageButton callWorker;
    @Bind(R.id.layout_img)
    LinearLayout layoutImgs;

    private Order order;
    private String from;
    private final int getOrder = 101;
    private String orderTypeStr = "";
    private int orderSatus = 0;
    private String orderSatusStr = "";
    private int btnType = 0;//按钮下一步操作标识


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("订单详情"));
        order = (Order) getIntent().getSerializableExtra("order");
        from = getIntent().getStringExtra("from");
        bindData(order);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strStutas = MyAccountMoudle.getInstance().getUserInfo().getCardyn();
                if ("4".equals(strStutas)) {
                    new AlertDialog.Builder(OrderDetailsActivity.this)
                            .setMessage("您的账户已被下架，请联系客服")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    switch (btnType) {
                        case 0:
                            break;
                        case 1:
                            startRequestData(getOrder);
                            break;
                        case 2:
                            startRequestData(getOrder);
                            break;
                        case 3:
                            break;
                        case 4:
                            startRequestData(4);
                            break;
                        case 5:
                            startRequestData(5);
                            break;
                        case 6:
                            break;
                    }
                }
            }
        });
        callOwner.setOnClickListener(this);
        callStore.setOnClickListener(this);
        callWorker.setOnClickListener(this);
        tvNavigation.setOnClickListener(this);
    }

    public void bindData(Order order) {
        final String[] serviceTypes;
        tvOrderNumber.setText(order.getOrdersnumber());
        if (!TextUtils.isEmpty(order.getServicenames())) {
            serviceTypes = order.getServicenames().split(",");
            String typeStr = "";
            for (int i = 0; i < serviceTypes.length; i++) {
                typeStr = serviceTypes[i] + " ";
            }
            tvOrderType.setText(typeStr.replaceAll(",", ""));
        }
        tvOrderDescription.setText(order.getServicecoutext());
        tvServiceTime.setText(order.getServicetime());
        tvServiceAddress.setText(order.getProvince() + order.getCity() + order.getArea() + order.getServaddress());
        tvPay.setText(order.getMoney());
        if (!TextUtils.isEmpty(order.getPersonaltype()) && "0".equals(order.getPersonaltype())) {
            layoutStoreInfo.setVisibility(View.VISIBLE);
            tvShopName.setText(order.getSjname());
            tvShopPhoneNumber.setText(order.getSjphone());
            tvShopAddress.setText(order.getSjaddress());
            tvShopRemark.setText(order.getMark());
        } else {
            layoutStoreInfo.setVisibility(View.GONE);
        }
        int count = order.getOrdersimg().size();
        if (count > 0) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            layoutParams.setMargins(4, 4, 4, 4);
            for (int i = 0; i < count; i++) {
                ImageView imageView = new ImageView(this);
                Glide.with(this).load(order.getOrdersimg().get(i)).diskCacheStrategy(DiskCacheStrategy.ALL).
                        into(imageView);
                imageView.setLayoutParams(layoutParams);
                layoutImgs.addView(imageView);
            }
        } else {
            layoutImgs.setVisibility(View.GONE);
        }
        tvOwnerName.setText(order.getYzname());
        tvPhoneNumber.setText(order.getYzphone());
        tvOwnerName.setText(order.getYzname());
        tvWorkerName.setText("");
        tvWorkerNumber.setText("");

        try {
            if ("home".equals(from)) {
                if ("0".equals(order.getOrderstype())) {
                    btnAction.setText("抢单");
                    orderTypeStr = "0";
                    btnType = 1;
                } else {
                    btnAction.setText("接单");
                    orderTypeStr = MyAccountMoudle.getInstance().getUserInfo().getMember_id();//师父ID
                    btnType = 2;
                }
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startRequestData(getOrder);
                    }
                });
//                layoutStoreInfo.setVisibility(View.GONE);
                layoutArtisanInfo.setVisibility(View.GONE);

            } else if ("done".equals(from)) {
                bottomBar.setVisibility(View.GONE);
//                layoutComment.setVisibility(View.VISIBLE);
//                btnCommentTag.setText(order.get);

            } else {
//                if ("1".equals(order.getYesno())) {
//                    layoutStoreInfo.setVisibility(View.VISIBLE);
//                } else {
//                    layoutStoreInfo.setVisibility(View.GONE);
//                }
                layoutArtisanInfo.setVisibility(View.VISIBLE);
                orderSatus = Integer.parseInt(order.getOrdersstate());
                switch (orderSatus) {
                    case 0:
                        btnType = 3;
                        btnAction.setText("已取消订单");
                        btnAction.setClickable(false);
                        btnAction.setBackgroundColor(getResources().getColor(R.color.gray_normal));
                        orderSatusStr = "待付款";
                        break;
                    case 1:
                        btnType = 4;
                        btnAction.setText("取消订单");
                        orderSatusStr = "待服务";
                        break;
                    case 2:
                        btnType = 5;
                        btnAction.setText("完成服务");
                        orderSatusStr = "待确认";
                        break;
                    case 3:
                        btnType = 6;
                        btnAction.setText("等待确认");
                        btnAction.setClickable(false);
                        btnAction.setBackgroundColor(getResources().getColor(R.color.gray_normal));
                        orderSatusStr = "已完成";
                        break;
                }
                tvOrderStatus.setText(orderSatusStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> param;
        switch (index) {
            case getOrder:
                param = new ArrayMap<>();
                param.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                param.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                param.put("orderstype", orderTypeStr);
                param.put("ordersnumber", order.getOrdersnumber());
                getDataWithPost(getOrder, Host.hostUrl + "api.php?m=Api&c=Orders&a=jieqD", param);
                break;
            case 4:
                param = new ArrayMap<>();
                param.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                param.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                param.put("state", "6");
                param.put("ordersnumber", order.getOrdersnumber());
                getDataWithPost(4, Host.hostUrl + "api.php?m=Api&c=Orders&a=ordersCot", param);
                break;
            case 5:
                param = new ArrayMap<>();
                param.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                param.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                param.put("state", "7");
                param.put("ordersnumber", order.getOrdersnumber());
                getDataWithPost(5, Host.hostUrl + "api.php?m=Api&c=Orders&a=ordersCot", param);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        switch (index) {
            case getOrder:
                CustomUtils.showTipShort(this, response);
                finish();
                break;
            case 4:
                CustomUtils.showTipShort(this, "取消成功");
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("select", 1);
                startActivity(intent);
                finish();
                break;
            case 5:
                CustomUtils.showTipShort(this, response);
                finish();
                break;
        }
    }

    @Override
    protected void doFailed(int what, int index, String response) {
        super.doFailed(what, index, response);
        CustomUtils.showTipShort(this, response);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_owner:
                Log.d("<<<<<<<<<<<", "");
                if (!TextUtils.isEmpty(tvPhoneNumber.getText().toString())) {
                    SystemUtils.callMobile(this, tvPhoneNumber.getText().toString());
                }
                break;
            case R.id.call_store:
                Log.d("<<<<<<<<<<<", "222222");
                if (!TextUtils.isEmpty(tvShopPhoneNumber.getText().toString())) {
                    SystemUtils.callMobile(this, tvShopPhoneNumber.getText().toString());
                } else {
                    CustomUtils.showTipShort(this, "数据错误");
                }
                break;
            case R.id.call_worker:
                Log.d("<<<<<<<<<<<", "222222");
                if (!TextUtils.isEmpty(tvWorkerNumber.getText().toString())) {
                    SystemUtils.callMobile(this, tvWorkerNumber.getText().toString());
                } else {
                    CustomUtils.showTipShort(this, "数据错误");
                }
                break;
            case R.id.tv_navigation:
                Intent intent = new Intent(this, AboutActivity.class);
                intent.putExtra("title", "导航").putExtra("url", "http://handi.tstmobile.com/wap.php?m=Wap&c=Index&a=amap&lng=121.34&lat=31.12");
                startActivity(intent);
                break;
        }
    }
}
