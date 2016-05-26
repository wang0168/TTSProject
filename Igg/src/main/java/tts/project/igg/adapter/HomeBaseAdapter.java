package tts.project.igg.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.Host;
import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.project.igg.R;
import tts.project.igg.activity.goods.GoodsListActivity;
import tts.project.igg.activity.NewsDetailActivity;
import tts.project.igg.activity.SortListActivity;
import tts.project.igg.bean.GoodsBean;
import tts.project.igg.bean.NewsBean;
import tts.project.igg.utils.ImageLoader;

/**
 * Created by lenove on 2016/5/3.
 */
public class HomeBaseAdapter extends TTSBaseAdapterRecyclerView<GoodsBean> {
    private int sortMenuIndex = 100;
    private int bulletinIndex = 101;
    private int newsIndex = 102;
    private int hotsIndex = 103;
    private int titleIndex = 104;
    private Context mContext;

    private List<GoodsBean> sortList;
    private List<NewsBean> newsList;
    private List<GoodsBean> goodsList;

    public HomeBaseAdapter(Context context, List data) {
        super(context, data);
        mContext = context;
        sortList = data;
    }

    public List<GoodsBean> getSortList() {
        return sortList;
    }

    public List<NewsBean> getNews() {
        return newsList;
    }

    public List<GoodsBean> getGoods() {
        return goodsList;
    }

    public void setNews(List<NewsBean> news) {
        this.newsList = news;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goodsList = goods;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        if (viewType == sortMenuIndex)

        if (viewType == bulletinIndex)
            return new BulletViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bullet_view, null, false));
        if (viewType == newsIndex) {
            return new NewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hot_news_view, null, false));
        }
        if (viewType == hotsIndex)
            return new HotsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods_view, null, false));
        if (viewType == titleIndex)
            return new TitleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_span_title_view, null, false));
        else
            return new SortMenuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.picture_text_item, null, false));
    }

    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
