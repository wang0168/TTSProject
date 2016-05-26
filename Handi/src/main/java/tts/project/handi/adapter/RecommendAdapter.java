package tts.project.handi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import tts.moudle.api.widget.CircleImageView;
import tts.project.handi.R;
import tts.project.handi.bean.MyRecommend;

/**
 * Created by chen on 2016/3/16.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private Context mContext;
    private List<MyRecommend> mData;

    public RecommendAdapter(Context context, List<MyRecommend> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_recommend, null, false));
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        int realPos = position - 1;
        holder.tvName.setText(mData.get(realPos).getNickname());
        holder.tvPhone.setText(mData.get(realPos).getMobile());
        Glide.with(mContext).load(mData.get(realPos).getImg()).placeholder(R.mipmap.tx_2x).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(holder.imgFace);
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
