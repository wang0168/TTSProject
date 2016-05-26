package tts.project.igg.bean;

import java.util.List;

/**
 * Created by lenove on 2016/5/18.
 */
public class OrderBean {

    /**
     * order_id : 20
     * member_id : 5
     * address_id : 6
     * merchants_id : 1
     * merchants_name : 绿林旗舰店
     * merchants_img : http://wl.tstmobile.com/ShoppingMall//images/merchants/1461754228327.jpg
     * remark : 别邮寄坏了啊
     * mobile : 15026592839
     * province : 上海
     * city : 上海
     * country : 浦东
     * detailed_address : 外高桥保税区南
     * zip_code :
     * order_state : wait_send
     * create_time : 2016-05-12 11:56:34.0
     * is_delete : 0
     * invoice_rise : 上海整案
     * order_no : 20160512115634
     * assessment_state : 0
     * orderGoodsBeans : [{"order_goods_id":22,"order_id":"20","goods_id":"12","goods_num":"2","goods_price":"34","is_deduct_integral":"0","deduct_integral_value":"12","deduct_integral_price":"12","is_give_integral":"1","give_integral_value":"","is_express":"1","express_value":"","express_free_price":"","goods_name":"豆沙面包","goods_img":"/images/goods/goods_meishi.png","merchants_id":"1","goods_url":"https://www.baidu.com/","goods_address":"","assessment_state":"0","orderParameterBeans":[{"order_parameter_id":29,"order_id":"20","goods_id":"12","parameter_id":"2","parameter_price":"0"},{"order_parameter_id":30,"order_id":"20","goods_id":"12","parameter_id":"5","parameter_price":"0"}],"orderServiceBeans":[{"order_service_id":19,"order_id":"20","goods_id":"12","service_id":"4","service_price":"0"},{"order_service_id":20,"order_id":"20","goods_id":"12","service_id":"5","service_price":"0"}]},{"order_goods_id":23,"order_id":"20","goods_id":"13","goods_num":"3","goods_price":"45","is_deduct_integral":"0","deduct_integral_value":"","deduct_integral_price":"","is_give_integral":"0","give_integral_value":"","is_express":"0","express_value":"","express_free_price":"","goods_name":"火腿面包","goods_img":"/images/goods/goods_meishi.png","merchants_id":"1","goods_url":"https://www.baidu.com/","goods_address":"","assessment_state":"0","orderParameterBeans":[{"order_parameter_id":31,"order_id":"20","goods_id":"13","parameter_id":"8","parameter_price":"0"}],"orderServiceBeans":[]}]
     */

    private int order_id;
    private String member_id;
    private String address_id;
    private String merchants_id;
    private String merchants_name;
    private String merchants_img;
    private String remark;
    private String mobile;
    private String province;
    private String city;
    private String country;
    private String detailed_address;
    private String zip_code;
    private String order_state;
    private String create_time;
    private String is_delete;
    private String invoice_rise;
    private String order_no;
    private String assessment_state;
    private List<OrderGoodsBean> orderGoodsBeans;

    public List<OrderGoodsBean> getOrderGoodsBeens() {
        return orderGoodsBeans;
    }

    public void setOrderGoodsBeens(List<OrderGoodsBean> orderGoodsBeens) {
        this.orderGoodsBeans = orderGoodsBeens;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getMerchants_id() {
        return merchants_id;
    }

    public void setMerchants_id(String merchants_id) {
        this.merchants_id = merchants_id;
    }

    public String getMerchants_name() {
        return merchants_name;
    }

    public void setMerchants_name(String merchants_name) {
        this.merchants_name = merchants_name;
    }

    public String getMerchants_img() {
        return merchants_img;
    }

    public void setMerchants_img(String merchants_img) {
        this.merchants_img = merchants_img;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getInvoice_rise() {
        return invoice_rise;
    }

    public void setInvoice_rise(String invoice_rise) {
        this.invoice_rise = invoice_rise;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAssessment_state() {
        return assessment_state;
    }

    public void setAssessment_state(String assessment_state) {
        this.assessment_state = assessment_state;
    }
}
