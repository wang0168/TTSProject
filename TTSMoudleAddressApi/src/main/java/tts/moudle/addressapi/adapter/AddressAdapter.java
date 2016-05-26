package tts.moudle.addressapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

import moudle.project.tts.tts_moudle_address_api.R;
import tts.moudle.addressapi.bean.AddressBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.CircleImageView;
import tts.moudle.api.widget.RecyclerViewDragHolder;

/**
 * Created by sjb on 2016/1/22.
 */
public class AddressAdapter extends RecyclerView.Adapter {
    private List<AddressBean> data;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(View itemView, int position);

        void deleteAddress(int position);

        void editAddress(int position);

        void defaultAddress(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    //当viewholder创建的时候回调
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mybg = LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_item, null);
        mybg.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        View view = null;
        view = View.inflate(context, R.layout.address_item, null);
        return new ViewHolder(context, mybg, view, RecyclerViewDragHolder.EDGE_RIGHT).getDragViewHolder();
    }

    public AddressAdapter(Context context, List<AddressBean> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder myHolder, final int index) {
        final int position=index-1;

        final ViewHolder holder = (ViewHolder) RecyclerViewDragHolder.getHolder(myHolder);
        holder.mPhoneNum.setText(data.get(position).getMobile());
        holder.mProvince.setText(data.get(position).getCounty());
        holder.mDetail.setText(data.get(position).getAddress());
        if (data.get(position).getIs_default().equals("on")) {
            holder.mDafault.setVisibility(View.VISIBLE);
        } else {
            holder.mDafault.setVisibility(View.GONE);
        }

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.close();
                listener.deleteAddress(position);
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.close();
                listener.editAddress(position);
            }
        });

        holder.defaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.close();
                if (data.get(position).getIs_default().equals("on")) {
                    CustomUtils.showTipShort(context, "已是默认地址");
                    return;
                }
                listener.defaultAddress(position);
            }
        });

        holder.topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.topView, position);
            }
        });
    }

    public class ViewHolder extends RecyclerViewDragHolder {
        public CircleImageView mLogo;
        public TextView mPhoneNum, mProvince, mDetail, mDafault;

        public Button deleteBtn, defaultBtn, editBtn;

        public ViewHolder(Context context, View bgView, View topView) {
            super(context, bgView, topView);
        }

        public ViewHolder(Context context, View bgView, View topView, int mTrackingEdges) {
            super(context, bgView, topView, mTrackingEdges);
        }

        @Override
        public void initView(View itemView) {
            mPhoneNum = (TextView) itemView.findViewById(R.id.my_message_phone);
            mProvince = (TextView) itemView.findViewById(R.id.my_message_province);
            mDetail = (TextView) itemView.findViewById(R.id.my_message_detail);
            mDafault = (TextView) itemView.findViewById(R.id.my_message_default);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
            defaultBtn = (Button) itemView.findViewById(R.id.defaultBtn);
            editBtn = (Button) itemView.findViewById(R.id.editBtn);
        }
    }
}

