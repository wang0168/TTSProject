package chexin.project.tts.chexin.adapter.carowner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sina.weibo.sdk.api.share.Base;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.CarBean;
import chexin.project.tts.chexin.bean.CollectionBean;
import chexin.project.tts.chexin.bean.MemberBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/28.
 */
public class CollectionGoodsMemberAdapter extends TTSBaseAdapterRecyclerView<CollectionBean> {
    private List<CollectionBean> data;
    private Context context;

    public CollectionGoodsMemberAdapter(Context context, List<CollectionBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_collection_goods_member_item, null, false));
    }


    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvName.setText(data.get(position).getMemberBean().getUserName());
        Glide.with(context).load(data.get(position).getMemberBean().getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(viewHolder.imgFace);
    }

//    @Override
//    public int getItemCount() {
//        return 5;
//    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private CircleImageView imgFace;
        private TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgFace = (CircleImageView) itemView.findViewById(R.id.img_face);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}