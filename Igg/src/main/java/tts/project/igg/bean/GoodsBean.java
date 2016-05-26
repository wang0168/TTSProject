package tts.project.igg.bean;

import java.util.List;

/**
 * Created by lenove on 2016/5/3.
 */
public class GoodsBean {

    /**
     * goods_id : 10
     * merchants_id : 1
     * goods_name : 旺仔饼干
     * goods_img : /images/goods/goods_meishi.png
     * goods_price : 10
     * goods_grade : 3
     * goods_state : 1
     * goods_type : 2
     * is_give_integral : 0
     * give_integral_value :
     * is_deduct_integral : 0
     * deduct_integral_value :
     * deduct_integral_price :
     * is_express : 0
     * express_value :
     * express_free_price :
     * goods_url : https://www.baidu.com/
     * hostUrl :
     * goodsBeans : []
     * parameterBeans : []
     * serviceBeans : []
     * merchantsBean : {}
     */

    private int goods_id;
    private String merchants_id;
    private String goods_name;
    private String goods_img;
    private String goods_price;
    private String goods_grade;
    private String goods_state;
    private String goods_type;
    private String goods_address;
    private String is_give_integral;
    private String give_integral_value;
    private String is_deduct_integral;
    private String deduct_integral_value;
    private String deduct_integral_price;
    private String is_express;
    private String express_value;
    private String express_free_price;
    private String goods_url;
    private String hostUrl;
    private MerchantsBean merchantsBean;
    private List<GoodsBean> goodsBeans;
    private List<?> parameterBeans;
    private List<?> serviceBeans;
    private boolean isSelect;
    private List<GoodsImgBean> goodsImgBeens;

    public String getGoods_address() {
        return goods_address;
    }

    public void setGoods_address(String goods_address) {
        this.goods_address = goods_address;
    }

    public List<GoodsImgBean> getGoodsImgBeens() {
        return goodsImgBeens;
    }

    public void setGoodsImgBeens(List<GoodsImgBean> goodsImgBeens) {
        this.goodsImgBeens = goodsImgBeens;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getMerchants_id() {
        return merchants_id;
    }

    public void setMerchants_id(String merchants_id) {
        this.merchants_id = merchants_id;
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

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_grade() {
        return goods_grade;
    }

    public void setGoods_grade(String goods_grade) {
        this.goods_grade = goods_grade;
    }

    public String getGoods_state() {
        return goods_state;
    }

    public void setGoods_state(String goods_state) {
        this.goods_state = goods_state;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
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

    public String getGoods_url() {
        return goods_url;
    }

    public void setGoods_url(String goods_url) {
        this.goods_url = goods_url;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public MerchantsBean getMerchantsBean() {
        return merchantsBean;
    }

    public void setMerchantsBean(MerchantsBean merchantsBean) {
        this.merchantsBean = merchantsBean;
    }

    public List<GoodsBean> getGoodsBeans() {
        return goodsBeans;
    }

    public void setGoodsBeans(List<GoodsBean> goodsBeans) {
        this.goodsBeans = goodsBeans;
    }

    public List<?> getParameterBeans() {
        return parameterBeans;
    }

    public void setParameterBeans(List<?> parameterBeans) {
        this.parameterBeans = parameterBeans;
    }

    public List<?> getServiceBeans() {
        return serviceBeans;
    }

    public void setServiceBeans(List<?> serviceBeans) {
        this.serviceBeans = serviceBeans;
    }

    public static class MerchantsBeanBean {
    }
}
