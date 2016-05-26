package chexin.project.tts.chexin.adapter.carowner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.OrdersBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/28.
 */
public class CarOrderListAdapter extends TTSBaseAdapterRecyclerView<OrdersBean> {
    private List<OrdersBean> data;
    private Context context;
    private int type;

    public CarOrderListAdapter(Context context, List<OrdersBean> data, int type) {
        super(context, data);
        this.context = context;
        this.data = data;
        this.type = type;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_order_list_item, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        Glide.with(context).load(data.get(position).getMemberBean().getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(viewHolder.img);
        if (data.get(position).getMemberBean().getName() != null) {
            viewHolder.name.setText(data.get(position).getMemberBean().getUserName());
        }
        if (type == 1) {
            viewHolder.btnAction.setText("交易完成");
        } else {
            viewHolder.btnAction.setText("前往评价");
        }
        viewHolder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionOnClickListener != null) {
                    actionOnClickListener.doAction(v, position);
                }
            }
        });
        viewHolder.time.setText(data.get(position).getCreate_time());
        viewHolder.tv_from_place.setText(data.get(position).getGoodsBean().getFromProvince() + data.get(position).getGoodsBean().getFromCity() + data.get(position).getGoodsBean().getFromCountry());
        viewHolder.tv_to_place.setText(data.get(position).getGoodsBean().getToProvince() + data.get(position).getGoodsBean().getToCity() + data.get(position).getGoodsBean().getToCountry());
        viewHolder.tv_car_type.setText(data.get(position).getGoodsBean().getSKUType());
        viewHolder.tv_car_long.setText(data.get(position).getGoodsBean().getSKULong());
        viewHolder.tv_car_weight.setText(data.get(position).getGoodsBean().getSKUWeight());
        viewHolder.tv_mileage.setText(data.get(position).getGoodsBean().getDistance());
        if ("2".equals(data.get(position).getOrder_state())) {
            viewHolder.btnAction.setText("评价完成");
            viewHolder.btnAction.setClickable(false);
        }
    }

    ActionOnClickListener actionOnClickListener;

    public void setActionOnClickListener(ActionOnClickListener actionOnClickListener) {
        this.actionOnClickListener = actionOnClickListener;
    }

    public interface ActionOnClickListener {
        void doAction(View v, int postion);
    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {

        private TextView time;
        private TextView name;
        private TextView tv_from_place;
        private TextView tv_to_place;
        private TextView tv_car_type;
        private TextView tv_car_long;
        private TextView tv_car_weight;
        private TextView tv_mileage;
        private Button btnAction;
        private CircleImageView img;


        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            img = (CircleImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            tv_from_place = (TextView) itemView.findViewById(R.id.tv_from_place);
            tv_to_place = (TextView) itemView.findViewById(R.id.tv_to_place);
            tv_car_type = (TextView) itemView.findViewById(R.id.tv_car_type);
            tv_car_long = (TextView) itemView.findViewById(R.id.tv_car_long);
            tv_car_weight = (TextView) itemView.findViewById(R.id.tv_car_weight);
            tv_mileage = (TextView) itemView.findViewById(R.id.tv_mileage);
            btnAction = (Button) itemView.findViewById(R.id.btn_action);
        }
    }

}

