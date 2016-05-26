package tts.moudle.api.bean;

/**
 * Created by sjb on 2016/2/26.
 */
public class BannerBean {

    /**
     * banner_id : 1
     * img : http://img.ivsky.com/img/tupian/co/201511/13/maomi.jpg
     * url :
     * type : 0
     * remark :
     * create_time : 0
     */

    private String banner_id;
    private String img;
    private String url;
    private String type;
    private String remark;
    private String create_time;

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public String getImg() {
        return img;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getRemark() {
        return remark;
    }

    public String getCreate_time() {
        return create_time;
    }
}
