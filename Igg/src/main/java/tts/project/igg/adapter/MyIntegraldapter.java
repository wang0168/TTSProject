package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.project.igg.R;
import tts.project.igg.bean.IntegralBean;
import tts.project.igg.utils.ImageLoader;

/**
 * Created by lenove on 2016/5/16.
 */
public class MyIntegraldapter extends TTSBaseAdapterRecyclerView<IntegralBean> {
    private Context mContext;
    private List<IntegralBean> mData;

    public MyIntegraldapter(Context context, List<IntegralBean> data) {
        super(context, data);
        mData = data;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        String symbol = "";
        CollectionViewHolder collectionViewHolder = (CollectionViewHolder) holder;
        collectionViewHolder.date.setText(mData.get(position).getCreate_time());
        collectionViewHolder.title.setText(mData.get(position).getName());
        if (!TextUtils.isEmpty(mData.get(position).getType())) {
            String str = "";
            switch (mData.get(position).getType()) {
                case "1":
                    str = "看资讯赠送积分";
                    symbol = "+";
                    break;
                case "2":
                    symbol = "+";
                    str = "购物所得";
                    break;
                case "3":
                    str = "抵扣积分";
                    symbol = "-";
                    break;
            }
            collectionViewHolder.purpose.setText(str);
            collectionViewHolder.numeral.setText(symbol + mData.get(position).getIntegral());
        }

        ImageLoader.load(mContext, mData.get(position).getImg(), collectionViewHolder.img);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_integral, null, false);
        return new CollectionViewHolder(view);
    }

    public class CollectionViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView date;
        private TextView purpose;
        private TextView numeral;

        public CollectionViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            purpose = (TextView) itemView.findViewById(R.id.purpose);
            numeral = (TextView) itemView.findViewById(R.id.numeral);
        }

    }

}
