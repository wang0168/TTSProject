package chexin.project.tts.chexin.bean;

/**
 * Created by sjb on 2016/3/28.
 */
public class AssessmentBean {

    /**
     * assessment_id : 1
     * UserId : 1
     * relation_id : 1
     * assessment_type : 2
     * remark : 很好啊
     * timely_star : 3
     * service_star : 4
     * pay_timely_star : 5
     * create_time : 2016-04-06 19:20:38.0
     */

    private int assessment_id;
    private String UserId;
    private String relation_id;
    private String assessment_type;
    private String remark;
    private String timely_star;
    private String service_star;
    private String pay_timely_star;
    private String create_time;

    public int getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public String getAssessment_type() {
        return assessment_type;
    }

    public void setAssessment_type(String assessment_type) {
        this.assessment_type = assessment_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTimely_star() {
        return timely_star;
    }

    public void setTimely_star(String timely_star) {
        this.timely_star = timely_star;
    }

    public String getService_star() {
        return service_star;
    }

    public void setService_star(String service_star) {
        this.service_star = service_star;
    }

    public String getPay_timely_star() {
        return pay_timely_star;
    }

    public void setPay_timely_star(String pay_timely_star) {
        this.pay_timely_star = pay_timely_star;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
