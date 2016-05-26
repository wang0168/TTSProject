package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.project.igg.R;
import tts.project.igg.bean.BaseInfoItemBean;

/**
 * Created by lenove on 2016/5/9.
 */
public class BaseInfoItemAdapter extends TTSBaseAdapterRecyclerView<BaseInfoItemBean> {
    private Context mContext;
    private List<BaseInfoItemBean> mData;


    public BaseInfoItemAdapter(Context context, List<BaseInfoItemBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseInfoItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_base_info, null, false));
    }


    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        BaseInfoItemHolder baseInfoItemHolder = (BaseInfoItemHolder) holder;
        baseInfoItemHolder.name.setText(mData.get(position).getName());
        if (!TextUtils.isEmpty(mData.get(position).getContext())) {
            baseInfoItemHolder.context.setText(mData.get(position).getContext());
        } else {
            baseInfoItemHolder.context.setText("");
        }
        if (!TextUtils.isEmpty(mData.get(position).getContext_left())) {
            baseInfoItemHolder.context_left.setVisibility(View.VISIBLE);
            baseInfoItemHolder.context_left.setText(mData.get(position).getContext_left());
        } else {
            baseInfoItemHolder.context_left.setVisibility(View.GONE);

        }
        if (mData.get(position).isIcon()) {
            baseInfoItemHolder.right_icon.setVisibility(View.VISIBLE);
        } else {
            baseInfoItemHolder.right_icon.setVisibility(View.GONE);
        }
        if (mData.get(position).isBottomLine()) {
            baseInfoItemHolder.bottom_line.setVisibility(View.VISIBLE);
        } else {
            baseInfoItemHolder.bottom_line.setVisibility(View.GONE);
        }

    }

    public class BaseInfoItemHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView name;
        private TextView context;
        private EditText context_left;
        private View bottom_line;
        private ImageView right_icon;


        public BaseInfoItemHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            context = (TextView) itemView.findViewById(R.id.context);
            context_left = (EditText) itemView.findViewById(R.id.context_left);
            bottom_line = itemView.findViewById(R.id.bottom_line);
            right_icon = (ImageView) itemView.findViewById(R.id.right_icon);

        }
    }
}
