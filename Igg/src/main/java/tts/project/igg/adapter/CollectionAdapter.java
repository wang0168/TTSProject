package tts.project.igg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.widget.RecyclerViewDragHolder;
import tts.project.igg.R;
import tts.project.igg.bean.CollectionBean;
import tts.project.igg.utils.ImageLoader;

/**
 * Created by lenove on 2016/5/16.
 */
public class CollectionAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CollectionBean> mData;

    public CollectionAdapter(Context context, List<CollectionBean> data) {

        mData = data;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CollectionViewHolder collectionViewHolder = (CollectionViewHolder) RecyclerViewDragHolder.getHolder(holder);
//        collectionViewHolder.price.setText("￥" + mData.get(position).getGoods_price());
//        collectionViewHolder.title.setText(mData.get(position).getGoods_name());
        ImageLoader.load(mContext, mData.get(position).getGoodsBean().getGoods_img(), collectionViewHolder.img);
        collectionViewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    collectionViewHolder.close();
                    listener.deleteAddress(position);
                }
            }
        });

        collectionViewHolder.topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mybg = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_item, null);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collection, null, false);
        return new CollectionViewHolder(mContext, mybg, view).getDragViewHolder();
    }

    public class CollectionViewHolder extends RecyclerViewDragHolder {
        private ImageView img;
        private TextView title;
        private TextView price;
        public Button deleteBtn;

        public CollectionViewHolder(Context context, View bgView, View topView) {
            super(context, bgView, topView);
        }


        @Override
        public void initView(View itemView) {
            img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);

        void deleteAddress(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
