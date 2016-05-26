package chexin.project.tts.chexin.adapter.carowner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.activity.goodsowner.AcceptOrdersListActivity;
import chexin.project.tts.chexin.bean.ApplyBean;
import chexin.project.tts.chexin.common.Utils;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;

/**
 * Created by sjb on 2016/3/28.
 */
public class MyAcceptOrderListAdapter extends TTSBaseAdapterRecyclerView<ApplyBean> {
    private List<ApplyBean> mData;
    private Context context;
    private boolean isBtn;

    public MyAcceptOrderListAdapter(Context context, List<ApplyBean> data, boolean isBtn) {
        super(context, data);
        this.context = context;
        this.mData = data;
        this.isBtn = isBtn;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_accept_order_item, null, false));
    }


    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        if (isBtn) {
            viewHolder.state.setVisibility(View.GONE);
            viewHolder.RlBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.state.setVisibility(View.VISIBLE);
            viewHolder.RlBtn.setVisibility(View.GONE);
        }
        viewHolder.origplace.setText(mData.get(position).getGoodsBean().getFromProvince() + mData.get(position).getGoodsBean().getFromCity() + mData.get(position).getGoodsBean().getFromCountry());
        viewHolder.tv_toplace.setText(mData.get(position).getGoodsBean().getToProvince() + mData.get(position).getGoodsBean().getToCity() + mData.get(position).getGoodsBean().getToCountry());
        viewHolder.time.setText(Utils.subTime(mData.get(position).getCreate_time()));
        viewHolder.tv_car_type.setText(mData.get(position).getGoodsBean().getSKUType());
        if (!TextUtils.isEmpty(mData.get(position).getGoodsBean().getSKULong())) {
            viewHolder.tv_car_long.setText(mData.get(position).getGoodsBean().getSKULong() + "米");
        }
        viewHolder.tv_car_weight.setText(mData.get(position).getGoodsBean().getSKUWeight());
        viewHolder.tv_mileage.setText("里程约" + mData.get(position).getGoodsBean().getDistance() + "公里");
//        String stateStr = mData.get(position).getStatus();
//        switch (stateStr) {
//            case "1":
//                viewHolder.state.setText("");
//                break;
//            case "2":
//                viewHolder.state.setText("");
//                break;
//            case "3":
//                viewHolder.state.setText("");
//                break;
//            case "4":
//                viewHolder.state.setText("");
//                break;
//        }
        viewHolder.btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AcceptOrdersListActivity.class)
                        .putExtra("relation_id", mData.get(position).getApply_id() + ""));
            }
        });
        viewHolder.btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redActionOnClickListener != null) {
                    redActionOnClickListener.redAction(position);
                }

            }
        });
    }


    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView state;
        private TextView origplace;
        private TextView tv_toplace;
        private TextView time;
        private TextView tv_car_type;
        private TextView tv_car_long;
        private TextView tv_car_weight;
        private TextView tv_mileage;
        private RelativeLayout RlBtn;
        private Button btnBlue;
        private Button btnRed;

        public ViewHolder(View itemView) {
            super(itemView);
            origplace = (TextView) itemView.findViewById(R.id.origplace);
            tv_toplace = (TextView) itemView.findViewById(R.id.tv_toplace);
            time = (TextView) itemView.findViewById(R.id.time);
            tv_car_type = (TextView) itemView.findViewById(R.id.tv_car_type);
            tv_car_long = (TextView) itemView.findViewById(R.id.tv_car_long);
            tv_car_weight = (TextView) itemView.findViewById(R.id.tv_car_weight);
            tv_mileage = (TextView) itemView.findViewById(R.id.tv_mileage);
            state = (TextView) itemView.findViewById(R.id.state);
            RlBtn = (RelativeLayout) itemView.findViewById(R.id.RlBtn);
            btnBlue = (Button) itemView.findViewById(R.id.btn_blue);
            btnRed = (Button) itemView.findViewById(R.id.btn_red);
        }
    }

    RedActionOnClickListener redActionOnClickListener;

    public void setRedActionOnClickListener(RedActionOnClickListener redActionOnClickListener) {
        this.redActionOnClickListener = redActionOnClickListener;
    }

    public interface RedActionOnClickListener {
        void redAction(int postion);
    }

}
