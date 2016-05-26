package tts.project.igg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.bean.BarBean;
import tts.project.igg.BaseActivity;
import tts.project.igg.R;
import tts.project.igg.bean.NewsBean;
import tts.project.igg.utils.ImageLoader;

public class NewsDetailActivity extends BaseActivity implements OnClickListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_noidea)
    TextView tvNoidea;
    @Bind(R.id.tv_tags)
    TextView tvTags;
    @Bind(R.id.tv_like)
    TextView tvLike;
    @Bind(R.id.tv_talk)
    TextView tvTalk;
    @Bind(R.id.tv_context)
    TextView tvContext;
    @Bind(R.id.action)
    Button action;
    private NewsBean newsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        setTitle(new BarBean().setMsg("资讯"));
        newsBean = (NewsBean) getIntent().getSerializableExtra("data");
        if (newsBean != null) {
            bindData();
        }
        action.setOnClickListener(this);
    }

    private void bindData() {
        title.setText(newsBean.getInformation_title());
        time.setText(newsBean.getCreate_time());
        ImageLoader.load(this, newsBean.getInformation_img(), img);
        tvIntegral.setText(newsBean.getInformation_integral());
        tvNoidea.setText("(" + newsBean.
                getAlready_answer_num() == null ? "0" : newsBean.getAlready_answer_num()
                + "/" + newsBean.getAnswer_num() + ")");
        tvTags.setText(newsBean.getInformation_class_name());

        tvContext.setText(newsBean.getInformation_desc());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action:
                startActivity(new Intent(this, AnswerActivity.class).putExtra("id", newsBean.getInformation_id() + ""));
                break;
        }
    }
}
