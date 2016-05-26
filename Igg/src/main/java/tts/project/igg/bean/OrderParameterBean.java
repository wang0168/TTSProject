package tts.project.igg.bean;

/**
 * Created by lenove on 2016/5/18.
 */
public class OrderParameterBean {
    /**
     * order_parameter_id : 29
     * order_id : 20
     * goods_id : 12
     * parameter_id : 2
     * parameter_price : 0
     */

    private int order_parameter_id;
    private String order_id;
    private String goods_id;
    private String parameter_id;
    private String parameter_price;

    public int getOrder_parameter_id() {
        return order_parameter_id;
    }

    public void setOrder_parameter_id(int order_parameter_id) {
        this.order_parameter_id = order_parameter_id;
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

    public String getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(String parameter_id) {
        this.parameter_id = parameter_id;
    }

    public String getParameter_price() {
        return parameter_price;
    }

    public void setParameter_price(String parameter_price) {
        this.parameter_price = parameter_price;
    }
}
