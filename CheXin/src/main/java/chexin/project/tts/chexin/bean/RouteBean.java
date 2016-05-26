package chexin.project.tts.chexin.bean;

import java.io.Serializable;

/**
 * Created by sjb on 2016/3/28.
 *  路线相关数据模型
 */
public class RouteBean implements Serializable{

    /**
     * route_id : 16
     * UserId : 101
     * FromProvince : 天津市
     * FromCity :
     * FromCountry :
     * ToProvince : 河北省
     * ToCity :
     * ToCountry :
     * company_name :
     * company_address :
     * fixed_telephone1 :
     * fixed_telephone2 :
     * city_name :
     * company_logo :
     * company_img1 :
     * company_img2 :
     * company_img3 :
     * company_img4 :
     * route_type : 1
     * create_time : 2016-04-17 18:45:39.0
     * route_state : 1
     * is_listen : 0
     * is_check : 0
     */

    private int route_id;
    private String UserId;
    private String FromProvince;
    private String FromCity;
    private String FromCountry;
    private String ToProvince;
    private String ToCity;
    private String ToCountry;
    private String company_name;
    private String company_address;
    private String fixed_telephone1;
    private String fixed_telephone2;
    private String city_name;
    private String company_logo;
    private String company_img1;
    private String company_img2;
    private String company_img3;
    private String company_img4;
    private String route_type;
    private String create_time;
    private String route_state;
    private String is_listen;
    private String is_check;
    private MemberBean memberBean;

    public MemberBean getMemberBean() {
        return memberBean;
    }

    public void setMemberBean(MemberBean memberBean) {
        this.memberBean = memberBean;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getFromProvince() {
        return FromProvince;
    }

    public void setFromProvince(String FromProvince) {
        this.FromProvince = FromProvince;
    }

    public String getFromCity() {
        return FromCity;
    }

    public void setFromCity(String FromCity) {
        this.FromCity = FromCity;
    }

    public String getFromCountry() {
        return FromCountry;
    }

    public void setFromCountry(String FromCountry) {
        this.FromCountry = FromCountry;
    }

    public String getToProvince() {
        return ToProvince;
    }

    public void setToProvince(String ToProvince) {
        this.ToProvince = ToProvince;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String ToCity) {
        this.ToCity = ToCity;
    }

    public String getToCountry() {
        return ToCountry;
    }

    public void setToCountry(String ToCountry) {
        this.ToCountry = ToCountry;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getFixed_telephone1() {
        return fixed_telephone1;
    }

    public void setFixed_telephone1(String fixed_telephone1) {
        this.fixed_telephone1 = fixed_telephone1;
    }

    public String getFixed_telephone2() {
        return fixed_telephone2;
    }

    public void setFixed_telephone2(String fixed_telephone2) {
        this.fixed_telephone2 = fixed_telephone2;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCompany_logo() {
        return company_logo;
    }

    public void setCompany_logo(String company_logo) {
        this.company_logo = company_logo;
    }

    public String getCompany_img1() {
        return company_img1;
    }

    public void setCompany_img1(String company_img1) {
        this.company_img1 = company_img1;
    }

    public String getCompany_img2() {
        return company_img2;
    }

    public void setCompany_img2(String company_img2) {
        this.company_img2 = company_img2;
    }

    public String getCompany_img3() {
        return company_img3;
    }

    public void setCompany_img3(String company_img3) {
        this.company_img3 = company_img3;
    }

    public String getCompany_img4() {
        return company_img4;
    }

    public void setCompany_img4(String company_img4) {
        this.company_img4 = company_img4;
    }

    public String getRoute_type() {
        return route_type;
    }

    public void setRoute_type(String route_type) {
        this.route_type = route_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getRoute_state() {
        return route_state;
    }

    public void setRoute_state(String route_state) {
        this.route_state = route_state;
    }

    public String getIs_listen() {
        return is_listen;
    }

    public void setIs_listen(String is_listen) {
        this.is_listen = is_listen;
    }

    public String getIs_check() {
        return is_check;
    }

    public void setIs_check(String is_check) {
        this.is_check = is_check;
    }
}
