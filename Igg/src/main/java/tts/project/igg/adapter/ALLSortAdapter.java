package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
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
public class ALLSortAdapter extends TTSBaseAdapterRecyclerView<GoodsBean> {
    private Context mContext;
    private List<GoodsBean> sortList;
    private int selectID;

    public void setSelectID(int selectID) {
        this.selectID = selectID;
    }

    public ALLSortAdapter(Context context, List<GoodsBean> data) {
        super(context, data);
        mContext = context;
        sortList = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SortMenuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_all_sort, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final SortMenuViewHolder sortMenuViewHolder = (SortMenuViewHolder) holder;
        sortMenuViewHolder.name.setText(sortList.get(position).getGoods_name());
        if (selectID == position) {
            sortMenuViewHolder.name.setTextColor(mContext.getResources().getColor(R.color.RGB255_163_117));
        } else {
            sortMenuViewHolder.name.setTextColor(mContext.getResources().getColor(R.color.RGB80_80_80));
        }
        sortMenuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickLiseren != null) {
                    selectID = position;
                    onItemClickLiseren.onClick(v, position);
//                    notifyDataSetChanged();
                }
            }
        });
    }

    public class SortMenuViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {

        private TextView name;

        public SortMenuViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    OnItemClickLiseren onItemClickLiseren;

    public void OnItemClickListener(OnItemClickLiseren onItemClickLiseren) {
        this.onItemClickLiseren = onItemClickLiseren;
    }

    public interface OnItemClickLiseren {
        void onClick(View v, int pos);
    }
}
