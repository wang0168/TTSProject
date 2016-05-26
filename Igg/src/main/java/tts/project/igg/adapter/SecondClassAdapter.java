package tts.project.igg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.project.igg.R;
import tts.project.igg.bean.GoodsBean;

/**
 * Created by lenove on 2016/5/9.
 */
public class SecondClassAdapter extends TTSBaseAdapterRecyclerView<GoodsBean> {
    private Context mContext;
    private List<GoodsBean> mList;

    public SecondClassAdapter(Context context, List<GoodsBean> data) {
        super(context, data);
        mContext = context;
        mList = data;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mList != null) {
            count += mList.size();
            for (int i = 0; i < mList.size(); i++) {
                count += mList.get(i).getGoodsBeans().size();
            }
        }
        return count;
    }

    public class SortMenuViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {

        private TextView name;

        public SortMenuViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
