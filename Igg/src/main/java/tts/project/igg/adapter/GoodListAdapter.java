package tts.project.igg.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.project.igg.R;
import tts.project.igg.activity.goods.GoodsDetailActivity;
import tts.project.igg.bean.GoodsBean;
import tts.project.igg.utils.ImageLoader;

/**
 * Created by lenove on 2016/5/12.
 */
public class GoodListAdapter extends TTSBaseAdapterRecyclerView<GoodsBean> {
    private Context mContext;
    private List<GoodsBean> mData;

    public GoodListAdapter(Context context, List<GoodsBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods_view, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        GoodsViewHolder hotsViewHolder = (GoodsViewHolder) holder;
        hotsViewHolder.tv_description.setText(mData.get(position).getGoods_name());
        hotsViewHolder.tv_price.setText(mData.get(position).getGoods_price());
        hotsViewHolder.tv_sales.setText("0");
        if ("0".equals(mData.get(position).getIs_deduct_integral()))
            hotsViewHolder.tv_tags.setVisibility(View.GONE);
        //.placeholder(R.drawable.default_face)
//                Glide.with(mContext).load(mData.get(pos).getGoods_url()).diskCacheStrategy(DiskCacheStrategy.ALL).
//                        into(hotsViewHolder.img);
        ImageLoader.load(mContext, Host.hostUrl + mData.get(position).getGoods_img(), hotsViewHolder.img);
        hotsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("good_id",mData.get(position).getGoods_id()+""));
//                if (onHotsItemClickListener != null) {
//                    onHotsItemClickListener.onClick(v, position);
//                }
            }
        });
    }

    public class GoodsViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView tv_description;
        private TextView tv_sales;
        private TextView tv_price;
        private TextView tv_tags;
        private ImageView img;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_sales = (TextView) itemView.findViewById(R.id.tv_sales);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
