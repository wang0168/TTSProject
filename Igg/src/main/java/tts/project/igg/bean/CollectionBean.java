package tts.project.igg.bean;

/**
 * Created by lenove on 2016/5/16.
 */
public class CollectionBean {

    /**
     * collection_id : 2
     * member_id : 5
     * relation_id : 1
     * collection_type : 1
     * is_delete : 0
     * create_time : 2016-05-10 17:48:51.0
     * merchantsBean : {"merchants_id":0,"merchants_id1":"","merchants_name":"绿林旗舰店","merchants_account":"","password":"","merchants_img":"http://wl.tstmobile.com/ShoppingMall//images/merchants/1461754228327.jpg","merchants_star1":"3.4","merchants_star2":"2.3","merchants_star3":"4.2","hostUrl":"","merchants_token":"","member_id":"","is_collection":"0"}
     */

    private int collection_id;
    private String member_id;
    private String relation_id;
    private String collection_type;
    private String is_delete;
    private String create_time;
    private MerchantsBean merchantsBean;
    private GoodsBean goodsBean;

    public GoodsBean getGoodsBean() {
        return goodsBean;
    }

    public void setGoodsBean(GoodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    public MerchantsBean getMerchantsBean() {
        return merchantsBean;
    }

    public void setMerchantsBean(MerchantsBean merchantsBean) {
        this.merchantsBean = merchantsBean;
    }

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getRelation_id() {
        return relation_id;
    }

    public void setRelation_id(String relation_id) {
        this.relation_id = relation_id;
    }

    public String getCollection_type() {
        return collection_type;
    }

    public void setCollection_type(String collection_type) {
        this.collection_type = collection_type;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
