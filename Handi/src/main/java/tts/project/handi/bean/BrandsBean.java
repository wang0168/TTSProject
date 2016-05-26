package tts.project.handi.bean;

import java.util.List;

/**
 * Created by lenove on 2016/4/1.
 */
public class BrandsBean {

    /**
     * pink_id : 1
     * province : 上海
     * city : 上海
     * area : 杨浦区
     * pinkname : 品牌1
     * meb : [{"nickname":"带点东西快的哦,","img":"http://handi.tstmobile.com/img/20160318/56ebfc78d4b07.jpg,"},{"nickname":"美丽动人的话1,","img":"http://handi.tstmobile.com/img/20160323/56f1fa68e0b7a.jpg,"},{"nickname":"小宋,","img":"http://handi.tstmobile.com/img/20160328/56f8a54ad5955.jpg,"},{"nickname":"13512213,","img":"http://handi.tstmobile.com/img/20160323/56f20cab67ece.jpg,"},{"nickname":"Cheil,","img":"http://static.quanjing.com/image/2015index/index7_94.jpg,"},{"nickname":"HD_15893038270,","img":"http://static.quanjing.com/image/2015index/index7_94.jpg,"},{"nickname":"HD_13512398765,","img":"http://static.quanjing.com/image/2015index/index7_94.jpg,"},{"nickname":"HD_13598765432,","img":"http://static.quanjing.com/image/2015index/index7_94.jpg,"}]
     */

    private String pink_id;
    private String province;
    private String city;
    private String area;
    private String pinkname;
    /**
     * nickname : 带点东西快的哦,
     * img : http://handi.tstmobile.com/img/20160318/56ebfc78d4b07.jpg,
     */

    private List<MyRecommend> meb;

    public String getPink_id() {
        return pink_id;
    }

    public void setPink_id(String pink_id) {
        this.pink_id = pink_id;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinkname() {
        return pinkname;
    }

    public void setPinkname(String pinkname) {
        this.pinkname = pinkname;
    }

    public List<MyRecommend> getMeb() {
        return meb;
    }

    public void setMeb(List<MyRecommend> meb) {
        this.meb = meb;
    }


}
