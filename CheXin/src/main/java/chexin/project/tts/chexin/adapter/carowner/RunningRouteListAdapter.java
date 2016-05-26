package chexin.project.tts.chexin.adapter.carowner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.RouteBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;

/**
 * Created by sjb on 2016/3/28.
 */
public class RunningRouteListAdapter extends TTSBaseAdapterRecyclerView<RouteBean> {
    private List<RouteBean> data;
    private Context context;

    public RunningRouteListAdapter(Context context, List<RouteBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.running_route_item, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_route_No.setText("路线" + "");
        viewHolder.tv_route.setText(data.get(position).getFromProvince() + data.get(position).getFromCity()
                + data.get(position).getFromCountry() + " ———— " + data.get(position).getToProvince() +
                data.get(position).getToCity() + data.get(position).getToCountry());
        if ("0".equals(data.get(position).getIs_listen())) {
            viewHolder.is_lister.setChecked(true);
        } else if ("1".equals(data.get(position).getIs_listen())) {
            viewHolder.is_lister.setChecked(false);
        }
        viewHolder.is_lister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkChangeListener != null) {
                    checkChangeListener.onCheckedChanged(buttonView, isChecked, position);
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
        viewHolder.btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redActionOnClickListener != null) {
                    redActionOnClickListener.redAction(position);
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
//        return 10;
//    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView tv_route_No;
        private TextView tv_route;
        private Button btn_blue;
        private Button btn_red;
        private RadioButton is_lister;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_route_No = (TextView) itemView.findViewById(R.id.tv_route_No);
            tv_route = (TextView) itemView.findViewById(R.id.tv_route);
            btn_blue = (Button) itemView.findViewById(R.id.btn_blue);
            btn_red = (Button) itemView.findViewById(R.id.btn_red);
            is_lister = (RadioButton) itemView.findViewById(R.id.is_lister);
        }
    }

}
