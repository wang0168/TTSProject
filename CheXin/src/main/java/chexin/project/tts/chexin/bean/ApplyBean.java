package chexin.project.tts.chexin.bean;

/**
 * Created by lenove on 2016/4/14.
 */
public class ApplyBean {

    /**
     * apply_id : 13
     * UserId : 101
     * relation_id : 54
     * apply_type : 2
     * create_time : 2016-04-14 10:03:21.0
     * state : 0
     * goodsBean : {}
     * carBean : {}
     * status :
     */

    private int apply_id;
    private String UserId;
    private String relation_id;
    private String apply_type;
    private String create_time;
    private String state;
    private String status;
    private GoodsBean goodsBean;
    private CarBean carBean;
    private MemberBean memberBean;

    public MemberBean getMemberBean() {
        return memberBean;
    }

    public void setMemberBean(MemberBean memberBean) {
        this.memberBean = memberBean;
    }

    public GoodsBean getGoodsBean() {
        return goodsBean;
    }

    public void setGoodsBean(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    public CarBean getCarBean() {
        return carBean;
    }

    public void setCarBean(CarBean carBean) {
        this.carBean = carBean;
    }

    public int getApply_id() {
        return apply_id;
    }

    public void setApply_id(int apply_id) {
        this.apply_id = apply_id;
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

    public String getApply_type() {
        return apply_type;
    }

    public void setApply_type(String apply_type) {
        this.apply_type = apply_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
