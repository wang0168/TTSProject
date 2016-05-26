package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.widget.CircleImageView;
import tts.project.igg.R;
import tts.project.igg.bean.MyNewsBean;

/**
 * Created by lenove on 2016/5/9.
 */
public class MyNewsAdapter extends TTSBaseAdapterRecyclerView<MyNewsBean> {
    private Context mContext;
    private List<MyNewsBean> newsList;
//    private int selectID;
//
//    public void setSelectID(int selectID) {
//        this.selectID = selectID;
//    }

    public MyNewsAdapter(Context context, List<MyNewsBean> data) {
        super(context, data);
        mContext = context;
        newsList = data;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyNewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_news, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);


    }

    public class MyNewsViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_time;
        private TextView context;
        private CircleImageView face;

        public MyNewsViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.title);
            tv_time = (TextView) itemView.findViewById(R.id.time);
            context = (TextView) itemView.findViewById(R.id.context);
            face = (CircleImageView) itemView.findViewById(R.id.face);
        }
    }
}