//        Logger.wtf("count===" + getItemCount());
//        Logger.d("type==" + type);
//        Logger.w("position==" + position);
        if (type == bulletinIndex) {
            BulletViewHolder bulletViewHolder = (BulletViewHolder) holder;
            bulletViewHolder.title.setText("公告");
        }
        if (type == sortMenuIndex) {
            SortMenuViewHolder sortMenuViewHolder = (SortMenuViewHolder) holder;
            if (sortList != null) {
                final int pos = position;
                ImageLoader.load(mContext, sortList.get(pos).getGoods_img(), sortMenuViewHolder.img);
                sortMenuViewHolder.name.setText(sortList.get(pos).getGoods_name());

                sortMenuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pos == sortList.size() - 1) {
//                            CustomUtils.showTipShort(mContext, "sorts.size==" + sorts.size() + "position==" + position);
                            mContext.startActivity(new Intent(mContext, SortListActivity.class));
                        } else {
                            mContext.startActivity(new Intent(mContext, GoodsListActivity.class).
                                    putExtra("good_id", sortList.get(pos).getGoods_id() + "").putExtra("title", sortList.get(pos).getGoods_name()));
                        }
                    }
                });
            }

        }
        if (type == newsIndex) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            if (newsList.size() != 0) {
                final int pos = position - sortList.size() - 2;
                Log.e("","position=="+position+"  sortList.size()=="+sortList.size()+"  newsList.size()=="+newsList.size());
                newsViewHolder.title.setText(newsList.get(pos).getInformation_title());
                newsViewHolder.time.setText(newsList.get(pos).getCreate_time());
                ImageLoader.load(mContext, newsList.get(pos).getInformation_img(), newsViewHolder.img);
                newsViewHolder.tv_integral.setText(newsList.get(pos).getInformation_integral());
                newsViewHolder.tv_noidea.setText("(" + newsList.get(pos).getAlready_answer_num() == null ? "0" : newsList.get(pos).getAlready_answer_num() + "/" + newsList.get(pos).getAnswer_num() + ")");
                newsViewHolder.tv_like.setText("0");
                newsViewHolder.tv_talk.setText("0");
                newsViewHolder.tv_tags.setText(newsList.get(pos).getInformation_class_name());
                newsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (onNewsItemClickListener != null) {
                          mContext.startActivity(new Intent(mContext, NewsDetailActivity.class).putExtra("data",newsList.get(pos)));
//                        }
                    }
                });
            }
        }
        if (type == hotsIndex) {
            HotsViewHolder hotsViewHolder = (HotsViewHolder) holder;
            if (goodsList.size() != 0) {
                final int pos = position - sortList.size() - newsList.size() - 3;

                Log.i("","===================="+position+"=="+sortList.size()+"==="+newsList.size());

                hotsViewHolder.tv_description.setText(goodsList.get(pos).getGoods_name());
                hotsViewHolder.tv_price.setText(goodsList.get(pos).getGoods_price());
                hotsViewHolder.tv_sales.setText("0");
                if ("0".equals(goodsList.get(pos).getIs_deduct_integral()))
                    hotsViewHolder.tv_tags.setVisibility(View.GONE);
                //.placeholder(R.drawable.default_face)
//                Glide.with(mContext).load(goodsList.get(pos).getGoods_url()).diskCacheStrategy(DiskCacheStrategy.ALL).
//                        into(hotsViewHolder.img);
                ImageLoader.load(mContext, Host.hostUrl + goodsList.get(pos).getGoods_img(), hotsViewHolder.img);
                hotsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onHotsItemClickListener != null) {
                            onHotsItemClickListener.onClick(v, pos);
                        }
                    }
                });
            }
        }
        if (type == titleIndex) {
            try {
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                if (position == sortList.size() + 1) {
                    titleViewHolder.title.setText("看资讯的积分");
                }
                if (position == sortList.size() + newsList.size() + 2) {
                    titleViewHolder.title.setText("热门商品");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("", "绑定标题null指针");
            }
        }

    }

    OnSortMenuClickListener onSortMenuClickListener;
    OnNewsItemClickListener onNewsItemClickListener;
    OnHotsItemClickListener onHotsItemClickListener;

    public void setOnSortMenuClickListener(OnSortMenuClickListener onSortMenuClickListener) {
        this.onSortMenuClickListener = onSortMenuClickListener;
    }

    public void setOnNewsItemClickListener(OnNewsItemClickListener onNewsItemClickListener) {
        this.onNewsItemClickListener = onNewsItemClickListener;
    }

    public void setOnHotsItemClickListener(OnHotsItemClickListener onHotsItemClickListener) {
        this.onHotsItemClickListener = onHotsItemClickListener;
    }

    public interface OnSortMenuClickListener {
        void onClick(View itemView, int position);

        void onLongClick(View itemView, int position);
    }

    public interface OnNewsItemClickListener {
        void onClick(View itemView, int position);

        void onLongClick(View itemView, int position);
    }

    public interface OnHotsItemClickListener {
        void onClick(View itemView, int position);

        void onLongClick(View itemView, int position);
    }

    @Override
    public int getItemViewType(int position) {

//        Logger.d(sortList.size() + "=1");
//        Logger.d(newsList.size() + "=2");

        if (position >= 0 && position < sortList.size()) {
            return sortMenuIndex;
        }else
        if ((sortList.size() == 0&&position==0)||(sortList.size() != 0 && position == sortList.size())) {
            return bulletinIndex;
        }else
        if (newsList.size() != 0 && position >= sortList.size() + 2 && position < sortList.size() + newsList.size() + 2) {
            return newsIndex;
        }
//
        if (position == sortList.size() + 1 || position == sortList.size() + newsList.size() + 2)
            return titleIndex;

            return hotsIndex;

    }

    @Override
    public int getItemCount() {
        int count =3;
        if (sortList != null) {
            count += sortList.size();
        }
        if (newsList != null) {
            count += newsList.size();
        }
        if (goodsList != null) {
            count += goodsList.size();
        }
        return count;
    }

    public class BulletViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView title;
        private TextView context;

        public BulletViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            context = (TextView) itemView.findViewById(R.id.context);
        }
    }

    public class SortMenuViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;

        public SortMenuViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public class NewsViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView title;
        private TextView time;
        private TextView tv_integral;
        private TextView tv_noidea;
        private TextView tv_tags;
        private TextView tv_talk;
        private TextView tv_like;
        private ImageView img;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
            tv_integral = (TextView) itemView.findViewById(R.id.tv_integral);
            tv_noidea = (TextView) itemView.findViewById(R.id.tv_noidea);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            tv_talk = (TextView) itemView.findViewById(R.id.tv_talk);
            tv_like = (TextView) itemView.findViewById(R.id.tv_like);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public class HotsViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView tv_description;
        private TextView tv_sales;
        private TextView tv_price;
        private TextView tv_tags;
        private ImageView img;

        public HotsViewHolder(View itemView) {
            super(itemView);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_sales = (TextView) itemView.findViewById(R.id.tv_sales);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public class TitleViewHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView title;
        private TextView action;

        public TitleViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            action = (TextView) itemView.findViewById(R.id.action);
        }
    }
}
