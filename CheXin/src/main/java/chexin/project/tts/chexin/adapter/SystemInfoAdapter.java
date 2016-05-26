package chexin.project.tts.chexin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.CarBean;
import chexin.project.tts.chexin.bean.SystemInfoBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.CircleImageView;

/**
 * 系统消息适配器
 * Created by sjb on 2016/3/25.
 */
public class SystemInfoAdapter extends TTSBaseAdapterRecyclerView<SystemInfoBean> {
    private List<SystemInfoBean> data;
    private Context context;

    public SystemInfoAdapter(Context context, List<SystemInfoBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_system_info, null, false));
    }

    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.time.setText(data.get(position).getMessagetime());
        viewHolder.context.setText(data.get(position).getMessagecontext());
        Glide.with(context).load(data.get(position).getHeadPath()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(viewHolder.img_face);
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView name;
        private TextView time;
        private CircleImageView img_face;
        private TextView context;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            img_face = (CircleImageView) itemView.findViewById(R.id.img_face);
            context = (TextView) itemView.findViewById(R.id.context);
        }
    }
}

