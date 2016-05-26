package tts.project.igg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import tts.moudle.api.TTSBaseAdapterRecyclerView;
import tts.project.igg.R;
import tts.project.igg.bean.QuestionAnswerBean;
import tts.project.igg.bean.QuestionBean;

/**
 * Created by lenove on 2016/5/9.
 */
public class AnswerAdapter extends TTSBaseAdapterRecyclerView<QuestionBean> {
    private Context mContext;
    private List<QuestionBean> mData;
    private int selectID;

    public void setSelectID(int selectID) {
        this.selectID = selectID;
    }

    public AnswerAdapter(Context context, List<QuestionBean> data) {
        super(context, data);
        mContext = context;
        mData = data;
    }


    @Override
    public TTSBaseAdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AnswerItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_answer_question, null, false));
    }


    @Override
    public void onBindViewHolder(TTSBaseAdapterRecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        AnswerItemHolder answerItemHolder = (AnswerItemHolder) holder;
        answerItemHolder.title.setText(mData.get(position).getQuestions_id() + "." + mData.get(position).getQuestions_title());

        if (mData.get(position).getQuestionsAnswerBeans() != null) {
            List<QuestionAnswerBean> optionlist = mData.get(position).getQuestionsAnswerBeans();
            for (int i = 0; i < optionlist.size(); i++) {
                RadioButton radioButton = new RadioButton(mContext);
//                if (i == 0) {
//                    radioButton.setChecked(true);
//                }
                radioButton.setText(optionlist.get(i).getAnswer_title());
                answerItemHolder.options.addView(radioButton);
            }
        }

    }

    public class AnswerItemHolder extends TTSBaseAdapterRecyclerView.ViewHolder {
        private TextView title;
        private RadioGroup options;


        public AnswerItemHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            options = (RadioGroup) itemView.findViewById(R.id.options);
        }
    }
}
