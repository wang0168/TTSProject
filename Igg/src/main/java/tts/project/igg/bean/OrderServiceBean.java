package tts.project.igg.bean;

/**
 * Created by lenove on 2016/5/18.
 */
public class OrderServiceBean {

    /**
     * order_service_id : 19
     * order_id : 20
     * goods_id : 12
     * service_id : 4
     * service_price : 0
     */

    private int order_service_id;
    private String order_id;
    private String goods_id;
    private String service_id;
    private String service_price;

    public int getOrder_service_id() {
        return order_service_id;
    }

    public void setOrder_service_id(int order_service_id) {
        this.order_service_id = order_service_id;
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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_price() {
        return service_price;
    }

    public void setService_price(String service_price) {
        this.service_price = service_price;
    }
}
