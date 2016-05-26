package chexin.project.tts.chexin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.CarBean;
import chexin.project.tts.chexin.bean.GoodsBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/25.
 */
public class CarAdapter extends TTSBaseAdapterRecyclerView<CarBean> {
    private List<CarBean> data;
    private Context context;

    public CarAdapter(Context context, List<CarBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.car_item, null, false));
    }

    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(data.get(position).getMemberBean().getUserName());
        if (!TextUtils.isEmpty(data.get(position).getMemberBean().getStar())) {
            viewHolder.ratingbar.setRating(Float.parseFloat(data.get(position).getMemberBean().getStar()));
        }
        viewHolder.time.setText(data.get(position).getAddDate());
        viewHolder.tv_from_place.setText(data.get(position).getFromProvince() + data.get(position).getFromCity() +
                data.get(position).getFromCountry() + "");
        viewHolder.tv_to_place.setText(data.get(position).getToProvince() + data.get(position).getToCity() +
                data.get(position).getToCountry() + "");
        viewHolder.tv_car_type.setText(data.get(position).getCarType());
        viewHolder.tv_car_long.setText(data.get(position).getCarLength());
        viewHolder.tv_car_weight.setText(data.get(position).getCarWeight());
        viewHolder.tv_mileage.setText(data.get(position).getDistance());
        Glide.with(context).load(data.get(position).getMemberBean().getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(viewHolder.img);
    }

//    @Override
//    public int getItemCount() {
//        return 5;
//    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView name;
        private TextView time;
        private TextView tv_from_place;
        private TextView tv_to_place;
        private TextView tv_car_type;
        private TextView tv_car_long;
        private TextView tv_car_weight;
        private TextView tv_mileage;
        private CircleImageView img;
        private RatingBar ratingbar;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            tv_from_place = (TextView) itemView.findViewById(R.id.tv_from_place);
            tv_to_place = (TextView) itemView.findViewById(R.id.tv_to_place);
            tv_car_type = (TextView) itemView.findViewById(R.id.tv_car_type);
            tv_car_long = (TextView) itemView.findViewById(R.id.tv_car_long);
            tv_car_weight = (TextView) itemView.findViewById(R.id.tv_car_weight);
            tv_mileage = (TextView) itemView.findViewById(R.id.tv_mileage);
            ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);
        }
    }
}

