package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.moudle.api.utils.TextUtils;
import tts.project.igg.R;
import tts.project.igg.bean.NewsBean;
import tts.project.igg.utils.ImageLoader;

/**
 * Created by lenove on 2016/5/9.
 */
public class NewsAdapter extends TTSBaseAdapterRecyclerView<NewsBean> {
    private Context mContext;
    private List<NewsBean> newsList;
    private int selectID;

    public void setSelectID(int selectID) {
        this.selectID = selectID;
    }

    public NewsAdapter(Context context, List<NewsBean> data) {
        super(context, data);
        mContext = context;
        newsList = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news_view, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
        if (TextUtils.isEmpty(newsList.get(position).getCreate_time())) {
            newsViewHolder.tv_time.setText("时间未知");
        } else {
            newsViewHolder.tv_time.setText(newsList.get(position).getCreate_time());
        }

        ImageLoader.load(mContext, newsList.get(position).getInformation_img(), newsViewHolder.img);
        newsViewHolder.tv_integral.setText(newsList.get(position).getInformation_integral()+"");
        newsViewHolder.tv_noidea.setText("(" + newsList.get(position).
                getAlready_answer_num() == null ? "0" : newsList.get(position).getAlready_answer_num()
                + "/" + newsList.get(position).getAnswer_num() + ")");
        newsViewHolder.tv_title.setText(newsList.get(position).getInformation_title()+"");
        newsViewHolder.tv_tags.setText(newsList.get(position).getInformation_class_name()+"");

    }

    public class NewsViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_time;
        private TextView tv_integral;
        private TextView tv_noidea;
        private TextView tv_tags;

        private ImageView img;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_integral = (TextView) itemView.findViewById(R.id.tv_integral);
            tv_noidea = (TextView) itemView.findViewById(R.id.tv_noidea);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);

            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
