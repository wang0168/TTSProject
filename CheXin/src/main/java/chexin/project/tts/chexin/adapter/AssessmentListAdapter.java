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
import chexin.project.tts.chexin.bean.AssessmentBean;
import chexin.project.tts.chexin.bean.GoodsBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/28.
 */
public class AssessmentListAdapter extends TTSBaseAdapterRecyclerView<AssessmentBean> {
    private List<AssessmentBean> data;
    private Context context;

    public AssessmentListAdapter(Context context, List<AssessmentBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.assessment_item, null, false));
    }

    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
//        Glide.with(context).load(data.get(position).get.getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
//                into(imgFace);
//        viewHolder.name.setText(data.get(position).get);
        viewHolder.assessment_context.setText(data.get(position).getRemark());
    }

//    @Override
//    public int getItemCount() {
//        return 5;
//    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private CircleImageView img_face;
        private TextView name;
        private TextView assessment_context;

        public ViewHolder(View itemView) {
            super(itemView);

            img_face = (CircleImageView) itemView.findViewById(R.id.img_face);
            name = (TextView) itemView.findViewById(R.id.name);
            assessment_context = (TextView) itemView.findViewById(R.id.assessment_context);
        }
    }
}
