package tts.project.igg.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.project.igg.R;
import tts.project.igg.activity.EditAddressActivity;
import tts.project.igg.bean.AddressBean;

/**
 * Created by lenove on 2016/5/11.
 */
public class AddressManagerAdapter extends TTSBaseAdapterRecyclerView<AddressBean> {
    private Context mContext;
    private List<AddressBean> mData;

    public AddressManagerAdapter(Context context, List<AddressBean> data) {
        super(context, data);
        mData = data;
        mContext = context;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressManagerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        AddressManagerViewHolder addressManagerViewHolder = (AddressManagerViewHolder) holder;
        addressManagerViewHolder.name.setText(mData.get(position).getName());
        addressManagerViewHolder.phone.setText(mData.get(position).getMobile());
        addressManagerViewHolder.address.setText(mData.get(position).getProvince() + mData.get(position).getCity()
                + mData.get(position).getCountry() + mData.get(position).getDetailed_address());
        if ("1".equals(mData.get(position).getIs_default())) {
            addressManagerViewHolder.tv_default.setVisibility(View.VISIBLE);
            addressManagerViewHolder.set_default.setVisibility(View.GONE);
        } else {
            addressManagerViewHolder.tv_default.setVisibility(View.GONE);
            addressManagerViewHolder.set_default.setVisibility(View.VISIBLE);
            addressManagerViewHolder.set_default.setChecked(false);
            addressManagerViewHolder.set_default.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSetDefaultClickListener != null) {
                        onSetDefaultClickListener.setDefault(v, position);
                    }
                }
            });
        }
        addressManagerViewHolder.action_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.delete(v, position);
                }
            }
        });
        addressManagerViewHolder.action_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).startActivityForResult(new Intent(mContext, EditAddressActivity.class).putExtra("from", 1).putExtra("data", mData.get(position)), 1000);
//                mContext.startActivity(new Intent(mContext, EditAddressActivity.class).putExtra("from", 1).putExtra("data",mData.get(position)));
            }
        });
    }

    public class AddressManagerViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView name;
        private TextView address;
        private TextView phone;
        private TextView tv_default;
        private TextView action_edit;
        private TextView action_delete;
        private RadioButton set_default;

        public AddressManagerViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);
            tv_default = (TextView) itemView.findViewById(R.id.tv_default);
            action_edit = (TextView) itemView.findViewById(R.id.action_edit);
            action_delete = (TextView) itemView.findViewById(R.id.action_delete);
            set_default = (RadioButton) itemView.findViewById(R.id.set_default);
        }
    }

    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void delete(View itemView, int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    private OnSetDefaultClickListener onSetDefaultClickListener;

    public interface OnSetDefaultClickListener {
        void setDefault(View itemView, int position);
    }

    public void setOnSetDefaultClickListener(OnSetDefaultClickListener onSetDefaultClickListener) {
        this.onSetDefaultClickListener = onSetDefaultClickListener;
    }
}
