package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapter;
import tts.moudle.api.widget.ListViewForScrollView;
import tts.project.igg.R;
import tts.project.igg.bean.OrderBean;

/**
 * Created by lenove on 2016/5/16.
 */
public class OrderListAdapter extends TTSBaseAdapter {
    private Context mContext;
    private List<OrderBean> mData;

    public OrderListAdapter(Context context, List<OrderBean> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.fare = (TextView) convertView.findViewById(R.id.fare);
            viewHolder.count = (TextView) convertView.findViewById(R.id.count);
            viewHolder.btn_blank_left = (Button) convertView.findViewById(R.id.btn_blank_left);
            viewHolder.btn_blank_right = (Button) convertView.findViewById(R.id.btn_blank_right);
            viewHolder.btn_assist = (Button) convertView.findViewById(R.id.btn_assist);
            viewHolder.listView = (ListViewForScrollView) convertView.findViewById(R.id.mListView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mData.get(position).getMerchants_name());
        String stateStr = "";
        switch (mData.get(position).getOrder_state()) {
            case "wait_pay":
                stateStr = "待付款";
                break;
        }
        viewHolder.status.setText(stateStr);
        viewHolder.price.setText("合计：￥");
        if (mData.get(position).getOrderGoodsBeens() != null) {
            viewHolder.count.setText("共" + mData.get(position).getOrderGoodsBeens().size() + "件商品");
            viewHolder.listView.setAdapter(new OrderGoodListAdapter(mContext, mData.get(position).getOrderGoodsBeens()));
        }
        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView status;
        private TextView count;
        private TextView price;
        private TextView fare;
        private Button btn_blank_left;
        private Button btn_blank_right;
        private Button btn_assist;
        private ListViewForScrollView listView;
    }
}
