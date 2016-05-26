package tts.project.handi.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import tts.moudle.api.Host;
import tts.moudle.api.bean.BarBean;
import tts.moudle.api.utils.TextUtils;
import tts.moudle.api.widget.RecyclerViewAutoRefresh;
import tts.project.handi.BaseActivity;
import tts.project.handi.R;
import tts.project.handi.adapter.CommentAdapter;
import tts.project.handi.bean.CommentBean;
import tts.project.handi.bean.CommentContext;
import tts.project.handi.common.DividerItemDecoration;
import tts.project.handi.utils.MyAccountMoudle;

/**
 * 我的评论
 */
public class MyCommentActivity extends BaseActivity {
    @Bind(R.id.tv_very_satisfaction_num)
    TextView tvVerySatisfactionNum;
    @Bind(R.id.tv_satisfaction_num)
    TextView tvSatisfactionNum;
    @Bind(R.id.tv_dissatisfaction_num)
    TextView tvDissatisfactionNum;
    @Bind(R.id.recycleview_comment)
    RecyclerViewAutoRefresh recycleviewComment;
    private CommentBean commentBean;
    private List<CommentContext> contexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        ButterKnife.bind(this);

        setTitle(new BarBean().setMsg("我的评价"));
        startRequestData(getData);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleviewComment.setLayoutManager(manager);
        recycleviewComment.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

    }

    @Override
    protected void startRequestData(int index) {
        super.startRequestData(index);
        Map<String, String> params;
        switch (index) {
            case getData:
                params = new ArrayMap<>();
                params.put("member_id", MyAccountMoudle.getInstance().getUserInfo().getMember_id());
                params.put("token", MyAccountMoudle.getInstance().getUserInfo().getUser_token());
                params.put("pagenow", "1");
                getDataWithPost(getData, Host.hostUrl + "api.php?m=Api&c=Personal&a=PerEvaluate", params);
                break;
        }

    }

    @Override
    protected void doSuccess(int index, String response) {
        super.doSuccess(index, response);
        if (!TextUtils.isEmpty(response)) {
            Logger.w("1");
            commentBean = JSON.parseObject(response, CommentBean.class);
            bindData(commentBean);
            CommentAdapter adapter=new CommentAdapter(this, commentBean.getCountext());
            recycleviewComment.setAdapter(adapter);
        }

    }

    private void bindData(CommentBean commentBean) {
        Logger.w(commentBean.getOne()+"");
        tvVerySatisfactionNum.setText(commentBean.getOne()+"");
        tvSatisfactionNum.setText(commentBean.getTwo()+"");
        tvDissatisfactionNum.setText(commentBean.getThree()+"");

    }
}
