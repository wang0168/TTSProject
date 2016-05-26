package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.project.igg.R;
import tts.project.igg.bean.PushItemBean;

/**
 * Created by lenove on 2016/5/9.
 */
public class PushItemAdapter extends TTSBaseAdapterRecyclerView<PushItemBean> {
    private Context mContext;
    private List<PushItemBean> mData;
    private int selectID;

    public void setSelectID(int selectID) {
        this.selectID = selectID;
    }

    public PushItemAdapter(Context context, List<PushItemBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PushItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_push_setting, null, false));
    }


    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        PushItemHolder pushItemHolder = (PushItemHolder) holder;
        pushItemHolder.name.setText(mData.get(position).getInformation_name());
        if ("1".equals(mData.get(position).getIs_have())) {
            pushItemHolder.checkbox.setChecked(true);
        } else {
            pushItemHolder.checkbox.setChecked(false);
        }
        if (mData.get(position).isBottomLine()) {
            pushItemHolder.bottom_line.setVisibility(View.VISIBLE);
        } else {
            pushItemHolder.bottom_line.setVisibility(View.GONE);
        }
        pushItemHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(buttonView, isChecked, position);
                }
            }
        });
    }

    public class PushItemHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView name;
        private CheckBox checkbox;
        private View bottom_line;


        public PushItemHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            bottom_line = itemView.findViewById(R.id.bottom_line);

        }
    }

    OnCheckedChangeListener onCheckedChangeListener;

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int pos);
    }
}
