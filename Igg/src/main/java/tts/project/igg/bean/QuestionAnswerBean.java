package tts.project.igg.bean;

/**
 * Created by lenove on 2016/5/11.
 */
public class QuestionAnswerBean {

    /**
     * answer_id : 1
     * questions_id : 1
     * answer_title : 不好吃
     * is_correct : 0
     */

    private int answer_id;
    private String questions_id;
    private String answer_title;
    private String is_correct;

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public String getQuestions_id() {
        return questions_id;
    }

    public void setQuestions_id(String questions_id) {
        this.questions_id = questions_id;
    }

    public String getAnswer_title() {
        return answer_title;
    }

    public void setAnswer_title(String answer_title) {
        this.answer_title = answer_title;
    }

    public String getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(String is_correct) {
        this.is_correct = is_correct;
    }
}
