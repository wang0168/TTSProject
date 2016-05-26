package chexin.project.tts.chexin.adapter.carowner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.carowner.MyApplyListActivity;
import chexin.project.tts.chexin.bean.CarBean;
import chexin.project.tts.chexin.common.MyAccountMoudle;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/28.
 */
public class MyReleaseCarListAdapter extends TTSBaseAdapterRecyclerView<CarBean> {
    private List<CarBean> data;
    private Context context;
    private boolean isBtn;

    public MyReleaseCarListAdapter(Context context, List<CarBean> data, boolean IsBtn) {
        super(context, data);
        this.context = context;
        this.data = data;
        this.isBtn = IsBtn;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_release_car_item, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (isBtn) {
            viewHolder.state.setVisibility(View.GONE);
            viewHolder.RlBtn.setVisibility(View.VISIBLE);
//            viewHolder.time.setVisibility(View.GONE);
            viewHolder.carState.setVisibility(View.VISIBLE);
            if ("1".equals(data.get(position).getVehicle_state())) {
                viewHolder.carState.setChecked(true);
            } else {
                viewHolder.carState.setChecked(false);
            }
            viewHolder.carState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkChangeListener != null) {
                        checkChangeListener.onCheckedChanged(buttonView, isChecked, position);
                    }
                }
            });
        } else {
            viewHolder.state.setVisibility(View.VISIBLE);
            viewHolder.RlBtn.setVisibility(View.GONE);
//            viewHolder.time.setVisibility(View.VISIBLE);
            viewHolder.carState.setVisibility(View.GONE);
            String stateStr = "";
            if ("0".equals(data.get(position).getStatus())) {
                stateStr = "申请状态：等待对方确认";
            } else if ("1".equals(data.get(position).getStatus())) {
                stateStr = "申请状态：已确认";
            } else if ("2".equals(data.get(position).getStatus())) {
                stateStr = "申请状态：已拒绝";
            }
            viewHolder.state.setText(stateStr);

        }
        viewHolder.btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MyApplyListActivity.class));
            }
        });
        Glide.with(context).load(MyAccountMoudle.getInstance().getUserInfo().getHead_path()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(viewHolder.img);
//        if (data.get(position).getMemberBean().getName() != null) {
        viewHolder.name.setText(MyAccountMoudle.getInstance().getUserInfo().getUsername());
//        }
        viewHolder.tv_from_place.setText(data.get(position).getFromProvince() + data.get(position).getFromCity() + data.get(position).getFromCountry());
        viewHolder.tv_to_place.setText(data.get(position).getToProvince() + data.get(position).getToCity() + data.get(position).getToCountry());
        viewHolder.tv_car_type.setText(data.get(position).getCarType());
        viewHolder.tv_car_long.setText(data.get(position).getCarLength());
        viewHolder.tv_car_weight.setText(data.get(position).getCarWeight());
        viewHolder.tv_mileage.setText(data.get(position).getDistance());
        if (!TextUtils.isEmpty(MyAccountMoudle.getInstance().getUserInfo().getStar())) {
            viewHolder.ratingbar.setRating(Float.parseFloat(MyAccountMoudle.getInstance().getUserInfo().getStar()));
        }
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
        public TextView state;
        RelativeLayout RlBtn;
        private TextView name;
        private TextView tv_from_place;
        private TextView tv_to_place;
        private TextView tv_car_type;
        private TextView tv_car_long;
        private TextView tv_car_weight;
        private TextView tv_mileage;
        private RatingBar ratingbar;
        private CheckBox carState;
        private Button btn_blue;
        private Button btn_red;
        private CircleImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView.findViewById(R.id.state);
            img = (CircleImageView) itemView.findViewById(R.id.img);
            RlBtn = (RelativeLayout) itemView.findViewById(R.id.RlBtn);
            name = (TextView) itemView.findViewById(R.id.name);
            tv_from_place = (TextView) itemView.findViewById(R.id.tv_from_place);
            tv_to_place = (TextView) itemView.findViewById(R.id.tv_to_place);
            tv_car_type = (TextView) itemView.findViewById(R.id.tv_car_type);
            tv_car_long = (TextView) itemView.findViewById(R.id.tv_car_long);
            tv_car_weight = (TextView) itemView.findViewById(R.id.tv_car_weight);
            tv_mileage = (TextView) itemView.findViewById(R.id.tv_mileage);
            ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);
            carState = (CheckBox) itemView.findViewById(R.id.carState);
            btn_blue = (Button) itemView.findViewById(R.id.btn_blue);
            btn_red = (Button) itemView.findViewById(R.id.btn_red);
        }
    }

}

