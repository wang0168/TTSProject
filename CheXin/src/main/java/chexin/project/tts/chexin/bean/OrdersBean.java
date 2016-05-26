package chexin.project.tts.chexin.bean;

/**
 * Created by lenove on 2016/4/18.
 */
public class OrdersBean {

    /**
     * order_id : 1
     * order_type : 1
     * order_state : 0
     * relation_id : 44
     * create_time : 2016-04-05 11:48:45.0
     * UserId : 7
     * UserId2 : 19
     * memberBean : {"ID":"19","CardID":"2","Deposit":"22","Balance":"22","Token":"12345","CreateDate":"2016-04-05 10:59:40.23","ModifyDate":"2016-04-05 10:59:40.23"}
     * goodsBean : {"ID":44,"UserId":"7","FromProvince":"2","FromCity":"3","FromCountry":"4","ToProvince":"5","ToCity":"6","ToCountry":"7","SKUType":"8","SKUWeight":"9","SKUName":"12","SKULong":"10","SKUWidth":"11","Address":"14","MobileNo":"13","ExpiryDate":"2016-04-06 03:07:40.0","Status":"1","Note":"不是吧","AddDate":"2016-04-05 11:07:40.0"}
     */

    private int order_id;
    private String order_type;
    private String order_state;
    private String relation_id;
    private String create_time;
    private String UserId;
    private String UserId2;
    private MemberBean memberBean;
    private GoodsBean goodsBean;

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

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserId2() {
        return UserId2;
    }

    public void setUserId2(String UserId2) {
        this.UserId2 = UserId2;
    }
}
