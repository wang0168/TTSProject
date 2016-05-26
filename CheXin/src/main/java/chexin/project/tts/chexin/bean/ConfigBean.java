package chexin.project.tts.chexin.bean;

import java.util.ArrayList;

/**
 * Created by lenove on 2016/4/7.
 */
public class ConfigBean {

    /**
     * id : 01
     * name : 高栏车
     */
    private long id;
    private String strId;
    private String name;
    private String others;
    private ArrayList<ConfigBean> cityBeans;

    public ConfigBean(long id, String strId, String name, String others) {
        this.id = id;
        this.strId = strId;
        this.name = name;
        this.others = others;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public ArrayList<ConfigBean> getCityBeans() {
        return cityBeans;
    }

    public void setCityBeans(ArrayList<ConfigBean> cityBeans) {
        this.cityBeans = cityBeans;
    }

    public ArrayList<ConfigBean> getCountryBeans() {
        return countryBeans;
    }

    public void setCountryBeans(ArrayList<ConfigBean> countryBeans) {
        this.countryBeans = countryBeans;
    }

    private ArrayList<ConfigBean> countryBeans;

    public String getId() {
        return strId;
    }

    public void setId(String id) {
        this.strId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
