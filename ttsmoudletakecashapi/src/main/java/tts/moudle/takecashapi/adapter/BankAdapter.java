package tts.moudle.takecashapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import moudle.project.tts.ttsmoudletakecashapi.R;
import tts.moudle.api.bean.BankBean;
import tts.moudle.api.utils.CustomUtils;
import tts.moudle.api.widget.CircleImageView;
import tts.moudle.api.widget.RecyclerViewDragHolder;

/**
 * Created by sjb on 2016/2/24.
 */
public class BankAdapter extends RecyclerView.Adapter {
    private List<BankBean> data;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);

        void deleteAddress(int position);
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
        view = View.inflate(context, R.layout.bank_item, null);
        return new ViewHolder(context, mybg, view, RecyclerViewDragHolder.EDGE_RIGHT).getDragViewHolder();
    }

    public BankAdapter(Context context, List<BankBean> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder myHolder, final int index) {
        final ViewHolder holder = (ViewHolder) RecyclerViewDragHolder.getHolder(myHolder);
        final int position = index;
        holder.bankName.setText(data.get(position).getBank());
        holder.name.setText(data.get(position).getName());
        holder.endNum.setText("尾号是" + data.get(position).getCard().substring(data.get(position).getCard().length() - 4, data.get(position).getCard().length()));
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.close();
                listener.deleteAddress(position);
            }
        });

        holder.topView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    public class ViewHolder extends RecyclerViewDragHolder {
        public TextView bankName, name, endNum;
        public Button deleteBtn;

        public ViewHolder(Context context, View bgView, View topView) {
            super(context, bgView, topView);
        }

        public ViewHolder(Context context, View bgView, View topView, int mTrackingEdges) {
            super(context, bgView, topView, mTrackingEdges);
        }

        @Override
        public void initView(View itemView) {
            bankName = (TextView) itemView.findViewById(R.id.bankName);
            name = (TextView) itemView.findViewById(R.id.name);
            endNum = (TextView) itemView.findViewById(R.id.endNum);
            deleteBtn = (Button) itemView.findViewById(R.id.deleteBtn);
        }
    }
}


