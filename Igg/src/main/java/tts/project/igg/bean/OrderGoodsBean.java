package tts.project.igg.bean;

import java.util.List;

/**
 * Created by lenove on 2016/5/18.
 */
public class OrderGoodsBean {
    /**
     * order_goods_id : 22
     * order_id : 20
     * goods_id : 12
     * goods_num : 2
     * goods_price : 34
     * is_deduct_integral : 0
     * deduct_integral_value : 12
     * deduct_integral_price : 12
     * is_give_integral : 1
     * give_integral_value :
     * is_express : 1
     * express_value :
     * express_free_price :
     * goods_name : 豆沙面包
     * goods_img : /images/goods/goods_meishi.png
     * merchants_id : 1
     * goods_url : https://www.baidu.com/
     * goods_address :
     * assessment_state : 0
     * orderParameterBeans : [{"order_parameter_id":29,"order_id":"20","goods_id":"12","parameter_id":"2","parameter_price":"0"},{"order_parameter_id":30,"order_id":"20","goods_id":"12","parameter_id":"5","parameter_price":"0"}]
     * orderServiceBeans : [{"order_service_id":19,"order_id":"20","goods_id":"12","service_id":"4","service_price":"0"},{"order_service_id":20,"order_id":"20","goods_id":"12","service_id":"5","service_price":"0"}]
     */

    private int order_goods_id;
    private String order_id;
    private String goods_id;
    private String goods_num;
    private String goods_price;
    private String is_deduct_integral;
    private String deduct_integral_value;
    private String deduct_integral_price;
    private String is_give_integral;
    private String give_integral_value;
    private String is_express;
    private String express_value;
    private String express_free_price;
    private String goods_name;
    private String goods_img;
    private String merchants_id;
    private String goods_url;
    private String goods_address;
    private String assessment_state;
    private List<OrderParameterBean> orderParameterBeens;
    private List<OrderServiceBean> orderServiceBeens;

    public List<OrderParameterBean> getOrderParameterBeens() {
        return orderParameterBeens;
    }

    public void setOrderParameterBeens(List<OrderParameterBean> orderParameterBeens) {
        this.orderParameterBeens = orderParameterBeens;
    }

    public List<OrderServiceBean> getOrderServiceBeens() {
        return orderServiceBeens;
    }

    public void setOrderServiceBeens(List<OrderServiceBean> orderServiceBeens) {
        this.orderServiceBeens = orderServiceBeens;
    }

    public int getOrder_goods_id() {
        return order_goods_id;
    }

    public void setOrder_goods_id(int order_goods_id) {
        this.order_goods_id = order_goods_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getIs_deduct_integral() {
        return is_deduct_integral;
    }

    public void setIs_deduct_integral(String is_deduct_integral) {
        this.is_deduct_integral = is_deduct_integral;
    }

    public String getDeduct_integral_value() {
        return deduct_integral_value;
    }

    public void setDeduct_integral_value(String deduct_integral_value) {
        this.deduct_integral_value = deduct_integral_value;
    }

    public String getDeduct_integral_price() {
        return deduct_integral_price;
    }

    public void setDeduct_integral_price(String deduct_integral_price) {
        this.deduct_integral_price = deduct_integral_price;
    }

    public String getIs_give_integral() {
        return is_give_integral;
    }

    public void setIs_give_integral(String is_give_integral) {
        this.is_give_integral = is_give_integral;
    }

    public String getGive_integral_value() {
        return give_integral_value;
    }

    public void setGive_integral_value(String give_integral_value) {
        this.give_integral_value = give_integral_value;
    }

    public String getIs_express() {
        return is_express;
    }

    public void setIs_express(String is_express) {
        this.is_express = is_express;
    }

    public String getExpress_value() {
        return express_value;
    }

    public void setExpress_value(String express_value) {
        this.express_value = express_value;
    }

    public String getExpress_free_price() {
        return express_free_price;
    }

    public void setExpress_free_price(String express_free_price) {
        this.express_free_price = express_free_price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getMerchants_id() {
        return merchants_id;
    }

    public void setMerchants_id(String merchants_id) {
        this.merchants_id = merchants_id;
    }

    public String getGoods_url() {
        return goods_url;
    }

    public void setGoods_url(String goods_url) {
        this.goods_url = goods_url;
    }

    public String getGoods_address() {
        return goods_address;
    }

    public void setGoods_address(String goods_address) {
        this.goods_address = goods_address;
    }

    public String getAssessment_state() {
        return assessment_state;
    }

    public void setAssessment_state(String assessment_state) {
        this.assessment_state = assessment_state;
    }
}
