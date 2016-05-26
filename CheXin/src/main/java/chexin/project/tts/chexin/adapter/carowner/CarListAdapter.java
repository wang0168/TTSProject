package chexin.project.tts.chexin.adapter.carowner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class CarListAdapter extends TTSBaseAdapterRecyclerView<CarDetailBean> {
    private List<CarDetailBean> data;
    private Context context;

    public CarListAdapter(Context context, List<CarDetailBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.car_detail_item, null, false));
    }


    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.carCode.setText(data.get(position).getCarCode());
        if ("0".equals(data.get(position).getVehicle_state())) {
            viewHolder.btn_check.setChecked(false);
        } else if ("1".equals(data.get(position).getVehicle_state())) {
            viewHolder.btn_check.setChecked(true);
        }
        viewHolder.btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blueActionOnClickListener != null) {
                    blueActionOnClickListener.blueAction(position);
                }
            }
        });
        viewHolder.btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redActionOnClickListener != null) {
                    redActionOnClickListener.redAction(position);
                }
            }
        });
        viewHolder.btn_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkChangeListener != null) {
                    checkChangeListener.onCheckedChanged(buttonView, isChecked, position);
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
//    @Override
//    public int getItemCount() {
//        return 5;
//    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView txt_cheliang;
        private TextView carCode;
        private Button btn_blue;
        private Button btn_red;
        private CheckBox btn_check;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_cheliang = (TextView) itemView.findViewById(R.id.txt_cheliang);
            carCode = (TextView) itemView.findViewById(R.id.carCode);
            btn_blue = (Button) itemView.findViewById(R.id.btn_blue);
            btn_red = (Button) itemView.findViewById(R.id.btn_red);
            btn_check = (CheckBox) itemView.findViewById(R.id.btn_check);
        }
    }

}


