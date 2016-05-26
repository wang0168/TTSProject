package tts.project.handi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import tts.moudle.api.utils.TextUtils;
import tts.project.handi.R;
import tts.project.handi.bean.BillBean;

/**
 * Created by chen on 2016/3/15.
 */
public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillAdapterViewHolder> {
    private Context mContext;
    private List<BillBean> mData;

    public BillAdapter(Context context, List<BillBean> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public BillAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BillAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bill, null, false));
    }

    @Override
    public void onBindViewHolder(BillAdapterViewHolder holder, int position) {
        int realPos = position - 1;
        String txt = "";
        if (!TextUtils.isEmpty(mData.get(realPos).getState())) {
            int state = Integer.parseInt(mData.get(realPos).getState());
            switch (state) {
                case 0:
                    txt = "-";
                    break;
                case 1:
                    txt = "+";
                    break;
            }
            holder.textService.setText(txt);
        }
        holder.textService.setText(mData.get(realPos).getBillcot() + "");
        holder.textMoneyChange.setText(txt + " " + mData.get(realPos).getBillmoney());
        holder.textTime.setText(mData.get(realPos).getBilltime() + "");
        holder.textMoneyBalance.setText(mData.get(realPos).getDlmoney() + "");
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class BillAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView textService;
        private TextView textTime;
        private TextView textMoneyChange;
        private TextView textMoneyBalance;

        public BillAdapterViewHolder(View itemView) {
            super(itemView);
            textService = (TextView) itemView.findViewById(R.id.text_service);
            textTime = (TextView) itemView.findViewById(R.id.text_time);
            textMoneyChange = (TextView) itemView.findViewById(R.id.text_money_change);
            textMoneyBalance = (TextView) itemView.findViewById(R.id.text_money_balance);
        }
    }
}
