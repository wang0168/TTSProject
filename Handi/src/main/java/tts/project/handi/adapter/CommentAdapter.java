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

import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;
import tts.project.handi.R;
import tts.project.handi.bean.CommentContext;

/**
 * Created by chen on 2016/3/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentAdapterViewHolder> {
    private Context mContext;
    private List<CommentContext> mData;

    public CommentAdapter(Context context, List<CommentContext> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public CommentAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, null));
    }

    @Override
    public void onBindViewHolder(CommentAdapterViewHolder holder, int position) {
        int realPos=position-1;
        if (!TextUtils.isEmpty(mData.get(realPos).getLevel())) {
            int level = Integer.parseInt(mData.get(realPos).getLevel());
            String txt="";
            switch (level) {
                case 0:
                    txt="非常满意";
                    break;
                case 1:
                    txt="满意";
                    break;
                case 2:
                    txt="不满意";
                    break;
            }
            holder.tvCommentGrade.setText(txt);
        }
        holder.commentusername.setText(mData.get(realPos).getNickname());
        holder.tvCommentContext.setText(mData.get(realPos).getEvaluatecot());
        Glide.with(mContext).load(mData.get(realPos).getImg()).placeholder(R.mipmap.tx_2x).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(holder.commentFace);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class CommentAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCommentGrade;
        private TextView tvCommentContext;
        private TextView commentusername;
        private CircleImageView commentFace;

        public CommentAdapterViewHolder(View itemView) {
            super(itemView);
            tvCommentGrade = (TextView) itemView.findViewById(R.id.tv_comment_grade);
            tvCommentContext = (TextView) itemView.findViewById(R.id.tv_comment_context);
            commentusername = (TextView) itemView.findViewById(R.id.comment_username);
            commentFace = (CircleImageView) itemView.findViewById(R.id.comment_face);
        }
    }
}
