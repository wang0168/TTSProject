package tts.project.igg.bean;

import java.io.Serializable;

/**
 * Created by lenove on 2016/5/11.
 */
public class AddressBean implements Serializable {
    /**
     * address_id : 6
     * member_id : 5
     * mobile : 15026592839
     * name : 沈佳波
     * province : 上海
     * city : 上海
     * country : 浦东
     * create_time : 2016-04-17 17:40:34.0
     * detailed_address : 外高桥保税区南
     * is_default : 1
     * address_state : 1
     * zip_code:邮编
     */

    private int address_id;
    private String member_id;
    private String mobile;
    private String name;
    private String province;
    private String city;
    private String country;
    private String create_time;
    private String detailed_address;
    private String is_default;
    private String address_state;
    private String zip_code;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }
}
