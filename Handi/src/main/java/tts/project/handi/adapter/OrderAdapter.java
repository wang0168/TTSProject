package tts.project.handi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.Map;

import tts.moudle.api.activity.AboutActivity;
import tts.moudle.api.utils.TextUtils;
import tts.project.handi.R;
import tts.project.handi.bean.Order;
import tts.project.handi.myInterface.OnItemClickLitener;

/**
 * Created by chen on 2016/3/1.
 * <p/>
 * 订单列表适配器（可添加头部）
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context mContext;
    private List<Order> mData;
    private OnItemClickLitener mOnItemClickLitener;
    private OnActionClickListener mOnActionClickListener;
    private boolean isAction = true;
    private int actionStatus = 1;

    public OrderAdapter(Context context, List<Order> data) {
        mContext = context;
        mData = data;
    }

    //
    public void setAction(boolean b) {
        isAction = b;
    }


    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_orders, null,
                false));
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {

        //获取真实position
        final int pos = getRealPosition(holder);
        // recycleview Item 点击事件回调 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
        if (mData.get(pos).getOrdersimg() != null) {
            int count = mData.get(pos).getOrdersimg().size();
            holder.layoutImgs.removeAllViews();
            if (count > 0) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                layoutParams.setMargins(4, 4, 4, 4);
                for (int i = 0; i < count; i++) {
                    ImageView imageView = new ImageView(mContext);
                    Glide.with(mContext).load(mData.get(pos).getOrdersimg().get(i)).diskCacheStrategy(DiskCacheStrategy.ALL).
                            into(imageView);
                    imageView.setLayoutParams(layoutParams);
                    holder.layoutImgs.addView(imageView);
                }
            } else {
                holder.layoutImgs.setVisibility(View.GONE);
            }
        } else {
            holder.layoutImgs.setVisibility(View.GONE);
        }

        holder.TvServiceType.setText(mData.get(pos).getServicenames());
        holder.TvServiceDescription.setText(mData.get(pos).getServicecoutext());
        holder.TvServiceTime.setText(mData.get(pos).getServicetime());
        holder.TvServiceAddress.setText(mData.get(pos).getProvince() + mData.get(pos).getCity() +
                mData.get(pos).getArea() + mData.get(pos).getServaddress());
        holder.TvServiceCost.setText("￥" + mData.get(pos).getMoney());
        if ("1".equals(mData.get(pos).getYesno())) {
            holder.TvServiceAffixation.setVisibility(View.VISIBLE);
        } else {
            holder.TvServiceAffixation.setVisibility(View.GONE);
        }
        if (!isAction) {
            holder.TvServiceAffixation.setVisibility(View.GONE);
//            holder.BtnServiceGrab.setVisibility(View.GONE);
            String orderStatus = mData.get(pos).getOrdersstate();
            String orderStatusStr = "";
            if (!TextUtils.isEmpty(orderStatus)) {
                switch (orderStatus) {
                    case "1":
                        actionStatus = 2;
                        orderStatusStr = "取消订单";
                        holder.BtnServiceGrab.setText(orderStatusStr);
                        break;
                    case "2":
                        actionStatus = 3;
                        orderStatusStr = "完成服务";
                        holder.BtnServiceGrab.setText(orderStatusStr);
                        break;
                    case "3":
                        actionStatus = 4;
                        holder.BtnServiceGrab.setClickable(false);
                        orderStatusStr = "等待确认";
                        holder.BtnServiceGrab.setText(orderStatusStr);
                        holder.BtnServiceGrab.setBackgroundColor(mContext.getResources().getColor(R.color.grayFontsColor));
                        break;
                    case "4":
                        actionStatus = 5;
                        orderStatusStr = "已完成";
                        holder.BtnServiceGrab.setClickable(false);
                        holder.BtnServiceGrab.setText(orderStatusStr);
                        holder.BtnServiceGrab.setBackgroundColor(mContext.getResources().getColor(R.color.grayFontsColor));
                        break;
                }
            }
        } else {
            actionStatus = 1;
        }

        if (mOnActionClickListener != null) {
            holder.BtnServiceGrab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> param = new ArrayMap<>();
                    if ("0".equals(mData.get(pos).getOrderstype())) {
                        param.put("orderstype", "0");
                    } else {
                        param.put("orderstype", "memberid");
                    }
                    param.put("ordersnumber", mData.get(pos).getOrdersnumber());
                    mOnActionClickListener.doAction(actionStatus, param);
                }
            });
        }
        holder.TvNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AboutActivity.class);
                intent.putExtra("title", "导航").putExtra("url", "http://handi.tstmobile.com/wap.php?m=Wap&c=Index&a=amap&lng=" + mData.get(pos).getL() + "&lat=" + mData.get(pos).getB());
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 获取除头部外真正的下标
     *
     * @param holder
     * @return real position
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position - 1;

    }

    /**
     * 订单列表Item 的ViewHolder
     */
    class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView TvServiceType;
        private TextView TvServiceDescription;
        private TextView TvServiceTime;
        private TextView TvServiceAddress;
        private TextView TvServiceCost;
        private TextView TvServiceAffixation;
        private TextView TvNavigation;
        private Button BtnServiceGrab;
        private LinearLayout layoutImgs;

        public OrderViewHolder(View itemView) {
            super(itemView);
            TvServiceType = (TextView) itemView.findViewById(R.id.tv_service_type);
            TvServiceDescription = (TextView) itemView.findViewById(R.id.tv_service_description);
            TvServiceTime = (TextView) itemView.findViewById(R.id.tv_service_time);
            TvServiceAddress = (TextView) itemView.findViewById(R.id.tv_service_address);
            TvServiceCost = (TextView) itemView.findViewById(R.id.tv_service_cost);
            TvServiceAffixation = (TextView) itemView.findViewById(R.id.tv_service_affixation);
            TvNavigation = (TextView) itemView.findViewById(R.id.tv_navigation);
            BtnServiceGrab = (Button) itemView.findViewById(R.id.btn_service_grab);
            layoutImgs = (LinearLayout) itemView.findViewById(R.id.layout_img);
        }
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public interface OnActionClickListener {
        void doAction(int index, Map<String, String> param);
    }

}
