package chexin.project.tts.chexin.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.HomeMenuBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/23.
 */
public class HomeMenuAdapter extends TTSBaseAdapterRecyclerView<HomeMenuBean> {
    private Context mContext;
    private List<HomeMenuBean> data;

    public HomeMenuAdapter(Context context, List<HomeMenuBean> data) {
        super(context, data);
        mContext = context;
        this.data = data;
    }
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.picture_text_item, null, false));
    }

    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.img.setImageResource(data.get(position).getIconId());
        holder.name.setText(data.get(position).getName());
    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
