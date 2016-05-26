package chexin.project.tts.chexin.adapter.carowner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.CarDetailBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;

/**
 * Created by sjb on 2016/3/28.
 */
public class CarStateListAdapter extends TTSBaseAdapterRecyclerView<CarDetailBean> {
    private List<CarDetailBean> data;
    private Context context;

    public CarStateListAdapter(Context context, List<CarDetailBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.car_state_item, null, false));
    }


    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.carCode.setText(data.get(position).getCarCode());
        viewHolder.type.setText(data.get(position).getCarType());
        viewHolder.carLong.setText(data.get(position).getCarLength());
        if ("0".equals(data.get(position).getVehicle_state())) {
            viewHolder.rbtn_check.setChecked(false);
        } else if ("1".equals(data.get(position).getVehicle_state())) {
            viewHolder.rbtn_check.setChecked(true);
        }
        viewHolder.rbtn_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkChangeListener != null) {
                    checkChangeListener.onCheckedChanged(buttonView, isChecked, position);
                }
            }
        });
    }

    CheckListener checkChangeListener;

    public void setCheckChangeListener(CheckListener checkChangeListener) {
        this.checkChangeListener = checkChangeListener;
    }

    public interface CheckListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position);
    }
//    @Override
//    public int getItemCount() {
//        return 5;
//    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView carCode;
        private TextView type;
        private TextView carLong;
        private CheckBox rbtn_check;


        public ViewHolder(View itemView) {
            super(itemView);
            carCode = (TextView) itemView.findViewById(R.id.carCode);
            type = (TextView) itemView.findViewById(R.id.type);
            carLong = (TextView) itemView.findViewById(R.id.carLong);
            rbtn_check = (CheckBox) itemView.findViewById(R.id.rbtn_check);
        }
    }

}


