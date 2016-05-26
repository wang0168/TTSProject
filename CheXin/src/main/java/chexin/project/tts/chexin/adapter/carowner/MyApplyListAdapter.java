package chexin.project.tts.chexin.adapter.carowner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.ApplyBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/28.
 */
public class MyApplyListAdapter extends TTSBaseAdapterRecyclerView<ApplyBean> {
    private List<ApplyBean> data;
    private Context context;
    private boolean isBtn;
    private int from;//车主1；货主2


    public MyApplyListAdapter(Context context, List<ApplyBean> data, int from) {
        super(context, data);
        this.context = context;
        this.data = data;
        this.from = from;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_apply, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (from == 1) {
            viewHolder.name.setText(data.get(position).getMemberBean().getUserName());
            viewHolder.tvTime.setText(data.get(position).getCreate_time());
            viewHolder.btn_blue.setText("确认申请");
            viewHolder.btn_red.setText("拒绝申请");
            if (!TextUtils.isEmpty(data.get(position).getMemberBean().getStar())) {
                viewHolder.ratingbar.setRating(Float.parseFloat(data.get(position).getMemberBean().getStar()));
            }
            Glide.with(context).load(data.get(position).getMemberBean().getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                    into(viewHolder.img);
//            viewHolder.name.setText(data.get(position).get);
        } else if (from == 2) {
            Glide.with(context).load(data.get(position).getMemberBean().getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                    into(viewHolder.img);
            viewHolder.name.setText(data.get(position).getMemberBean().getUserName());
            viewHolder.tvTime.setText(data.get(position).getCreate_time());
            viewHolder.btn_blue.setText("确认接单");
            viewHolder.btn_red.setText("拒绝接单");
            viewHolder.btn_blue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        viewHolder.btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blueActionOnClickListener != null) {
                    blueActionOnClickListener.blueAction(position);
                }
            }
        });
        viewHolder.btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redActionOnClickListener != null) {
                    redActionOnClickListener.redAction(position);
                }
            }
        });
    }

    RedActionOnClickListener redActionOnClickListener;
    BlueActionOnClickListener blueActionOnClickListener;

    public void setRedActionOnClickListener(RedActionOnClickListener redActionOnClickListener) {
        this.redActionOnClickListener = redActionOnClickListener;
    }

    public void setBlueActionOnClickListener(BlueActionOnClickListener blueActionOnClickListener) {
        this.blueActionOnClickListener = blueActionOnClickListener;
    }

    public interface RedActionOnClickListener {
        void redAction(int postion);
    }

    public interface BlueActionOnClickListener {
        void blueAction(int postion);
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {

        public TextView name;
        public TextView tvTime;
        private CircleImageView img;
        private RatingBar ratingbar;
        private Button btn_blue;
        private Button btn_red;

        public ViewHolder(View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.img);
            ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            name = (TextView) itemView.findViewById(R.id.name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            btn_blue = (Button) itemView.findViewById(R.id.btn_blue);
            btn_red = (Button) itemView.findViewById(R.id.btn_red);
        }
    }

}

