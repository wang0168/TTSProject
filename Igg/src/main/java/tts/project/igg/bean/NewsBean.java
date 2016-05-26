package tts.project.igg.bean;

import java.io.Serializable;

/**
 * Created by lenove on 2016/5/3.
 */
public class NewsBean implements Serializable {

    /**
     * information_id : 1
     * information_title : 舌尖上的中国
     * information_desc : 回锅肉:就是煮过2次的肉
     * information_img : http://wl.tstmobile.com/ShoppingMall//images/information/1461145198770.jpg
     * information_url :
     * information_integral : 80
     * information_type : 1
     * create_time : 2016-04-20 18:07:27.0
     * parent_id : 1
     * information_class_name :
     * is_delete : 0
     * is_recommend : 1
     * answer_num : 2
     * already_answer_num : 0
     * hostUrl :
     */

    private int information_id;
    private String information_title;
    private String information_desc;
    private String information_img;
    private String information_url;
    private String information_integral;
    private String information_type;
    private String create_time;
    private String parent_id;
    private String information_class_name;
    private String is_delete;
    private String is_recommend;
    private String answer_num;
    private String already_answer_num;
    private String hostUrl;

    public int getInformation_id() {
        return information_id;
    }

    public void setInformation_id(int information_id) {
        this.information_id = information_id;
    }

    public String getInformation_title() {
        return information_title;
    }

    public void setInformation_title(String information_title) {
        this.information_title = information_title;
    }

    public String getInformation_desc() {
        return information_desc;
    }

    public void setInformation_desc(String information_desc) {
        this.information_desc = information_desc;
    }

    public String getInformation_img() {
        return information_img;
    }

    public void setInformation_img(String information_img) {
        this.information_img = information_img;
    }

    public String getInformation_url() {
        return information_url;
    }

    public void setInformation_url(String information_url) {
        this.information_url = information_url;
    }

    public String getInformation_integral() {
        return information_integral;
    }

    public void setInformation_integral(String information_integral) {
        this.information_integral = information_integral;
    }

    public String getInformation_type() {
        return information_type;
    }

    public void setInformation_type(String information_type) {
        this.information_type = information_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getInformation_class_name() {
        return information_class_name;
    }

    public void setInformation_class_name(String information_class_name) {
        this.information_class_name = information_class_name;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getAnswer_num() {
        return answer_num;
    }

    public void setAnswer_num(String answer_num) {
        this.answer_num = answer_num;
    }

    public String getAlready_answer_num() {
        return already_answer_num;
    }

    public void setAlready_answer_num(String already_answer_num) {
        this.already_answer_num = already_answer_num;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
}
