package chexin.project.tts.chexin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import chexin.project.tts.chexin.R;
import chexin.project.tts.chexin.bean.CarBean;
import chexin.project.tts.chexin.bean.RouteBean;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.CircleImageView;

/**
 * Created by sjb on 2016/3/28.
 */
public class LineListAdapter extends TTSBaseAdapterRecyclerView<RouteBean> {
    private List<RouteBean> data;
    private Context context;

    public LineListAdapter(Context context, List<RouteBean> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_line, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_company_name.setText(data.get(position).getCompany_name());
        viewHolder.tv_to_place.setText(data.get(position).getToProvince() + data.get(position).getToCity() + data.get(position).getToCountry());
        viewHolder.tv_from_place.setText(data.get(position).getFromProvince() + data.get(position).getFromCity() + data.get(position).getFromCountry());
        viewHolder.tv_name.setText("联系人："+data.get(position).getMemberBean().getUserName());
        viewHolder.tv_phone_number.setText("联系电话："+data.get(position).getMemberBean().getMobileNo());
        Glide.with(context).load(data.get(position).getCompany_logo()).placeholder(R.drawable.default_face).diskCacheStrategy(DiskCacheStrategy.ALL).
                into(viewHolder.img_face);
    }

    public class ViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        public TextView tv_company_name;
        public TextView tv_from_place;
        public TextView tv_to_place;
        public TextView tv_name;
        public TextView tv_phone_number;
        public CircleImageView img_face;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_company_name = (TextView) itemView.findViewById(R.id.tv_company_name);
            tv_from_place = (TextView) itemView.findViewById(R.id.tv_from_place);
            tv_to_place = (TextView) itemView.findViewById(R.id.tv_to_place);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone_number = (TextView) itemView.findViewById(R.id.tv_phone_number);
            img_face = (CircleImageView) itemView.findViewById(R.id.img_face);
        }
    }

}

