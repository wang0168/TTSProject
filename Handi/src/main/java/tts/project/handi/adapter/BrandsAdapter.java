package tts.project.handi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.widget.CircleImageView;
import tts.project.handi.R;
import tts.project.handi.bean.BrandsBean;
import tts.project.handi.myInterface.OnItemClickLitener;

/**
 * Created by chen on 2016/3/16.
 */
public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.RecommendViewHolder> {
    private Context mContext;
    private List<BrandsBean> mData;
    private OnItemClickLitener listener;

    public BrandsAdapter(Context context, List<BrandsBean> data) {
        mContext = context;
        mData = data;
    }

    public void setOnItemClickListener(OnItemClickLitener listener) {
        this.listener = listener;
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_recommend, null, false));
    }

    @Override
    public void onBindViewHolder(final RecommendViewHolder holder, final int position) {
        int realPos = position - 1;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.itemView, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onItemLongClick(holder.itemView, position);
                }
                return true;
            }
        });
        holder.tvName.setText(mData.get(realPos).getPinkname());
        holder.imgFace.setBackgroundResource(R.mipmap.wdtj_3x);
//        Glide.with(mContext).load(mData.get(realPos).getImg()).placeholder(R.mipmap.tx_2x).diskCacheStrategy(DiskCacheStrategy.ALL).
//                into(holder.imgFace);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPhone;
        private CircleImageView imgFace;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            imgFace = (CircleImageView) itemView.findViewById(R.id.img_face);
        }
    }
}
