package chexin.project.tts.chexin.adapter.goodsowner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.goodsowner.AcceptOrdersListActivity;
import chexin.project.tts.chexin.bean.GoodsBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;

/**
 * Created by sjb on 2016/3/28.
 */
public class MyReleaseGoodListAdapter extends TTSBaseAdapterRecyclerView<GoodsBean> {
    private List<GoodsBean> data;
    private Context context;


    public MyReleaseGoodListAdapter(Context context, List<GoodsBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;

    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_release_good_item, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AcceptOrdersListActivity.class));
            }
        });
        viewHolder.tv_from_place.setText(data.get(position).getFromProvince() + data.get(position).getFromCity() + data.get(position).getFromCountry());
        viewHolder.tv_to_place.setText(data.get(position).getToProvince() + data.get(position).getToCity() + data.get(position).getToCountry());
        viewHolder.tv_car_type.setText(data.get(position).getSKUType());
        viewHolder.tv_car_long.setText(data.get(position).getSKULong());
        viewHolder.tv_car_weight.setText(data.get(position).getSKUWeight());
        viewHolder.tv_mileage.setText(data.get(position).getDistance());
        viewHolder.btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redActionOnClickListener != null) {
                    redActionOnClickListener.redAction(position);
                }
            }
        });
        viewHolder.btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blueActionOnClickListener != null) {
                    blueActionOnClickListener.blueAction(position);
                }
            }
        });

    }

    RedActionOnClickListener redActionOnClickListener;
    BlueActionOnClickListener blueActionOnClickListener;
    CheckListener checkChangeListener;

    public void setCheckChangeListener(CheckListener checkChangeListener) {
        this.checkChangeListener = checkChangeListener;
    }

    public void setRedActionOnClickListener(RedActionOnClickListener redActionOnClickListener) {
        this.redActionOnClickListener = redActionOnClickListener;
    }

    public void setBlueActionOnClickListener(BlueActionOnClickListener blueActionOnClickListener) {
        this.blueActionOnClickListener = blueActionOnClickListener;
    }

    public interface RedActionOnClickListener {
        void redAction(int postion);
    }

    public interface BlueActionOnClickListener {
        void blueAction(int postion);
    }

    public interface CheckListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position);
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {

        private TextView tv_from_place;
        private TextView tv_to_place;
        private TextView tv_car_type;
        private TextView tv_car_long;
        private TextView tv_car_weight;
        private TextView tv_mileage;
        private Button btn_blue;
        private Button btn_red;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_from_place = (TextView) itemView.findViewById(R.id.tv_from_place);
            tv_to_place = (TextView) itemView.findViewById(R.id.tv_to_place);
            tv_car_type = (TextView) itemView.findViewById(R.id.tv_car_type);
            tv_car_long = (TextView) itemView.findViewById(R.id.tv_car_long);
            tv_car_weight = (TextView) itemView.findViewById(R.id.tv_car_weight);
            tv_mileage = (TextView) itemView.findViewById(R.id.tv_mileage);
            btn_blue = (Button) itemView.findViewById(R.id.btn_blue);
            btn_red = (Button) itemView.findViewById(R.id.btn_red);
        }
    }

}

