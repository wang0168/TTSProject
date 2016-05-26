package chexin.project.tts.chexin.adapter.goodsowner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.CarBean;
import chexin.project.tts.chexin.bean.CollectionBean;
import chexin.project.tts.chexin.bean.GoodsBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/28.
 */
public class CollectionCarSourceAdapter extends TTSBaseAdapterRecyclerView<CollectionBean> {
    private List<CollectionBean> data;
    private Context context;

    public CollectionCarSourceAdapter(Context context, List<CollectionBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_collection_goods_source_item, null, false));
    }


    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(data.get(position).getMemberBean().getUserName());
        viewHolder.time.setText(data.get(position).getMemberBean().getAddDate());
        viewHolder.tv_from_place.setText(data.get(position).getCarBean().getFromProvince() + data.get(position).getCarBean().getFromCity() + data.get(position).getCarBean().getFromCountry());
        viewHolder.tv_to_place.setText(data.get(position).getCarBean().getToProvince() + data.get(position).getCarBean().getToCity() + data.get(position).getCarBean().getToCountry());
        viewHolder.tv_car_type.setText(data.get(position).getCarBean().getCarType());
        viewHolder.tv_car_long.setText(data.get(position).getCarBean().getCarLength());
        viewHolder.tv_car_weight.setText(data.get(position).getCarBean().getCarWeight());
        viewHolder.tv_mileage.setText(data.get(position).getCarBean().getDistance());
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

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private CircleImageView imgFace;
        private TextView name;
        private TextView time;
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
            imgFace = (CircleImageView) itemView.findViewById(R.id.img_face);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
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

