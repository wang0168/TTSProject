package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapter;
import tts.project.igg.R;
import tts.project.igg.bean.OrderGoodsBean;
import tts.project.igg.utils.ImageLoader;

/**
 * Created by lenove on 2016/5/19.
 */
public class OrderGoodListAdapter extends TTSBaseAdapter {
    private Context mContext;
    private List<OrderGoodsBean> mData;

    public OrderGoodListAdapter(Context context, List<OrderGoodsBean> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_collection, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.attr = (TextView) convertView.findViewById(R.id.attr);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.weight = (TextView) convertView.findViewById(R.id.weight);
            viewHolder.count = (TextView) convertView.findViewById(R.id.count);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mData.get(position).getGoods_name());
        viewHolder.attr.setText("");
        viewHolder.price.setText("￥" + mData.get(position).getGoods_price());
        viewHolder.count.setText("X " + mData.get(position).getGoods_num());
        viewHolder.weight.setText("重量");
        ImageLoader.load(mContext, mData.get(position).getGoods_img(), viewHolder.img);
        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView attr;
        private TextView count;
        private TextView price;
        private TextView weight;

        private ImageView img;
    }
}
