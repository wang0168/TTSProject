package chexin.project.tts.chexin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.GoodsBean;
import chexin.project.tts.chexin.common.Utils;
import tts.moudle.api.TTSBaseAdapterRecyclerView;

/**
 * Created by sjb on 2016/3/25.
 */
public class GoodsAdapter extends TTSBaseAdapterRecyclerView<GoodsBean> {
    private List<GoodsBean> data;
    private Context context;

    public GoodsAdapter(Context context, List<GoodsBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.goods_item, null, false));
    }

    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.origplace.setText(data.get(position).getFromProvince() + data.get(position).getFromCity() + data.get(position).getFromCountry());
        viewHolder.tv_toplace.setText(data.get(position).getToProvince() + data.get(position).getToCity() + data.get(position).getToCountry() + "");
        viewHolder.time.setText(Utils.subTime(data.get(position).getAddDate()));
        viewHolder.tv_goods_type.setText(data.get(position).getSKUName());
        viewHolder.tv_car_long.setText("需" + data.get(position).getSKULong() + "米车");
        viewHolder.tv_car_weight.setText(data.get(position).getSKUWeight());
        viewHolder.tv_mileage.setText(data.get(position).getDistance() + "公里");
    }

//    @Override
//    public int getItemCount() {
//        return data;
//    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView origplace;
        private TextView tv_toplace;
        private TextView time;
        private TextView tv_goods_type;
        private TextView tv_car_long;
        private TextView tv_car_weight;
        private TextView tv_mileage;

        public ViewHolder(View itemView) {
            super(itemView);
            origplace = (TextView) itemView.findViewById(R.id.origplace);
            tv_toplace = (TextView) itemView.findViewById(R.id.tv_toplace);
            time = (TextView) itemView.findViewById(R.id.time);
            tv_goods_type = (TextView) itemView.findViewById(R.id.tv_goods_type);
            tv_car_long = (TextView) itemView.findViewById(R.id.tv_car_long);
            tv_car_weight = (TextView) itemView.findViewById(R.id.tv_car_weight);
            tv_mileage = (TextView) itemView.findViewById(R.id.tv_mileage);
        }
    }
}
