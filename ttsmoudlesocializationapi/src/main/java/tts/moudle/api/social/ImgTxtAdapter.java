package tts.moudle.api.social;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import moudle.project.tts.ttsmoudlesocializationapi.R;
import tts.moudle.api.TTSBaseAdapterRecyclerView;

/**
 * Created by sjb on 2016/3/26.
 */
public class ImgTxtAdapter extends TTSBaseAdapterRecyclerView<ImgTxtBean> {
    private Context mContext;
    private List<ImgTxtBean> data;

    public ImgTxtAdapter(Context context, List<ImgTxtBean> data) {
        super(context, data);
        mContext = context;
        this.data = data;
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
