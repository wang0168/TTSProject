package tts.project.handi.bean;

/**
 * Created by lenove on 2016/3/24.
 */
public class AreaBean {

    private String area;
    private String city;
    private String province;

    public String getCity() {
        return city;
    }

    public AreaBean setCity(String city) {
        this.city = city;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public AreaBean setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getArea() {
        return area == null ? "选择城市" : area;
    }

    public AreaBean setArea(String area) {
        this.area = area;
        return this;
    }
}
