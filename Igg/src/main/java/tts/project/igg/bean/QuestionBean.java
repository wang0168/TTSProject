package tts.project.igg.bean;

import java.util.List;

/**
 * Created by lenove on 2016/5/11.
 */
public class QuestionBean {

    /**
     * questions_id : 1
     * information_id : 1
     * questions_type : 1
     * question_title : 回锅肉好吃嘛
     * question_desc :
     * create_time : 2016-04-21 11:49:17.0
     * questionsAnswerBeans : [{"answer_id":1,"questions_id":"1","answer_title":"不好吃","is_correct":"0"},{"answer_id":2,"questions_id":"1","answer_title":"好吃","is_correct":"1"}]
     */

    private int questions_id;
    private String information_id;
    private String questions_type;
    private String questions_title;
    private String question_desc;
    private String create_time;
    private List<QuestionAnswerBean> questionsAnswerBeans;

    public int getQuestions_id() {
        return questions_id;
    }

    public void setQuestions_id(int questions_id) {
        this.questions_id = questions_id;
    }

    public String getInformation_id() {
        return information_id;
    }

    public void setInformation_id(String information_id) {
        this.information_id = information_id;
    }

    public String getQuestions_type() {
        return questions_type;
    }

    public void setQuestions_type(String questions_type) {
        this.questions_type = questions_type;
    }

    public String getQuestions_title() {
        return questions_title;
    }

    public void setQuestions_title(String question_title) {
        this.questions_title = question_title;
    }

    public String getQuestion_desc() {
        return question_desc;
    }

    public void setQuestion_desc(String question_desc) {
        this.question_desc = question_desc;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<QuestionAnswerBean> getQuestionsAnswerBeans() {
        return questionsAnswerBeans;
    }

    public void setQuestionsAnswerBeans(List<QuestionAnswerBean> questionsAnswerBeans) {
        this.questionsAnswerBeans = questionsAnswerBeans;
    }
}
